<template>
  <v-app>
    <nav-bar></nav-bar>
    <div class="put-index">
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
                style="width: 100%"
              ></el-date-picker>
            </el-col>
            <el-col class="col" :span="1"></el-col>
            <el-col :span="10">
              <el-time-picker
                placeholder="选择时间"
                v-model="form.date2"
                style="width: 100%"
              ></el-time-picker>
            </el-col>
          </el-form-item>

          <el-alert
            title="（包含末尾数字，折扣区间0-10)"
            type="warning"
          ></el-alert>
          <el-form-item
            v-for="(domain, index) in dynamicValidateForm.domains"
            :key="domain.key"
            :label="'活动' + (index + 1)"
            class="mt-2"
          >
            第
            <el-input
              v-model.number="domain.start"
              type="number"
              @input="changePaidAmount($event, index)"
              class="w-65"
            />名到第
            <el-input
              v-model.number="domain.end"
              type="number"
              @input="changePaidAmount1($event, index)"
              class="w-65"
            ></el-input
            >名 打
            <el-input
              v-model="domain.discount"
              type="number"
              @input="changePaidAmount2($event, index)"
              class="w-40"
            ></el-input
            >折
            <el-button
              @click.prevent="removeDomain(domain)"
              class="btn-none border-no btn-hover ml-8"
              style="background-color: #e64c4c; color: #fff"
              >删除</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button @click="addDomain">新增规则</el-button>
          </el-form-item>

          <el-form-item label="活动介绍">
            <el-input type="textarea" v-model="form.desc"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              class="bg-color btn-none border-no btn-hover"
              style="color: #fff"
              @click="addSecKill"
              >立即创建</el-button
            >
            <el-button @click="dialogControl">取消</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <div
        v-for="(item, index) in goodsInfo"
        :key="index"
        class="put-card pa-15 glassmorphism"
      >
        <div style="width: 50%" class="right mt-12">
          <h1 class="put-title">
            {{ item.goodName }}
          </h1>
          <div class="put-price">￥ {{ item.price }}</div>
          <div class="mt-8">{{ item.description }}</div>
          <div class="put-btn mt-12">
            <button
              @click="dialogControl(item.pkGoodId)"
              class="overlay__btn overlay__btn--colors btn-none"
            >
              <span>秒杀活动</span>
              <span class="overlay__btn-emoji">🎨</span>
            </button>
          </div>
        </div>
        <img class="put-img" :src="item.image" />
      </div>
    </div>
  </v-app>
</template>

<script>
import NavBar from "../../components/NavBar";
import { hbStrategyadd } from "@/utils/request.js";
import { USER_ID } from "@/store/mutation-types";
const limit = require("@/utils/limitInputNum.js");
import Vue from "vue";
export default {
  data() {
    return {
      // startRules:[{ required: true, message: "请输入开始排名",trigger: "blur" }],
      goodsInfo: [],
      updatecenterDialogVisible: false,
      form: {
        date1: "",
        date2: "",
        desc: "",
      },
      goodId: "",
      dynamicValidateForm: {
        domains: [
          {
            start: "",
            end: "",
            discount: "",
          },
        ],
      },
    };
  },
  created() {
    this.getAllGoodsByUser();
  },
  methods: {
    changePaidAmount(e, index) {
      this.dynamicValidateForm.domains[index].start = limit.limitInputNumber(
        e,
        1000,
        0,
        4
      );
      if (e > 1000) {
        this.$message({
          message: "请输入少于1000的值",
          type: "warning",
        });
      }
    },
    changePaidAmount1(e, index) {
      this.dynamicValidateForm.domains[index].end = limit.limitInputNumber(
        e,
        1000,
        1,
        4
      );
      if (e > 1000) {
        this.$message({
          message: "请输入少于1000的值",
          type: "warning",
        });
      }
    },
    changePaidAmount2(e, index) {
      this.dynamicValidateForm.domains[index].discount = limit.limitInputNumber(
        e,
        10,
        0,
        3
      );
      if (e > 10) {
        this.$message({
          message: "最多打10折（即原价）",
          type: "warning",
        });
      }
    },
    getAllGoodsByUser() {
      let params = new URLSearchParams();
      params.append("userId", Vue.ls.get(USER_ID));
      this.axios
        .post(this.GLOBAL.contentUrl + "/goods/findGoodsByUserId", params)
        .then((res) => {
          this.goodsInfo = res.data.data.Goods;
        });
    },
    dialogControl(goodId) {
      this.updatecenterDialogVisible = !this.updatecenterDialogVisible;
      this.goodId = goodId;
      this.getSecKillInfo();
    },
    getSecKillInfo() {
      let params = new URLSearchParams();
      params.append("goodId", this.goodId);
      this.axios
        .post(this.GLOBAL.contentUrl + "/hbStrategy/get", params)
        .then((res) => {
          let data = res.data.data;
          //根据策略开始的名次对折扣进行排序
          data.ruleDtoList.sort(this.compare("start"));
          this.goodId = data.goodId;
          this.form.date1 = data.day;
          this.form.date2 = data.detail;
          this.form.desc = data.description;
          for (let i = 0; i < data.ruleDtoList.length; i++) {
            let domain = {
              start: data.ruleDtoList[i].start,
              end: data.ruleDtoList[i].end,
              discount: data.ruleDtoList[i].discount,
            };
            this.dynamicValidateForm.domains[i] = domain;
          }
        });
    },
    compare(property) {
      return function (a, b) {
        var value1 = a[property];
        var value2 = b[property];
        return value1 - value2;
      };
    },
    removeDomain(item) {
      var index = this.dynamicValidateForm.domains.indexOf(item);
      if (index !== -1) {
        this.dynamicValidateForm.domains.splice(index, 1);
      }
    },
    addDomain() {
      this.dynamicValidateForm.domains.push({});
    },
    addSecKill() {
      this.$confirm("此操作将修改商品获得, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
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
          console.log(date1, date2);
          let params = {
            goodId: this.goodId,
            day: date1,
            detail: date2,
            description: this.form.desc,
            ruleDtoList: this.dynamicValidateForm.domains,
          };
          hbStrategyadd(params).then((res) => {
            this.$message({
              message: "创建成功",
              type: "success",
            });
            this.updatecenterDialogVisible = !this.updatecenterDialogVisible;
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消修改",
          });
        });
    },
  },
  components: {
    NavBar,
  },
};
</script>

<style>
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
.put-index {
  height: 100%;
  width: 100%;
  background-image: linear-gradient(#d4ecec, #91c2be);
}

.put-card {
  margin: 100px auto;
  text-align: center;
  width: 83%;
  height: 530px;
}
.put-title {
  font-size: 3rem;
  width: 100%;
}
.put-img {
  width: 400px;
  height: 400px;
  float: right;
}
.put-price {
  color: #26a69a;
  font-size: 1.875rem;
  width: 100%;
}
.overlay__btn {
  width: 30%;
  height: 2.5rem;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 0.875rem;
  font-weight: 600;
  color: #fff;
  background: #26a69a;
  border: none;
  border-radius: 0.5rem;
  transition: transform 150ms ease;
  outline-color: hsl(#26a69a, 95%, 50%);
}

.overlay__btn:hover {
  transform: scale(1.05);
  cursor: pointer;
}

.overlay__btn--transparent {
  background: transparent;
  color: #26a69a;
  border: 2px solid #26a69a;
  border-width: 2px;
  margin-right: 0.75rem;
}

.overlay__btn-emoji {
  margin-left: 0.375rem;
}
.put-btn {
  display: flex;
  justify-content: center;
}
</style>
