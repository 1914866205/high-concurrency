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
  },
  {
    path: '/',
    name: 'Home',
    component: HomePage
  },
  {
    path: '/message',
    name: 'Message',
    component: () => import('../views/my/Message.vue'),
    meta:{requireAuth:true}
  },
  {
    path: '/order',
    name: 'Order',
    component: () => import('../views/my/Order.vue'),
    meta:{requireAuth:true}
  },
  {
    path: '/goods',
    name: 'Goods',
    component: () => import('../views/goods/Goods.vue')
  },
  {
    path: '/put',
    name: 'Put',
    component: () => import('../views/my/Put.vue'),
    meta:{requireAuth:true}
  },
  {
    path: '/router',
    name: 'RouterUtil',
    component: () => import('../views/other/RouterUtil.vue')
  },
  {
    path: '/publish',
    name: 'Publish',
    component: () => import('../views/put/Publish.vue'),
    meta:{requireAuth:true}
  },
]

const router = new VueRouter({
  routes,
  mode: 'history'
})

export default router
