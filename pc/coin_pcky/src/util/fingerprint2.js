import Fingerprint2 from 'fingerprintjs2';//浏览器指纹

export function getFinger () {
  return new Promise(resolve => {
    Fingerprint2.get(function (components) {
      const values = components.map(function (component, index) {
        if (index === 0) { //把微信浏览器里UA的wifi或4G等网络替换成空,不然切换网络会ID不一样
          return component.value.replace(/\bNetType\/\w+\b/, '')
        }
        return component.value
      })
      // 生成最终id murmur   
      const murmur = Fingerprint2.x64hash128(values.join(''), 31);
      resolve(murmur)
    })
  })
}