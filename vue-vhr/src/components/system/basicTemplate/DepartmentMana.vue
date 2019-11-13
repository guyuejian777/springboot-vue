<template>
  <div>
    <div class="box-toolbar" align="left">
      <div class="search-bar">
        <el-input
          placeholder="输入部门名称搜索部门..."
          prefix-icon="el-icon-search"
          size="mini"
          style="width: 400px;margin-right: 10px"
          v-model="filterText"
        ></el-input>
      </div>
    </div>
    <div class="custom-tree-container">
      <el-tree
        class="filter-tree"
        :props="defaultProps"
        :data="treeData"
        ref="tree"
        highlight-current
        default-expand-all
        :render-content="renderContent"
        :filter-node-method="filterNode"
        style="width:500px; margin-top:10px"
      ></el-tree>
    </div>
    <div style="text-align:left">
      <el-dialog title="添加部门" :visible.sync="dialogVisible" width="25%">
        <div>
          <span>上级部门</span>
          <el-select v-model="department.pid" placeholder="请选择" style="width:200px" size="mini">
            <div v-for="item in allDep" :key="item.id">
              <el-option :label="item.name" :value="item.id"></el-option>
            </div>
          </el-select>
        </div>
        <div style="margin-top:10px;">
          <span>部门名称</span>
          <el-input v-model="department.name" size="mini" style="width:200px"></el-input>
        </div>

        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="confirmAppend">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      treeData: [],
      filterText: "",
      department: {
        name: "",
        pid: ""
      },
      defaultProps: {
        children: "children",
        isLeaf: "leaf",
        label: "name"
      },
      allDep: [],
      dialogVisible: false,
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    getTreeData() {
      this.$http.get("system/basicMsg/departments/-1").then(({ data }) => {
        this.treeData = data;
      });
    },
    getAllDepartment() {
      this.$http.get("system/basicMsg/departments").then(({ data }) => {
        this.allDep = data;
      });
    },
    append(data) {
      this.dialogVisible = true;
      this.getAllDepartment();
      this.department.pid = data.id;
    },
    confirmAppend() {
      if (
        this.department.name == null ||
        this.department.name == "" ||
        this.department.pid == null ||
        this.department.pid == ""
      ) {
        return;
      }
      this.$http
        .post("system/basicMsg/departments", {
          name: this.department.name,
          parentId: this.department.pid
        })
        .then(({ data }) => {
          this.department.name = "";
          this.department.pid = "";
          this.setDataToTree(this.treeData, data);
          this.dialogVisible = false;
        });
    },
    setDataToTree(treeData, data) {
      for (let i = 0; i < treeData.length; i++) {
        if (treeData[i].id == data.parentId) {
          treeData[i].children.push(data);
          return;
        } else {
          this.setDataToTree(treeData[i].children, data);
        }
      }
    },
    remove(node, data) {
      if (data.children.length > 0) {
        this.$message({
          type: "error",
          message: "该部门下还有其他部门，不能删除"
        });
        return;
      }
      this.$confirm("此操作将永久" + data.name + "部门, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.$http
            .delete("system/basicMsg/departments/" + data.id)
            .then(resp => {
              if (resp.status == 405) {
                this.$message({
                  type: "warning",
                  message: "该部门尚有员工或有子部门"
                });
              } else if (resp.status == 200) {
                this.deleteFromTree(this.treeData, data);
                this.$notify({
                  title: "删除成功！",
                  dangerouslyUseHTMLString: true,
                  message: `
            <div>部门名称: ${data.name}</div>
            <div>部门id: ${data.id}</div>
          `,
                  // <div>Description: ${description}</div>
                  type: "success"
                });
              }
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    deleteFromTree(treeData, data) {
      for (let i = 0; i < treeData.length; i++) {
        if (treeData[i].id == data.id) {
          treeData.splice(i, 1);
          return;
        } else {
          this.deleteFromTree(treeData[i].children, data);
        }
      }
    },
    renderContent(h, { node, data, store }) {
      return (
        <span class="custom-tree-node">
          <span>{node.label}</span>
          <span>
            <el-button
              style="font-size: 12px; padding:3px"
              size="mini"
              type="primary"
              on-click={() => this.append(data)}
            >
              添加部门
            </el-button>
            <el-button
              style="font-size: 12px; padding:3px"
              size="mini"
              type="danger"
              on-click={() => this.remove(node, data)}
            >
              删除部门
            </el-button>
          </span>
        </span>
      );
    }
  },
  mounted() {
    this.getTreeData();
  }
};
</script>

<style>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>