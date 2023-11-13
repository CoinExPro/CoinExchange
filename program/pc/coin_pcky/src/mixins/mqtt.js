import config from '@/config/index';
import mqtt from 'mqtt';
export default {
  data() {
    return {
      path: config.mqttMy,
      mqttArr: []
      // client: null,
    }
  },
  methods: {
    initMqtt(single, mesFun, clear) {
      if (clear) {
        this.mqttArr = this.mqttArr.filter(item => {
          if (item.key == single) {
            this.closeMqtt(item)
            return false
          }
          return true
        })
      }
      let client = mqtt.connect(config.mqttMy, {
        username: config.mqttUserName,
        password: config.mqttPassword,
        keepalive: 5,
      })
      client.key = single
      const singleTopic = single ? single : "/all/123"
      client.isConnect = false
      client.on("connect", () => {
        if (client.isConnect) {
          return false
        }
        client.isConnect = true
        client.subscribe(singleTopic, err => {
          if (!err) {
            console.log("订阅成功:" + singleTopic);
          }
        });
        client.on("message", (topic, message) => {
          try {
            message = JSON.parse(message)
          } catch (error) {
            console.log('mqtt JSON转换异常', topic, ':', message)
          }
          mesFun(message)
        });
      });
      this.mqttArr.push(client)
      return client

    },
    closeMqtt: function (client) {
      client.end();
    },
  },
  beforeDestroy() {
    this.mqttArr.forEach(item => {
      item.end();
    })
  },
}