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
import { ACCESS_TOKEN } from "@/store/mutation-types";
//导航守卫
router.beforeEach((to, from, next) => {
   //to即将要进入的目标 
   //from当前导航正要离开的路由
   // next一定要调用这个方法来resolve这个钩子 执行效果依赖Next方法调用参数

  let token = Vue.ls.get(ACCESS_TOKEN);
  if (to.meta.requireAuth) {//判断该路由是否需要登录全新
    if (token) {//判断本地token是否存在
      next()
    } else {
      if(to.path === '/login'){
        next()
      }else {
        next({ path: '/login' })
      }
    } 
  } else {
    next();
  }
  //存在token则不跳转
  if(to.fullPath == '/login') {
    if(token) {
      next({path:from.fullPath});
    } else {
      next();
    }
  }
});

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
