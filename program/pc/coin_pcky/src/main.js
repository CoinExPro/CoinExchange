import Vue from 'vue'
import axios from './router/axios';
import VueAxios from 'vue-axios';
import App from './App.vue'
import router from './router'
import './permission'; // 权限
import './cache';
import store from './store'
import Element from 'element-ui';
import i18n from './lang';
//计算精度处理
import './util/calculate';
import './styles/common.scss';
//引入图标库
import '@/assets/css/font-awesome.min.css'

Vue.use(VueAxios, axios);
Vue.use(Element, {
  i18n: (key, value) => i18n.t(key, value)
});
Vue.use(window.AVUE, {
  size: 'small',
  tableSize: 'small',
  calcHeight: 65,
  i18n: (key, value) => i18n.t(key, value)
});


Vue.config.productionTip = false

new Vue({
  router,
  store,
  i18n,
  render: h => h(App)
}).$mount('#app')
