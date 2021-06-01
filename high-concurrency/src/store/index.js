import Vue from 'vue'
import Vuex from 'vuex'
import { login,phoneLogin,sendCode,userRegister } from '@/utils/http'
const STORAGE = require("@/views/login/login");
Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    token: '',
    avatar: '',
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
  },
  actions: {
    Login({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(response => {
          if (response.code === 1) {
            //存入token
            let data = response.data;
            console.log(data);
            STORAGE.storage(
              data.token,
              data.user.phone,
              data.user,
              data.user.pkUserId,
              data.user.avatar
            );
            commit('SET_TOKEN', data.tokenn)
            commit('SET_AVATAR', data.user.avatar)
            resolve(response)
          }else{
            reject(response)
          }
        }).catch(error => {
          console.log(error)
          this.$message.error('登录失败，请稍后重试');
        })
      })
    },
    //手机号登录
    PhoneLogin({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        phoneLogin(userInfo).then(response => {
          if (response.code === 1) {
            //存入token
            let data = response.data;
            STORAGE.storage(
              data.token,
              data.user.phone,
              data.user,
              data.user.pkUserId,
              data.user.avatar
            );
            commit('SET_TOKEN', data.tokenn)
            commit('SET_AVATAR', data.user.avatar)
            resolve(response)
          }else{
            reject(response)
          }
        }).catch((error) => {
          console.log(error)
          this.$message.error('登录失败，请稍后重试');
        })
      })
    },
  },
  modules: {
  }
})

export default store
