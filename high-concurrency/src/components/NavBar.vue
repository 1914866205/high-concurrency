<template>
  <v-card class="static" flat height="70px" tile>
    <v-toolbar>
      <v-toolbar-title
      @click="goHome()"
        style="color: #26a69a; font-weight: 300; font-size: 1.75rem"
        >骸冰商城</v-toolbar-title
      >

      <v-menu offset-y>
        <template v-slot:activator="{ attrs, on }">
          <div class="input" v-bind="attrs" v-on="on">
            <input
              class="inputBorder"
              type="text"
              v-model="content"
              placeholder=" search.."
              maxlength="32"
              lay-verify="required"
              v-on:input="search"
            />
            <div @click="search()" class="search">
              <img class="icon" src="@/assets/icon/search.png" />
            </div>
          </div>
        </template>
        <v-list v-model="goods">
          <v-list-item v-for="(item, index) in goods" :key="index" link>
            <v-list-item-title
              @click="goGoods(goods,index)"
              v-text="item.goodName"
            ></v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>

      <v-menu offset-y>
        <template v-slot:activator="{ attrs, on }">
          <div class="user" v-bind="attrs" v-on="on">
            <img class="image" :src="avatar" />
          </div>
        </template>
        <v-list v-if="goods !== null">
          <v-list-item v-for="item in items" :key="item" link>
            <v-list-item-title
              @click="goUser(item)"
              v-text="item"
            ></v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-toolbar>
  </v-card>
</template>
   
<script>
const API = require("../utils/request.js")
export default {
  data() {
    return {
      items: ["用户中心", "订单中心","发布商品"],
      avatar: localStorage.getItem("avatar"),
      content: "",
      goods:''
    };
  },
  computed:{
  },
  methods: {
    goUser(item) {
      if (item === "用户中心") {
        this.$router.push("/message");
      } else if(item === "订单中心"){
        this.$router.push("/order");
      } else {
        this.$router.push("/put");
      }
    },
    goHome(){
      this.$router.push("/");
    },
    goGoods(goods,index) {
      this.$router.push({ path: "/goods", query: { goodsInfo: goods[index] } });
    },
    async search() {

        this.url= this.GLOBAL.contentUrl + "/goods/search",
        this.data= {
          content: this.content,
          currentPage: 1,
          pageSize: 10,
        },
        this.result = await API.init(this.url,this.data,"post")
        this.goods = this.result.data.Goods.content
      
    },
  },
};
</script>

<style scoped lang="scss">
.input {
  margin: auto;
  border: 3px solid #26a69a;
  border-radius: 20px;
  width: 500px;
  height: 50px;
  display: flex;
  position: relative;
}
.input::before {
  transform: scaleX(0);
  transform-origin: bottom right;
}
.input:hover::before {
  transform: scaleX(1);
  transform-origin: bottom left;
}
.input::before {
  content: " ";
  display: block;
  position: absolute;
  top: 0; right: 0; bottom: 0; left: 0;
  inset: 0 0 0 0;
  background: #f6f6f6;
  border-radius: 20px;
  z-index: -1;
  transition: transform .3s ease;
}

.inputBorder {
  width: 450px;
  height: 45px;
  margin-left: 20px;
  border: 0; // 去除未选中状态边框
  outline: none; // 去除选中状态边框
  background-color: rgba(0, 0, 0, 0);
}
.user {
  position: absolute;
  right: 30px;
}
.image {
  width: 50px;
  height: 50px;
  border-radius: 50px;
  border: 3px solid #26a69a;
}
.search {
  width: 50px;
  height: 50px;
  background-color: #26a69a;
  border-radius: 0 20px 20px 0;
  margin: -3px;
}
.icon {
  width: 30px;
  height: 30px;
  margin: 10px;
}
// .static {
//   position: relative;
//   top:0;
// }
</style>
