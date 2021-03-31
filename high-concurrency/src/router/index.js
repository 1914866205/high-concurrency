import Vue from 'vue'
import VueRouter from 'vue-router'
import HomePage from '../views/index/HomePage.vue'
import Login from '../views/login/Login.vue'


Vue.use(VueRouter)

const routes = [
  {
    path: '/login',
    name: 'Login',
    component: Login
    // component: () => import('../views/login/Login.vue')
  },
  {
    path: '/',
    name: 'Home',
    component: HomePage
  },
  {
    path: '/message',
    name: 'Message',
    component: () => import('../views/my/Message.vue')
  },
  {
    path: '/order',
    name: 'Order',
    component: () => import('../views/my/Order.vue')
  },
  {
    path: '/goods',
    name: 'Goods',
    component: () => import('../views/goods/Goods.vue')
  }
]

const router = new VueRouter({
  routes,
  mode: 'history'
})

export default router
