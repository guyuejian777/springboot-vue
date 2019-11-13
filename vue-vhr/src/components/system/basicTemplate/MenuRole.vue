<template>
  <div class="app-container">
    <div style="text-align: left; margin-top:10px">
      <el-button type="primary" size @click="handleAddRole">添加角色</el-button>
    </div>

    <el-table :data="rolesList" style="width: 70%;margin-top:30px;" border>
      <el-table-column align="center" label="角色名称" width="200">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column align="center" label="中文角色" width="120">
        <template slot-scope="scope">{{ scope.row.nameZh }}</template>
      </el-table-column>
      <el-table-column label="详细信息" align="center" width="95">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleFetchDetail(row)">
            <el-link :underline="false" type="primary">可访问资源</el-link>
          </span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleEdit(scope)">修改</el-button>
          <el-button type="danger" size="small" @click="handleDelete(scope)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :visible.sync="dialogVisible" :title="dialogType==='edit'?'修改角色':'新建角色'">
      <div style="margin-top: 20px">
        <el-input v-model="role.name" placeholder="角色英文名称">
          <template slot="prepend">ROLE_</template>
        </el-input>
      </div>
      <div style="margin-top: 20px">
        <el-input v-model="role.nameZh" placeholder="角色英文名称"></el-input>
      </div>
      <div style="margin-top: 20px">
        <el-tree
          ref="tree"
          :props="defaultProps"
          :data="treeData"
          :default-checked-keys="checkedKeys"
          node-key="id"
          show-checkbox
          highlight-current
          default-expand-all
        />
      </div>

      <div style="text-align:right;">
        <el-button type="danger" @click="dialogVisible=false">取消</el-button>
        <el-button type="primary" @click="confirmRole">确认</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogDetailVisible" :title="role.nameZh">
      <el-tree
        ref="tree"
        :props="defaultProps"
        :data="treeData"
        :default-checked-keys="checkedKeys"
        node-key="id"
        show-checkbox
        highlight-current
        default-expand-all
      />
    </el-dialog>
  </div>
</template>

<script>
// import path from 'path'
import { deepClone } from "@/utils";
// import { getRoutes, getRoles, addRole, deleteRole, updateRole } from '@/api/role'

const defaultRole = {
  id: "",
  name: "",
  nameZh: ""
};

export default {
  data() {
    return {
      role: Object.assign({}, defaultRole),
      treeData: [],
      rolesList: [],
      checkedKeys: [],
      dialogVisible: false,
      dialogDetailVisible: false,
      loading: false,
      dialogType: "new",
      defaultProps: {
        children: "children",
        label: "name"
      }
    };
  },
  mounted() {
    // Mock: get all routes and roles list from server
    this.loading = true;
    this.getRoles();
  },

  methods: {
    async getRoles() {
      await this.$http.get("/system/basicMsg/roles").then(({ data }) => {
        this.rolesList = data;
      });
      this.loading = false;
    },
    async getMenuTree() {
      await this.$http.get("/system/basicMsg/menuTree/").then(({ data }) => {
        this.treeData = data.menus;
      });
    },
    async handleAddRole() {
      this.role = Object.assign({}, defaultRole);
      this.checkedKeys = [];
      await this.getMenuTree();
      this.dialogType = "new";
      this.dialogVisible = true;
    },
    async getTreeDataAndKesy(row) {
      this.role = deepClone(row);
      await this.$http.get("/system/basicMsg/menuTree/" + row.id).then(({ data }) => {
        this.treeData = data.menus;
        this.checkedKeys = data.mids;
      });
    },
    async handleEdit(scope) {
      await this.getTreeDataAndKesy(scope.row);

      this.dialogType = "edit";
      this.dialogVisible = true;
    },
    async handleFetchDetail(row) {
      await this.getTreeDataAndKesy(row);

      this.dialogDetailVisible = true;
    },
    handleDelete({ $index, row }) {
      this.$confirm("Confirm to remove the role?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          await this.$http
            .delete("/system/basicMsg/role/" + row.id)
            .then(() => {
              this.rolesList.splice($index, 1);
              this.$message({
                type: "success",
                message: "删除成功!"
              });
            });
        }).catch(err => {
          console.error(err);
        });
    },
    async confirmRole() {
      this.loading = true;
      if (
        this.role.name == null ||
        this.role.name == "" ||
        this.role.nameZh == null ||
        this.role.nameZh == ""
      ) {
        return;
      }
      const isEdit = this.dialogType === "edit";

      const checkedKeys = this.$refs.tree.getCheckedKeys(true);
      console.log(checkedKeys);
      let params = {
        id: this.role.id,
        name: this.role.name,
        nameZh: this.role.nameZh,
        mids: checkedKeys.join(",")
      };

      if (isEdit) {
        // await updateRole(this.role.key, this.role);

        await this.$http
          .put("/system/basicMsg/role", this.$qs.stringify(params))
          .then(() => {
            this.$message({
              type: "success",
              message: "修改成功"
            });
            this.getRoles();
          });
      } else {
        // this.role.key = data.key
        await this.$http
          .post("/system/basicMsg/role", this.$qs.stringify(params))
          .then(resp => {
            this.$message({
              type: "success",
              message: "新增成功！"
            });
            this.getRoles();
          })
          .catch(() => {
            this.$message({
              type: "error",
              message: "新增失败！"
            });
          });
      }

      const { name, nameZh } = this.role;
      this.dialogVisible = false;
      this.$notify({
        title: "成功！",
        dangerouslyUseHTMLString: true,
        message: `
            <div>角色名称: ${nameZh}</div>
            <div>Role Nmae: ${name}</div>
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
