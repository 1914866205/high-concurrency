<template>
  <v-app class="goods">
    <nav-bar></nav-bar>
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
              <div class="font" style="margin: 5px">类型</div>
              <div class="font-s" style="margin: 5px">
                {{ goodsInfo.type }}
              </div>
            </div>
            <div class="border-left">
              <div class="font" style="margin: 5px">秒杀商品</div>
              <div class="font-s" style="margin: 5px">
                秒杀成功后可在订单中查看
              </div>
            </div>
            <div class="border-left">
              <div class="font" style="margin: 5px">邮送</div>
              <div class="font-s" style="margin: 5px">免费</div>
            </div>
            <div class="border-left">
              <div class="font" style="margin: 5px">其他商品</div>
              <div class="font-s" style="margin: 5px">
                更多商品请点击卖家主页
              </div>
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
            <div>
              <img class="user_avatar" src="../../assets/images/me.jpg" />
            </div>
            <span class="font-s">用户名</span>
            <div style="margin-top: -60px; margin-left: 60px">
              <span>{{ item.content }}</span>
            </div>
            <div class="line1"></div>
          </div>
        </div>
      </div>

      <div class="goods-right">
        <div class="font" style="margin: 100px 0 20px 50px">价格</div>
        <span
          style="
            color: #26a69a;
            font-size: 2.5rem;
            margin-left: 50px;
            font-weight: 300;
          "
          >RMB￥{{ goodsInfo.price }}</span
        >

        <div style="color: gray; margin: 50px">
          <span>促 销</span>
          <span style="margin-left: 34px">{{ goodsInfo.description }}</span>
        </div>

        <div style="margin-left: 50px">
          <span style="font-size: 1rem">购买数量</span>
          <button @click="btnMinute" class="btn_minute">-</button>
          <input
            class="input"
            type="number"
            min="1"
            max="3"
            :oninput="
              'if(value>' +
              goodsInfo.count +
              ')value=' +
              goodsInfo.count +
              ';if(value.length>10)value=value.slice(0,4);if(value<0)value=0'
            "
            v-model="count"
          />
          <button @click="btnAdd" class="btn_add">+</button>
          <span style="color: gray; margin-left: 10px"
            >(库存：{{ goodsInfo.count }})</span
          >
        </div>

        <div style="margin-left: 50px; margin-top: 50px">
          <button @click="sumbitGoods()" class="btn">立即购买</button>
        </div>
      </div>
    </div>
    <div class="goods-footer">
      <div>
      <img src="../../assets/icon/yinhao.png" />
      <span style="font-size: 1.5rem">{{ user.nickname }}的所有商品</span>
      </div>

      <div>
      <v-sheet style="height:380px;" class="mx-auto mt-8" elevation="8" max-width="100%">
        <v-slide-group v-model="model" prev-icon="«" next-icon="»" class="pa-4 mt-12">
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
                    >${{
                    item.price
                  }}</span
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
import NavBar from "../../components/NavBar";
const API = require("../../utils/request.js");

export default {
  name: "goods",
  inject: ["reload"],
  data() {
    return {
      goodsInfo: [],
      count: 0,
      comments: [],
      user: [],
      model: null,
      goods: [],
    };
  },
  created: function () {
    this.getIndex();
    this.getCommont();
  },
  methods: {
    btnAdd() {
      if (this.count >= 10) {
        console.log("该宝贝不能购买更多了");
      } else {
        this.count++;
      }
    },
    goGoods(goodsId) {
      this.$router.push({ path: "/router", query: { goodsId: goodsId } });
      // this.reload()
      //   this.getIndex()
      // this.getCommont();
    },
    btnMinute() {
      if (this.count <= 0) {
        console.log("该宝贝不能减少了哟~");
      } else {
        this.count -= 1;
      }
    },
    async sumbitGoods() {
      this.url = this.GLOBAL.contentUrl + "/order/addOrder";
      this.data = {
        number: this.count,
        phone: "18851699003",
        pkGoodId: this.goodsId,
        userId: localStorage.getItem("userId"),
      };
      await API.init(_this.url, _this.data, "post");
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
              console.log(this.user.pkUserId);
              let userId = new URLSearchParams();
              userId.append("userId", this.user.pkUserId);
              this.axios
                .post(
                  this.GLOBAL.contentUrl + "/goods/findGoodsByUserId",
                  userId
                )
                .then((res2) => {
                  this.goods = res2.data.data.Goods;
                  console.log(this.goods);
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
          //   let id = res.data.data.pkUserIngId
          // console.log(res.data.data)
          // this.axios
          // .get(this.GLOBAL.baseUrl + "/user/getInfoById/" + id)
          // .then((res) => {
          //   console.log(res);
          // });
        });
    },
  },
  components: {
    NavBar,
  },
};
</script>
<style lang="scss" scoped>
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
  outline: none; // 去除选中状态边框
}
.input {
  padding: 0.146rem 0.12rem;
  text-align: center;
  border: #f6f6f6;
  outline: none;
}
.btn_add {
  border-radius: 0 0.133rem 0.133rem 0;
  padding: 0.146rem 0.226rem 0.146rem 0.2rem;
  background: #f5f5f5;
  border: 0; // 去除未选中状态边框
  outline: none; // 去除选中状态边框
}
.btn {
  background-color: #26a69a;
  border-radius: 8px;
  border: none;
  color: #ffffff;
  font-size: 1rem;
  width: 80%;
  height: 65px;
  padding: 10px;
  &:hover {
    transform: scale(1.05);
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
</style>