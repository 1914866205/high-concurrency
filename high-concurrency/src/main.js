import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import VueAxios from 'vue-axios'
import global_ from './utils/Global'
import axios from 'axios'

Vue.use(VueAxios, axios)

Vue.prototype.GLOBAL = global_

//导航守卫
// router.beforeEach((to, from, next) => {

//   let token = localStorage.getItem('token')
//   let isLogin
//   if (!token) {
//     isLogin = false
//   } else {
//     isLogin = true
//   }
//   if (!isLogin) {
//     if (to.path !== '/login') {
//       return next({ path: '/login' })
//     } else {
//       next()
//     }
//   } else {
//     if (to.path === '/login') {
//       return next({ path: '/' })
//     }
//     next()
//   }
// })

//全局请求拦截
axios.interceptors.request.use((config) => {
  //请求的接口不是登录和验证码接口
  if (['/user/login'].indexOf(config.url) === -1) {
    const token = localStorage.getItem('token')
    // const id = JSON.parse(localStorage.getItem('admin')).id
    if (token) {
      config.headers.Authorization = token
      config.headers.id = 'DE35D7CC05AF96A21D7ADFC8651E6614'
    }
  }
  // 默认参数与其他传来的参数进行合并
  // config.data = Object.assign(defaultParams, config.data)
  //判断如果请求类型为formdata
  // if (config.headers['Content-Type'] != 'multipart/form-data') {
  //   config.data = Object.assign(defaultParams, config.data)
  // }
  return config
})

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')
