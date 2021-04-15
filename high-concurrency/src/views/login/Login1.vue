<template>
  <div class="body">
    <div class="container right-panel-active">
      <!-- Sign Up -->
      <div class="container__form container--signup">
        <v-form action="#" class="form" id="form1">
          <h2 class="form__title">注 册</h2>
          <v-text-field
            solo
            height="45"
            type="number"
            :rules="phoneRules"
            label="phone"
            class="input"
            v-model="validateForm.phone"
          ></v-text-field>
          <!-- <input type="number" :rules="phoneRules" placeholder="Phone" class="input" /> -->
          <!-- <input type="email" placeholder="Email" class="input" /> -->
          <v-text-field
            solo
            height="45"
            :rules="codeRules"
            label="code"
            type="number"
            class="input"
            v-model="validateForm.code"
          ></v-text-field>
          <div>
            <button class="link" v-if="!send" @click="sendCode()">
              发送验证码
            </button>
            <p class="link" v-else>还剩{{ time }}s</p>
          </div>
          <button @click="signUp()" class="btn">Sign Up</button>
        </v-form>
      </div>

      <!-- Sign In -->
      <div class="container__form container--signin">
        <v-form action="#" class="form" id="form2">
          <h2 class="form__title">登 录</h2>
          <div v-if="upLogin">
            <v-text-field
              solo
              height="45"
              name="name"
              type="text"
              v-model="validateForm.username"
              :rules="phoneRules"
              label="username"
              class="input"
            ></v-text-field>
            <v-text-field
              solo
              height="45"
              name="password"
              type="password"
              v-model="validateForm.password"
              :rules="codeRules"
              label="password"
              class="input"
            ></v-text-field>
          </div>
          <div v-else>
            <v-text-field
              solo
              height="45"
              type="number"
              :rules="phoneRules"
              label="phone"
              class="input"
              v-model="validateForm.phone"
            ></v-text-field>
            <v-text-field
              solo
              height="45"
              :rules="codeRules"
              label="code"
              type="number"
              class="input"
              v-model="validateForm.code"
            ></v-text-field>
            <button class="link right" v-if="!send" @click="sendCode()">
              发送验证码
            </button>
            <p class="link right" v-else>还剩{{ time }}s</p>
          </div>
          <div @click="goUpLogin()">
            <button class="link">
              {{ upLogin ? "手机号登录" : "账号密码登录" }}
            </button>
          </div>
          <div style="margin-top: 20px">
            <button @click="login()" class="btn">Sign In</button>
          </div>
        </v-form>
      </div>

      <!-- Overlay -->
      <div class="container__overlay">
        <div class="overlay">
          <div class="overlay__panel overlay--left">
            <button @click="changeSingUp()" class="btn" id="signIn">
              Sign In
            </button>
          </div>
          <div class="overlay__panel overlay--right">
            <button @click="changeSingIn()" class="btn" id="signUp">
              Sign Up
            </button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
const API = require("../../utils/request.js");
export default {
  name: "Login",
  data() {
    return {
      phoneRules: [
        (v) => !!v || "手机号必须填写",
        (v) => (v && v.length <= 11) || "手机号必须为十一位",
        (v) => /^1[34578]\d{9}$/.test(v) || "手机号填写不规范",
      ],
      codeRules: [
        (v) => !!v || "必须填写验证码",
        (v) => (v && v.length >= 6) || "验证码必须为6位",
      ],
      validateForm: {
        username: "ntt",
        password: "123",
        phone: "",
        code: "",
      },
      upLogin: true,
      send: false,
      time: 60,
    };
  },
  methods: {
    changeSingUp() {
      const container = document.querySelector(".container");
      container.classList.remove("right-panel-active");
    },
    changeSingIn() {
      const container = document.querySelector(".container");
      container.classList.add("right-panel-active");
    },
    goUpLogin() {
      console.log("111111");
      this.upLogin = !this.upLogin;
    },
    async login() {
      if (this.upLogin == true) {
        //账密登录
        (this.url = this.GLOBAL.baseUrl + "/user/login"),
          (this.data = {
            username: this.validateForm.username,
            password: this.validateForm.password,
          }),
          (this.result = await API.init(this.url, this.data, "post"));
        //登录成功
        console.log("登录成功");
        console.log(this.result);
        if (this.result.code === 1) {
          //存入token
          localStorage.setItem("phone", this.result.data.user.phone);
          localStorage.setItem("user", this.result.data.user);
          localStorage.setItem("userId", this.result.data.user.pkUserId);
          localStorage.setItem("avatar", this.result.data.user.avatar);
          this.$store.commit("setToken", this.result.data.token);
          this.$router.push("/");
        }
      } else {
        //手机号登录
        (this.url = this.GLOBAL.baseUrl + "/user/code/login"),
          (this.data = {
            phoneNumber: this.validateForm.phone,
            verifyCode: this.validateForm.code,
          }),
          (this.result = await API.init(this.url, this.data, "post"));
        if (this.result.code === 1) {
          //存入token
          localStorage.setItem("phone", this.result.data.user.phone);
          localStorage.setItem("user", this.result.data.user);
          localStorage.setItem("userId", this.result.data.user.pkUserId);
          localStorage.setItem("avatar", this.result.data.user.avatar);
          this.$store.commit("setToken", this.result.data.token);
          this.$router.push("/");
        }
      }
    },
    async signUp() {
      (this.url = this.GLOBAL.baseUrl + "/user/register"),
        (this.data = {
          address: "",
          avatar: "",
          birthday: "",
          code: this.validateForm.code,
          email: "",
          nickname: "",
          password: "",
          phone: this.validateForm.phone,
          sex: 0,
          username: "",
        }),
        (this.result = await API.init(this.url, this.data, "post"));
      alert("注册成功");
      if (this.result.code === 1) {
        //存入token
        localStorage.setItem("phone", this.result.data.user.phone);
        localStorage.setItem("user", this.result.data.user);
        localStorage.setItem("userId", this.result.data.user.pkUserId);
        localStorage.setItem("avatar", this.result.data.user.avatar);
        this.$store.commit("setToken", this.result.data.token);
        this.$router.push("/");
      }
    },

    async sendCode() {
      //手机号正则
      var mPattern = /^1[34578]\d{9}$/;
      if (!mPattern.test(this.validateForm.phone)) {
        alert("手机号格式不正确");
      } else {
        (this.url = this.GLOBAL.baseUrl + "/sendCode"),
          (this.data = {
            phoneNumber: this.validateForm.phone,
          }),
          (this.result = await API.init(this.url, this.data, "post"));
        this.send = true;
        // 倒计时60s结束后 可再次发送验证码
        let promise = new Promise((resolve, reject) => {
          let setTimer = setInterval(() => {
            this.time = this.time - 1;
            if (this.time <= 0) {
              this.send = false;
              resolve(setTimer);
              this.time = 60;
            }
          }, 1000);
        });
        promise.then((setTimer) => {
          clearInterval(setTimer);
        });
      }
    },
  },
};
</script>

<style>
:root {
  /* COLORS */
  --white: #e9e9e9;
  --gray: #333;
  --blue: #0367a6;
  --lightblue: #008997;

  /* RADII */
  --button-radius: 0.7rem;

  /* SIZES */
  --max-width: 758px;
  --max-height: 420px;

  font-size: 16px;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, Oxygen,
    Ubuntu, Cantarell, "Open Sans", "Helvetica Neue", sans-serif;
}

.body {
  align-items: center;
  background-color: var(--white);
  background: url("https://res.cloudinary.com/dci1eujqw/image/upload/v1616769558/Codepen/waldemar-brandt-aThdSdgx0YM-unsplash_cnq4sb.jpg");
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  display: grid;
  height: 100vh;
  place-items: center;
}

.form__title {
  font-weight: 300;
  margin: 70px 0;
  margin-bottom: 1.25rem;
}
.right {
  float: left;
  margin-left: 40px;
}
.link {
  color: var(--gray);
  font-size: 0.9rem;

  outline: none;
}

.container {
  background-color: var(--white);
  border-radius: var(--button-radius);
  box-shadow: 0 0.9rem 1.7rem rgba(0, 0, 0, 0.25),
    0 0.7rem 0.7rem rgba(0, 0, 0, 0.22);
  height: var(--max-height);
  max-width: var(--max-width);
  overflow: hidden;
  position: relative;
  width: 100%;
}

.container__form {
  height: 100%;
  position: absolute;
  top: 0;
  transition: all 0.6s ease-in-out;
}

.container--signin {
  left: 0;
  width: 50%;
  z-index: 2;
}

.container.right-panel-active .container--signin {
  transform: translateX(100%);
}

.container--signup {
  left: 0;
  opacity: 0;
  width: 50%;
  z-index: 1;
}

.container.right-panel-active .container--signup {
  animation: show 0.6s;
  opacity: 1;
  transform: translateX(100%);
  z-index: 5;
}

.container__overlay {
  height: 100%;
  left: 50%;
  overflow: hidden;
  position: absolute;
  top: 0;
  transition: transform 0.6s ease-in-out;
  width: 50%;
  z-index: 100;
}

.container.right-panel-active .container__overlay {
  transform: translateX(-100%);
}

.overlay {
  background-color: var(--lightblue);
  background: url("https://res.cloudinary.com/dci1eujqw/image/upload/v1616769558/Codepen/waldemar-brandt-aThdSdgx0YM-unsplash_cnq4sb.jpg");
  background-attachment: fixed;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
  height: 100%;
  left: -100%;
  position: relative;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
  width: 200%;
}

.container.right-panel-active .overlay {
  transform: translateX(50%);
}

.overlay__panel {
  align-items: center;
  display: flex;
  flex-direction: column;
  height: 100%;
  justify-content: center;
  position: absolute;
  text-align: center;
  top: 0;
  transform: translateX(0);
  transition: transform 0.6s ease-in-out;
  width: 50%;
}

.overlay--left {
  transform: translateX(-20%);
}

.container.right-panel-active .overlay--left {
  transform: translateX(0);
}

.overlay--right {
  right: 0;
  transform: translateX(0);
}

.container.right-panel-active .overlay--right {
  transform: translateX(20%);
}

.btn {
  background-color: var(--blue);
  background-image: linear-gradient(
    90deg,
    var(--blue) 0%,
    var(--lightblue) 74%
  );
  border-radius: 20px;
  border: 1px solid var(--blue);
  color: var(--white);
  cursor: pointer;
  font-size: 0.8rem;
  font-weight: bold;
  letter-spacing: 0.1rem;
  padding: 0.9rem 4rem;
  text-transform: uppercase;
  transition: transform 80ms ease-in;
}

.form > .btn {
  margin-top: 1.5rem;
}

.btn:active {
  transform: scale(0.95);
}

.btn:focus {
  outline: none;
}

.form {
  background-color: var(--white);
  /* display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column; */
  padding: 0 3rem;
  height: 100%;
  text-align: center;
}

.input {
  /* background-color: #fff;
  border: none; */
  /* padding: 0.9rem 0.9rem; */
  /* margin: 0.5rem 0; */
  width: 100%;
}

@keyframes show {
  0%,
  49.99% {
    opacity: 0;
    z-index: 1;
  }

  50%,
  100% {
    opacity: 1;
    z-index: 5;
  }
}
</style>
