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

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springblade.gateway.provider.AuthProvider;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 鉴权认证
 *
 *
 */
@Slf4j
@Component
@AllArgsConstructor
/**
 * body只能被消费一次 获取body重写getBody方法
 */
public class BodyCacheFilter implements GlobalFilter, Ordered {
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if (exchange.getRequest().getPath().toString().matches(AuthProvider.API_PATH_REG) &&
			HttpMethod.POST.equals(exchange.getRequest().getMethod())) {
			return DataBufferUtils.join(exchange.getRequest().getBody())
				.flatMap(dataBuffer -> {
					DataBufferUtils.retain(dataBuffer);
					Flux<DataBuffer> cachedFlux = Flux.defer(() ->
						Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));
					ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
						@Override
						public Flux<DataBuffer> getBody() {
							return cachedFlux;
						}
					};
					return chain.filter(exchange.mutate().request(mutatedRequest).build());
				});
		}
		return chain.filter(exchange);
	}

	@Override
	public int getOrder() {
		return HIGHEST_PRECEDENCE;
	}

}
