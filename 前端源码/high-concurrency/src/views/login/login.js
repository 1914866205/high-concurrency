import Vue from 'vue'
import {
    ACCESS_TOKEN,
    USER_PHONE,
    USER_INFO,
    USER_ID,
    USER_AVATAR
  } from "@/store/mutation-types";
export function storage(token,phone,userInfo,userId,avatar) {
    Vue.ls.set(ACCESS_TOKEN,token, 7 * 24 * 60 * 60 * 1000);
    Vue.ls.set(USER_PHONE,phone, 7 * 24 * 60 * 60 * 1000);
    Vue.ls.set(USER_INFO,userInfo, 7 * 24 * 60 * 60 * 1000);
    Vue.ls.set(USER_ID,userId, 7 * 24 * 60 * 60 * 1000);
    Vue.ls.set(USER_AVATAR,avatar, 7 * 24 * 60 * 60 * 1000);
}