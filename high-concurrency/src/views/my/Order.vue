<template>
  <v-app>
    <nav-bar></nav-bar>
    <!-- <alert
      v-if="isPay"
      @click="cancelInfo()"
      :alertdata="isYue ? '余额不足' : '支付成功'"
    ></alert> -->
    <!-- <alert
      v-if="isComSuc"
      @click="cancelComInfo()"
      alertdata="评价成功"
    ></alert> -->
    <!-- <alert v-if="isDel" @click="cancelDelInfo()" alertdata="删除成功"></alert> -->
    <div class="order">
      <v-card>
        <v-tabs
          v-model="tab"
          background-color="teal light-1"
          dark
          icons-and-text
        >
          <v-tabs-slider></v-tabs-slider>

          <v-tab @click="allTab()" href="#tab-1"> 全部订单 </v-tab>

          <v-tab @click="noPushTab()" href="#tab-2"> 待付款 </v-tab>

          <v-tab @click="noComTab()" href="#tab-3"> 待评价 </v-tab>
        </v-tabs>

        <v-tabs-items v-model="tab">
          <v-tab-item v-for="inx in 3" :key="inx" :value="'tab-' + inx">
            <v-card flat>
              <div
                v-for="(item, index) in order"
                :key="index"
                class="order-one"
              >
                <div class="order-border">
                  <span style="margin-left: 30px; color: gray">{{
                    item.createTime
                  }}</span>
                  <span style="margin-left: 50px; color: gray"
                    >订单编号:{{ item.pkOrderId }}</span
                  >
                </div>
                <div class="col" @click="goGoods(item.pkGoodId)">
                  <img
                    style="margin-left: 30px; width: 100px; height: 100px"
                    :src="item.goodImage"
                  />
                  <span>{{ item.goodName }}</span>
                  <span>x{{ item.count }}</span>
                  <span>{{ item.userName }}</span>
                  <span>￥{{ item.money }}</span>
                </div>
                <div class="pay" v-if="item.status == 0">
                  <button @click="pay(item.pkOrderId)" class="pay-btn">
                    支付订单
                  </button>
                </div>
                <div class="pay" v-if="item.status == 2">
                  <v-dialog
                    transition="dialog-bottom-transition"
                    max-width="600"
                  >
                    <template v-slot:activator="{ on, attrs }">
                      <button
                        v-bind="attrs"
                        v-on="on"
                        @click="comDialog = true"
                        class="pay-btn"
                      >
                        发表评价
                      </button>
                    </template>
                    <template v-if="comDialog" v-slot:default="comDialog">
                      <v-card>
                        <v-toolbar color="teal lighten-1" dark
                          >发表评价</v-toolbar
                        >
                        <v-card-text>
                          <v-container fluid>
                            <v-textarea
                              counter
                              clearable
                              clear-icon="mdi-close-circle"
                              label="Text"
                              :rules="rules"
                              v-model="value"
                            ></v-textarea>
                          </v-container>
                          <div class="star">
                            <span>评级：</span>
                            <v-rating
                              style="margin-top: -10px"
                              v-model="rating"
                              icon-label="custom icon label text {0} of {1}"
                            ></v-rating>
                          </div>
                        </v-card-text>
                        <v-card-actions class="justify-end">
                          <v-btn
                            text
                            @click="
                              putComment(
                                item.pkOrderId,
                                item.pkGoodId,
                                item.userId
                              )
                            "
                            >发布</v-btn
                          >

                          <v-btn text @click="comDialog.value = false"
                            >取消</v-btn
                          >
                        </v-card-actions>
                      </v-card>
                    </template>
                  </v-dialog>
                </div>
                <div class="pay1">
                  <button @click="del(item.pkOrderId)" class="pay-btn">
                    删除订单
                  </button>
                </div>
              </div>

              <div
                class="order-one no-order"
                v-if="(isPush && inx == 2) || allOrder.length == 0"
              >
                <span class="order-text"
                  >您还没有相关的订单 可以去看看有哪些想买的</span
                >
              </div>
              <div class="order-one no-order" v-if="isCom && inx == 3">
                <span class="order-text">您已完成所有评价</span>
              </div>
            </v-card>
          </v-tab-item>
        </v-tabs-items>
      </v-card>
    </div>
  </v-app>
</template>

<script>
import NavBar from "../../components/NavBar";
const time = require("../../utils/time.js");
import Alert from "@/components/Alert";
import { commentAddComment } from "@/utils/request.js";
import Vue from "vue";
import { USER_ID } from "@/store/mutation-types";
export default {
  name: "HomePage",
  data() {
    return {
      tab: null,
      order: [],
      allOrder: [],
      noOrder: [],
      inx: 0,
      isPush: false,
      isPay: false,
      isYue: false,
      noCom: [],
      isCom: false,
      isDel: false,
      rules: [(v) => v.length <= 255 || "最多输入255个文字"],
      value: "",
      rating: 5,
      comDialog: false,
      isComSuc: false, //评价成功弹框
      delDialog: false,
    };
  },
  created: function () {
    this.getOrder();
  },

  methods: {
    getOrder() {
      let params = new URLSearchParams();
      let id = Vue.ls.get(USER_ID);
      params.append("pkUserId", id);
      this.axios
        .post(this.GLOBAL.contentUrl + "/order/findUserAllOrder", params)
        .then((res) => {
          let arr = res.data.data.data;
          console.log(arr);
          let allArr = [];
          let noArr = [];
          let noCom = [];
          for (let j = 0, len = arr.length; j < len; j++) {
            //时间格式化
            let time1 = Date.parse(arr[j].createTime);
            arr[j].createTime = time.timeFmt(time1);
            if (arr[j].status != 3) {
              allArr.push(arr[j]);
            }else if (arr[j].status == 0) {
              noArr.push(arr[j]);
            } else if (arr[j].status == 2) {
              noCom.push(arr[j]);
            }
          }
          this.order = allArr;
          this.noOrder = noArr;
          this.allOrder = allArr;
          this.noCom = noCom;
        });
    },
    dlDialog() {
      this.delDialog == true;
    },
    noPushTab() {
      this.order = this.noOrder;
      if (this.noOrder.length == 0) {
        this.isPush = true;
      }
    },
    cancelInfo() {
      this.noPushTab();
      if (this.tab == "tab-2") {
        this.noPushTab;
      }
    },
    noComTab() {
      this.order = this.noCom;
      if (this.noCom.length == 0) {
        this.isCom = true;
      }
    },
    pay(id) {
      let params = new URLSearchParams();
      params.append("orderId", id);
      console.log(id);
      this.axios
        .post(this.GLOBAL.contentUrl + "/order/payOrder", params)
        .then((res) => {
          if (res.data.code == 1) {
            this.getOrder();
          } else if (res.data.code == 8004) {
            this.isYue = true;
            if (this.tab == "tab-2") {
              this.noPushTab();
            }
          }
          if (this.isYue) {
            this.$message({
              message: "余额不足",
              type: "warning",
            });
          } else {
            this.$message({
              message: "支付成功",
              type: "success",
            });
          }
        });
    },
    cancelComInfo() {
      this.isComSuc = false;
      this.comDialog = false;
      console.log(this.tab);
      if (this.tab == "tab-3") {
        this.noComTab();
      }
    },
    putComment(orderId, goodId, userId) {
      if (this.value == "") {
        this.value == "该用户已发表默认评价";
      }
      let data = {
        content: this.value,
        pkGoodId: goodId,
        pkUserEdId: userId,
        pkUserIngId: Vue.ls.get(USER_ID),
        type: this.rating,
      };
      commentAddComment(data).then((res) => {
        if (res.code === 1) {
          this.$message({
            message: "评价成功",
            type: "success",
          });
          let params = new URLSearchParams();
          params.append("hbOrderId", orderId);
          params.append("state", 1);
          this.axios
            .post(this.GLOBAL.contentUrl + "/order/commentOrder", params)
            .then((res) => {
              console.log(res)
              this.getOrder();
              this.comDialog = false;
              if (this.tab == "tab-3") {
                this.noComTab();
              }
            });
        }
      });
    },
    del(id) {
      let params = new URLSearchParams();
      params.append("hBorderId", id);
      this.axios
        .post(this.GLOBAL.contentUrl + "/order/celOrder", params)
        .then((res) => {
          console.log(res);
          if (res.data.code == 1) {
            this.$message({
              message: "删除成功",
              type: "success",
            });
            this.getOrder();
          }
        });
    },
    goGoods(goodId) {
      this.$router.push({ path: "/goods", query: { goodsId: goodId } });
    },
    allTab() {
      this.order = this.allOrder;
      this.isPush = false;
    },
  },
  components: {
    NavBar,
    Alert,
  },
};
</script>
<style lang="scss" scoped>
.order {
  width: 85%;
  margin: 100px auto;
}
.order-one {
  border: 1px solid rgb(228, 225, 225);
  height: 280px;
}
.no-order {
  display: flex;
  justify-content: center;
  align-items: center;
}
.order-border {
  height: 40px;
  background-color: #f3f3f3;
  padding: 5px;
}
.col {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding-right: 100px;
  margin-top: 30px;
}
.order-text {
  color: gray;
}
.pay1 {
  float: right;
  margin-right: 30px;
  margin-top: 30px;
}
.pay {
  float: right;
  margin-right: 30px;
  margin-top: 30px;
}
.pay-btn {
  background-color: #fff;
  border: 1px solid #26a69a;
  color: #26a69a;
  border-radius: 15px;
  padding: 6px;
  outline: none;
  &:hover {
    background-color: #26a69a;
    border: 1px solid #26a69a;
    color: #fff;
  }
}

.star {
  display: flex;
  margin: 10px;
}
.dialog {
  z-index: 999;
}
</style>