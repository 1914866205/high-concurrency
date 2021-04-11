<template>
  <v-app>
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
        <div class="font" style="margin: 50px 0 20px 50px">价格</div>
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
          <input class="input" type="text" size="1" v-model="count" />
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
        <span style="font-size: 1.5rem">{{ user.nickname}}的其他商品</span>
      </div>
  </v-app>
</template>

<script>
import NavBar from "../../components/NavBar";
const API = require("../../utils/request.js");
export default {
  name: "HomePage",
  data() {
    return {
      goodsInfo: [],
      count: 0,
      comments: [],
      user: [],
    };
  },
  created: function () {
    let params = new URLSearchParams();
    params.append("goodsId", this.$route.query.goodsId);
    this.axios
      .post(this.GLOBAL.contentUrl + "/goods/findGoodsById", params)
      .then((res) => {
        this.goodsInfo = res.data.data.Goods;
        let id = this.goodsInfo.userId;
        this.axios
          .get(this.GLOBAL.baseUrl + "/user/getInfoById/" + id)
          .then((res) => {
            this.user = JSON.parse(res.data.data);
            console.log(this.user);
          });
      });
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
    goIndex() {},
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
}
.img {
  height: 700px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.goodsInfo {
  margin: 0 auto;
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
  color: #ffffff;
  font-size: 1rem;
  width: 80%;
  height: 65px;
  padding: 10px;
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
  width:100%;
  height: 300px;
}
</style>