import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store =  new Vuex.Store({
  state: {
    token: localStorage.getItem('token'),
  },
  mutations: {
    setToken(state, data) {
      state.token = data
    }
  },
  actions: {
  },
  modules: {
  }
})

export default store
