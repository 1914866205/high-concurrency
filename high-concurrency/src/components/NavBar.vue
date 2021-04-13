<template>
  <v-card class="navBarWrap" flat height="64px" tile>
    <link
      href="https://fonts.googleapis.com/css?family=Abril+Fatface|Comfortaa|Yellowtail"
      rel="stylesheet"
    />
    <link
      href="https://fonts.googleapis.com/css?family=Marck+Script|Monoton|Oleo+Script|Oswald"
      rel="stylesheet"
    />
    <link
      href="https://fonts.googleapis.com/css?family=Kurale"
      rel="stylesheet"
    />
    <v-toolbar>
      <a href="/" class="nav-title">Haibing shops</a>
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
              @click="goGoods(item.pkGoodId)"
              v-text="item.goodName"
            ></v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>

      <v-menu offset-y>
        <template v-slot:activator="{ attrs, on }">
          <div v-if="isLogin" class="user" v-bind="attrs" v-on="on">
            <img class="image" :src="avatar" />
          </div>
          <div v-else @click="goLogin()" class="user" style="color: #26a69a">
            未登录，请先登录
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

    <!-- <v-overlay :z-index="zIndex" :value="overlay">
      <v-btn class="white--text" color="teal" @click="overlay = false">
        Hide Overlay
      </v-btn>
    </v-overlay> -->
  </v-card>
</template>
   
<script>
const API = require("../utils/request.js");
export default {
  data() {
    return {
      items: ["用户中心", "订单中心", "我的发布", "退出登录"],
      avatar: "",
      content: "",
      goods: "",
      overlay: false,
      zIndex: 0,
      isLogin: false,
    };
  },
  computed: {},
  mounted() {
    // 事件监听滚动条
    window.addEventListener("scroll", this.watchScroll);
  },
  created: function () {
    if (localStorage.getItem("avatar") != null) {
      this.avatar = localStorage.getItem("avatar");
      this.isLogin = true;
    } else {
      this.isLogin = false;
    }
  },
  methods: {
    goUser(item) {
      if (item === "用户中心") {
        this.$router.push("/message");
      } else if (item === "订单中心") {
        this.$router.push("/order");
      } else if (item === "我的发布") {
        this.$router.push("/put");
        //this.overlay = !this.overlay;
      } else {
        localStorage.clear("user");
        localStorage.clear("userId");
        localStorage.clear("avatar");
        this.$router.push("/login");
      }
    },
    watchScroll() {
      // var scrollTop =
      //   window.pageYOffset ||
      //   document.documentElement.scrollTop ||
      //   document.body.scrollTop;
      // //  当滚动超过 50 时，实现吸顶效果
      // if (scrollTop > 70) {
      //   this.navBarFixed = true;
      // } else {
      //   this.navBarFixed = false;
      // }
    },
    goLogin() {
      this.$router.push("/login");
    },
    goGoods(id) {
      this.$router.push({ path: "/goods", query: { goodsId: id } });
    },
    async search() {
      (this.url = this.GLOBAL.contentUrl + "/goods/search"),
        (this.data = {
          content: this.content,
          currentPage: 1,
          pageSize: 10,
        }),
        (this.result = await API.init(this.url, this.data, "post"));
      this.goods = this.result.data.Goods.content;
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
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  inset: 0 0 0 0;
  background: #f6f6f6;
  border-radius: 20px;
  z-index: -1;
  transition: transform 0.3s ease;
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
  float: right;
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
.navBarWrap {
  position: fixed;
  top: 0;
  z-index: 999;
  width: 100%;
  background-color: #f6f6f6;
}
.nav-title {
  font-family: "Abril Fatface", cursive;
  text-decoration: none;
  color: #26a69a;
  font-weight: 300;
  font-size: 1.5rem;
}
.nav-title:hover {
  background: #fff;
  color: #000;
}
</style>
