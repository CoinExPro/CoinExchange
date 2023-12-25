/* 
  
*/
import request from '@/router/axios';
import config from "@/config/index";

export const getMqttInitDataApi=(type)=>{
  return request({
    url: `/api/${config.apiRequestHead}/coin/open/market/initData`,
    method: 'get',
    params: {
      type
    },
  })
}
export const getExchangeOptionalApi = () => {
  return request({
    url: `/api/${config.apiRequestHead}/coin/quotes/getOptional`,
    method: 'get',
    data: {},
  })
}
