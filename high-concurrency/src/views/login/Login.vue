<template>
  <v-app class="bg">
    <div class="title text-h3 mt-6 font-weight-thin" tile>骸冰商城</div>
    <v-content>
      <v-container class="fill-height" fluid>
        <v-row align="center" justify="center" class="login-form">
          <v-col cols="12" sm="8" md="4">
            <v-card class="elevation-8">
              <v-toolbar color="teal lighten-1" dark flat>
                <v-toolbar-title v-if="loginNum !== 2"
                  >{{ loginNum === 0 ? "账号密码登录" : "手机验证码登录" }}
                </v-toolbar-title>
                <v-toolbar-title v-else>注册</v-toolbar-title>
                <v-spacer />
                <v-tooltip bottom>
                  <template v-slot:activator="{ on }">
                    <v-btn :href="source" icon large target="_blank" v-on="on">
                      <v-icon>mdi-code-tags</v-icon>
                    </v-btn>
                  </template>
                  <span>Source</span>
                </v-tooltip>
              </v-toolbar>
              <v-card-text>
                <v-form v-if="loginNum === 0" :model="validateForm">
                  <v-text-field
                    id="name"
                    label="用户名"
                    name="name"
                    type="text"
                    
                    v-model="validateForm.username"
                  />
                  <v-text-field
                    id="password"
                    label="密码"
                    name="password"
                    type="password"
                    
                    v-model="validateForm.password"
                  />
                </v-form>
                <v-form v-else :model="validateForm">
                  <v-text-field
                    id="name"
                    label="手机号"
                    name="name"
                    type="phone"
                   
                    v-model="validateForm.phone"
                  />

                  <v-text-field
                    id="password"
                    label="验证码"
                    name="password"
                    type="password"
                    
                    v-model="validateForm.code"
                  >
                  </v-text-field>
                  <p v-if="!send" @click="sendCode()">发送验证码</p>
                  <p v-else>还剩{{ time }}s</p>
                </v-form>
                <div @click="changeLogin()">
                  {{ loginNum === 1 ? "账号密码登录" : "手机验证码登录" }}
                </div>
                <div
                  v-if="loginNum !== 2"
                  @click="changeRes()"
                  style="margin-top: 10px"
                >
                  注册
                </div>
              </v-card-text>
              <v-card-actions>
                <v-spacer />
                <v-btn @click="submit" color="teal lighten-1">{{
                  loginNum === 2 ? "Resgister" : "Login"
                }}</v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-content>
    <div class="mask"></div>
  </v-app>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      usernameRules: [
        { validate: (val) => !!val, message: "必须填写用户名" },
        { validate: (val) => val.length >= 3, message: "用户名长度大于3" },
      ],
      passwordRules: [
        { validate: (val) => !!val, message: "必须填写密码" },
        {
          validate: (val) => val.length >= 3 && val.length <= 10,
          message: "密码长度大于3小于10",
        },
      ],
      phoneRules: [
        { validate: (val) => !!val, message: "必须填写手机号" },
        {
          validate: (val) => val.test(/^1[34578]\d{9}$/),
          message: "手机号格式不正确",
        },
      ],
      codeRules: [
        { validate: (val) => !!val, message: "必须填写验证码" },
        {
          validate: (val) => val.length == 5,
          message: "验证码必须为6位",
        },
      ],
      validateForm: {
        username: "ntt",
        password: "123",
        phone: "",
        code: "",
      },
      loginNum: 0,
      send: false,
      time: 60,
    };
  },
  props: {
    source: String,
  },
  methods: {
    submit() {
      if (this.loginNum === 0) {
        //表单验证
        this.axios({
          method: "post",
          url: this.GLOBAL.baseUrl + "/user/login",
          data: {
            username: this.validateForm.username,
            password: this.validateForm.password,
          },
        }).then((res) => {
          //登录成功
          console.log(res);
          if (res.data.code === 1) {
            //存入token
            localStorage.setItem("token", res.data.data.token);
            localStorage.setItem("userId", res.data.data.user.pkUserId);
            localStorage.setItem("avatar", res.data.data.user.avatar);
            this.$store.commit("setToken", res.data.data.token);
            this.$router.push("/");
          }
        });
      } else if (this.loginNum === 1) {
        this.axios({
          method: "post",
          url: this.GLOBAL.baseUrl + "/user/code/login",
          data: {
            phoneNumber: this.validateForm.phone,
            verifyCode: this.validateForm.code,
          },
        }).then((res) => {
          //登录成功
          console.log(res);
          if (res.data.code === 1) {
            //存入token
            localStorage.setItem("token", res.data.data.token);
            this.$store.commit("setToken", res.data.data.token);
            this.$router.push("/");
          }
        });
      } else {
        this.axios({
          method: "post",
          url: this.GLOBAL.baseUrl + "/user/register",
          data: {
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
          },
        }).then((res) => {
          //注册成功
          console.log(res);
          this.loginNum === 0;
        });
      }
    },
    changeLogin() {
      if (this.loginNum === 0) {
        this.loginNum = 1;
      } else {
        this.loginNum = 0;
      }
    },
    changeRes() {
      this.loginNum = 2;
    },
    sendCode() {
      //手机号正则
      var mPattern = /^1[34578]\d{9}$/;
      if (!mPattern.test(this.validateForm.phone)) {
        alert("手机号格式不正确");
      } else {
        this.axios({
          method: "post",
          url: this.GLOBAL.baseUrl + "/sendCode",
          data: {
            phoneNumber: this.validateForm.phone,
          },
        }).then((res) => {
          console.log(res);
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
  computed: {},
};
</script>
<style lang="scss" scoped>
.bg {
  background-image: url("../../assets/images/login-bg.jpg");
  background-size: 100% 100%;
  // opacity: 0.8;
  .login-form {
    z-index: 100;
  }
}
.title {
  text-align: center;
  font-weight: 300;
  font-size: 3.75rem;
}

.mask {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  //background-image: linear-gradient(to right, #14475f 0%, #6482af 50%);
  opacity: 0.3;
}
</style>
