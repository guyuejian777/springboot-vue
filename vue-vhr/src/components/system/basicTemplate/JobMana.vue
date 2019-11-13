<template>
  <div class="app-container">
    <div style="margin-top:10px">
      <div style="text-align: left; margin-top:10px">
        <el-button type="primary" size @click="handleAddPosition">{{addButton}}</el-button>
      </div>

      <el-table
        :data="dataList"
        style="width: 80%;margin-top:30px;"
        @selection-change="handleSelectionChange"
        stripe
      >
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column align="center" label="编号" width="70">
          <template slot-scope="scope">{{ scope.row.id }}</template>
        </el-table-column>
        <el-table-column align="center" :label="state=='position' ? '职位名称' : '职称名称'" width="200">
          <template slot-scope="scope">{{ scope.row.name }}</template>
        </el-table-column>
        <el-table-column align="center" v-if="state=='jobTitle'" label="职称级别" width="200">
          <template slot-scope="scope">{{ scope.row.titleLevel }}</template>
        </el-table-column>
        <el-table-column align="center" label="创建时间" width="120">
          <template slot-scope="scope">{{ scope.row.createDate | dataFormat('yyyy-MM-dd') }}</template>
        </el-table-column>
        <el-table-column align="center" label="操作" width="200">
          <template slot-scope="scope">
            <el-button type="primary" size="small" @click="handleEdit(scope)">修改</el-button>
            <el-button type="danger" size="small" @click="handleDelete(scope)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="text-align:left">
        <el-button
          type="danger"
          size="mini"
          v-if="dataList.length>0"
          :disabled="multipleSelection.length<1"
          @click="deleteSelection"
        >批量删除</el-button>
      </div>
    </div>

    <el-dialog :visible.sync="dialogVisible" width="25%" :title="dialogType==='edit'?'修改':'新建'">
      <div style>
        <el-select
          v-model="titleLevel"
          v-if="state=='jobTitle'"
          style="width:200px;"
          placeholder="职称等级"
        >
          <div v-for="(tl,index) in titleLevels" :key="index">
            <el-option :label="tl" :value="tl"></el-option>
          </div>
        </el-select>
        <el-input v-model="dialogName" style="width:200px;margin:10px" placeholder="请输入新的..."></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button type="danger" @click="dialogVisible=false" size="mini">Cancel</el-button>
        <el-button type="primary" @click="confirmPosition" size="mini">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { paresTime } from "@/utils";

const defaultPosition = {
  id: "",
  name: "",
  createDate: ""
};
const defaultJobTitle = {
  id: "",
  name: "",
  titleLevel: "",
  createDate: ""
};

export default {
  data() {
    return {
      position: Object.assign({}, defaultPosition),
      jobTitle: Object.assign({}, defaultJobTitle),
      dataList: [],
      checkedKeys: [],
      dialogVisible: false,
      loading: false,
      dialogType: "new",
      indexx: 0,
      dialogName: "",
      titleLevel: "",
      titleLevels: ["正高级", "副高级", "中级", "初级", "员级"],
      addButton: this.state == "position" ? "添加职位" : "添加职称",
      multipleSelection: []
    };
  },
  props: ["state"],
  mounted() {
    this.loading = true;
    this.getDataList();
  },

  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    deleteSelection() {
      this.$confirm(
        "确定删除" + this.multipleSelection.length + "条数据吗？?",
        "Warning",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }
      )
        .then(async () => {
          let ids = "";
          this.multipleSelection.forEach(sel => {
            ids = ids + sel.id + ",";
          });
          ids = ids.substring(0, ids.length - 1);
          await this.doDelete(ids);
          this.getDataList();
        })
        .catch(err => {
          this.$message({
            type: "info",
            message: "已取消删除!"
          });
        });
    },
    async getDataList() {
      await this.$http
        .get(
          this.state == "position"
            ? "/system/basicMsg/position/all"
            : "/system/basicMsg/jobTitle/all"
        )
        .then(({ data }) => {
          this.dataList = data;
        });
      this.loading = false;
    },

    async handleAddPosition() {
      this.position = Object.assign({}, defaultPosition);
      this.dialogType = "new";
      this.dialogVisible = true;
    },
    handleEdit(scope) {
      this.dialogName = scope.row.name;
      this.position.id = scope.row.id;
      this.titleLevel = scope.row.titleLevel;
      this.dialogType = "edit";
      this.dialogVisible = true;
    },
    async doDelete(ids) {
      let url =
        this.state == "position"
          ? "/system/basicMsg/position/"
          : "/system/basicMsg/jobTitle/";
      await this.$http.delete(url + ids).then(() => {
        this.$message({
          type: "success",
          message: "删除成功!"
        });
      });
    },
    async handleDelete({ $index, row }) {
      this.$confirm("确定删除" + row.name + "吗？?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          await this.doDelete(row.id);
          this.dataList.splice($index, 1);
        })
        .catch(err => {
          this.$message({
            type: "info",
            message: "已取消删除!"
          });
        });
    },
    async confirmPosition() {
      this.loading = true;
      if (this.dialogName == null || this.dialogName == "") {
        return;
      }
      const isEdit = this.dialogType === "edit";
      let editParams =
        this.state == "position"
          ? {
              id: this.position.id,
              name: this.dialogName
            }
          : {
              id: this.position.id,
              name: this.dialogName,
              titleLevel: this.titleLevel
            };
      let addParams =
        this.state == "position"
          ? {
              name: this.dialogName
            }
          : {
              name: this.dialogName,
              titleLevel: this.titleLevel
            };

      if (isEdit) {
        await this.$http
          .put(
            this.state == "position"
              ? "/system/basicMsg/position"
              : "/system/basicMsg/jobTitle",
            this.$qs.stringify(editParams)
          )
          .then(() => {
            this.$message({
              type: "success",
              message: "修改成功"
            });
            this.getDataList();
          });
      } else {
        //新增
        await this.$http
          .post(
            this.state == "position"
              ? "/system/basicMsg/position"
              : "/system/basicMsg/jobTitle",
            this.$qs.stringify(addParams)
          )
          .then(resp => {
            this.$message({
              type: "success",
              message: "新增成功！"
            });
            this.getDataList();
          })
          .catch(() => {
            this.$message({
              type: "error",
              message: "新增失败！"
            });
          });
      }

      const name = this.dialogName;
      this.dialogVisible = false;
      this.$notify({
        title: "成功！",
        dangerouslyUseHTMLString: true,
        message: `
            <div>职位名称: ${name}</div>
          `,
        // <div>Description: ${description}</div>
        type: "success"
      });
    }
    // reference: src/view/layout/components/Sidebar/SidebarItem.vue
  }
};
</script>

<style scoped>
</style>
