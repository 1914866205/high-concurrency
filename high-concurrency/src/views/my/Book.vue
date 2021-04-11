<template>
  <div style="width:100%">
    <!-- 修改弹出框 -->
    <el-dialog
      class="dialog"
      title="编辑图书"
      :visible.sync="updatecenterDialogVisible"
      width="30%"
      left
      :modal="false"
    >
      <el-form :model="ruleForm" status-icon label-width="80px">
        <el-form-item required label="书名" prop="bookName">
          <el-input v-model="ruleForm.bookName"></el-input>
        </el-form-item>
        <el-form-item required label="作者" prop="author">
          <el-input v-model="ruleForm.author"></el-input>
        </el-form-item>
        <el-form-item required label="类型" prop="type">
          <el-input v-model="ruleForm.type"></el-input>
        </el-form-item>
        <el-form-item required label="简介" prop="description">
          <el-input v-model="ruleForm.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item required label="封面">
          <img :src="ruleForm.cover" alt style="width:100px;height:100px" @click="getClick()" />
          <!-- 隐藏的文件输入框 -->
          <input type="file" ref="upload" style="display:none;" @change="handlderFile()" />
        </el-form-item>
        <el-form-item required label="总数" prop="bookNumber">
          <el-input v-model="ruleForm.bookNumber"></el-input>
        </el-form-item>
        <el-form-item required label="库存量" prop="bookResidueNumber">
          <el-input v-model="ruleForm.bookResidueNumber"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="updatecenterDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmUpdate">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 增加弹出框 -->
    <el-dialog
      class="dialog"
      :modal="false"
      title="新增图书"
      :visible.sync="addcenterDialogVisible"
      width="50%"
      center
    >
      <el-form label-width="80px" :model="ruleForm1">
        <el-form-item required label="书名" prop="bookName">
          <el-input v-model="ruleForm1.bookName"></el-input>
        </el-form-item>
        <el-form-item required label="作者" prop="author">
          <el-input v-model="ruleForm1.author"></el-input>
        </el-form-item>
        <el-form-item required label="类型" prop="jobNumber">
          <el-input v-model="ruleForm1.jobNumber"></el-input>
        </el-form-item>
        <el-form-item required label="简介" prop="description">
          <el-input v-model="ruleForm1.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item required label="封面" prop="cover">
          <img :src="ruleForm1.cover" alt style="width:100px;height:100px" @click="getClick()" />
          <!-- 隐藏的文件输入框 -->
          <input type="file" ref="upload" style="display:none;" @change="handlderFile()" />
        </el-form-item>
        <el-form-item required label="总数" prop="bookNumber">
          <el-input v-model="ruleForm1.bookNumber"></el-input>
        </el-form-item>
        <el-form-item required label="库存量" prop="bookResidueNumber">
          <el-input v-model="ruleForm1.bookResidueNumber"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="addcenterDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmAdd">确 定</el-button>
      </span>
    </el-dialog>
    <el-row type="flex" class="ml-20 mt-10">
      <el-input
        v-model="input"
        clearable
        placeholder="请输入内容"
        class="blur-search"
        size="mini"
        @input="filterSearch()"
      ></el-input>
      <el-select size="mini" v-model="selectValue" placeholder="请选择" class="statu-search ml-10">
        <el-option
          v-for="item in options"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        ></el-option>
      </el-select>
      <el-button type="success" size="mini" class="ml-10">
        <i class="el-icon-search" style="color: rgb(247, 251, 255)"></i>
        <span class="light-font-color">搜索</span>
      </el-button>
    </el-row>
    <el-row class="df-jr-ac ml-20 mt-10">
      <el-col class="tl">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="small"
          @click="addcenterDialogVisible = true"
        >
          <span class="light-font-color">新增</span>
        </el-button>
        <el-button type="danger" icon="el-icon-delete" size="small" @click="delAll()">
          <span class="light-font-color">批量删除</span>
        </el-button>
        <!-- 删除提示框 -->
        <el-dialog
          class="dialog"
          :modal="false"
          title="提示"
          :visible.sync="batchdelVisible"
          width="300px"
          center
        >
          <div class="del-dialog-cnt">批量删除图书信息后不可恢复，是否确定删除？</div>
          <span slot="footer" class="dialog-footer">
            <el-button @click="batchdelVisible = false">取 消</el-button>
            <el-button type="primary" @click="deleteBatch()">确 定</el-button>
          </span>
        </el-dialog>
      </el-col>
      <el-col class="tr mr-20">
        <el-button icon="el-icon-refresh" size="small"></el-button>
      </el-col>
    </el-row>
    <!-- 表格展示 -->
    <el-row>
      <el-col span="1"></el-col>
      <el-col span="23" class="ml-20 mt-10">
        <el-table
          :data="bookList"
          stripe="true"
          style="width: 100%;"
          class="light-small-font"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" min-width="10%"></el-table-column>
          <el-table-column label="图书名" show-overflow-tooltip min-width="13%">
            <template slot-scope="scope">
              <span style="margin-left:-5%">{{ scope.row.bookName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="作者" show-overflow-tooltip min-width="13%">
            <template slot-scope="scope">
              <span>{{ scope.row.author }}</span>
            </template>
          </el-table-column>
          <el-table-column label="封面" show-overflow-tooltip min-width="15%">
            <template slot-scope="scope">
              <el-popover placement="right" width="300px" trigger="hover">
                <img :src="scope.row.cover" />
                <img
                  slot="reference"
                  :src="scope.row.cover"
                  style="max-height: 50px;max-width: 130px"
                />
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column label="类型" show-overflow-tooltip min-width="13%">
            <template slot-scope="scope">
              <span>{{ scope.row.type }}</span>
            </template>
          </el-table-column>
          <el-table-column label="简介" min-width="13%">
            <template slot-scope="scope">
              <el-popover placement="top" max-width="300px" trigger="hover">
                <span style="display:block; width: 600px;">{{ scope.row.description }}</span>
                <span slot="reference" class="text-ellipsis">{{ scope.row.description }}</span>
              </el-popover>
            </template>
          </el-table-column>
          <el-table-column
            label="是否还有库存"
            show-overflow-tooltip
            min-width="15%"
            :formatter="statusChange"
          ></el-table-column>
          <el-table-column label="创建时间" show-overflow-tooltip min-width="18%">
            <template slot-scope="scope">
              <i class="el-icon-time"></i>
              <span>{{ scope.row.gmtCreate }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" show-overflow-tooltip min-width="23%">
            <template slot-scope="scope">
              <el-button size="mini" type="success" @click="handleUpdate(scope.$index, scope.row)">
                <span class="light-font-color">编辑</span>
              </el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">
                <span class="light-font-color">删除</span>
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <!-- 删除提示框 -->
    <el-dialog
      class="dialog"
      title="提示"
      :modal="false"
      :visible.sync="delVisible"
      width="300px"
      center
    >
      <div class="del-dialog-cnt">图书信息删除不可恢复，是否确定删除？</div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="delVisible = false">取 消</el-button>
        <el-button type="primary" @click="deleteRow">确 定</el-button>
      </span>
    </el-dialog>
    <div class="block" style="margin-top:2%">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[8, 16, 24, 32, 40]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </div>
  </div>
</template>
<script src="https://gosspublic.alicdn.com/aliyun-oss-sdk.min.js"></script>
<script>
const API = require("../utils/api");
export default {
  data() {
    var validatePass = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.ruleForm.checkPass !== "") {
          this.$refs.ruleForm.validateField("checkPass");
        }
        callback();
      }
    };
    var validatePass2 = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.ruleForm.pass) {
        callback(new Error("两次输入密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      bookList: [],
      bookList1: [],
      detailList: [],
      currentPage: 0,
      total: 40,
      pageSize: 8,
      updatecenterDialogVisible: false,
      addcenterDialogVisible: false,
      datailcenterDialogVisible: false,
      delVisible: false, //删除提示弹框的状态
      batchdelVisible: false,
      delarr: [], //存放删除的数据
      multipleSelection: [],
      value1: "",
      input: "",
      msg: "", //记录每一条的信息，便于取id
      ruleForm: {
        author: "",
        type: "",
        cover:
          "https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png",
        description: "",
        bookNumber: "",
        bookResidueNumber: "",
        bookName: ""
      },
      ruleForm1: {
        author: "",
        type: "",
        cover:
          "https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png",
        description: "",
        bookNumber: "",
        bookResidueNumber: "",
        bookName: ""
      },
      file: "",
      rules: {
        pass: [{ validator: validatePass, trigger: "blur" }],
        checkPass: [{ validator: validatePass2, trigger: "blur" }]
      }
    };
  },
  created() {
    this.getBookAll();
  },
  watch: {
    pageSize: function() {
      this.getBookAll();
    },
    currentPage: function() {
      this.getBookAll();
    },
    total: function() {}
  },
  filters: {
    ellipsis(value) {
      if (!value) return "";
      if (value.length > 10) {
        return value.slice(0, 6) + "...";
      }
      return value;
    }
  },
  methods: {
    // eslint-disable-next-line no-unused-vars
    statusChange: function(row, column) {
      return row.bookResidueNumber > 0
        ? "有库存"
        : row.bookResidueNumber == 0
        ? "无库存"
        : "aaa";
    },
    // 分页查询所有
    async getBookAll() {
      this.data = { currentPage: this.currentPage, pageSize: this.pageSize };
      this.url = "/book/all";
      this.result = await API.init(this.url, this.data, "post");
      this.bookList = this.result.data;
      console.log(this.result.data);
      this.bookList1 = this.result.data;
      for (let i = 0; i < this.bookList.length; i++) {
        this.bookList[i].gmtCreate = this.formatDate(
          this.bookList[i].gmtCreate
        );
      }
    },
    // 当前页展示数据
    handleSizeChange: function(pageSize) {
      this.pageSize = pageSize;
    },
    // 当前页
    handleCurrentChange: function(currentPage) {
      this.currentPage = currentPage;
    },
    //单行删除
    handleDelete(index, row) {
      this.idx = index;
      this.msg = row; //每一条数据的记录
      this.delVisible = true;
    },
    async deleteRow() {
      this.data = { field: this.msg.pkBookId };
      this.url = "/book/id";
      this.result = await API.init(this.url, this.data, "post");
      if (this.data) {
        this.getBookAll();
        this.$message.success("删除成功");
      } else {
        this.$message.error("图书信息删除失败");
      }
      this.delVisible = false; //关闭删除提示模态框
    },
    //批量删除
    delAll() {
      this.batchdelVisible = true; //显示删除弹框
      const length = this.multipleSelection.length;
      for (let i = 0; i < length; i++) {
        this.delarr.push(this.multipleSelection[i].pkBookId);
      }
    },
    //操作多选
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //批量删除
    async deleteBatch() {
      this.data = { ids: String(this.delarr) };
      this.url = "/book/deletion/batch";
      this.result = await API.init(this.url, this.data, "post");
      if (this.data) {
        this.getBookAll();
        this.$message.success("批量删除成功");
      } else {
        this.$message.error("图书信息批量删除失败");
      }
      this.batchdelVisible = false; //关闭删除提示模态框
    },
    //编辑
    handleUpdate(index, row) {
      this.idx = index;
      this.msg = row; //每一条数据的记录
      this.updatecenterDialogVisible = true;
      this.ruleForm.author = this.msg.author;
      this.ruleForm.bookName = this.msg.bookName;
      this.ruleForm.cover = this.msg.cover;
      this.ruleForm.description = this.msg.description;
      this.ruleForm.type = this.msg.type;
      this.ruleForm.bookResidueNumber = this.msg.bookResidueNumber;
      this.ruleForm.bookNumber = this.msg.bookNumber;
    },
    //修改图书信息
    async confirmUpdate() {
      this.data = {
        pkBookId: this.msg.pkBookId,
        author: this.ruleForm.author,
        cover: this.ruleForm.cover,
        type: this.ruleForm.type,
        description: this.ruleForm.description,
        bookNumber: this.ruleForm.bookNumber,
        bookResidueNumber: this.ruleForm.bookResidueNumber,
        bookName: this.ruleForm.bookName
      };
      this.url = "/book/updation";
      this.result = await API.init(this.url, this.data, "post");
      this.updatecenterDialogVisible = false;
      this.getBookAll();
      this.$message.success("信息修改成功");
    },
    //新增图书
    async confirmAdd() {
      this.data = {
        author: this.ruleForm1.author,
        cover: this.ruleForm1.cover,
        type: this.ruleForm1.type,
        description: this.ruleForm1.description,
        bookNumber: this.ruleForm1.bookNumber,
        bookResidueNumber: this.ruleForm1.bookResidueNumber,
        bookName: this.ruleForm1.bookName
      };
      this.url = "/book/increased";
      this.result = await API.init(this.url, this.data, "post");
      this.addcenterDialogVisible = false;
      this.getBookAll();
      if (this.result.data == null) {
        this.$message.success("该图书账号已存在，请勿重复新增");
      } else {
        this.$message.success("图书添加成功");
      }
    },
    getClick() {
      this.$refs.upload.click();
    },
    handlderFile() {
      this.getClient();
      this.file = this.$refs.upload.files[0];
      var _this = this;
      async function put() {
        try {
          let result = await _this.client.put(
            _this.$refs.upload.files[0].name,
            _this.file
          );
          _this.ruleForm1.cover = result.url;
          _this.update();
        } catch (e) {
          alert("文件上传成功");
          console.log(e);
        }
      }
      put();
    },
    getClient() {
      let OSS = require("ali-oss");
      this.client = new OSS({
        region: "oss-cn-beijing",
        accessKeyId: "LTAIaG9RkwvVwXq6",
        accessKeySecret: "5WPkPJ4JY0nWciRfDpMFxzScm3oJn2",
        bucket: "zhent-img"
      });
    },
    formatDate(value) {
      let date = new Date(value);
      let y = date.getFullYear();
      let MM = date.getMonth() + 1;
      MM = MM < 10 ? "0" + MM : MM;
      let d = date.getDate();
      d = d < 10 ? "0" + d : d;
      let h = date.getHours();
      h = h < 10 ? "0" + h : h;
      let m = date.getMinutes();
      m = m < 10 ? "0" + m : m;
      let s = date.getSeconds();
      s = s < 10 ? "0" + s : s;
      return y + "年" + MM + "月" + d + "日" + h + ":" + m + ":" + s;
    },
    //过滤搜索
    filterSearch() {
      // 获取输入框的值
      let search = this.input;
      //数组元素按条件过滤
      this.bookList = this.bookList1.filter(v => {
        if (JSON.stringify(v).includes(search)) {
          return v;
        }
      });
    }
  }
};
</script>

<style scoped>
.top-input {
  width: 200px;
  height: 30px;
  margin-left: 50px;
}
.blur-search {
  width: 200px;
}
.statu-search {
  width: 100px;
}

el-input {
  height: 30px;
}

.search-btn {
  height: 30px;
  width: 80px;
}
.el-input__inner {
  height: 30px;
}
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

>>> .el-input__inner {
  height: 30px;
}

>>> .el-icon-edit {
  color: #f7fbff;
}

>>> .el-icon-plus {
  color: #f7fbff;
}

>>> .el-icon-delete {
  color: #f7fbff;
}

>>> .el-icon-download {
  color: #f7fbff;
}

>>> .el-range-separator {
  margin-bottom: 10px;
}

/* >>> .el-icon-search {
  color: #f7fbff;
} */

>>> .el-input__prefix {
  display: flex;
  align-items: center;
}

>>> .el-select__caret {
  margin-top: 5px;
}
</style>
