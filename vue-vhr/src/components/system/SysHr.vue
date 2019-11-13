<template>
  <div class="box" style="margin: 10px">
    <div class="box-toolbar">
      <div class="search-bar" align="left">
        <el-input
          placeholder="默认展示部分用户，可以通过用户名搜索更多用户..."
          prefix-icon="el-icon-search"
          size="small"
          style="width: 400px;margin-right: 10px"
          v-model="search"
        ></el-input>
      </div>
    </div>

    <el-table v-loading="loading" :stripe="true" :data="hrs">
      <el-table-column align="center" width="90" label="用户角色">
        <template slot-scope="scope">
          <el-popover
            placement="right"
            title="角色列表"
            width="200"
            trigger="click"
            @hide="handleUpdateHrRoles(scope.row, scope.$index)"
          >
            <el-select v-model="selRoles" multiple placeholder="请选择">
              <el-option
                v-for="item in allRoles"
                :key="item.id"
                :label="item.nameZh"
                :value="item.id"
              ></el-option>
            </el-select>
            <el-button
              slot="reference"
              type="text"
              icon="el-icon-more"
              style="color: #09c0f6"
              :disabled="moreBtnState"
              @click="loadSelRoles(scope)"
            ></el-button>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column align="center" width="90" class-name="text-mono" label="名字">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column align="center" width="150" class-name="text-mono" label="电话号码">
        <template slot-scope="scope">{{ scope.row.phone }}</template>
      </el-table-column>
      <el-table-column width="150" align="center" label="用户状态">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.enabled"
            active-text="启用"
            inactive-text="禁用"
            @change="handleSwitch(scope.row)"
            size="mini"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column width="60" align="center" label="头像">
        <template slot-scope="scope">
          <img :src="scope.row.userface" style="width: 50px; height: 50px; border-radius: 50px" />
        </template>
      </el-table-column>
      <el-table-column align="center" width="160" class-name="text-mono" label="用户角色">
        <template slot-scope="scope">
          <el-tag
            type="success"
            v-for="role in scope.row.roles"
            :key="role.id"
            effect="dark"
            size="mini"
            closable
          >{{role.nameZh}}</el-tag>
        </template>
      </el-table-column>
      <el-table-column align="center" width="79" class-name="text-mono" label="删除">
        <template slot-scope="scope">
          <el-button
            type="text"
            style="color: #F6061b; width: 20px; heigh:20px"
            icon="el-icon-delete"
            @click="handleHrDelete(scope)"
          ></el-button>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="text-mono" label="备注">
        <template slot-scope="scope">{{scope.row.remark}}</template>
      </el-table-column>
    </el-table>

    <div class="bp-pages" v-if="pagination">
      <el-pagination
        @current-change="handleCurrentChange"
        :background="true"
        :current-page="pagination.currentPage"
        :page-size="pagination.rowsPerPage|| 10"
        layout="total, prev, pager, next, jumper"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      loading: true,
      search: "",
      hrs: [],
      totalHrs: 0,
      pagination: {},
      moreBtnState: false,
      allRoles: [],
      selRoles: [],
      //   search
    };
  },
  watch: {
    pagination: {
      // 监视pagination属性的变化
      deep: true, // deep为true，会监视pagination的属性及属性中的对象属性变化
      handler() {
        // 变化后的回调函数，这里我们再次调用getDataFromServer即可
        this.getHrs();
      }
    },
    search: {
      // 监视搜索字段
      handler() {
        this.getHrs();
      }
    }
  },
  methods: {
    // searchClick() {
    //   this.getHrs();
    // },
    handleCurrentChange(page) {
      this.pagination.currentPage = page;
      this.getHrs();
    },
    getHrs() {
      this.loading = true;
      this.$http
        .get("/system/hr", {
          params: {
            key: this.search === "" ? "all" : this.search, // 搜索条件
            page: this.pagination.currentPage, // 当前页
            rows: this.pagination.rowsPerPage // 每页大小
          }
        })
        .then(resp => {
          this.hrs = resp.data.items;
          this.totalHrs = resp.data.total;
          this.loading = false;
        })
        .catch(() => {
          this.$message({
            type: "error",
            message: "出现错误！"
          });
        });
    },
    async getRoles() {
      await this.$http.get("/system/basicMsg/roles").then(({ data }) => {
        this.allRoles = data;
      });
      this.loading = false;
    },
    loadSelRoles({ row, rowIndex }) {
      this.moreBtnState = true;
      (this.selRoles = []),
        row.roles.forEach(role => {
          this.selRoles.push(role.id);
        });
    },
    handleUpdateHrRoles(row, index) {
      this.moreBtnState = false;
      //   console.log(row);
      //   console.log(index);
      let params = {
        hrId: row.id,
        rids: this.selRoles.join(",")
      };
      this.$http
        .put("/system/hr/roles", this.$qs.stringify(params))
        .then(() => {
          this.reloadHr(row.id, index);
        });
    },
    reloadHr(hrId, index) {
      this.$http.get("/system/hr/" + hrId).then(({ data }) => {
        this.hrs.splice(index, 1, data);
        this.selRoles = [];
      });
    },
    handleSwitch(row){
      
      this.$http.put("/system/hr/switch",this.$qs.stringify({
        id: row.id,
        enabled: row.enabled
      })
         ).then(() => {
        this.$notify({
        title: "更新成功！",
        dangerouslyUseHTMLString: true,
        message: `
            <div>名字: ${row.name}</div>
            <div>用户状态: ${row.enabled}</div>
          `,
        // <div>Description: ${description}</div>
        type: "success"
      });
      });
    },
    handleHrDelete(scope){
      this.$http.delete("system/hr/"+scope.row.id).then(() => {
        this.$notify({
        title: "删除成功！",
        dangerouslyUseHTMLString: true,
        message: `
            <div>名字: ${row.name}</div>
            <div>用户状态: 已删除！</div>
          `,
        // <div>Description: ${description}</div>
        type: "success"
      });
      this.hrs.splice(scope.$index,1);
      })
    }
  },
  created() {
    this.getHrs();
    this.getRoles();
  }
};
</script>
