<template>
  <div>
    <div>
      <el-input
        placeholder="默认展示部分用户，可以通过用户名搜索更多用户..."
        prefix-icon="el-icon-search"
        size="small"
        style="width: 400px;margin-right: 10px"
        v-model="search"
      ></el-input>
      <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%;margin:10px;float:left;"
        border
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="60" align="center"></el-table-column>
        <el-table-column prop="name" label="姓名" width="120" align="center"></el-table-column>
        <el-table-column prop="workID" label="工号" align="center"></el-table-column>
        <el-table-column prop="phone" label="手机号码" align="center"></el-table-column>
        <el-table-column prop="email" label="邮箱" align="center"></el-table-column>
        <el-table-column prop="departmentName" label="部门" align="center"></el-table-column>
        <el-table-column label="工资账套" align="center" width="120px">
          <template slot-scope="scope">
            <el-tooltip placement="right">
              <div slot="content">
                <template v-if="scope.row.salary">
                  <div>
                    <el-tag size="mini">基本工资</el-tag>
                    ￥{{scope.row.salary.basicSalary}}
                  </div>
                  <div>
                    <el-tag size="mini">奖金</el-tag>
                    ￥{{scope.row.salary.bonus}}
                  </div>
                  <div>
                    <el-tag size="mini">午餐补助</el-tag>
                    ￥{{scope.row.salary.lunchSalary}}
                  </div>
                  <div>
                    <el-tag size="mini">交通补助</el-tag>
                    ￥{{scope.row.salary.trafficSalary}}
                  </div>
                  <div>
                    <el-tag size="mini">应发工资</el-tag>
                    ￥{{scope.row.salary.allSalary}}
                  </div>
                  <div>
                    <el-tag size="mini">医疗保险基数</el-tag>
                    ￥{{scope.row.salary.medicalBase}}
                  </div>
                  <div>
                    <el-tag size="mini">医疗保险比率</el-tag>
                    ￥{{scope.row.salary.medicalPer}}
                  </div>
                  <div>
                    <el-tag size="mini">养老金基数</el-tag>
                    ￥{{scope.row.salary.pensionper}}
                  </div>
                  <div>
                    <el-tag size="mini">养老金比率</el-tag>
                    ￥{{scope.row.salary.pensionPer}}
                  </div>
                  <div>
                    <el-tag size="mini">公积金基数</el-tag>
                    ￥{{scope.row.salary.accumulationFundBase}}
                  </div>
                  <div>
                    <el-tag size="mini">公积金比率</el-tag>
                    ￥{{scope.row.salary.accumulationFundPer}}
                  </div>
                </template>
              </div>
              <el-tag size="mini">{{scope.row.salary ? scope.row.salary.name: '暂未设置'}}</el-tag>
            </el-tooltip>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-popover
              width="200px"
              @hide="updateEmployeeSalary(scope.row) "
              placement="right"
              trigger="click"
            >
              <el-select size="mini" style="width:120px" v-model="sid" placeholder="请选择">
                <el-option
                  v-for="item in salarys"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
              <el-button
                type="danger"
                slot="reference"
                style="width:120px"
                size="mini"
                @click="handleEdit(scope)"
              >修改{{scope.row.name}}账套</el-button>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div style="margin-top: 20px">
      <el-pagination
        :current-page.sync="pagination.page"
        :page-size="pagination.rowsPerPage"
        layout="prev, pager, next, jumper"
        :total="total"
        style="float:right; margin-right:10px"
      ></el-pagination>
    </div>
  </div>
</template>


<script>
import { parseTime } from "@/utils";
import { log } from "util";

export default {
  data() {
    return {
      loading: false,
      salarys: [],
      sid: "",
      tableData: [],
      multipleSelection: [],
      dialogDetailVisible: false,
      search: "",
      pagination: {
        page: 1,
        rowsPerPage: 10
      },
      total: 0
    };
  },
  watch: {
    pagination: {
      // 监视pagination属性的变化
      deep: true, // deep为true，会监视pagination的属性及属性中的对象属性变化
      handler() {
        // 变化后的回调函数，这里我们再次调用getDataFromServer即可
        this.getEmpleeSalarys();
      }
    },
    search: {
      // 监视搜索字段
      handler() {
        this.getEmpleeSalarys();
      }
    }
  },
  computed: {
    // submitButton() {
    //   return this.dialogType == "add" ? "添加账套" : "修改账套";
    // }
  },

  methods: {
    handleEdit(scope) {
      this.sid = scope.row.salary ? scope.row.salary.id : null;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    getEmpleeSalarys() {
      this.$http
        .get("/salary/sobcfg", {
          params: {
            key: this.search, // 搜索条件
            page: this.pagination.page, // 当前页
            rows: this.pagination.rowsPerPage
          }
        })
        .then(({ data }) => {
          this.tableData = data.items;
          this.total = data.total;
          this.loading = false;
        });
    },
    getSalaies() {
      this.$http.get("/salary/sob").then(({ data }) => {
        this.salarys = data;
      });
    },
    updateEmployeeSalary(row) {
      console.log(row)
      this.$http
        .put(
          "/salary/sobcfg",
          this.$qs.stringify({
            eid: row.id,
            sid: this.sid
          })
        )
        .then(() => {
          this.$message({ message: "修改成功", type: "success" });
          this.getEmpleeSalarys();
        });
    }
  },
  mounted() {
    this.loading = true;
    this.getEmpleeSalarys();
    this.getSalaies();
  }
};
</script>