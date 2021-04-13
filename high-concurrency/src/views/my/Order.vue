<template>
  <v-app>
    <nav-bar></nav-bar>
    <div class="order">
      <v-card>
        <v-tabs
          v-model="tab"
          background-color="teal light-1"
          dark
          icons-and-text
        >
          <v-tabs-slider></v-tabs-slider>

          <v-tab href="#tab-1"> 全部订单 </v-tab>

          <v-tab href="#tab-2"> 待付款 </v-tab>

          <v-tab href="#tab-3"> 待评价 </v-tab>
        </v-tabs>

        <v-tabs-items v-model="tab">
          <v-tab-item v-for="inx in 3" :key="inx" :value="'tab-' + inx">
            <v-card flat>
              <!-- <div class="order-border">
                <span style="margin-left: 50px">订单详情</span>
                <span style="margin-left: 400px">数量</span>
                <span style="margin-left: 150px">收货人</span>
                <span style="margin-left: 150px">金额</span>
                <span style="margin-left: 150px">状态</span>
              </div> -->
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
                    style="
                      margin-left: 30px;
                      margin-top: 20px;
                      width: 100px;
                      height: 100px;
                    "
                    :src="item.goodImage"
                  />
                  <span class="mt">{{ item.goodName }}</span>
                  <span class="mt">x{{ item.count }}</span>
                  <span class="mt">{{ item.userName }}</span>
                  <span class="mt">￥{{ item.money }}</span>
                  <span class="mt">{{ item.status }}</span>
                </div>
              </div>

              <!-- <v-card-text>{{ text }}</v-card-text> -->
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
export default {
  name: "HomePage",
  data() {
    return {
      tab: null,
      order: [],
      noOrder:[],
      inx:0
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
        let noArr = []
        for (let j = 0, len = arr.length; j < len; j++) {
          //时间格式化
          let time1 = Date.parse(arr[j].createTime);
          console.log(time1)
          arr[j].createTime = time.timeFmt(time1);
          if(arr[j].status == 1) {
            noArr.push(arr[j])
          }
        }
        this.noArr = noArr
        this.order = arr;
        console.log(this.order);
      });
  },
  methods: {
    // statusTab(){
    //   if(this.order.length != 0) {
    //     console.log(this.order)
    //     for (let j = 0, len = this.order.length; j < len; j++) {
    //       if(this.order[j].status != 0) {

    //       }
    //     }
         
    //   }
     
    // }
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
  height: 200px;

  // margin-top: 10px;
}
.order-border {
  height: 40px;
  background-color: #f3f3f3;
  padding: 5px;
}
.col {
  display: flex;
  justify-content: space-between;
  padding-right: 100px;
}
.mt {
  margin-top:100px;
}
</style>