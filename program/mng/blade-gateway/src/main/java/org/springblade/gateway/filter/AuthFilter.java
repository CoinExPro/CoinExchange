/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.gateway.filter;

import com.alibaba.nacos.common.utils.MapUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springblade.core.jwt.JwtUtil;
import org.springblade.core.jwt.props.JwtProperties;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.gateway.mapper.MemberMapper;
import org.springblade.gateway.props.AuthProperties;
import org.springblade.gateway.provider.AuthProvider;
import org.springblade.gateway.provider.RequestProvider;
import org.springblade.gateway.provider.ResponseProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 鉴权认证
 *
 *
 */
@Slf4j
@Component
@AllArgsConstructor
public class AuthFilter implements GlobalFilter, Ordered {
	private final AuthProperties authProperties;
	private final ObjectMapper objectMapper;
	private final JwtProperties jwtProperties;
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	private final MemberMapper memberMapper;
	public static final String SIGNATURE_JSON = "\"signature\":";
	public static final String SIGNATURE_FORM = "signature=";

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		//校验 Token 放行
		String originalRequestUrl = RequestProvider.getOriginalRequestUrl(exchange);
		String path = exchange.getRequest().getURI().getPath();
		if (isSkip(path) || isSkip(originalRequestUrl)) {
			return chain.filter(exchange);
		}

		ServerHttpResponse resp = exchange.getResponse();

		if (path.matches(AuthProvider.API_PATH_REG)) { // api请求通过apiKey鉴权
			//请求参数
			String parameters;
			String signature = exchange.getRequest().getHeaders().getFirst(AuthProvider.API_SECRET_KEY);

			if (HttpMethod.POST.equals(exchange.getRequest().getMethod())) {// body/json参数
				String param = exchange.getAttribute(AuthProvider.BODY_REQUEST_BODY_PARAMETERS);
				if (StringUtils.isBlank(param)) {
					return unAuth(resp, "DATA_NOT_SUPPORT");
				}
				int startIndex = param.indexOf(SIGNATURE_JSON);
				if (startIndex == -1) {
					startIndex = param.indexOf(SIGNATURE_FORM);
				}
				int endIndex = param.indexOf(",", startIndex);
				if (endIndex == -1) {
					endIndex = param.indexOf("&", startIndex);
				}
				String signSub = "";
				if (startIndex != -1) {
					signSub = param.substring(startIndex, endIndex == -1 ? param.length() - 1 : endIndex);
				}
				signature = Func.isNotEmpty(signature) ? signature :
					signSub.replace(SIGNATURE_JSON, "")
						.replace(SIGNATURE_FORM, "")
						.replaceAll("\"", "")
						.replaceAll("\\s*|\t|\r|\n", "")
						.replaceAll("&", "");

				parameters = param.replace(signSub, "")
					.replaceAll(",}", "}")
					.replaceAll(",(\\s*|\t|\r|\n)}", "\r\n}")
					.replaceAll("&&", "&");

			} else { // url参数
				MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
				signature = Func.isNotEmpty(signature) ? signature : queryParams.getFirst(AuthProvider.API_SECRET_KEY);
				StringBuilder params = new StringBuilder();
				queryParams.forEach((key, value) -> {
					if (key.equals(AuthProvider.API_KEY) || key.equals(AuthProvider.API_SECRET_KEY))
						return;
					params.append(key).append("=").append(value.get(0)).append("&");
				});
				if (params.length() > 3)
					params.deleteCharAt(params.length() - 1);
				parameters = params.toString();
			}

			String paramApiKey = exchange.getRequest().getQueryParams().getFirst(AuthProvider.API_KEY);
			String headerApiKey = exchange.getRequest().getHeaders().getFirst(AuthProvider.API_KEY);
			String apiKey = StringUtils.isBlank(headerApiKey) ? paramApiKey : headerApiKey;
			Map<String, Object> member = memberMapper.getMember(apiKey);
			if (MapUtil.isEmpty(member)) {
				return unAuth(resp, "API_KEY_NOT_FOUND");
			}

			String secretKey = String.valueOf(member.get("secret_key"));

			String sha256Hex = Func.hmacSha256Hex(parameters, secretKey);
			if (!sha256Hex.equalsIgnoreCase(signature)) {
				return unAuth(resp, "INVALID_SIGNATURE");
			}

			//鉴权通过 生成token方便后续获取用户信息
			String accessToken = JwtUtil.getAccessToken(Func.toStr(member.get("tenant_id")), Func.toStr(member.get("blade_user_id")), JwtUtil.getToken(Func.toStr(member.get("token"))));
			if (accessToken.equals("null")) {
				JwtUtil.addRefreshToken(Func.toStr(member.get("tenant_id")), Func.toStr(member.get("blade_user_id")), Func.toStr(member.get("token")), 500);
				accessToken = JwtUtil.getAccessToken(Func.toStr(member.get("tenant_id")), Func.toStr(member.get("blade_user_id")), JwtUtil.getToken(Func.toStr(member.get("token"))));
			}
			ServerHttpRequest request = exchange.getRequest();
			request.mutate().header(AuthProvider.AUTH_KEY, accessToken);
			return chain.filter(exchange.mutate().request(request).build());
		}

		//校验 Token 合法性
		String headerToken = exchange.getRequest().getHeaders().getFirst(AuthProvider.AUTH_KEY);
		String paramToken = exchange.getRequest().getQueryParams().getFirst(AuthProvider.AUTH_KEY);
		if (StringUtils.isBlank(headerToken) && StringUtils.isBlank(paramToken)) {
			return unAuth(resp, "缺失令牌,鉴权失败");
		}
		String auth = StringUtils.isBlank(headerToken) ? paramToken : headerToken;
		String token = JwtUtil.getToken(auth);
		Claims claims = JwtUtil.parseJWT(token);
		if (token == null || claims == null) {
			return unAuth(resp, "请求未授权");
		}
		//判断 Token 状态
		if (jwtProperties.getState()) {
			String tenantId = String.valueOf(claims.get(TokenConstant.TENANT_ID));
			String userId = String.valueOf(claims.get(TokenConstant.USER_ID));
			String accessToken = JwtUtil.getAccessToken(tenantId, userId, token);
			if (!token.equalsIgnoreCase(accessToken)) {
				return unAuth(resp, "令牌已失效");
			}
		}
		return chain.filter(exchange);
	}

	private boolean isSkip(String path) {
		return AuthProvider.getDefaultSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path))
			|| authProperties.getSkipUrl().stream().anyMatch(pattern -> antPathMatcher.match(pattern, path));
	}

	private Mono<Void> unAuth(ServerHttpResponse resp, String msg) {
		resp.setStatusCode(HttpStatus.UNAUTHORIZED);
		resp.getHeaders().add("Content-Type", "application/json;charset=UTF-8");
		String result = "";
		try {
			result = objectMapper.writeValueAsString(ResponseProvider.unAuth(msg));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
		}
		DataBuffer buffer = resp.bufferFactory().wrap(result.getBytes(StandardCharsets.UTF_8));
		return resp.writeWith(Flux.just(buffer));
	}

	@Override
	public int getOrder() {
		return -100;
	}

}
