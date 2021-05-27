import Vue from 'vue'
import App from './App.vue'
import router from './router'
import Storage from 'vue-ls'
import config from '@/defaultSettings'
import store from './store'
import vuetify from './plugins/vuetify'
import Vuetify from 'vuetify/lib'
import VueAxios from 'vue-axios'
//全局统一封装
import global_ from './utils/Global'
import axios from 'axios'
import 'element-ui/lib/theme-chalk/index.css';
import Element from 'element-ui';
//统一样式
import common from './common/common.css'
import  { Component } from 'react'
//防止用户在一定时间内重复频繁的触发事件
import preventReClick from '@/utils/preventReClick.js';
Vue.use(preventReClick);

Vue.use(Element, { size: 'small', zIndex: 3000 });
Vue.use(VueAxios, axios)
Vue.use(Storage, config.storageOptions)
Vue.use(Vuetify,Component,common)
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
// axios.interceptors.request.use((config) => {
//   //请求的接口不是登录和验证码接口
//   if (['/user/login'].indexOf(config.url) === -1) {
//     const token = localStorage.getItem('token')
//     // const id = JSON.parse(localStorage.getItem('admin')).id
//     if (token) {
//       config.headers.Authorization = token
//       config.headers.id = 'DE35D7CC05AF96A21D7ADFC8651E6614'
//     }
//   }
  // 默认参数与其他传来的参数进行合并
  // config.data = Object.assign(defaultParams, config.data)
  //判断如果请求类型为formdata
  // if (config.headers['Content-Type'] != 'multipart/form-data') {
  //   config.data = Object.assign(defaultParams, config.data)
  // }
//   return config
// })

new Vue({
  router,
  store,
  vuetify,
  mounted() {
    
  },
  render: h => h(App)
}).$mount('#app')
