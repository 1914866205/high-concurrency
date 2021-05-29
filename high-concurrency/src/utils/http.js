let userUrl = 'http://localhost:8082';
import { axios } from './api'
/**
 * get方法，对应get请求
 * @param {String} url [请求的url地址]
 * @param {Object} parameter [请求时携带的参数]
 */

export function get(url, parameter) {
  return axios({
    url: url,
    method:'get' ,
    data: parameter
  })
}
export function post(url, parameter) {
  return axios({
    url: url,
    method:'post' ,
    data: parameter
  })
}
export function put(url, parameter) {
  return axios({
    url: url,
    method:'put',
    data: parameter
  })
}
//post Stream
export function postActionStream(url,parameter) {
  return axios({
    url: url,
    method: 'post',
    headers:{'Content-Type':"application/x-www-form-urlencoded"},
    data:parameter
  })
}
/**
 * login方法
 * @param parameter
 * @returns {*}
 */
 export function login(parameter) {
  return axios({
    url: userUrl+'/user/login',
    method: 'post',
    data: parameter
  })
}
export function phoneLogin(parameter) {
  return axios({
    url: userUrl+'/user/code/login',
    method: 'post',
    data: parameter
  })
}

