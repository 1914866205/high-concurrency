<template>
  <v-app class="goods">
    <nav-bar></nav-bar>
    <alert
      v-if="isXiangou"
      @click="cancelXiangou()"
      :alertdata="isMiaosha ? '秒杀订单限购一个' : '不能购买更多了'"
    ></alert>
    <v-overlay :z-index="zIndex" :value="overlay">
      <div class="forms">
        <h2>请确认订单</h2>
        <v-form ref="form" class="mt-6" v-model="valid" lazy-validation>
          <v-text-field
            v-model="phone"
            :rules="phoneRules"
            label="phone"
            required
            dark="true"
          ></v-text-field>
          <div>数量 : {{ count }}</div>
          <div style="margin-top: 40px">
            <v-btn
              :disabled="!valid"
              color="success"
              class="mr-6"
              @click="validate"
            >
              提交订单
            </v-btn>

            <v-btn color="warning" class="mr-6" @click="reset">
              刷新表单
            </v-btn>

            <v-btn color="error" @click="cancel()"> 取消提交 </v-btn>
          </div>
        </v-form>
      </div>
    </v-overlay>

    <div class="goodsInfo">
      <div class="goods-left">
        <div class="img">
          <img class="goodsImg" :src="goodsInfo.image" />
        </div>
        <div class="line"></div>
        <div class="goods-info">
          <img src="../../assets/icon/yinhao.png" />
          <span style="font-size: 1.5rem">{{ goodsInfo.goodName }}</span>
          <div style="display: flex; margin-left: -20px">
            <div class="border-left">
              <div class="font m-5">类型</div>
              <div class="font-s m-5">
                {{ goodsInfo.type }}
              </div>
            </div>
            <div class="border-left">
              <div class="font m-5">秒杀商品</div>
              <div class="font-s m-5">秒杀成功后可在订单中查看</div>
            </div>
            <div class="border-left">
              <div class="font m-5">邮送</div>
              <div class="font-s m-5">免费</div>
            </div>
            <div class="border-left">
              <div class="font m-5">其他商品</div>
              <div class="font-s m-5">更多商品请点击卖家主页</div>
            </div>
          </div>
        </div>
        <div class="line"></div>
        <div class="comm">
          <img src="../../assets/icon/yinhao.png" />
          <span style="font-size: 1.5rem">商品评价</span>
          <div
            v-for="(item, index) in comments"
            :key="index"
            style="margin-top: 20px; padding: 20px; width: 100%"
          >
            <div class="user">
              <img class="user_avatar" :src="item.avatar" />
              <div class="font-s">{{ item.username }}</div>
            </div>
            <div style="margin-top: -60px; margin-left: 60px">
              <span style="margin-left: 10px">{{ item.content }}</span>
              <span
                @click="delCom(item.pkCommentId)"
                class="index-color button-hand"
                style="float: right"
                v-show="item.createdTime"
                >删除</span
              >
              <v-rating
                empty-icon="mdi-star-outline"
                full-icon="mdi-star"
                half-icon="mdi-star-half-full"
                length="5"
                size="30"
                readonly
                :value="item.type"
              ></v-rating>
            </div>
            <div class="line1"></div>
          </div>
        </div>
      </div>

      <div class="goods-right">
        <div class="font mt-10">价格</div>

        <div
          class="mt-8"
          style="color: #26a69a; font-size: 2.5rem; font-weight: 300"
        >
          RMB￥{{ goodsInfo.price }}
        </div>

        <div class="mt-8" style="color: gray">
          <span>{{ goodsInfo.description }}</span>
        </div>

        <div class="mt-8">
          <span style="font-size: 1rem">购买数量</span>
          <button @click="btnMinute" class="btn_minute btn-none">-</button>
          <input
            class="goods-input btn-none"
            type="number"
            min="1"
            max="3"
            :oninput="
              'if(' +
              isMiaosha +
              ' && value >1)value=1;' +
              'if(value>' +
              goodsInfo.count +
              ')value=' +
              goodsInfo.count +
              ';if(value<0)value=0'
            "
            v-model="count"
          />
          <button @click="btnAdd" class="btn_add btn-none">+</button>
          <span style="color: gray; margin-left: 10px"
            >(库存：{{ goodsInfo.count }})</span
          >
        </div>

        <div
          v-show="miaosha.description != null"
          class="goods-discount br-2 pa-1 mt-4"
        >
          <span>{{ miaosha.description }}</span>
          <div v-for="(item, index) in miaosha.ruleDtoList" :key="index">
            <span
              >第{{ item.start }}名到第{{ item.end }}名打{{
                item.discount
              }}折</span
            >
          </div>
        </div>
        <div style="margin-top: 50px">
          <v-btn
            @click="goSubmit()"
            width="380"
            height="60"
            v-if="miaosha.day <= 0 || miaosha.day == null"
            class="goods-btn btn-green"
            ><span style="color: #fff">立即购买</span></v-btn
          >
          <v-btn
            disabled
            width="380"
            height="60"
            v-else
            class="goods-btn btn-white"
            ><span style="color: #fff">{{ daojishi(miaosha.day) }}</span></v-btn
          >
        </div>

        <div v-show="isMiaosha">
          <div class="font mt-100">秒杀前1000名</div>
          <v-simple-table fixed-header height="500px" class="mt-4">
            <template v-slot:default>
              <thead>
                <tr>
                  <th class="text-left">用户名</th>
                  <th class="text-left">手机号</th>
                  <th class="text-left">折扣</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="item in preOrder" :key="item.name">
                  <td>{{ item.userName }}</td>
                  <td>{{ item.phoneNumber }}</td>
                  <td>{{ item.discount * 10 }}折</td>
                </tr>
              </tbody>
            </template>
          </v-simple-table>
        </div>
      </div>
    </div>
    <div class="goods-footer">
      <div>
        <img src="../../assets/icon/yinhao.png" />
        <span style="font-size: 1.5rem">{{ user.nickname }}的所有商品</span>
      </div>

      <div>
        <v-sheet
          style="height: 380px"
          class="mx-auto mt-8"
          elevation="8"
          max-width="100%"
        >
          <v-slide-group
            v-model="model"
            prev-icon="«"
            next-icon="»"
            class="pa-4 mt-12"
          >
            <v-slide-item
              v-for="(item, index) in goods"
              :key="index"
              @click="goGoods(item.pkGoodId)"
            >
              <v-card class="ma-4" height="300" width="300">
                <div class="ag-shop-card_box">
                  <div class="ag-shop-card_body">
                    <div
                      class="js-card-bg ag-card-bg"
                      :style="'background-image: url(' + item.image + ');'"
                    ></div>
                    <a
                      @click="goGoods(item.pkGoodId)"
                      class="ag-shop-card-body_link"
                      ><img
                        :src="item.image"
                        alt="Falu"
                        class="ag-shop-card-body_img"
                    /></a>
                  </div>
                  <div class="ag-shop-card_footer">
                    <a
                      @click="goGoods(item.pkGoodId)"
                      class="ag-shop-card-footer_link"
                      ><img
                        src="https://rawcdn.githack.com/SochavaAG/example-mycode/master/pens/parallax-swipe/images/arrow.svg"
                        class="ag-shop-card-footer_arrow"
                    /></a>
                    <span class="ag-shop-card-footer_title">{{
                      item.goodName
                    }}</span>
                    <span class="ag-shop-card-footer_products"
                      >${{ item.price }}</span
                    >
                  </div>
                </div>
              </v-card>
            </v-slide-item>
          </v-slide-group>
        </v-sheet>
      </div>
    </div>
  </v-app>
</template>

<script>
import NavBar from "@/components/NavBar";
import Alert from "@/components/Alert";
import {
  orderFindSecKillUserOrder,
  orderAddOrder,
  orderSpikeOrder,
} from "@/utils/request.js";
import Vue from "vue";
import { ACCESS_TOKEN, USER_PHONE, USER_ID } from "@/store/mutation-types";
export default {
  data() {
    return {
      preOrder: [],
      goodsInfo: [],
      count: 0,
      comments: [],
      user: [],
      isCount: false,
      model: null,
      goods: [],
      overlay: false,
      zIndex: 0,
      valid: true,
      phone: Vue.ls.get(USER_PHONE),
      phoneRules: [
        (v) => !!v || "手机号必须填写",
        (v) => (v && v.length <= 11) || "手机号不能小于十一位",
        (v) => /^1[34578]\d{9}$/.test(v) || "手机号填写不规范",
      ],
      checkbox: false,
      day: "",
      isMiaosha: false,
      miaosha: [],
      isXiangou: false,
    };
  },
  created: function () {
    //根据是否有秒杀时间来判定是否是秒杀订单
    this.day = this.$route.query.day;
    console.log(this.day);
    // if (this.day != null) {
    let params = new URLSearchParams();
    params.append("goodId", this.$route.query.goodsId);
    this.axios
      .post(this.GLOBAL.contentUrl + "/hbStrategy/get", params)
      .then((res) => {
        this.isMiaosha = true;
        this.miaosha = res.data.data;
        this.miaosha.day =
          new Date(this.miaosha.day).getTime() - new Date().getTime();
        if (this.ticker) {
          //这一段是防止进入页面出去后再进来计时器重复启动
          clearInterval(this.ticker);
        }
        this.beginTimer(); //启动计时器减指定字段的时间
      })
      .catch((err) => {
        console.log(err);
      });
    // }
    this.getIndex();
    this.getPreOrder();
    this.getCommont();
  },
  methods: {
    daojishi(time) {
      if (this.isShopping) {
        return s + "秒";
      } else if (time >= 0) {
        let d = Math.floor(time / 1000 / 60 / 60 / 24);
        let h = Math.floor((time / 1000 / 60 / 60) % 24);
        let m = Math.floor((time / 1000 / 60) % 60);
        let s = Math.floor((time / 1000) % 60);
        return "还有" + d + "天" + h + "时" + m + "分" + s + "秒";
      }
    },
    getPreOrder() {
      let data = {
        goodId: this.$route.query.goodsId,
        strategies: [
          {
            pkStrategyId: null,
            strategyName: null,
            goodId: null,
            rankStart: 101,
            rankEnd: 500,
            discount: 0.0,
            createdTime: null,
            updatedTime: null,
          },
        ],
        time: "2021-04-28 19:31:02",
      };
      orderFindSecKillUserOrder(data).then((res) => {
        for (let i = 0; i < res.data.length; i++) {
          let phone = "" + res.data[i].phoneNumber;
          res.data[i].phoneNumber = phone.replace(
            phone.substring(3, 7),
            "****"
          );
        }
        this.preOrder = res.data;
      });
    },
    beginTimer() {
      //这个计时器是每秒减去数组中指定字段的时间
      this.ticker = setInterval(() => {
        const item = this.miaosha;
        if (item.day > 0) {
          item.day = item.day - 1000;
        }
      }, 1000);
    },
    reset() {
      this.$refs.form.reset();
    },
    goSubmit() {
      console.log(Vue.ls.get(ACCESS_TOKEN));
      if (Vue.ls.get(ACCESS_TOKEN) == null) {
        this.$router.push("/login");
      }else if (this.count == 0) {
        this.$message({
          message: "购买数量必须大于0",
          type: "warning",
        });
      } else {
        
        this.overlay = true;
      }
    },
    cancelXiangou() {
      this.isXiangou = false;
    },
    cancel() {
      this.overlay = false;
    },
    validate() {
      this.$refs.form.validate();
      this.sumbitGoods();
    },
    btnAdd() {
      if (this.isMiaosha) {
        if (this.count >= 1) {
          this.isXiangou = true;
          setTimeout(this.cancelXiangou, 3000);
        } else {
          this.count++;
        }
      } else if (this.count >= 10) {
        this.isXiangou = true;
        setTimeout(this.cancelXiangou, 3000);
      } else {
        this.count++;
      }
    },
    goGoods(goodsId) {
      this.$router.push({ path: "/router", query: { goodsId: goodsId } });
    },
    btnMinute() {
      if (this.count <= 0) {
        this.$message({
          message: "该宝贝不能减少了哦",
          type: "warning",
        });
      } else {
        this.count -= 1;
      }
    },
    delCom(comId) {
      console.log(comId);
      let params = new URLSearchParams();
      params.append("commentId", comId);
      this.axios
        .post(this.GLOBAL.contentUrl + "/comment/delComment", params)
        .then((res) => {
          this.$message({
          message: "删除成功",
          type: "success",
        });
        this.getCommont();
        });
    },
    sumbitGoods() {
      //根据是否是秒杀订单来请求不同的接口
      let data = {
        number: this.count,
        phone: this.phone,
        pkGoodId: this.goodsInfo.pkGoodId,
        userId: Vue.ls.get(USER_ID),
      };
      if (this.isMiaosha) {
        orderSpikeOrder(data).then((res) => {
          console.log(res);
          this.overlay = false;
          this.$message({
            message: "成功参与秒杀",
            type: "success",
          });
          if (this.isMiaosha) {
            this.miaosha.day = 3000;
          }
        });
      } else {
        orderAddOrder(data).then((res) => {
          console.log(res);
          this.overlay = false;
          this.$message({
            message: "购买成功，可在我的订单中查看",
            type: "success",
          });
          if (this.isMiaosha) {
            this.miaosha.day = 5000;
          }
        });
      }
    },
    getIndex() {
      let params = new URLSearchParams();
      params.append("goodsId", this.$route.query.goodsId);
      this.axios
        .post(this.GLOBAL.contentUrl + "/goods/findGoodsById", params)
        .then((res) => {
          this.goodsInfo = res.data.data.Goods;
          let id = this.goodsInfo.userId;
          this.axios
            .get(this.GLOBAL.baseUrl + "/user/getInfoById/" + id)
            .then((res1) => {
              this.user = JSON.parse(res1.data.data);
              let userId = new URLSearchParams();
              userId.append("userId", this.user.pkUserId);
              this.axios
                .post(
                  this.GLOBAL.contentUrl + "/goods/findGoodsByUserId",
                  userId
                )
                .then((res2) => {
                  this.goods = res2.data.data.Goods;
                });
            });
        });
    },
    getCommont() {
      let params = new URLSearchParams();
      params.append("goodId", this.$route.query.goodsId);
      this.axios
        .post(this.GLOBAL.contentUrl + "/comment/selectCommentsById", params)
        .then((res) => {
          this.comments = res.data.data;
          for (let i = 0; i < this.comments.length; i++) {
            if (this.comments[i].pkUserIngId == Vue.ls.get(USER_ID)) {
              this.comments[i].createdTime = true;
            }
          }
        });
    },
  },
  components: {
    NavBar,
    Alert,
  },
};
</script>
<style lang="scss" scoped>
.goods-discount {
  border: 5px solid #f6f6f6;
  span {
    color: gray;
  }
}
.goodsImg {
  width: 400px;
  height: 400px;
  transition: all 2s;
  &:hover {
    cursor: crosshair;
    /*放大倍数*/
    transform: scale(1.5);
  }
}
.img {
  height: 700px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.goodsInfo {
  margin: 50px auto;
  display: flex;
  width: 100%;
  height: 100%;
}
.goods-left {
  width: 65%;
}
.goods-right {
  background-color: #fbfbfb;
  width: 35%;
  height: 100%;
  padding: 50px;
}
.user {
  text-align: center;
  width: 50px;
}
.type {
  background-color: #26a69a;
  color: #ffffff;
  height: 35px;
  padding: 5px;
  margin: 5px;
}

.btn_minute {
  border-radius: 0.133rem 0 0 0.133rem;
  padding: 0.146rem 0.2rem 0.146rem 0.226rem;
  background: #f5f5f5;
  margin-left: 20px;
  border: 0; // 去除未选中状态边框
}
.goods-input {
  padding: 0.146rem 0.12rem;
  text-align: center;
  border: #f6f6f6;
}
.btn_add {
  border-radius: 0 0.133rem 0.133rem 0;
  padding: 0.146rem 0.226rem 0.146rem 0.2rem;
  background: #f5f5f5;
  border: 0; // 去除未选中状态边框
}
.goods-btn {
  border-radius: 8px;
  font-size: 1rem;
  width: 80%;
  height: 65px;
  padding: 10px;
  &:hover {
    transform: scale(1.05);
    cursor: pointer;
  }
}

.line {
  border-bottom: 0.1px solid #f1f1f1;
  width: 80%;
  margin: 0 auto;
}
.line1 {
  border-bottom: 0.1px solid #f1f1f1;
  width: 100%;
  margin-top: 50px;
}
.goods-info {
  width: 80%;
  margin: 0 auto;
  margin-top: 30px;
}
.comm {
  width: 80%;
  margin: 0 auto;
  margin-top: 30px;
}
.comment {
  background-color: #f6f6f6;
  border: 1px solid #e6e6e6;
  height: 35px;
  padding: 5px;
  color: gray;
}
.user_avatar {
  width: 40px;
  height: 40px;
  border-radius: 20px;
}
.border-left {
  border-left: 2px solid #26a69a;
  margin: 30px;
}
.font {
  font-weight: 100;
}
.font-s {
  font-size: 0.8rem;
  color: gray;
}
.goods-footer {
  width: 90%;
  height: 300px;
  margin: 0 auto;
}

.ag-card-bg {
  height: 100%;
  width: 200%;
  background-position: 50%;
  background-size: cover;
  position: absolute;
  top: 0;
  left: -50%;
  transition: height 0.6s;
}

.ag-shop-card_box {
  background-color: #fff;
  width: 300px;
  overflow: hidden;
  box-shadow: 0 0 10px 0 rgba(0, 0, 35, 0.25);
  border-radius: 8px;
  transition: 0.4s;
  &:hover {
    transform: scale(1.05);
  }
}

.ag-shop-card_body {
  display: block;
  height: 250px;
  width: 300px;
  background-position: 50%;
  background-size: cover;
  overflow: hidden;
  transition: 0.4s;
  position: relative;
}

.ag-shop-card-body_link {
  height: 100px;
  width: 100px;
  margin: 0 auto;
  overflow: hidden;
  z-index: 9;
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;
  border-radius: 50%;
  box-shadow: 0 10px 20px 0 rgba(0, 0, 0, 0.35);
  transform: translateY(-50%);
  transition: top 0.4s;
}

.ag-shop-card-body_img {
  max-width: 100%;
}

.ag-shop-card_footer {
  padding: 15px 30px;
  border: 0;
  background-color: #fff;
  position: relative;
}

.ag-shop-card-footer_title {
  display: block;
  line-height: 1.3;

  font-weight: 700;
  font-size: 14px;
  color: #690911;
}

.ag-shop-card-footer_products {
  display: block;
  line-height: 1.3;
  font-size: 12px;
}

.ag-shop-card-footer_link {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 35px;
  width: 35px;
  background-color: #26a69a;
  position: absolute;
  right: 10px;
  top: 15px;
  border-radius: 50%;
}

.ag-shop-card-footer_arrow {
  max-width: 16px;
}

.forms {
  background-color: #21949c;
  color: #fff;
  width: 450px;
  padding: 20px;
  border-radius: 20px;
  box-shadow: 1px #fff;
}

.infom {
  position: absolute;
  top: 400px;
  left: 40%;
  width: 20%;
}
</style>