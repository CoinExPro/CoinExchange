import Vue from 'vue';

Vue.mixin({
  beforeRouteEnter: function (to, from, next) {
    next(() => {
      window.scrollTo(0,0)
    })
  },
})