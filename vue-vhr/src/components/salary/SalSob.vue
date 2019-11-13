<template>
  <div>
    <el-button
      size="small"
      style="margin:10px;float:left;"
      type="primary"
      @click="addSalary()"
    >{{submitButton}}</el-button>

    <div>
      <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%;margin:10px;float:left;"
        border
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="60" align="center"></el-table-column>
        <el-table-column label="启用日期">
          <template slot-scope="scope">{{ scope.row.createDate | dataFormat("yyyy-MM-dd") }}</template>
        </el-table-column>
        <el-table-column prop="name" label="账套名" width="120" align="center"></el-table-column>
        <el-table-column prop="basicSalary" label="基本工资" align="center"></el-table-column>
        <el-table-column prop="bonus" label="奖金" align="center"></el-table-column>
        <el-table-column prop="lunchSalary" label="午餐补助" align="center"></el-table-column>
        <el-table-column prop="trafficSalary" label="交通补助" align="center"></el-table-column>
        <el-table-column prop="allSalary" label="应发工资" align="center"></el-table-column>
        <el-table-column label="养老金" align="center">
          <el-table-column prop="pensionBase" label="基数" align="center"></el-table-column>
          <el-table-column prop="pensionPer" label="比率" align="center"></el-table-column>
        </el-table-column>
        <el-table-column label="医疗保险" align="center">
          <el-table-column prop="medicalBase" label="基数" align="center"></el-table-column>
          <el-table-column prop="medicalPer" label="比率" align="center"></el-table-column>
        </el-table-column>
        <el-table-column label="公积金" align="center">
          <el-table-column prop="accumulationFundBase" label="基数" align="center"></el-table-column>
          <el-table-column prop="accumulationFundPer" label="比率" align="center"></el-table-column>
        </el-table-column>

        <el-table-column label="操作" width="160" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleEdit(scope)">修改</el-button>
            <el-button type="danger" size="mini" @click="handleDelete(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div style="margin-top: 20px">
      <el-button
        type="danger"
        size="mini"
        @click="deleteMany"
        :disabled="multipleSelection.length<1"
      >批量删除</el-button>
    </div>
    <el-dialog
      :visible.sync="dialogDetailVisible"
      :title="dialogType==='edit'? '修改:'+salary.name:'添加账套'"
      width="400px"
    >
      <el-form
        :model="salary"
        :rules="rules"
        style="width:400px;margin:left:5px;"
        ref="mySalaryForm"
        label-width="120px"
        class="demo-salary"
      >
        <el-form-item label="账套名称" prop="name">
          <el-input v-model="salary.name" style="width:180px;" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="启用日期" prop="createDate">
          <el-date-picker
            :disabled="dialogType==='edit'"
            style="width:180px;"
            size="mini"
            v-model="salary.createDate"
            type="date"
            placeholder="选择日期"
            format="yyyy 年 MM 月 dd 日"
            value-format="yyyy-MM-dd"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="基本工资" prop="basicSalary">
          <el-input v-model="salary.basicSalary" style="width:180px;" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="奖金" prop="bonus">
          <el-input v-model="salary.bonus" style="width:180px;" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="午餐补助" prop="lunchSalary">
          <el-input v-model="salary.lunchSalary" style="width:180px;" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="交通补助" prop="trafficSalary">
          <el-input v-model="salary.trafficSalary" style="width:180px;" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="应发工资" prop="allSalary">
          <el-input v-model="salary.allSalary" style="width:180px;" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="养老金基数" prop="pensionBase">
          <el-input v-model="salary.pensionBase" style="width:180px;" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="养老金比率" prop="pensionPer">
          <el-input v-model="salary.pensionPer" style="width:180px;" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="医疗保险基数" prop="medicalBase">
          <el-input v-model="salary.medicalBase" style="width:180px;" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="医疗保险比率" prop="medicalPer">
          <el-input v-model="salary.medicalPer" style="width:180px;" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="公积金基数" prop="accumulationFundBase">
          <el-input v-model="salary.accumulationFundBase" style="width:180px;" size="mini"></el-input>
        </el-form-item>
        <el-form-item label="公积金比率" prop="accumulationFundPer">
          <el-input v-model="salary.accumulationFundPer" style="width:180px;" size="mini"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="danger" @click="resetForm('mySalaryForm')">取消</el-button>
          <el-button type="primary" @click="submitForm('mySalaryForm')">{{submitButton}}</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>


<script>
import { parseTime } from "@/utils";

const defaultSalary = {
  id: "",
  basicSalary: "",
  bonus: "",
  lunchSalary: "",
  trafficSalary: "",
  allSalary: "",
  pensionBase: "",
  pensionPer: "",
  createDate: "",
  medicalBase: "",
  medicalPer: "",
  accumulationFundBase: "",
  accumulationFundPer: "",
  name: ""
};

export default {
  data() {
    return {
      salary: Object.assign({}, defaultSalary),
      tableData: [],
      multipleSelection: [],
      dialogType: "add",
      dialogDetailVisible: false,
      rules: {
        name: [{ required: true, message: "必填:账套名称", trigger: "blur" }],
        basicSalary: [
          { required: true, message: "不能为空" },
          {
            pattern: /^[0-9]+(\.?(?=[0-9])[0-9]{0,2})?$/,
            message: "格式不正确"
          }
        ],
        bonus: [
          { required: true, message: "不能为空" },
          {
            pattern: /^[0-9]+(\.?(?=[0-9])[0-9]{0,2})?$/,
            message: "格式不正确"
          }
        ],
        lunchSalary: [
          { required: true, message: "不能为空" },
          {
            pattern: /^[0-9]+(\.?(?=[0-9])[0-9]{0,2})?$/,
            message: "格式不正确"
          }
        ],
        trafficSalary: [
          { required: true, message: "不能为空" },
          {
            pattern: /^[0-9]+(\.?(?=[0-9])[0-9]{0,2})?$/,
            message: "格式不正确"
          }
        ],
        allSalary: [
          { required: true, message: "不能为空" },
          {
            pattern: /^[0-9]+(\.?(?=[0-9])[0-9]{0,2})?$/,
            message: "格式不正确"
          }
        ],
        pensionBase: [
          { required: true, message: "不能为空" },
          {
            pattern: /^[0-9]+(\.?(?=[0-9])[0-9]{0,2})?$/,
            message: "格式不正确"
          }
        ],
        pensionPer: [
          { required: true, message: "不能为空" },
          {
            pattern: /^[0-9]+(\.?(?=[0-9])[0-9]{0,2})?$/,
            message: "格式不正确"
          }
        ],
        medicalBase: [
          { required: true, message: "不能为空" },
          {
            pattern: /^[0-9]+(\.?(?=[0-9])[0-9]{0,2})?$/,
            message: "格式不正确"
          }
        ],
        medicalPer: [
          { required: true, message: "不能为空" },
          {
            pattern: /^[0-9]+(\.?(?=[0-9])[0-9]{0,2})?$/,
            message: "格式不正确"
          }
        ],
        accumulationFundBase: [
          { required: true, message: "不能为空" },
          {
            pattern: /^[0-9]+(\.?(?=[0-9])[0-9]{0,2})?$/,
            message: "格式不正确"
          }
        ],
        accumulationFundPer: [
          { required: true, message: "不能为空" },
          {
            pattern: /^[0-9]+(\.?(?=[0-9])[0-9]{0,2})?$/,
            message: "格式不正确"
          }
        ],
        createDate: [{ required: true, message: "不能为空", trigger: "blur" }],
        posId: [{ required: true, message: "必填:职位", trigger: "blur" }],
        school: [{ required: true, message: "必填:毕业院校", trigger: "blur" }]
      }
    };
  },
  computed: {
    submitButton() {
      return this.dialogType == "add" ? "添加账套" : "修改账套";
    }
  },

  methods: {
    addSalary() {
      (this.salary = Object.assign({}, defaultSalary)),
        (this.dialogType = "add");
      this.dialogDetailVisible = true;
    },
    handleEdit(scope) {
      this.salary = scope.row;
      this.dialogType = "edit";
      this.dialogDetailVisible = true;
    },
    handleDelete(id) {
      this.$confirm("Confirm to remove the role?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.doDelete(id);
        this.getTableData();
      });
    },
    deleteMany() {
      let ids = "";
      this.multipleSelection.forEach(m => {
        ids += "," + m.id;
      });
      ids = ids.substring(1, ids.length);
      this.$confirm("Confirm to remove the role?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        this.doDelete(ids);
        this.getTableData();
      });
    },
    doDelete(id) {
      this.$http.delete("/salary/sob/" + id).then(() => {
        this.$message({
          type: "success",
          message: "删除成功"
        });
      });
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          const isEdit = this.dialogType === "edit";

          if (isEdit) {
            this.$http.put("/salary/sob", this.salary).then(() => {
              this.getTableData();
              this.$message({
                type: "success",
                message: "修改成功"
              });
            });
          } else {
            //     // this.role.key = data.key
            this.$http
              .post("/salary/sob", this.salary)
              .then(() => {
                this.getTableData();
                this.$message({
                  type: "success",
                  message: "新增成功！"
                });
              })
              .catch(() => {
                this.$message({
                  type: "error",
                  message: "新增失败！"
                });
              });
          }
          const { name, createDate } = this.salary;
          this.salary = Object.assign({}, defaultSalary);
          let c = parseTime(createDate, "{y}-{m}-{d}");
          this.dialogDetailVisible = false;
          this.$notify({
            title: "成功！",
            dangerouslyUseHTMLString: true,
            message: `
              <div>账套名称: ${name}</div>
              <div>启用日期: ${c}</div>
            `,
            // <div>Description: ${description}</div>
            type: "success"
          });
        } else {
          console.log("error submit!!");
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    getTableData() {
      this.$http.get("/salary/sob").then(({ data }) => {
        this.tableData = data;
      });
    }
  },
  mounted() {
    this.getTableData();
  }
};
</script>