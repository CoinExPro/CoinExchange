/**
 * 全站http配置
 */
import axios from 'axios';
import store from '@/store/';
import router from '@/router/index';
import { getToken } from '@/util/auth';
import { Message } from 'element-ui';
import website from '@/config/index';
import { Base64 } from 'js-base64';
import NProgress from 'nprogress';
import 'nprogress/nprogress.css';

//默认超时时间
axios.defaults.timeout = 60*5*1000;
//返回其他状态码
axios.defaults.validateStatus = function (status) {
  return status >= 200 && status <= 500;
};
//跨域请求，允许保存cookie
axios.defaults.withCredentials = true;
// NProgress 配置
NProgress.configure({
  showSpinner: false
});
//http request拦截
let throttle = false //节流限制错误提示
axios.interceptors.request.use(config => {
  /*if(process.env.NODE_ENV === 'production'){
    config.url=`https://api.coinub.com${config.url}`
  }*/
  //开启 progress bar
  NProgress.start();
  const meta = (config.meta || {});
  const isToken = meta.isToken === false;
  if (meta.clientId && meta.clientSecret) {
    config.headers['Authorization'] = `Basic ${Base64.encode(`${meta.clientId}:${meta.clientSecret}`)}`;
  } else {
    config.headers['Authorization'] = `Basic ${Base64.encode(`${website.clientId}:${website.clientSecret}`)}`;
  }
  //让每个请求携带token
  if (getToken() && !isToken) {
    config.headers[website.tokenHeader] = 'bearer ' + getToken()
  }
  // 携带当前系统语言
  config.headers[website.langHeader] = store.getters.language
  //headers中配置text请求
  if (config.text === true) {
    config.headers["Content-Type"] = "text/plain";
  }
  return config
}, error => {
  return Promise.reject(error)
});
//http response 拦截
axios.interceptors.response.use(res => {
  //关闭 progress bar
  NProgress.done();
  //获取状态码
  const status = res.data.code || res.status;
  const statusWhiteList = website.statusWhiteList || [];
  const descriptionWhiteList = website.descriptionWhiteList || []
  const message = res.data.msg || res.data.error_description || '未知错误';
  //如果在白名单里则自行catch逻辑处理
  if (statusWhiteList.includes(status)) return Promise.reject(res);
  if (descriptionWhiteList.includes(message)) return Promise.reject(res);
  //如果是401则跳转到首页页面
  if (status === 401) {
    store.dispatch('FedLogOut').then(() => router.push({ path: '/' }));
    return Promise.reject(new Error(message))
  }
  // 如果请求为非200否者默认统一处理
  if (status !== 200) {
    if (throttle) {
      return Promise.reject(new Error(message))
    }
    throttle = true
    setTimeout(() => {
      throttle = false
    }, 2000);
    Message({
      message: message,
      type: 'error'
    });
    return Promise.reject(new Error(message))
  }
  return res;
}, error => {
  NProgress.done();
  return Promise.reject(new Error(error));
});

export default axios;
