<template>
  <v-app>
    <nav-bar></nav-bar>
    <div class="goodsInfo">
      <img class="goodsImg" :src="goodsInfo.image" />
      <div style="margin: 20px">
        <div style="display: flex">
          <span class="type">{{ goodsInfo.type }}</span>
          <h1>{{ goodsInfo.description }} {{ goodsInfo.goodName }}</h1>
        </div>

        <div class="goodsBorder">
          <div class="goodsMiaosha">
            <img
              style="width: 25px; height: 25px"
              src="../../assets/icon/naozhong.png"
            />
            <span style="margin-left: 8px">骸冰秒杀</span>
          </div>
          <div>
            <span style="color: gray; margin: 10px">秒 杀 价</span>
            <span style="color: #26a69a; font-size: 2rem"
              >￥ {{ goodsInfo.price }}</span
            >
          </div>
          <div style="color: gray; margin: 10px">
            <span>促 销</span>
            <span style="margin-left: 34px"
              >第1-100名商品1折销售，第101-500名5折，第501-1000名8折，第1001名后商品原价销售</span
            >
          </div>
        </div>

        <div style="margin-top: 30px; margin-left: 5px">
          <span style="color: gray">购买数量</span>
          <button @click="btnMinute" class="btn_minute">-</button>
          <input class="input" type="text" size="1" v-model="count" />
          <button @click="btnAdd" class="btn_add">+</button>
          <span style="color: gray; margin-left: 10px"
            >(库存：{{ goodsInfo.count }})</span
          >
        </div>

        <div style="margin-top: 30px; margin-left: 5px">
          <button @click="sumbitGoods()" class="btn">立即购买</button>
        </div>
      </div>
    </div>
    <div class="comm">
      <div class="comment">商品评价</div>

      <div v-for="(item,index) in comments" :key="index" style="margin-top: 30px">
        <img class="user_avatar" src="../../assets/images/me.jpg" />
        <div style="margin-top: -40px">
          <span style="margin: 50px">用户名</span>
          <span style="margin-left: 100px">{{item.content}}</span>
        </div>
        <div class="line"></div>
      </div>
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
    };
  },
  mounted: function () {
    this.goodsInfo = this.$route.query.goodsInfo;
    localStorage.setItem("goods", this.goodsInfo);
    console.log(localStorage.getItem("goods"));
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
    getCommont() {
      let params = new URLSearchParams();
      params.append("goodId", this.goodsInfo.pkGoodId);
      this.axios.post(
        this.GLOBAL.contentUrl + "/comment/selectCommentsById",
        params
      ).then((res) => {
        
        this.comments = res.data.data
        console.log(this.comments);
      })
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
.goodsInfo {
  width: 85%;
  margin: 0 auto;
  margin-top: 40px;
  display: flex;
  border: 2px solid #f5f5f5;
}
.type {
  background-color: #26a69a;
  color: #ffffff;
  height: 35px;
  padding: 5px;
  margin: 5px;
}
.goodsBorder {
  width: 100%;
  height: 150px;
  background-color: #f6f6f6;
  margin-top: 20px;
  margin-left: 5px;
}
.goodsMiaosha {
  background-color: #26a69a;
  color: #ffffff;
  height: 30px;
  width: 100%;
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
  color: #ffffff;
  font-size: 1.2rem;
  width: 100px;
  height: 50px;
  padding: 10px;
}
.line {
  border: 0.1px solid rgb(240, 237, 237);
  width: 100%;
  margin-top: 30px;
}
.comm {
  width: 90%;
  margin: 100px auto;
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
</style>