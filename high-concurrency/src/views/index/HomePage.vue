<template>
  <v-app style="background-color: #f3f3f3; width: 100%">
    <nav-bar></nav-bar>
    <div class="solid">
      <link rel="preconnect" href="https://fonts.gstatic.com" />
      <link
        href="https://fonts.googleapis.com/css2?family=Montserrat:wght@800&display=swap"
        rel="stylesheet"
      />

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

      <!-- <div class="miaosha">
        <div class="goods">
          <div
            style="margin: 10px; width: 30%"
            v-for="(item, index) in goods"
            :key="index"
          >
            <v-card class="ma-1" height="370" @click="goGoods(item.pkGoodId)">
              <img class="goodsImg" :src="item.image" />

              <v-card-title>{{ item.goodName }}</v-card-title>

              <v-card-text>
                <v-row align="center" class="mx-0"> </v-row>

                <div class="my-2 subtitle-1">
                   <span class="type">{{ item.type }}</span> -->
      <!-- <span> ${{ item.price }}</span>
                </div>
                <div>倒计时</div>
              </v-card-text>
            </v-card>
          </div>
        </div>
      </div> -->
    </div>

    <div class="goods">
      <div
        class="shop-card"
        v-for="(item, index) in goods"
        :key="index"
        @click="goGoods(item.pkGoodId)"
      >
        <div class="title">
          {{ item.goodName }}
        </div>
        <div class="desc">Men's training shoe</div>
        <div class="slider">
          <figure data-color="#E24938, #A30F22">
            <img class="goodsImg" :src="item.image" />
          </figure>
        </div>

        <div class="cta">
          <div class="price">${{ item.price }}</div>
          <button class="btn">Add to cart<span class="bg"></span></button>
        </div>
      </div>
      <div class="bg"></div>
    </div>
  </v-app>
</template>


<script>
import NavBar from "../../components/NavBar";
function background(c1, c2) {
  return {
    background: "-moz-linear-gradient(15deg, " + c1 + " 50%, " + c2 + " 50.1%)",
    background: "-o-linear-gradient(15deg, " + c1 + ", " + c2 + " 50.1%)",
    background: "-webkit-linear-gradient(15deg, " + c1 + " 50%, " + c2 + ")",
    background: "-ms-linear-gradient(15deg, " + c1 + " 50%, " + c2 + " 50.1%)",
    background: "linear-gradient(15deg, " + c1 + " 50%," + c2 + " 50.1%)",
  };
}

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
  created: function () {
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
    goGoods(goodsId) {
      this.$router.push({ path: "/goods", query: { goodsId: goodsId } });
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
  font-family: "Montserrat", sans-serif;
  color: #fff;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 50vh;
  background: #26a69a; /* fallback for old browsers */
  background: -webkit-linear-gradient(
    to right,
    #2894af,
    #26a69a
  ); /* Chrome 10-25, Safari 5.1-6 */
  background: linear-gradient(
    to right,
    #2894af,
    #26a69a
  ); /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
}
.grp {
  position: relative;
  height: 150px;
  width: 400px;
}

.card {
  height: 150px;
  width: 400px;
  background: #ffffff40;
  border: 1px solid #ffffff20;
  border-radius: 10px;
  backdrop-filter: blur(4px);
  z-index: 999;
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

.goods {
  width: 85%;
  margin: 10px auto;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}

.shop-card {
  width: 290px;
  margin-top: 20px;
  background: #f5f5f5;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.3);
  overflow: hidden;
  border-radius: 5px;
  padding: 25px;
  text-align: center;
  z-index: 2;

  figure {
    margin: 0;
    padding: 0;
    overflow: hidden;
    outline: none !important;

    & img {
      // margin: -95px 0 -60px;
      width: 100%;
      height: 150px;
    }
  }

  .title {
    font-weight: 900;
    text-transform: uppercase;
    font-size: 30px;
    color: #23211f;
    margin-bottom: 5px;
  }

  .desc {
    font-size: 17px;
    opacity: 0.8;
    margin-bottom: 3px;
  }

  .cta {
    padding: 20px 20px 5px;

    &::after {
      content: "";
      display: table;
      clear: both;
    }
  }

  .price {
    float: left;
    color: #ff3100;
    font-size: 22px;
    font-weight: 900;
    padding-top: 2px;
    transition: color 0.3s ease-in-out;
    margin-top: 4px;
  }

  .btn {
    position: relative;
    z-index: 1;
    float: right;
    display: inline-block;
    font-size: 13px;
    font-weight: 900;
    text-transform: uppercase;
    color: #ff3100;
    padding: 12px 12px;
    cursor: pointer;
    transition: all 0.3s ease-in-out;
    line-height: 0.95;
    border: none;
    background: none;
    outline: none;
    border: 1px solid #ff3100;
    border-radius: 20px;
    overflow: hidden;

    & .bg {
      width: 101%;
      height: 101%;
      display: block !important;
      z-index: -1;
      opacity: 0;
      transition: all 0.3s ease-in-out;
      background: linear-gradient(135deg, #a61322, #d33f34);
    }

    &:hover {
      color: #fff !important;
      border: 1px solid transparent !important;

      & .bg {
        opacity: 1;
      }
    }
  }
}
</style>
