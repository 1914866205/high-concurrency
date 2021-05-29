<template>
  <v-card class="navBarWrap" style="overflow-x: hidden" flat height="64px" tile>
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
              @click="goGoods(item.goodId)"
              v-text="item.goodName"
            ></v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>

      <v-tooltip v-if="isLogin" bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-icon
            @click="goPublish()"
            class="mr-4"
            color="teal"
            dark
            v-bind="attrs"
            v-on="on"
          >
            mdi-plus-circle-outline
          </v-icon>
        </template>
        <span>发布商品</span>
      </v-tooltip>

      <v-tooltip v-if="isLogin" bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-icon
            @click="goOrder()"
            class="mr-4"
            color="teal"
            dark
            v-bind="attrs"
            v-on="on"
          >
            mdi-file-document-outline
          </v-icon>
        </template>
        <span>我的订单</span>
      </v-tooltip>

      <v-tooltip v-if="isLogin" bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-icon
            @click="goPut()"
            class="mr-4"
            color="teal"
            dark
            v-bind="attrs"
            v-on="on"
          >
            mdi-cog-outline
          </v-icon>
        </template>
        <span>商品策略</span>
      </v-tooltip>

      <v-tooltip v-if="isLogin" bottom>
        <template v-slot:activator="{ on, attrs }">
          <v-icon
            @click="logout()"
            class="mr-4"
            color="teal"
            dark
            v-bind="attrs"
            v-on="on"
          >
            mdi-logout-variant
          </v-icon>
        </template>
        <span>退出登录</span>
      </v-tooltip>

      <!-- <v-menu offset-y>
        <template v-slot:activator="{ attrs, on }"> -->
      <!-- <div v-if="isLogin" class="user" v-bind="attrs" v-on="on"> -->
      <div @click="goUser()" v-if="isLogin" class="user button-hand">
        <img class="image" :src="avatar" />
      </div>
      <div
        v-else
        @click="goLogin()"
        class="user button-hand"
        style="color: #26a69a"
      >
        未登录，请先登录
      </div>
    </v-toolbar>
  </v-card>
</template>
   
<script>
import { goodsSearch } from "@/utils/request.js";
import Vue from "vue";
import {
  ACCESS_TOKEN,
  USER_PHONE,
  USER_INFO,
  USER_ID,
  USER_AVATAR,
} from "@/store/mutation-types";
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
    // window.addEventListener("scroll", this.watchScroll);
  },
  created: function () {
    if (Vue.ls.get(USER_AVATAR) != null) {
      this.avatar = Vue.ls.get(USER_AVATAR);
      this.isLogin = true;
    } else {
      this.isLogin = false;
    }
  },
  methods: {
    goUser() {
      this.$router.push("/message");

      //this.overlay = !this.overlay;
    },
    goOrder() {
      this.$router.push("/order");
    },
    goPut() {
      this.$router.push("/put");
    },
    logout() {
      Vue.ls.remove(ACCESS_TOKEN);
      Vue.ls.remove(USER_PHONE);
      Vue.ls.remove(USER_INFO);
      Vue.ls.remove(USER_ID);
      Vue.ls.remove(USER_AVATAR);
      this.$router.push("/login");
    },
    goPublish() {
      this.$router.push("/publish");
    },
    goLogin() {
      this.$router.push("/login");
    },
    goGoods(id) {
      this.$router.push({ path: "/goods", query: { goodsId: id } });
    },
    search() {
      let data = {
        content: this.content,
        currentPage: 1,
        pageSize: 10,
      };
      goodsSearch(data).then((res) => {
        this.goods = res.data.Goods.content;
      });
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
