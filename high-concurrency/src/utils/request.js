/**
 * 统一接口管理
 */
 import { get, post, put } from "./http";
 const goodsAddGoods = (data) => post('/goods/addGoods',data);
 const goodsSearch = (data) => post('/goods/search',data);
 const hbStrategyadd = (data) => post('/hbStrategy/add',data);
 const orderAddOrder = (data) => post('/order/addOrder',data);
 const orderBatchAddOrder = (data) => post('/order/batchAddOrder',data);
 const orderFindSecKillUserOrder = (data) => post('/order/findSecKillUserOrder',data);
 const orderSpikeOrder = (data) => post('/order/spikeOrder',data);
 const commentAddComment = (data) => post('/comment/addComment',data);
 export {
  goodsAddGoods,
  goodsSearch,
  hbStrategyadd,
  orderAddOrder,
  orderBatchAddOrder,
  orderFindSecKillUserOrder,
  orderSpikeOrder,
  commentAddComment
 }
//  export const apiGetDetail = (url, data) => get(url, data);

//  export const apiDeviceDetail = (url, data) => post(url, data);

//  export const apiUpdeteDevice = (url, data) => put(url, data);

 
//  export async function init(url, data, method) {
//    // 方法
//    if (method == "get") {
//      this.result = await apiGetDetail(url, data).then((res) => {
//        return res;
//      });
//    }
//    if (method == "post") {
//      this.result = await apiDeviceDetail(url, data).then((res) => {
//        return res;
//      });
//    }
//    if (method == "put") {
//      this.result = await apiUpdeteDevice(url, data).then((res) => {
//        return res;
//      });
//    }
  
//    return this.result;
//  }