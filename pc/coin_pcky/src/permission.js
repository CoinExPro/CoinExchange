import router from '@/router/index';
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
NProgress.configure({ showSpinner: false });
router.beforeEach((to, from, next) => {
  console.log(to)
  if(to.matched.length>0){
    next()
  }else{
    next('/home/index')
  }
  
})
router.afterEach(() => {
  NProgress.done();
})