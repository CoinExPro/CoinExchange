import Vue from 'vue'
import VueRouter from 'vue-router'
import Layout from '@/page/index/index.vue'
Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/home'
  },
  {
    path: '/home',
    redirect: '/home/index',
    component: Layout,
    children: [
      {
        path: 'index',
        name: '首页',
        component: () => import( /* webpackChunkName: "views" */ '@/views/home/index.vue')
      }
    ]
  },
]

const router = new VueRouter({
  routes
})

export default router
