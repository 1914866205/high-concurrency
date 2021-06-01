<template>
  <v-app>
    <nav-bar></nav-bar>
    <alert
      v-show="publishSuc"
      @click="cancelSuc()"
      alertdata="商品发布成功"
    ></alert>
    <div class="publish index index-border">
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-text-field
          v-model="goodsValidate.goodName"
          :counter="30"
          :rules="nameRules"
          label="商品名称"
          required
        ></v-text-field>
        <v-textarea
          clearable
          clear-icon="mdi-close-circle"
          label="商品详情"
          v-model="goodsValidate.description"
        ></v-textarea>
        <div class="image-arr" :style="'max-width:' + maxLength + 'px;'">
          <div v-for="(item, index) in goodsValidate.image" :key="index">
            <figure
              @mouseover="mouseOver"
              @mouseleave="mouseLeave"
              data-color="#E24938, #A30F22"
            >
              <img style="width: 150px; height: 150px" :src="item" />
              <transition name="slideFade">
                <div
                  class="del-icon button-hand"
                  @click="delImage(index)"
                  transiton="slideFade"
                  v-show="showDel"
                >
                  <v-icon>mdi-trash-can-outline</v-icon>
                </div>
              </transition>
            </figure>
          </div>
          <div class="publish-image">
            <v-icon @click="imageClick()" size="90" color="#f6f6f6"
              >mdi-plus</v-icon
            >
            <input
              type="file"
              @change="uploadImage($event)"
              ref="file"
              style="display: none"
              id="file"
              accept="image/jpg, image/jpeg, image/png"
            />
          </div>
        </div>

        <div class="mt-6"></div>
        <span class="small">数量 :</span>
        <input
          v-model="goodsValidate.count"
          class="publish-input"
          type="number"
        />
        <span class="small ml-6">价格 : $</span>
        <input
          v-model="goodsValidate.price"
          class="publish-input"
          type="number"
        />
        <div class="mt-6 publish-tags">
          <v-chip-group mandatory active-class="primary--text">
            <v-chip @click="selectTag(tag)" v-for="tag in tags" :key="tag">
              {{ tag }}
            </v-chip>
          </v-chip-group>
        </div>
        <div class="mt-100"></div>
        <div style="margin-left: 45%">
          <v-btn
            color="teal"
            width="130"
            height="45"
            style="color: white"
            class="compon-btn btn-none border-no"
            :disabled="valid"
            @click="publish()"
            v-preventReClick
            >提交</v-btn
          >
        </div>
      </v-form>
    </div>
  </v-app>
</template>

<script>
import NavBar from "../../components/NavBar";
import Vue from "vue";
import Alert from "@/components/Alert";
import { goodsAddGoods } from "@/utils/request.js";
import { USER_ID } from "@/store/mutation-types";
export default {
  data: () => ({
    showDel: false,
    valid: true,
    maxLength: 170,
    publishSuc: false,
    nameRules: [
      (v) => !!v || "商品名称不能为空",
      (v) => (v && v.length <= 30) || "超过最长命名",
    ],
    select: null,
    tags: [
      "数码",
      "家电",
      "电脑",
      "家居",
      "服装",
      "箱包",
      "鞋类",
      "钟表",
      "珠宝",
      "母婴",
      "食品",
      "生鲜",
      "艺术",
      "医药",
      "图书",
      "机票",
      "酒店",
      "理财",
      "教育",
      "众筹",
      "维修",
      "工业品",
      "二手",
      "酒类",
      "旅游",
      "生活",
      "电子书",
      "其他",
    ],
    goodsValidate: {
      goodName: "",
      count: 0,
      image: [],
      price: 0,
      type: "数码",
      description: "",
    },
  }),
  watch: {
    goodsValidate: {
      handler(val, oldVal) {
        console.log("currentForm", val, oldVal);
        if (val.goodName != "" && val.image.length != 0 && val.count != 0) {
          this.valid = false;
        }
      },
      deep: true,
    },
  },
  methods: {
    mouseOver() {
      this.showDel = true;
    },
    mouseLeave() {
      this.showDel = false;
    },
    delImage(index) {
      //根据索引删除照片
      console.log(index);
      this.goodsValidate.image.splice(index, 1);
    },

    imageClick() {
      this.$refs.file.click();
    },
    selectTag(tag) {
      console.log(tag);
      this.goodsValidate.type = tag;
    },
    uploadImage(event) {
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
        let url ;
        if(index == -1) {
           url = result.res.requestUrls[0]
        } else{
           url = result.res.requestUrls[0].slice(0, index);
        }
        _this.goodsValidate.image.push(url);
        console.log(result.res);
        console.log(_this.goodsValidate.image.length);
        if (_this.goodsValidate.image.length == 1) {
          _this.maxLength = 320;
        } else if (_this.goodsValidate.image.length == 2) {
          _this.maxLength = 490;
        } else {
          _this.maxLength = 630;
        }
      });
    },
    publish() {
      let data = {
        count: this.goodsValidate.count,
        description: this.goodsValidate.description,
        goodName: this.goodsValidate.goodName,
        image: this.goodsValidate.image.toString(),
        pkUserIngId: Vue.ls.get(USER_ID),
        price: this.goodsValidate.price,
        type: this.goodsValidate.type,
      };

      goodsAddGoods( data ).then((res) => {
        this.publishSuc = true;
        setTimeout(this.cancelSuc(), 5000);
      });
      // await API.init(this.url, this.data, "post");
    },
    cancelSuc() {
      this.publishSuc = false;
      setTimeout(this.goIndex(), 5000);
    },
    goIndex() {
      this.$router.push("/");
    },
  },
  components: {
    NavBar,
    Alert,
  },
};
</script>

<style>
.publish {
  margin-top: 100px;
  padding: 40px;
}
.publish-input {
  border: 1px solid #f0eeee;
  width: 70px;
  height: 40px;
  border-radius: 10px;
  margin-left: 10px;
  outline: none;
  padding: 5px;
  color: gray;
}
.publish-image {
  border: 1px solid #f6f6f6;
  border-radius: 10px;
  width: 150px;
  height: 150px;
  padding: 30px;
}
.image-arr {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}
.publish-tags {
  position: absolute;
  width: 80%;
  left: 8%;
}
/* .publish-btn {
  width: 150px;
  margin-left: 45%;
  border-radius: 30px;
} */
.del-icon {
  position: absolute;
  z-index: 999;
  margin-top:-160px;
}
</style>
