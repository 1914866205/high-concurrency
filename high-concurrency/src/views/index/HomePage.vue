<template>
  <v-app style="background-color: #f3f3f3; width: 100%">
    <nav-bar></nav-bar>
    <div>
      <div class="solid">
        <!-- <v-card flat tile>
          <v-window v-model="onboarding" reverse>
            <v-window-item v-for="n in length" :key="`card-${n}`">
              <v-card color="teal lighten-1" height="300">
                <v-row class="fill-height" align="center" justify="center">
                  <img src="../../assets/images/nav1.jpg" />
                </v-row>
              </v-card>
            </v-window-item>
          </v-window>

          <v-card-actions class="justify-space-between">
            <v-btn text @click="prev">
              <img src="../../assets/icon/hou.png" />
            </v-btn>
            <v-item-group v-model="onboarding" class="text-center" mandatory>
              <v-item
                v-for="n in length"
                :key="`btn-${n}`"
                v-slot="{ active, toggle }"
              >
                <v-btn :input-value="active" icon @click="toggle">
                  <img src="../../assets/icon/dain.png" />
                </v-btn>
              </v-item>
            </v-item-group>
            <v-btn text @click="next">
              <img
                style="width:30px;height30px;"
                src="../../assets/icon/prev.png"
              />
            </v-btn>
          </v-card-actions>
        </v-card> -->
        <link rel="preconnect" href="https://fonts.gstatic.com">
<link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@800&display=swap" rel="stylesheet"> 

<div class="grp">
  <h1>HAIBINGSHOP</h1>
  <div class="card"></div>
  <div class="squares">
    <div class="square square-1"></div>
    <div class="square square-2"></div>
    <div class="square square-3"></div>
    <div class="square square-4"></div>
  </div>
</div>
      </div>

      <div class="miaosha">
        <!-- <div class="miaosha-border">
          <h1 style="margin-top: 20px">骸冰秒杀</h1>
          <img class="dian" src="../../assets/icon/dian.png" />
          <div class="daojishi">
            <p class="time-border margin">倒</p>
            <p class="margin">:</p>
            <p class="time-border margin">计</p>
            <p class="margin">:</p>
            <p class="time-border margin">时</p>
          </div>
        </div> -->
        <div class="goods">
          <div
            style="margin: 10px; width: 30%"
            v-for="(item, index) in goods"
            :key="index"
          >
            <v-card
              class="ma-1"
              height="370"
              @click="goGoods(item)"
            >
              <img class="goodsImg" :src="item.image" />

              <v-card-title
                >{{ item.description }} {{ item.goodName }}</v-card-title
              >

              <v-card-text>
                <v-row align="center" class="mx-0"> </v-row>

                <div class="my-2 subtitle-1">
                  <!-- <span class="type">{{ item.type }}</span> -->
                  <span> ${{ item.price }}</span>
                </div>
                <div>倒计时</div>
              </v-card-text>
            </v-card>
          </div>
        </div>
      </div>
    </div>
  </v-app>
</template>


<script>
import NavBar from "../../components/NavBar";
export default {
  name: "HomePage",
  data() {
    return {
      length: 3,
      onboarding: 0,
      model: null,
      goods: [],
      loading: false,
      selection: 1,
    };
  },
  mounted: function () {
    let params = new URLSearchParams();
    params.append("currentPage", "1");
    params.append("pageSize", "10");
    this.axios
      .post(this.GLOBAL.contentUrl + "/goods/findAllGoods", params)
      .then((res) => {
        this.goods = res.data.data.Goods.content;
        console.log(this.goods);
      });
  },
  methods: {
    reserve() {
      this.loading = true;

      setTimeout(() => (this.loading = false), 2000);
    },

    next() {
      this.onboarding =
        this.onboarding + 1 === this.length ? 0 : this.onboarding + 1;
    },
    prev() {
      this.onboarding =
        this.onboarding - 1 < 0 ? this.length - 1 : this.onboarding - 1;
    },
    goGoods(goods) {
      this.$router.push({ path: "/goods", query: { goodsInfo: goods } });
    },
  },
  components: {
    NavBar,
  },
};
</script>
<style lang="scss" scoped>
.solid {
  width: 90%;
  margin: auto;
  font-family: 'Montserrat', sans-serif;
  color: #FFF;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50vh;
  background: #26a69a;  /* fallback for old browsers */
  background: -webkit-linear-gradient(to right, #2894af, #26a69a);  /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(to right, #2894af, #26a69a); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
}
.grp {
  position: relative;
  height: 150px;
  width: 400px;
}

.card {
  height: 150px;
  width: 400px;
  background: #FFFFFF40;
  border: 1px solid #FFFFFF20;
  border-radius: 10px;
  backdrop-filter: blur(4px);
  position: absolute;
  box-shadow: 0 0 40px 0 #00000040;
}

h1 {
  margin: 0;
  position: absolute;
  font-size: 70px;
  letter-spacing: 2px;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
}

.square {
  border-radius: 10px;
  border: 1px solid #00000020;
  background: #ffffff40;
  position: absolute;
  box-shadow: 0 0 40px 0 #00000040;
  backdrop-filter: blur(4px);
  
  &-1 {
    height: 40px;
    width: 40px;
    bottom: -20px;
    left: 40px;
  }
  
  &-2 {
    height: 60px;
    width: 60px;
    top: -30px;
    right: 30px;
  }
  
  &-3 {
    height: 30px;
    width: 30px;
    border-radius: 50%;
    top: -40px;
    left: 40px;
  }
  
  &-4 {
    height: 20px;
    width: 20px;
    border-radius: 50%;
    bottom: -40px;
    right: -20px;
  }
}

.daojishi {
  display: flex;
}
.miaosha {
  width: 85%;
  margin: auto;
  display: flex;
  margin-top: 10px;
}
.miaosha-border {
  height: 400px;
  width: 300px;
  background-color: #26a69a;
  text-align: center;
}
.daojishi {
  border: 100px;
  margin: 120px 50px 50px 50px;
}
.margin {
  margin: 10px;
}
.time-border {
  width: 50px;
  height: 30px;
  background: #ffffff;
}
.dian {
  width: 50px;
  height: 50px;
  margin-top: 80px;
}
.goodsImg {
  width: 100%;
  height: 240px;
  border: 1px solid #f6f6f6;
  
}
.type {
  background-color: #26a69a;
  color: #ffffff;
  padding: 5px;
  width: 40px;
}
.goods {
  display: flex;
  justify-content: space-between;
}
.goods-border {
  border-radius: 50px;
  background: #ffffff;
  box-shadow: 26px 26px 52px #d9d9d9, -26px -26px 52px #ffffff;
}
</style>
