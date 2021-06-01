<template>
  <div class="login-body">
    <div class="container-login right-panel-active">
      <!-- Sign Up -->
      <div class="container__form container-signup">
        <v-form :model="validateForm" class="form">
          <h2 class="form__title">注 册</h2>
          <text-field
            @valueChange="value1change"
            :rules="phoneRules"
            type="number"
            label="phone"
            :dValue="validateForm.phone"
          ></text-field>
          <text-field
            @valueChange="value2change"
            :rules="codeRules"
            type="text"
            label="code"
            :dValue="validateForm.code"
          ></text-field>
          <div>
            <span
              class="link button-hand"
              v-preventReClick
              v-if="!send"
              @click="sendCode()"
            >
              发送验证码
            </span>
            <p class="link" v-else>还剩{{ time }}s</p>
          </div>
          <v-btn
            width="200"
            height="50"
            v-preventReClick
            rounded
            @click="signUp()"
            class="btn"
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
              @valueChange="value3change"
              :rules="userRules"
              type="text"
              label="username"
              v-model="validateForm.username"
            ></text-field>
            <text-field
              @valueChange="value4change"
              :rules="psdRules"
              type="password"
              label="password"
              v-model="validateForm.password"
            ></text-field>
          </div>
          <div v-else>
            <text-field
              @valueChange="value1change"
              :rules="phoneRules"
              type="number"
              label="phone"
              :dValue="validateForm.phone"
            ></text-field>
            <text-field
              @valueChange="value2change"
              :rules="codeRules"
              type="text"
              label="code"
              :dValue="validateForm.code"
            ></text-field>
            <span
              class="link right button-hand"
              v-if="!send"
              v-preventReClick
              @click="sendCode()"
            >
              发送验证码
            </span>
            <p class="link right" v-else>还剩{{ time }}s</p>
          </div>
          <div @click="goUpLogin()">
            <span class="link button-hand">
              {{ upLogin ? "手机号登录" : "账号密码登录" }}
            </span>
          </div>
          <div style="margin-top: 20px">
            <v-btn
              width="200"
              height="50"
              v-preventReClick
              rounded
              @click="submit()"
              class="btn"
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
import TextField from "../../components/TextField.vue";
import { mapActions } from "vuex";
const STORAGE = require("./login");
export default {
  name: "Login",
  components: {
    TextField,
  },
  // beforeRouteEnter(to, from, next) {
  //   next(vm => {
  //     vm.getData()
  //   });
  // },
  // beforeRouteEnter(to, from, next) {
  //   next((vm) => {
  //     if (vm) {
  //       
  //     }
  //     if (vm.profileCompleted) {
  //       vm.nextReplace();
  //     }
  //   });
  // },
  created() {
    console.log(location.href)
    if(location.href) {
      this.$message({
          message: "请先登录",
          type: "warning",
        });
    }
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
        (v) => (v && v.length == 4) || "验证码必须为4位",
      ],
      userRules: [
        (v) => !!v || "必须填写用户名",
        (v) => (v && v.length >= 3) || "用户名不小于3位",
      ],
      psdRules: [
        (v) => !!v || "必须填写密码",
        (v) => (v && v.length >= 3) || "密码不小于3位",
      ],
      validateForm: {
        username: "ntt",
        password: "123",
        phone: "",
        code: "",
      },
      preUrl: "/",
      upLogin: true,
      send: false,
      time: 60,
    };
  },
  methods: {
    ...mapActions(["Login", "PhoneLogin", "SendCode"]),
    // 切换状态
    changeSingUp() {
      const container = document.querySelector(".container-login");
      container.classList.remove("right-panel-active");
    },
    value1change(value) {
      this.validateForm.phone = value;
    },
    value2change(value) {
      this.validateForm.code = value;
    },
    value3change(value) {
      this.validateForm.username = value;
    },
    value4change(value) {
      this.validateForm.password = value;
    },
    changeSingIn() {
      const container = document.querySelector(".container-login");
      container.classList.add("right-panel-active");
    },
    goUpLogin() {
      this.upLogin = !this.upLogin;
    },
    submit() {
      let that = this;
      if (that.upLogin == true) {
        //账密登录
        let data = {
          username: that.validateForm.username,
          password: that.validateForm.password,
        };
        that.Login(data).then((res) => {
          
          if (res.code != 1) {
            this.$message.error("登录失败，请稍后重试");
          }else {
            this.loginSuc();
          }
        });
      } else {
        //手机号登录
        let phoneData = {
          phoneNumber: that.validateForm.phone,
          verifyCode: that.validateForm.code,
        };
        that.PhoneLogin(phoneData).then((res) => {
          
          if (res.code != 1) {
            this.$message.error("验证码错误，请稍后重试");
          }else{
            that.loginSuc();
          }
        });
      }
    },
    loginSuc() {
      this.$router.push("/");
      this.$message({
        message: "登录成功",
        type: "success",
      });
    },
    signUp() {
      let that = this;
      let data = {
        address: "",
        avatar: "",
        birthday: "",
        code: that.validateForm.code,
        email: "",
        nickname: "",
        password: "",
        phone: that.validateForm.phone,
        sex: 0,
        username: "",
      };
      console.log(that.validateForm.code);
      that.axios
        .post(that.GLOBAL.baseUrl + "/user/register", data)
        .then((res) => {
          // console.log(res)
          if (res.data.code == 1) {
            let data = res.data.data;
            STORAGE.storage(
              data.token,
              data.user.phone,
              data.user,
              data.user.pkUserId,
              data.user.avatar
            );
            that.$router.push("/");
            that.$message({
              message: "注册成功",
              type: "success",
            });
          } else {
            that.$message({
              message: "注册失败",
              type: "warning",
            });
          }
        });
    },

    sendCode() {
      //手机号正则
      var mPattern = /^1[34578]\d{9}$/;
      if (!mPattern.test(this.validateForm.phone)) {
        this.$message({
          message: "手机号格式不正确",
          type: "warning",
        });
      } else {
        this.axios
          .post(this.GLOBAL.baseUrl + "/sendCode", {
            phoneNumber: this.validateForm.phone,
          })
          .then((res) => {
            console.log(res)
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
