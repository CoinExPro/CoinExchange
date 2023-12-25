/*用来得到精确的加法结果
*说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
*调用：accAdd(arg1,arg2)
*返回值：arg1加上arg2的精确结果
*/
import {toNonExponential} from './util';

function accAdd(arg1, arg2) {
  var r1, r2, m;
  try { r1 = toNonExponential(arg1,'string').split(".")[1].length } catch (e) { r1 = 0 }
  try { r2 = toNonExponential(arg2,'string').split(".")[1].length } catch (e) { r2 = 0 }
  m = Math.pow(10, Math.max(r1, r2))
  return (setToFixed(arg1 * m) + setToFixed(arg2 * m)) / m
}

//减法函数
function accSub(arg1, arg2) {
  var r1, r2, m, n;
  try { r1 = toNonExponential(arg1,'string').split(".")[1].length } catch (e) { r1 = 0 }
  try { r2 = toNonExponential(arg2,'string').split(".")[1].length } catch (e) { r2 = 0 }
  m = Math.pow(10, Math.max(r1, r2));
  //last modify by deeka
  //动态控制精度长度
  n = (r1 >= r2) ? r1 : r2;
  return ((setToFixed(arg1 * m) - setToFixed(arg2 * m)) / m).toFixed(n);
}
//乘法函数
function accMul(arg1, arg2) {
  var m = 0, s1 = toNonExponential(arg1,'string'), s2 = toNonExponential(arg2,'string');
  try { m += s1.split(".")[1].length } catch (e) { }
  try { m += s2.split(".")[1].length } catch (e) { }
  return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m)
}
//除法
function accDiv(arg1, arg2) {
  var t1 = 0, t2 = 0, r1, r2;
  try { t1 = toNonExponential(arg1,'string').split(".")[1].length } catch (e) { }
  try { t2 = toNonExponential(arg2,'string').split(".")[1].length } catch (e) { }
  r1 = Number(toNonExponential(arg1,'string').replace(".", ""))
  r2 = Number(toNonExponential(arg2,'string').replace(".", ""))
  return accMul((r1 / r2), Math.pow(10, t2 - t1))
}

//处理乘10的n次方也有可能出现精度问题
function setToFixed(num) {
  num = Number(num)
  if (toNonExponential(num,'string').indexOf('.') != -1) {
    return Number(num.toFixed())
  }
  return num
}

//给Number类型增加一个add方法，调用起来更加方便。
//加
Number.prototype.myAdd = function (arg) {
  return accAdd(this - 0, arg - 0) - 0;
}
//减
Number.prototype.mySub = function (arg) {
  return accSub(this - 0, arg - 0) - 0;
}
//乘
Number.prototype.myMul = function (arg) {
  return accMul(this - 0, arg - 0) - 0;
}
//除
Number.prototype.myDiv = function (arg) {
  return accDiv(this - 0, arg - 0) - 0;
}