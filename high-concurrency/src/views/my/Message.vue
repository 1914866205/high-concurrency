<template>
  <v-app>
    <nav-bar></nav-bar>
    <div class="message">
      <div class="fengmian"></div>
      <div class="biankuang">
        <img class="avatar" src="@/assets/images/me.jpg" />
        <div class="xinxi">
          <span style="display: flex">
            <v-text-field
              v-if="userInput"
              label="用户名"
              placeholder="请输入用户名.."
              v-model="validate.username"
            ></v-text-field>
            <h1 v-else>用户名</h1>
            <img
              @click="updateUser()"
              style="width: 30px; height: 30px; margin: 10px"
              src="../../assets/icon/bi.png"
            />
          </span>
          <span style="display: flex; margin-top: 50px">
            <h4>电话</h4>
            <span class="label">
              <p>18851699003</p>
            </span>
          </span>
          <div class="line"></div>

          <span style="display: flex; margin-top: 50px">
            <h4>邮箱</h4>
            <span class="label">
              <p>更改</p>
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
                  :value="validate.sex"
                />男</label
              >
              <label style="margin-left: 20px"
                ><input
                  type="radio"
                  name="sex"
                  :value="validate.sex"
                />女</label
              >
            </span>
          </span>
          <div class="line"></div>

          <span style="display: flex; margin-top: 50px">
            <h4>余额</h4>
            <span class="label">
              <p>充值</p>
            </span>
          </span>
          <div class="line"></div>

          <span style="display: flex; margin-top: 50px">
            <h4>地址</h4>
            <span class="label">
              <v-text-field
                label="地址"
                placeholder="Placeholder"
                filled
              ></v-text-field>
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
export default {
  name: "HomePage",
  data() {
    return {
      userInput: false,
      validate: {
        username: "",
        sex: "",
      },
    };
  },
  components: {
    NavBar,
  },
  methods: {
    updateUser() {
      this.userInput = !this.userInput;
    },
  },
};
</script>
<style lang="scss" scoped>
.message {
  width: 85%;
  margin: auto;
  box-shadow: #26a69a 0 0 2px 1px;
  border-radius: 10px;
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
  width: 270%;
  margin-top: 30px;
}
.label {
  margin-left: 100px;
}
.biankuang {
  display: flex;
}
</style>