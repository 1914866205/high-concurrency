<template>
  <v-card flat height="75px" tile>
    <v-toolbar>
      <v-toolbar-title
        style="color: #26a69a; font-weight: 300; font-size: 1.75rem"
        >骸冰商城</v-toolbar-title
      >
      <div class="input">
        <input class="inputBorder" type="text" placeholder="    search.." />
        <div @click="goSearch()" class="search">
          <img class="icon" src="@/assets/icon/search.png" />
        </div>
      </div>

      <v-menu offset-y>
        <template v-slot:activator="{ attrs, on }">
          <div class="user" v-bind="attrs" v-on="on">
            <img class="image" src="@/assets/images/me.jpg" />
          </div>
        </template>
        <v-list>
          <v-list-item v-for="item in items" :key="item" link>
            <v-list-item-title @click="goUser(item)" v-text="item"></v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-toolbar>
  </v-card>
</template>
   
<script>
export default {
  data() {
    return {
      items: ['用户中心','订单中心'],
      userId:localStorage.getItem("userId"),
      avatar:''
    };
  },
  mounted:function() {
    // this.getUser()
    // this.axios.get(this.GLOBAL.baseUrl + '/user/getInfoById/'+this.userId).then((res) => {
    //     localStorage.setItem("avatar", res.data.data.avatar);
    //   })
  },
  methods: {
    goUser(item) {
      if(item === '用户中心'){
        this.$router.push("/message");
      }else {
        this.$router.push("/order");
      }
      
    },
    goSearch() {
      console.log(this.userId)
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
}
.inputBorder {
  width: 450px;
  height: 45px;
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
</style>
