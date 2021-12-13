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