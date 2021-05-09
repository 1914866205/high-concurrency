<template>
  <v-app style="width: 100%">
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
    <nav-bar></nav-bar>
    <div class="bghome" id="home" style="height: 855px">
      <div>
        <h1 class="home_title text-center">Haibing shops</h1>
      </div>
    </div>
    <div class="mt-8"></div>
    <div class="neighborhood-title">
      <h4>秒杀商品</h4>
    </div>
    <div class="goods">
      <div
        class="shop-card"
        v-for="(item, index) in miaoshaGoods"
        :key="index"
        @click="goGoods(item.goodId, item.day)"
      >
        <div class="slider">
          <figure
            @mouseover="mouseOver"
            @mouseleave="mouseLeave"
            data-color="#E24938, #A30F22"
          >
            <img class="goodsImg" :src="item.image" />
            <transition name="slideFade">
              <div class="goodname" transiton="slideFade" v-show="showTitle">
                {{ item.goodName }}
              </div>
            </transition>
          </figure>
        </div>

        <div class="cta">
          <div class="price">${{ item.price }}</div>
          <div class="cta-text" v-if="item.day <= 0 && item.count > 0">秒杀已开始</div>
          <div class="cta-text" v-if="item.count == 0">秒杀已结束</div>
          <div class="cta-text" v-if="item.day > 0">{{ daojishi(item.day) }}</div>
        </div>
      </div>
    </div>
    <div class="neighborhood-title">
      <h4>其他商品</h4>
    </div>
    <div class="goods">
      <div
        class="shop-card"
        v-for="(item, index) in goods"
        :key="index"
        @click="goGoods(item.goodId)"
      >
        <div class="slider">
          <figure
            @mouseover="mouseOver"
            @mouseleave="mouseLeave"
            data-color="#E24938, #A30F22"
          >
            <img class="goodsImg" :src="item.image" />
            <transition name="slideFade">
              <div class="goodname" transiton="slideFade" v-show="showTitle">
                {{ item.goodName }}
              </div>
            </transition>
          </figure>
        </div>

        <div class="cta">
          <div class="price">${{ item.price }}</div>
          <button class="btn">查看详情<span class="bg"></span></button>
        </div>
      </div>
    </div>

    <footer>
      <div class="footer-stuff">
        <p><strong>@骸冰</strong></p>
      </div>
    </footer>
  </v-app>
</template>


<script>
import NavBar from "../../components/NavBar";
export default {
  name: "HomePage",
  data() {
    return {
      model: null,
      ticker: null,
      goods: [],
      miaoshaGoods: [],
      showTitle: false,
    };
  },
  created: function () {
    let params = new URLSearchParams();
    params.append("currentPage", "1");
    params.append("pageSize", "10");
    this.axios
      .post(this.GLOBAL.contentUrl + "/goods/findAllGoods", params)
      .then((res) => {
        let goods = res.data.data.Goods.content;
        let arr = [];
        let miaoshaArr = [];
        for (let i = 0; i < goods.length; i++) {
          if (goods[i].day != null) {
            let time = new Date(goods[i].day).getTime() - new Date().getTime();
            goods[i].day = time;
            miaoshaArr.push(goods[i]);
            if (this.ticker) {
              //这一段是防止进入页面出去后再进来计时器重复启动
              clearInterval(this.ticker);
            }
            this.beginTimer(); //启动计时器减指定字段的时间
          } else {
            arr.push(goods[i]);
          }
        }
        this.goods = arr;
        this.miaoshaGoods = miaoshaArr;
        console.log(this.miaoshaGoods);
      });
  },
  methods: {
    daojishi(time) {
      if (time >= 0) {
        let d = Math.floor(time / 1000 / 60 / 60 / 24);
        let h = Math.floor((time / 1000 / 60 / 60) % 24);
        let m = Math.floor((time / 1000 / 60) % 60);
        let s = Math.floor((time / 1000) % 60);
        return "还有" + d + "天" + h + "时" + m + "分" + s + "秒";
      }
    },
    beginTimer() {
      //这个计时器是每秒减去数组中指定字段的时间
      this.ticker = setInterval(() => {
        for (let i = 0, len = this.miaoshaGoods.length; i < len; i++) {
          const item = this.miaoshaGoods[i];
          if (item.day > 0) {
            item.day = item.day - 1000;
          }
        }
      }, 1000);
    },
    goGoods(goodsId, day) {
      this.$router.push({
        path: "/goods",
        query: { goodsId: goodsId, day: day },
      });
    },
    mouseOver() {
      this.showTitle = true;
    },
    mouseLeave() {
      this.showTitle = false;
    },
  },
  components: {
    NavBar,
  },
};
</script>
<style lang="scss" scoped>
.bghome {
  padding-top: 100px;
  background: url("https://images.unsplash.com/photo-1488120299791-f9241bdf10c9?dpr=1&auto=format&fit=crop&w=1500&h=917&q=80&cs=tinysrgb&crop=")
    no-repeat center center;
  background-size: cover;
}
.home_title {
  color: #f9f1e9;
  font-family: "Marck Script", cursive;
  position: absolute;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.4);
  margin: 0;
  transform: translate(-50%, -80%);
  top: 500px;
  left: 50%;
  font-size: 4rem;
}
.home_motto {
  display: none;
  color: #f9f1e9;
  font-size: 2.5em;
  text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.4);
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, 10%);
}
.goods {
  width: 100%;
  margin: 20px auto;
  display: flex;
  justify-content: flex-start;
  flex-wrap: wrap;
  align-content: flex-start; //紧揍排列
}

.shop-card {
  width: 290px;
  height: 320px;
  background: #f5f5f5;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.3);
  overflow: hidden;
  margin: 2%;
  border-radius: 5px;
  // padding: 25px;
  text-align: center;
  z-index: 2;
  &:hover {
    transform: scale(1.05);
  }
  figure {
    & img {
      // margin: -95px 0 -60px;
      width: 100%;
      height: 230px;
    }
  }

  .goodname {
    position: absolute;
    top: 150px;
    left: 10px;
    color: #fff;
    font-size: 0.8rem;
    height: 30px;
    padding: 5px;
    background: rgba(0, 0, 0, 0.25);
    box-shadow: 0 8px 32px 0 rgba(31, 38, 135, 0.37);
    backdrop-filter: blur(2.5px);
    -webkit-backdrop-filter: blur(2.5px);
    border-radius: 5px;
    border: 1px solid rgba(255, 255, 255, 0.18);
  }
  .slideFade-enter {
    animation: slideFade-dialog-fade-in 0.5s ease;
  }
  .slideFade-leave {
    animation: slideFade-dialog-fade-out 0.5s ease forwards;
  }
  .slideFade-enter-active {
    animation: slideFade-dialog-fade-in 0.5s ease;
  }
  .slideFade-leave-active {
    animation: slideFade-dialog-fade-out 0.5s ease forwards;
  }

  @keyframes slideFade-dialog-fade-in {
    0% {
      transform: translate3d(
        -100%,
        0,
        0
      ); //修改这个可以控制，上下左右动画，例如：100%为从右到左
      opacity: 1;
    }
    100% {
      transform: translate3d(0, 0, 0);
      opacity: 1;
    }
  }
  @keyframes slideFade-dialog-fade-out {
    0% {
      transform: translate3d(0, 0, 0);
      opacity: 1;
    }
    100% {
      transform: translate3d(-100%, 0, 0);
      opacity: 1;
    }
  }

  .cta {
    padding: 20px 20px;
  }

  .cta-text {
    padding: 5px;
    float: right;
    background: #26a69a;
    color: #fff;
  }
  .price {
    float: left;
    color: #26a69a;
    font-size: 22px;
    font-weight: 900;
    transition: color 0.3s ease-in-out;
  }

  .btn {
    position: relative;
    z-index: 1;
    float: right;
    display: inline-block;
    font-size: 13px;
    font-weight: 900;
    text-transform: uppercase;
    color: #26a69a;
    padding: 12px 12px;
    cursor: pointer;
    transition: all 0.3s ease-in-out;
    line-height: 0.95;
    border: none;
    background: none;
    outline: none;
    border: 1px solid #26a69a;
    border-radius: 20px;
    overflow: hidden;

    & .bg {
      width: 101%;
      height: 101%;
      display: block !important;
      z-index: -1;
      opacity: 0;
      transition: all 0.3s ease-in-out;
    }

    &:hover {
      color: #26a69a !important;
      border: 1px solid transparent !important;

      & .bg {
        opacity: 1;
      }
    }
  }
}

.neighborhood-title {
  font-size: 32px;
  float: left;
  margin: 30px;
}

footer {
  background: #26a69a;
  color: #fff;
  position: relative;
  bottom: 0;
  width: 100%;
  margin-top: 30px;
}

.footer-stuff {
  margin: 20px auto;
  text-align: center;
}
</style>
