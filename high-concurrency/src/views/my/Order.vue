<template>
  <v-app>
    <nav-bar></nav-bar>
    <v-alert
      dense
      dismissible
      type="success"
      class="infom"
      @click="cancelInfo()"
      v-if="isPay"
      >支付成功</v-alert
    >
    <v-alert
      dense
      dismissible
      type="success"
      class="infom"
      @click="cancelComInfo()"
      v-if="isComSuc"
      >评价成功</v-alert
    >
    <v-alert
      dense
      dismissible
      type="success"
      class="infom"
      @click="cancelDelInfo()"
      v-if="isDel"
      >删除成功</v-alert
    >

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
                <div class="col">
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
                <div class="pay" v-if="item.status != 3">
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

              <div class="order-one no-order" v-if="isPush && inx == 2">
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
const API = require("../../utils/request.js");
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
    let params = new URLSearchParams();
    let id = localStorage.getItem("userId");
    console.log(id);
    params.append("pkUserId", id);
    this.axios
      .post(this.GLOBAL.contentUrl + "/order/findUserAllOrder", params)
      .then((res) => {
        this.order = res.data.data.data;
        let arr = res.data.data.data;
        let noArr = [];
        let noCom = [];
        for (let j = 0, len = arr.length; j < len; j++) {
          //时间格式化
          let time1 = Date.parse(arr[j].createTime);
          console.log(time1);
          arr[j].createTime = time.timeFmt(time1);
          if (arr[j].status == 0) {
            noArr.push(arr[j]);
          } else if (arr[j].status != 3) {
            noCom.push(arr[j]);
          }
        }
        this.noOrder = noArr;
        this.order = arr;
        this.allOrder = arr;
        this.noCom = noCom;
        console.log(this.noCom);
      });
  },

  methods: {
    dlDialog() {
      console.log("ssdfsdfds")
      this.delDialog == true
    },
    noPushTab() {
      this.order = this.noOrder;
      if (this.noOrder.length == 0) {
        this.isPush = true;
      }
    },
    cancelInfo() {
      this.isPay = false;
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
      this.axios
        .post(this.GLOBAL.contentUrl + "/order/payOrder", params)
        .then((res) => {
          if (res.data.code == 1) {
            this.isPay = true;
            let arr = this.noOrder;
            let arr1 = arr.filter((item) => item.pkOrderid == id);
            this.noOrder = arr1;
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
    async putComment(orderId, goodId, userId) {
      if (this.value == "") {
        this.value == "该用户已发表默认评价";
      }
      (this.url = this.GLOBAL.contentUrl + "/comment/addComment"),
        (this.data = {
          content: this.value,
          pkGoodId: goodId,
          pkUserEdId: userId,
          pkUserIngId: localStorage.getItem("userId"),
          type: this.rating,
        }),
        (this.result = await API.init(this.url, this.data, "post"));
      if (this.result.code === 1) {
        this.isComSuc = true;
        let arr = this.noCom;
        let arr1 = arr.filter((item) => item.pkOrderid == orderId);
        this.noCom = arr1;
      }
    },
    cancelDelInfo() {
      this.isDel = false;
      this.allTab();
    },
    del(id) {
      let params = new URLSearchParams();
      params.append("hBorderId", id);
      this.axios
        .post(this.GLOBAL.contentUrl + "/order/celOrder", params)
        .then((res) => {
          console.log(res);
          if (res.data.code == 1) {
            this.isDel = true;
            let arr = this.allOrder;
            let arr1 = arr.filter((item) => item.pkOrderid == id);
            this.allOrder = arr1;
          }
        });
    },
    allTab() {
      this.order = this.allOrder;
      this.isPush = false;
    },
  },
  components: {
    NavBar,
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
  margin-right: 60px;
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
.infom {
  position: absolute;
  top: 200px;
  left: 40%;
  width: 20%;
  z-index: 999;
}
.star {
  display: flex;
  margin: 10px;
}
.dialog {
  z-index: 999;
}
</style>