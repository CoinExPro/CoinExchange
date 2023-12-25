/* 
  系统相关api
*/
import request from '@/router/axios';
import config from "@/config/index";

/* 
  获取字典数据
*/
export const getDicTableData = (dictCode) => {
  return request({
    url: `/api/${config.apiRequestHead}/sys/sys/dict/getDictItems/${dictCode}`,
    method: 'get',
    params: {}
  })
}


/* 
  获取表单开发列表数据
*/
export const getDataApi = (headId, params) => {
  // 排序
  if (!params.column && !params.order) {
    params.column = 'id'
    params.order = 'desc'
  }
  return request({
    url: `/api/${config.apiRequestHead}/cgform-api/getData/${headId}`,
    method: 'get',
    params
  })
}




//设置后端语言
export const setHtLangApi = (type) => {
  return request({
    url: `/api/${config.apiRequestHead}/coin/open/lang/${type}`,
    method: 'get',
    params: {},
  })
}
