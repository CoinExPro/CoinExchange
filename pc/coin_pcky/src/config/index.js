/* 
  全局配置文件
*/
export default {
  key: 'coin_web',//用于存储
  apiRequestHead: 'mjkj-web',
  langHeader: 'mj-lang',
  tokenTime: 604800, //一个星期
  cookieTime: 7,//一个星期
  tokenHeader: 'Blade-Auth',
  clientId: 'saber', // 客户端id
  clientSecret: 'saber_secret', // 客户端密钥
  mqttUserName: 'admin', // rabbitmq连接用户名
  mqttPassword: 'password', // rabbitmq连接密码
  mqttMy: process.env.NODE_ENV === 'production' ? 'mqtts://xxx/ws' : 'mqtts://xxx/ws', // mica-mqtt服务地址
  //白名单
  statusWhiteList: [],
  descriptionWhiteList: ['步骤1登录成功'],

  exchangeTime: 1000 * 60 * 5,//更新汇率时间
}
