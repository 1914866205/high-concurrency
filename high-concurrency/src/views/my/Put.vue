<template>

  <v-app>
    <nav-bar></nav-bar>
    <el-dialog
      class="dialog"
      title="骸冰秒杀"
      :visible.sync="updatecenterDialogVisible"
      width="40%"
      left
      :modal="false"
    >
      <el-form ref="form" :model="form" label-width="80px">
        <el-form-item label="秒杀时刻">
          <el-col :span="10">
            <el-date-picker
              type="date"
              placeholder="选择日期"
              v-model="form.date1"
              style="width: 100%;"
            ></el-date-picker>
          </el-col>
          <el-col class="col" :span="1"></el-col>
          <el-col :span="10">
            <el-time-picker placeholder="选择时间" v-model="form.date2" style="width: 100%;"></el-time-picker>
          </el-col>
        </el-form-item>

        <el-alert title="（包含末尾数字，折扣区间0-10)" type="warning"></el-alert>
        <el-form-item
          v-for="(domain, index) in dynamicValidateForm.domains"
          :label="'活动规则' + index"
          :key="domain.key"
          style="padding: 10px;"
        >
          第
          <el-input v-model="domain.start" style="width: 50px;" />名到第
          <el-input v-model="domain.end" style="width: 50px;"></el-input>名打
          <el-input v-model="domain.discount" style="width: 50px;"></el-input>折
          <el-button
            @click.prevent="removeDomain(domain)"
            style="background-color: #FF5252;color:white;margin-left:30px"
          >删除</el-button>
        </el-form-item>
        <el-form-item>
          <el-button @click="addDomain" style="margin-left:300px;">新增规则</el-button>
        </el-form-item>

        <el-form-item label="活动介绍">
          <el-input type="textarea" v-model="form.desc"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="addSecKill">立即创建</el-button>
          <el-button @click="dialogControl">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <div class="goodsInfo" v-for="(item,index) in goodsInfo" :key="index">
      <div style="height:100%;width:30%;">
        <img :src="item.image" style="padding:2%;margin-left:10%;width:300px;height:300px" />
      </div>
      <div style="margin:20px">
        <div style="display: flex">
          <span class="type">{{ item.type }}</span>
          <h1>{{ item.goodName }}</h1>
        </div>

        <div class="goodsBorder">
          <div class="goodsMiaosha">
            <img style="width: 25px; height: 25px" src="../../assets/icon/naozhong.png" />
            <span style="margin-left: 8px;margin-top:10px">骸冰秒杀</span>
          </div>
          <div>
            <span style="color: gray; margin: 10px">价 格</span>
            <span style="color: #26a69a; font-size: 2rem ;margin-left:40px">￥ {{ item.price }}</span>
          </div>
          <div style="color: gray; margin: 10px">
            <span>活动介绍</span>
            <span style="margin-left: 34px">{{ item.description }}</span>
          </div>
        </div>

        <div style="margin-left: 15px">
          <span style="color: gray">库存修改</span>
          <button @click="btnMinute" class="btn_minute">-</button>
          <input class="input" type="text" size="1" v-model="count" />
          <button @click="btnAdd" class="btn_add">+</button>
          <span style="color: gray; margin-left: 10px">(库存：{{ item.count }})</span>
          <button
            @click="dialogControl(item.pkGoodId)"
            class="btn_edit"
            style="right: 400px;background-color:#E64A19"
          >秒杀活动</button>
          <button @click="edit" class="btn_edit">确定修改</button>
        </div>
      </div>
    </div>
  </v-app>
</template>
<script>
import NavBar from "../../components/NavBar";
const API = require("../../utils/request.js");
export default {
  name: "Put",
  data() {
    return {
      goodId: "",
      goodsInfo: [],
      count: 0,
      updatecenterDialogVisible: false,
      seckillForm: [],
      form: {
        date1: "",
        date2: "",
        desc: ""
      },
      dynamicValidateForm: {
        domains: [
          {
            start: "",
            end: "",
            discount: ""
          }
        ]
      }
    };
  },
  components: {
    NavBar
  },
  created() {
    this.getAllGoodsByUser();
  },
  methods: {
    addSecKill() {
      let d = new Date(this.form.date1);
      let date1 =
        d.getFullYear() +
        "-" +
        (d.getMonth() + 1) +
        "-" +
        d.getDate() +
        " " +
        d.getHours() +
        ":" +
        d.getMinutes() +
        ":" +
        d.getSeconds();
      d = new Date(this.form.date2);
      let date2 =
        d.getFullYear() +
        "-" +
        (d.getMonth() + 1) +
        "-" +
        d.getDate() +
        " " +
        d.getHours() +
        ":" +
        d.getMinutes() +
        ":" +
        d.getSeconds();
      let params = {
        goodId: this.goodId,
        day: date1,
        detail: date2,
        description: this.form.desc,
        ruleDtoList: this.dynamicValidateForm.domains
      };
      this.axios.post(this.GLOBAL.contentUrl + "/hbStrategy/add", params);
      this.$message({
        message: "创建成功",
        type: "success"
      });
      this.updatecenterDialogVisible = !this.updatecenterDialogVisible;
    },
    getAllGoodsByUser() {
      let params = new URLSearchParams();
      params.append("userId", localStorage.getItem("userId"));
      this.axios
        .post(this.GLOBAL.contentUrl + "/goods/findGoodsByUserId", params)
        .then(res => {
          this.goodsInfo = res.data.data.Goods;
        });
    },
    onSubmit() {},
    dialogControl(goodId) {
      this.updatecenterDialogVisible = !this.updatecenterDialogVisible;
      this.goodId = goodId;
    },
    btnAdd() {
      this.count++;
    },
    btnMinute() {
      if (this.count <= 0) {
        this.$message({
          message: "该宝贝不能减少了哟~",
          type: "warning"
        });
      } else {
        this.count -= 1;
      }
    },
    edit() {},
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          alert("submit!");
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    removeDomain(item) {
      var index = this.dynamicValidateForm.domains.indexOf(item);
      if (index !== -1) {
        this.dynamicValidateForm.domains.splice(index, 1);
      }
    },
    addDomain() {
      this.dynamicValidateForm.domains.push({});
    }
  }
};
</script>

<style lang="scss" scoped>
.dialog {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  z-index: 10000;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(0, 0, 0, 0.7);
}

.btn_edit {
  position: absolute;
  background-color: #26a69a;
  color: #ffffff;
  height: 35px;
  padding: 5px;
  margin: 5px;
  right: 250px;
  cursor: hand;
  border-radius: 10%;
}

.goodsInfo {
  width: 85%;
  height: 300px;
  margin: 0 auto;
  margin-top: 40px;
  background-color: #f5f5f5;
  display: flex;
  border: 2px solid #f5f5f5;
}

.goodsInfo {
  width: 85%;
  margin: 0 auto;
  margin-top: 40px;
  display: flex;
  border: 2px solid #f5f5f5;
}

.type {
  background-color: #26a69a;
  color: #ffffff;
  height: 35px;
  padding: 5px;
  margin: 5px;
}

.goodsBorder {
  width: 100%;
  height: 150px;
  background-color: #f6f6f6;
  margin-top: 20px;
  margin-left: 5px;
}

.goodsMiaosha {
  margin-top: -5px;
  background-color: #26a69a;
  color: #ffffff;
  height: 30px;
  width: 100%;
}

.btn_minute {
  border-radius: 0.133rem 0 0 0.133rem;
  padding: 0.146rem 0.2rem 0.146rem 0.226rem;
  background: #f5f5f5;
  margin-left: 20px;
  border: 0; // 去除未选中状态边框
  outline: none; // 去除选中状态边框
}

.input {
  padding: 0.146rem 0.12rem;
  text-align: center;
}

.btn_add {
  border-radius: 0 0.133rem 0.133rem 0;
  padding: 0.146rem 0.226rem 0.146rem 0.2rem;
  background: #f5f5f5;
  border: 0; // 去除未选中状态边框
  outline: none; // 去除选中状态边框
}

.btn {
  background-color: #26a69a;
  color: #ffffff;
  font-size: 1.2rem;
  width: 100px;
  height: 50px;
  padding: 10px;
}

.line {
  border: 0.1px solid rgb(240, 237, 237);
  width: 100%;
  margin-top: 30px;
}

.comm {
  width: 90%;
  margin: 100px auto;
}

.comment {
  background-color: #f6f6f6;
  border: 1px solid #e6e6e6;
  height: 35px;
  padding: 5px;
  color: gray;
}

  .user_avatar {
    width: 40px;
    height: 40px;
    border-radius: 20px;
  }
</style>
