<template>
  <div class="login-body">
    <div class="container-login right-panel-active">
      <!-- Sign Up -->
      <div class="container__form container-signup">
        <v-form :model="validateForm" class="form">
          <h2 class="form__title">注 册</h2>
          <text-field
            :rules="phoneRules"
            type="number"
            label="phone"
            v-model="validateForm.phone"
          ></text-field>
          <text-field
            :rules="codeRules"
            type="number"
            label="code"
            v-model="validateForm.code"
          ></text-field>
          <div>
            <button class="link" v-if="!send" @click="sendCode()">
              发送验证码
            </button>
            <p class="link" v-else>还剩{{ time }}s</p>
          </div>
          <v-btn width="200" height="50" rounded @click="signUp" class="btn"
            ><span style="color: #e9e9e9">Sign Up</span></v-btn
          >
        </v-form>
      </div>

      <!-- Sign In -->
      <div class="container__form container-signin">
        <v-form :model="validateForm" class="form">
          <h2 class="form__title">登 录</h2>
          <div v-if="upLogin">
            <text-field
              :rules="userRules"
              type="text"
              label="username"
              v-model="validateForm.username"
            ></text-field>
            <text-field
              :rules="psdRules"
              type="password"
              label="password"
              v-model="validateForm.password"
            ></text-field>
          </div>
          <div v-else>
            <text-field
              :rules="phoneRules"
              type="number"
              label="phone"
              v-model="validateForm.phone"
            ></text-field>
            <text-field
              :rules="codeRules"
              type="number"
              label="code"
              v-model="validateForm.code"
            ></text-field>
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
            <v-btn width="200" height="50" rounded @click="submit" class="btn"
              ><span style="color: #e9e9e9">Sign In</span></v-btn
            >
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
const API = require("@/utils/request.js");
import TextField from '../../components/TextField.vue';
import { mapActions } from "vuex";
const STORAGE = require("./login");
export default {
  name: "Login",
  components:{
    TextField
  },
  data() {
    return {
      phoneRules: [
        (v) => !!v || "手机号必须填写",
        (v) => (v && v.length <= 11) || "手机号必须为十一位",
        (v) => /^1[34578]\d{9}$/.test(v) || "手机号填写不规范",
      ],
      codeRules: [
        (v) => !!v || "必须填写验证码",
        (v) => (v && v.length != 6) || "验证码必须为6位",
      ],
      userRules:[
        (v) => !!v || "必须填写用户名",
        (v) => (v && v.length >= 3) || "用户名不小于3位",
      ],
      psdRules:[
        (v) => !!v || "必须填写密码",
        (v) => (v && v.length >= 3) || "密码不小于3位",
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
    ...mapActions(["Login", "PhoneLogin",]),
    // 切换状态
    changeSingUp() {
      const container = document.querySelector(".container-login");
      container.classList.remove("right-panel-active");
    },
    changeSingIn() {
      const container = document.querySelector(".container-login");
      container.classList.add("right-panel-active");
    },
    goUpLogin() {
      this.upLogin = !this.upLogin;
    },
    async submit() {
      let that = this
      if (that.upLogin == true) {
        //账密登录
        let data = {
            username: that.validateForm.username,
            password: that.validateForm.password,
          }
          that.Login(data).then((res) => {
            this.goIndex();
          })
      } else {
        //手机号登录
        // (this.url = this.GLOBAL.baseUrl + "/user/code/login"),
          let phoneData = {
            phoneNumber: that.validateForm.phone,
            verifyCode: that.validateForm.code,
          }
          that.PhoneLogin(phoneData).then((res) => {
            that.goIndex();
          })
        }
    },
    goIndex(){
      this.$router.push("/");
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
        let data = this.result.data;
        STORAGE.storage(
          data.token,
          data.user.phone,
          data.user,
          data.user.pkUserId,
          data.user.avatar
        );
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
.login-body {
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

.container-login {
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

.container-signin {
  left: 0;
  width: 50%;
  z-index: 2;
}

.container-login.right-panel-active .container-signin {
  transform: translateX(100%);
}

.container-signup {
  left: 0;
  opacity: 0;
  width: 50%;
  z-index: 1;
}

.container-login.right-panel-active .container-signup {
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

.container-login.right-panel-active .container__overlay {
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

.container-login.right-panel-active .overlay {
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

.container-login.right-panel-active .overlay--left {
  transform: translateX(0);
}

.overlay--right {
  right: 0;
  transform: translateX(0);
}

.container-login.right-panel-active .overlay--right {
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
