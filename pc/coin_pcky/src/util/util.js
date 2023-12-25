
/* 
  处理成树结构数据
  树结构数据处理方法 arr:数据  parentidKeyName:父id的key名  idName:id的key名  topPidDefault最顶级pid的值
*/
export const setTreeDataUtil = (
  arr,
  parentidKeyName,
  idName = 'id',
  topPidDefault = 0,
  delHasChild) => {
  if (delHasChild) {
    arr = arr.map((item) => {
      delete item[delHasChild]
      return item
    })
  }
  let data = arr
  let menuArr = []
  let childrenObj = {}

  data.forEach((item) => {
    if (
      item[parentidKeyName] != topPidDefault &&
      item[parentidKeyName] != null &&
      item[parentidKeyName] != ''
    ) {
      if (childrenObj[item[parentidKeyName]] == undefined) {
        childrenObj[item[parentidKeyName]] = []
      }
      childrenObj[item[parentidKeyName]] = [
        ...childrenObj[item[parentidKeyName]],
        item,
      ]
    } else {
      menuArr.push(item)
    }
  })
  menuArr.map((item) => {
    childrenFun(item)
  })
  function childrenFun(obj) {
    if (childrenObj[obj[idName]] && !obj.isScope) {
      obj.children = childrenObj[obj[idName]]
      obj.children.map((item) => {
        return childrenFun(item)
      })
    } else {
      return obj
    }
  }
  return menuArr
}
export const analysisFunction = (fun) => {
  if (fun) {
    fun = fun.replace(/\/\*{1,2}[\s\S]*?\*\//gis, '')
    fun = fun.replace(/(?:^|\n|\r)\s*\/\/.*(?:\r|\n|$)/g, '')
    try {
      if (eval(`(${fun})`)) {
        return eval(`(${fun})`)
      } else {
        return () => { }
      }
    } catch {
      return false
    }
  }
}
export const getStrDataFunction = (str) => {
  return analysisFunction(
    `function getData(){${str}}`
  )();
}
//处理数字格式  如：20000  处理成 200,00
export const toThousands = (num = 0) => {
  num = num - 0
  let abs = num >= 0
  num = Math.abs(num)
  num = toNonExponential(num, 'string')
  let value = num.replace(/\d+/, function (n) {
    return n.replace(/(\d)(?=(?:\d{3})+$)/g, '$1,');
  });
  return abs ? value : `-${value}`
}
//数字保留多少位
export const toNorounded = (num, decimal = 0, bool = true) => {
  let minNum = 1
  num = num - 0
  let abs = num >= 0
  num = Math.abs(num)
  num = toNonExponential(num, 'string')
  if (bool && num != 0) {
    for (let index = 0; index < decimal; index++) {
      minNum = minNum / 10
    }
    //防止保留小数为零情况
    if (num < minNum && num !== 0) {
      let decimalText = num.split('.')[1].split('')
      let maxDecimal = 0
      decimalText.forEach((item, index) => {
        if (item > 0 && maxDecimal == 0) {
          maxDecimal = index + 1
        }
      })
      decimal = maxDecimal
    }
  }
  if (num.indexOf('.') != -1) {
    let reg = RegExp("^\\d+(?:\\.\\d{0," + decimal + "})?")
    let value = Number(num.match(reg))
    value = toNonExponential(value)
    return abs ? value : `-${value}`
  } else {
    return abs ? num : `-${num}`
  }
}
/* 
  数字格式化
  num 数字
  isTohs 是否逗号分隔
  dec 最小小数位
*/
export const numberFilterFun = (num, isTohs = true, dec = 8) => {
  num = num - 0
  let abs = num >= 0
  num = Math.abs(num)
  let decimal = 0
  if (num.toString().indexOf('.') != -1) {
    decimal = num.toString().split('.')[1].length
  }
  if (num < 1) {
    return abs ? toNorounded(num, dec) : `-${toNorounded(num, dec)}`
  } else if (num >= 1 && num < 10) {
    num = decimal > 6 ? toNorounded(num, 6) : num
  } else if (num >= 10 && num < 100) {
    num = decimal > 5 ? toNorounded(num, 5) : num
  } else if (num >= 100 && num < 1000) {
    num = decimal > 4 ? toNorounded(num, 5) : num
  } else if (num >= 1000 && num < 10000) {
    num = decimal > 3 ? isTohs ? toThousands(toNorounded(num, 3)) : toNorounded(num, 3) : isTohs ? toThousands(num) : num
  } else if (num >= 10000 && num < 100000) {
    num = decimal > 2 ? isTohs ? toThousands(toNorounded(num, 2)) : toNorounded(num, 2) : isTohs ? toThousands(num) : num
  } else if (num >= 100000 && num < 1000000) {
    num = decimal > 1 ? isTohs ? toThousands(toNorounded(num, 1)) : toNorounded(num, 1) : isTohs ? toThousands(num) : num
  } else if (num >= 1000000 && num < 1000000000) {//百万到亿
    num = toNorounded(num / 1000000, 4) + 'M'
  } else if (num >= 1000000000 && num < 1000000000000) {//十亿到千亿
    num = toNorounded(num / 1000000000, 4) + 'B'
  } else if (num >= 1000000000000) {//万亿
    num = toNorounded(num / 1000000000000, 4) + 'T'
  }
  return abs ? isTohs ? num : num - 0 : isTohs ? `-${num}` : 0 - num
}
/* 
  控制输入框只能输入数字和小数位
*/
export const inputNumberLinmitFun = (value, decimal) => {
  decimal = decimal - 0
  value = value + ''
  // 此正则不对小数长度做限制
  let digitReg = /^\d+(\.\d+)?$/;
  // 步骤一: 将输入的值分割成一个一个的字符
  let strList = value.split("");
  let newValue = "";
  // 步骤二: 遍历字符数组，判断每个字符是否符合期望
  for (let item of strList) {
    if (isNaN(item - 0)) {
      if (item === ".") {
        newValue += item;
      }
    } else if (/\d/g.test(item)) {
      newValue += item;
    }
  }
  // 步骤三: 对已经符合期望的字符进行最后的格式验证
  // 因为有可能存在 2.323.32.. 这种多个小数点的情况，所以要再次验证
  if (!digitReg.test(newValue)) {
    let strs = newValue.split(".");
    if (strs.length > 1) {
      newValue = strs[0] + "." + strs[1];
    }
  }
  if (newValue.indexOf('.') === 0) {
    newValue = ''
  }
  if (newValue.indexOf('00') === 0) {
    newValue = '0'
  }
  if (newValue.indexOf('0') === 0 && newValue.indexOf('0.') !== 0) {
    newValue = '0'
  }
  if (decimal && newValue.indexOf('.') != newValue.length - 1 && newValue.indexOf('.') != -1) {
    if (newValue > 0 && newValue.split('.')[1].length <= decimal) {
      newValue = toNorounded(newValue, decimal, false)
    } else {
      let start = newValue.split('.')[0].length
      newValue = newValue.substring(0, start + 1 + decimal)
    }
  }
  if (decimal === 0) {
    newValue = newValue.split('.')[0]
  }
  return newValue;
}
//科学计数法处理
export const toNonExponential = (num, type) => {
  if (num && num.toString() === '0E-8') {
    return 0
  }
  if (/\d(?:.(\d*))?e([+-]\d+)/.test(num)) {
    var m = num.toExponential().match(/\d(?:\.(\d*))?e([+-]\d+)/);
    return num.toFixed(Math.max(0, (m[1] || '').length - m[2]));
  } else {
    if (type == 'string') {
      return num + ''
    }
    return Number(num)
  }
}


export const getCurrentDateFun = (type) => {
  let now = new Date();
  let year = now.getFullYear(); //获取完整的年份(4位,1970-????)
  let month = now.getMonth() + 1; //获取当前月份(0-11,0代表1月)
  let today = now.getDate(); //获取当前日(1-31)
  let hour = now.getHours(); //获取当前小时数(0-23)
  let minute = now.getMinutes(); //获取当前分钟数(0-59)
  let second = now.getSeconds(); //获取当前秒数(0-59)
  let nowTime = ''
  if (type == 'data') {
    nowTime = year + '-' + fillZero(month) + '-' + fillZero(today)
  } else if (type == 'time') {
    nowTime = fillZero(hour) + ':' + fillZero(minute) + ':' + fillZero(second)
  } else if (type == 'SplicingTime') {
    //用于根据时间生成编号
    nowTime = year + fillZero(month) + fillZero(today) + fillZero(hour) + fillZero(minute) + fillZero(second)
  } else {
    nowTime = year + '-' + fillZero(month) + '-' + fillZero(today) + ' ' + fillZero(hour) + ':' +
      fillZero(minute) + ':' + fillZero(second)
  }
  return nowTime
}
function fillZero(str) {
  var realNum;
  if (str < 10) {
    realNum = '0' + str;
  } else {
    realNum = str;
  }
  return realNum;
}