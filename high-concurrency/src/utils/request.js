/**
 * 统一接口管理
 */
 import { get, post, $delete, put } from "./http";

 // 设备详情
 export const apiGetDetail = (url, data) => get(url, data);
 // 设备详情
 export const apiDeviceDetail = (url, data) => post(url, data);
 // 修改设备
 export const apiUpdeteDevice = (url, data) => put(url, data);
 // 删除设备
 export const apiDelDevice = (url, data) => $delete(url, data);
 
 export async function init(url, data, method) {
   // 方法
   if (method == "get") {
     this.result = await apiGetDetail(url, data).then((res) => {
       return res;
     });
   }
   if (method == "post") {
     this.result = await apiDeviceDetail(url, data).then((res) => {
       return res;
     });
   }
   if (method == "put") {
     this.result = await apiUpdeteDevice(url, data).then((res) => {
       return res;
     });
   }
   if (method == "del") {
     this.result = await apiDelDevice(url, data).then((res) => {
       return res;
     });
   }
   return this.result;
 }