<template>
  <v-app>
    <nav-bar></nav-bar>
    <alert v-if="isSuc" @click="cancelSubmit()" alertdata="修改成功"></alert>
    <div class="message index index-border">
      <div class="fengmian"></div>
      <div class="biankuang">
        <v-hover>
          <template v-slot:default="{ hover }">
            <v-card class="avatar" max-width="200" max-height="200">
              <img
                style="height: 200px; width: 200px"
                class="br-1"
                :src="user.avatar"
              />
              <v-fade-transition>
                <v-overlay v-if="hover" absolute color="#f5f5f5">
                  <v-btn @click="avatarClick()"
                    >修改头像
                    <input
                      type="file"
                      @change="uploadAvatar($event)"
                      ref="file"
                      style="display: none"
                      id="file"
                      accept="image/jpg, image/jpeg, image/png"
                    />
                  </v-btn>
                </v-overlay>
              </v-fade-transition>
            </v-card>
          </template>
        </v-hover>

        <div class="btn-col">
          <v-btn
            :disabled="!isUpdate"
            color="teal"
            width="130"
            height="45"
            style="color: white"
            class="compon-btn btn-none border-no"
            @click="submit()"
            v-preventReClick
            >提交</v-btn
          >
        </div>
        <div class="xinxi">
          <span style="display: flex">
            <v-text-field
              v-if="userInput"
              label="用户名"
              placeholder="请输入用户名.."
              v-model="user.nickname"
            ></v-text-field>
            <h1 v-else>{{ user.nickname }}</h1>
            <img
              @click="updateUser()"
              class="button-hand"
              style="width: 30px; height: 30px; margin: 10px"
              src="../../assets/icon/bi.png"
            />
          </span>
          <span style="display: flex; margin-top: 50px">
            <h4>电话</h4>
            <span class="label">
              <span v-if="phoneInput">
                <v-text-field
                  label="电话"
                  placeholder="请输入电话.."
                  v-model="validate.phone"
                ></v-text-field>
                <v-text-field
                  label="验证码"
                  placeholder="请输入验证码.."
                  v-model="validate.code"
                ></v-text-field>
                <span>
                  <button @click="sendCode()" class="msg-btn">
                    获取验证码
                  </button>
                  <button
                    @click="verifyCode()"
                    class="msg-btn"
                    style="margin-left: 10px"
                  >
                    验证
                  </button>
                </span>
              </span>
              <span v-else>{{ user.phone }}</span>
              <span
                @click="updatePhone()"
                class="button-hand"
                style="color: #26a69a; margin-left: 20px"
                >更改</span
              >
            </span>
          </span>
          <div class="line"></div>

          <span style="display: flex; margin-top: 50px">
            <h4>密码</h4>
            <span class="label">
              <v-text-field
                v-if="codeInput"
                label="密码"
                placeholder="请输入密码.."
                v-model="validate.password"
              ></v-text-field>
              <span v-else>**********</span>
              <span
                @click="updateCode()"
                class="button-hand"
                style="color: #26a69a; margin-left: 20px"
                >更改</span
              >
            </span>
          </span>
          <div class="line"></div>

          <span style="display: flex; margin-top: 50px">
            <h4>邮箱</h4>
            <span class="label">
              <v-text-field
                v-if="emailInput"
                label="邮箱"
                placeholder="请输入邮箱.."
                v-model="user.email"
              ></v-text-field>
              <span v-else>{{ user.email }}</span>
              <span
                @click="updateEmail()"
                class="button-hand"
                style="color: #26a69a; margin-left: 20px"
                >更改</span
              >
            </span>
          </span>
          <div class="line"></div>

          <span style="display: flex; margin-top: 50px">
            <h4>性别</h4>
            <span class="label">
              <label
                ><input
                  type="radio"
                  name="sex"
                  value="1"
                  v-model="validate.sex"
                />男</label
              >
              <label style="margin-left: 20px"
                ><input
                  type="radio"
                  name="sex"
                  value="2"
                  v-model="validate.sex"
                />女</label
              >
            </span>
          </span>
          <div class="line"></div>

          <span style="display: flex; margin-top: 50px">
            <h4>余额</h4>
            <span class="label">
                <v-text-field
                  v-if="chongInput"
                  label="金额"
                  type="number"
                  placeholder="请输入金额.."
                  v-model="validate.money"
                ></v-text-field>
                <span v-else>{{ validate.money }}</span>
                <span
                  @click="chongChange()"
                  class="button-hand"
                  style="color: #26a69a; margin-left: 20px"
                  >充值</span
                >
            </span>
          </span>
          <div class="line"></div>

          <span style="display: flex; margin-top: 50px">
            <h4>地址</h4>
            <span class="label">
              <v-text-field
                v-if="addressInput"
                label="地址"
                placeholder="请输入地址.."
                v-model="validate.addresss"
              ></v-text-field>
              <span v-else>{{ user.address }}</span>
              <span
                @click="updateAddress()"
                class="button-hand"
                style="color: #26a69a; margin-left: 20px"
                >更改</span
              >
            </span>
          </span>
          <div class="line"></div>
        </div>
      </div>
    </div>
  </v-app>
</template>

<script>
import NavBar from "../../components/NavBar";
import Alert from "@/components/Alert";
import Vue from "vue";
const clickoutside = {
  // 初始化指令
  bind(el, binding) {
    function documentHandler(e) {
      // 这里判断点击的元素是否是本身，是本身，则返回
      if (el.contains(e.target)) {
        return false;
      }
      // 判断指令中是否绑定了函数
      if (binding.expression) {
        // 如果绑定了函数 则调用那个函数，此处binding.value就是handleClose方法
        binding.value(e);
      }
    }
    // 给当前元素绑定个私有变量，方便在unbind中可以解除事件监听
    el.__vueClickOutside__ = documentHandler;
    document.addEventListener("click", documentHandler);
  },
  update() {},
  unbind(el) {
    // 解除事件监听
    document.removeEventListener("click", el.__vueClickOutside__);
    delete el.__vueClickOutside__;
  },
};
import { USER_INFO, USER_ID, USER_AVATAR } from "@/store/mutation-types";
export default {
  name: "HomePage",
  data() {
    return {
      isUpdate: false,
      userInput: false,
      emailInput: false,
      addressInput: false,
      codeInput: false,
      verFaild: false,
      verSuccess: false,
      updateSuccess: false,
      phoneInput: false,
      overlay: false,
      isSuc: false,
      chongInput: false,
      user: [],
      validate: {
        nickname: "",
        sex: 1,
        avatar: "",
        address: "",
        email: "",
        password: "",
        phone: "",
        code: "",
        money: 0,
      },
    };
  },
  watch: {
    validate: {
      handler(val, oldVal) {
        console.log(val, oldVal);

        this.isUpdate = true;
      },
      deep: true,
    },
  },
  components: {
    NavBar,
    Alert,
  },
  created: function () {
    this.refreshUser();
  },
  //注册指令
  directives: { clickoutside },
  methods: {
    refreshUser() {
      const id = Vue.ls.get(USER_ID);
      this.axios
        .get(this.GLOBAL.baseUrl + "/user/getInfoById/" + id)
        .then((res) => {
          console.log(res);
          this.user = JSON.parse(res.data.data);
          this.validate.sex = this.user.sex;
          Vue.ls.set(USER_INFO, this.user);
        });
    },
    updateUser() {
      this.userInput = !this.userInput;
    },
    chongChange() {
      this.chongInput = !this.chongInput;
    },
    updateEmail() {
      this.emailInput = !this.emailInput;
    },
    updateAddress() {
      this.addressInput = !this.addressInput;
    },
    updatePhone() {
      this.phoneInput = !this.phoneInput;
    },
    updateCode() {
      this.codeInput = !this.codeInput;
    },
    avatarClick() {
      this.$refs.file.click();
    },
    submit() {
      const _this = this;
      let users = _this.user;
      if (_this.validate.sex === "男") {
        var sex = 1;
      } else if (_this.validate.sex === "女") {
        var sex = 2;
      } else {
        var sex = users.sex;
      }

      if (_this.validate.password === "") {
        var pwd = "123";
      } else {
        var pwd = _this.validate.password;
      }

      if (_this.validate.avatar === "") {
        var avatar = users.avatar;
      } else {
        var avatar = _this.validate.avatar;
      }

      if (_this.validate.code === "") {
        var code = "";
      } else {
        var code = _this.validate.code;
      }

      if (_this.validate.address === "") {
        var address = users.address;
      } else {
        var address = _this.validate.address;
      }
      let data = {
        address: address,
        avatar: avatar,
        code: code,
        email: users.email,
        money: this.validate.money,
        nickname: users.nickname,
        password: pwd,
        phone: users.phone,
        pkUserId: users.pkUserId,
        sex: sex,
        username: users.username,
      };
      _this.axios
        .post(_this.GLOBAL.baseUrl + "/user/edit", data)
        .then((res) => {
          // _this.isSuc = true;
          this.$message({
            message: "修改成功",
            type: "success",
          });
          _this.isUpdate = false;
          _this.refreshUser();

          if ((avatar = _this.validate.avatar)) {
            Vue.ls.set(USER_AVATAR, avatar, 7 * 24 * 60 * 60 * 1000);
          }
          this.$router.go(0);
        });
    },
    cancelSubmit() {
      this.isSuc = false;
      this.isUpdate = false;
    },
    updateAdminInfo(url) {
      console.log(url);
      this.validate.avatar = url;
      this.user.avatar = url;
    },
    sendCode() {
      //手机号正则
      let validate = this.validate;
      var mPattern = /^1[34578]\d{9}$/;
      if (!mPattern.test(this.validateForm.phone)) {
        this.$message({
          message: "手机号格式不正确",
          type: "warning",
        });
      } else {
        this.axios
          .post(this.GLOBAL.baseUrl + "/sendCode", {
            phoneNumber: validate.phone,
          })
          .then((res) => {
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
    verifyCode() {
      let validate = this.validate;
      this.axios
        .post(this.GLOBAL.baseUrl + "/verifyCode", {
          phoneNumber: validate.phone,
          verifyCode: validate.code,
        })
        .then((res) => {
          if (res.code === 1) {
            this.$message({
              message: "验证成功",
              type: "success",
            });
          } else {
            this.validate.code === "";
            this.$message({
              message: "验证失败",
              type: "warning",
            });
          }
        });
    },
    uploadAvatar(event) {
      const OSS = require("ali-oss");
      let client = new OSS({
        region: "oss-cn-beijing",
        accessKeyId: "LTAI4GD8r7BPa4ik89fSdFws",
        accessKeySecret: "H5uLKRHHYnndxuHctQjPPBJj5vRWSH",
        bucket: "nttbucket",
      });
      let timestamp = Date.parse(new Date());
      let imgUrl = "img/" + timestamp + "." + "jpeg";
      var file = event.target.files[0]; //获取文件流
      var _this = this;
      client.multipartUpload(imgUrl, file).then(function (result) {
        let index = result.res.requestUrls[0].indexOf("?");
        let url;
        if (index == -1) {
          url = result.res.requestUrls[0];
        } else {
          url = result.res.requestUrls[0].slice(0, index);
        }
        _this.validate.avatar = url;
        _this.updateAdminInfo(_this.validate.avatar);
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.message {
  box-shadow: 0 8px 32px 0 rgba(166, 169, 204, 0.37);
}
.fengmian {
  background-color: #26a69a;
  height: 300px;
  border-radius: 10px 10px 0 0;
}
.avatar {
  width: 200px;
  height: 200px;
  border-radius: 10px;
  margin-top: -50px;
  margin-left: 50px;
}
.xinxi {
  margin: 30px;
}
.line {
  border: 0.1px solid rgb(240, 237, 237);
  width: 200%;
  margin-top: 30px;
}
.label {
  margin-left: 100px;
  display: flex;
}
.biankuang {
  display: flex;
}
// .message-btn {
//   width: 80px;
//   height: 40px;
//   background-color: #26a69a;
//   color: #26a69a;
//   font-size: 1.3rem;
//   border-radius: 10px;
//   border: 0; // 去除未选中状态边框
//   outline: none; // 去除选中状态边框
// }
.btn-col {
  position: absolute;
  right: 15%;
  top: 35%;
}
.msg-btn {
  outline: none;
  color: #26a69a;
  font-size: 0.7rem;
}
</style>