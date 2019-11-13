<template>
  <div class="app-container">
    <div class="search-bar" style="margin-top:10px" align="left">
      <el-button
        type="danger"
        size="mini"
        style="margin-right: 10px"
        v-if="dataList.length>0"
        :disabled="multipleSelection.length<1"
        @click="deleteSelection"
      >批量删除</el-button>
      <el-input
        placeholder="默认展示部分用户，可以通过用户名搜索更多用户..."
        prefix-icon="el-icon-search"
        size="small"
        style="width: 400px;margin-right: 10px"
        v-model="search"
      ></el-input>
      <el-link @click="highSearch" :underline="false">
        {{moreSearchText}}
        <i :class="upOrDown"></i>
      </el-link>

      <el-upload
        action="/employee/basic/import"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        accept="application/vnd.ms-excel"
        :before-upload="beforeAvatarUpload"
        style="float:right;margin-right: 10px"
      >
        <el-button size="mini" type="success">{{importButton}}</el-button>
        <span slot="tip" class="el-upload__tip">只能上传.xls文件</span>
      </el-upload>
      <el-button
        size="mini"
        type="success"
        @click="exportEmployees"
        style="float:right;margin-right: 10px"
      >点击下载</el-button>

      <el-button
        type="primary"
        size="mini"
        style="float:right;margin-right:10px;"
        @click="addEmployee"
      >
        <i class="el-icon-plus"></i>新增员工
      </el-button>
    </div>
    <div>
      <div style="padding-left:0px; padding-top:0px;">
        <transition name="el-fade-in-linear">
          <div v-show="searchShow" class="transition-box">
            <el-row>
              <el-col :span="5">
                民族:
                <el-select
                  size="mini"
                  style="width:120px"
                  v-model="employee.nationId"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in nations"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-col>
              <el-col :span="5">
                政治面貌:
                <el-select
                  size="mini"
                  style="width:120px"
                  v-model="employee.politicId"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in politics"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-col>
              <el-col :span="5">
                部门:
                <el-popover v-model="treeShow" placement="right" trigger="manual">
                  <el-tree
                    :props="defaultProps"
                    :data="departments"
                    @node-click="handleNodeClick"
                    highlight-current
                    default-expand-all
                  ></el-tree>
                  <el-button
                    slot="reference"
                    style="width:120px"
                    size="mini"
                    @click="showTree()"
                  >{{employee.departmentName}}</el-button>
                </el-popover>
              </el-col>
              <el-col :span="5">
                职称:
                <el-select
                  size="mini"
                  style="width:120px"
                  v-model="employee.jobLevelId"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in jobLevels"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-col>
              <el-col :span="4">
                职位:
                <el-select
                  size="mini"
                  style="width:120px"
                  v-model="employee.posId"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in positions"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-col>
            </el-row>
            <el-row style="margin-top:10px">
              <el-col :span="7">
                聘用形式:
                <el-radio-group v-model="employee.engageForm">
                  <el-radio label="劳动合同">劳动合同</el-radio>
                  <el-radio label="劳务合同">劳务合同</el-radio>
                </el-radio-group>
              </el-col>
              <el-col :span="10">
                入职日期:
                <el-date-picker
                  size="mini"
                  v-model="beginDateScope"
                  type="daterange"
                  unlink-panels
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :picker-options="pickerOptions"
                ></el-date-picker>
              </el-col>
              <el-col :span="5" style="float:right">
                <el-button size="mini" @click="handleCancel()">取消</el-button>
                <el-button
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="handleConditionalSearch()"
                >搜索</el-button>
              </el-col>
            </el-row>
          </div>
        </transition>
      </div>
    </div>
    <el-table
      :data="dataList"
      style="margin-top:10px;"
      border
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column
        v-for="(item, index) in headers"
        :fixed="item.fixed"
        :key="index"
        :label="item.label"
        align="center"
        :prop="item.prop"
      ></el-table-column>

      <el-table-column align="center" label="操作" width="200" fixed="right">
        <template slot-scope="scope">
          <el-button size="mini" style="margin:2px" @click="handleEdit(scope)">修改</el-button>
          <el-button type="primary" style="margin:2px" size="mini" @click="handleDetail">查看高级资料</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-button
      type="danger"
      size="mini"
      style="float:left;margin-top:10px"
      v-if="dataList.length>0"
      :disabled="multipleSelection.length<1"
      @click="deleteSelection"
    >批量删除</el-button>

    <div class="block">
      <el-pagination
        :current-page.sync="pagination.page"
        :page-size="pagination.rowsPerPage"
        layout="prev, pager, next, jumper"
        :total="totlaEmployees"
        style="float:right; margin-right:10px"
      ></el-pagination>
    </div>

    <el-dialog :visible.sync="dialogVisible" :title="dialogType==='edit'?'修改员工':'新建员工'" width="80%">
      <div style="text-align:left;">
        <el-form :model="employee" :rules="rules" ref="myEmployeeForm" style="padding:0px;">
          <el-row>
            <el-col :span="6">
              <el-form-item label="姓名:" prop="name">
                <el-input
                  size="mini"
                  style="width:200px"
                  v-model="employee.name"
                  clearable
                  placeholder="请输入内容"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="工号:" prop="workID">
                <el-input
                  size="mini"
                  style="width:200px"
                  v-model="employee.workID"
                  clearable
                  :disabled="true"
                  placeholder="请输入内容"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="手机号:" prop="phone">
                <el-input
                  size="mini"
                  style="width:200px"
                  v-model="employee.phone"
                  clearable
                  placeholder="请输入内容"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="联系地址:" prop="address">
                <el-input
                  size="mini"
                  style="width:160px"
                  v-model="employee.address"
                  clearable
                  placeholder="请输入内容"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6">
              <el-form-item label="转正日期:" prop="conversionTime">
                <el-date-picker
                  size="mini"
                  v-model="employee.conversionTime"
                  type="date"
                  placeholder="选择日期"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="合同起始日期:" prop="beginContract">
                <el-date-picker
                  size="mini"
                  v-model="employee.beginContract"
                  type="date"
                  placeholder="选择日期"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
                ></el-date-picker>
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="合同终止日期:" prop="endContract">
                <el-date-picker
                  size="mini"
                  v-model="employee.endContract"
                  type="date"
                  placeholder="选择日期"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
                ></el-date-picker>
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="入职日期:" prop="beginDate">
                <el-date-picker
                  size="mini"
                  v-model="employee.beginDate"
                  type="date"
                  placeholder="选择日期"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
                ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="6">
              <el-form-item label="政治面貌:" prop="politicId">
                <el-select
                  size="mini"
                  style="width:120px"
                  v-model="employee.politicId"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in politics"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="部门:" prop="departmentId">
                <el-popover v-model="treeShow" placement="right" trigger="manual">
                  <el-tree
                    :props="defaultProps"
                    :data="departments"
                    @node-click="handleNodeClick"
                    highlight-current
                    default-expand-all
                  ></el-tree>
                  <el-button
                    slot="reference"
                    style="width:120px"
                    size="mini"
                    @click="showTree()"
                  >{{employee.departmentName}}</el-button>
                </el-popover>
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="电子邮箱:" prop="email">
                <el-input
                  size="mini"
                  style="width:200px"
                  v-model="employee.email"
                  clearable
                  placeholder="请输入内容"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="聘用形式:" prop="engageForm">
                <el-radio-group v-model="employee.engageForm">
                  <el-radio label="劳动合同">劳动合同</el-radio>
                  <el-radio label="劳务合同">劳务合同</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6">
              <el-form-item label="职位:" prop="posId">
                <el-select
                  size="mini"
                  style="width:120px"
                  v-model="employee.posId"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in positions"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="职称:" prop="jobLevelId">
                <el-select
                  size="mini"
                  style="width:120px"
                  v-model="employee.jobLevelId"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in jobLevels"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="民族:" prop="nationId">
                <el-select
                  size="mini"
                  style="width:120px"
                  v-model="employee.nationId"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="item in nations"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="籍贯:" prop="nativePlace">
                <el-input
                  size="mini"
                  style="width:200px"
                  v-model="employee.nativePlace"
                  clearable
                  placeholder="请输入内容"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="10">
            <el-col :span="6">
              <el-form-item label="离职日期:" prop="notWorkDate">
                <el-date-picker
                  size="mini"
                  v-model="employee.notWorkDate"
                  type="date"
                  placeholder="选择日期"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
                ></el-date-picker>
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="出生日期:" prop="birthday">
                <el-date-picker
                  size="mini"
                  v-model="employee.birthday"
                  type="date"
                  placeholder="选择日期"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="身份证号:" prop="idCard">
                <el-input
                  size="mini"
                  style="width:200px"
                  v-model="employee.idCard"
                  clearable
                  placeholder="请输入内容"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="所属专业:" prop="specialty">
                <el-input
                  size="mini"
                  style="width:200px"
                  v-model="employee.specialty"
                  clearable
                  placeholder="请输入内容"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6">
              <el-form-item label="学历:" prop="tiptopDegree">
                <el-select
                  size="mini"
                  style="width:120px;margin-top: 40px"
                  v-model="employee.tiptopDegree"
                  placeholder="请选择"
                >
                  <el-option
                    v-for="(item,index) in tiptopDegrees"
                    :key="index"
                    :label="item"
                    :value="item"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="婚姻状况:" prop="wedlock">
                <el-radio-group v-model="employee.wedlock">
                  <el-radio label="已婚">已婚</el-radio>
                  <el-radio label="未婚">未婚</el-radio>
                  <el-radio label="离异">离异</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>

            <el-col :span="5">
              <el-form-item label="性别:" prop="gender">
                <el-radio-group v-model="employee.gender" style="margin-top: 50px">
                  <el-radio label="男">男</el-radio>
                  <el-radio label="女" style="margin-left: 0px">女</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>

          <el-form-item align="right">
            <el-button type="primary" @click="submitForm('myEmployeeForm')">{{submitButton}}</el-button>
            <el-button @click="resetForm('myEmployeeForm')">取消</el-button>
          </el-form-item>
        </el-form>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// import path from 'path'
import { parseTime } from "@/utils";
// import { getRoutes, getEmployees, addRole, deleteRole, updateRole } from '@/api/role'

const defaultEmployee = {
  address: "",
  beginContract: "",
  beginDate: "",
  birthday: "",
  contractTerm: "",
  conversionTime: null,
  departmentId: "",
  departmentName: "请选择",
  email: "",
  endContract: "",
  engageForm: "",
  gender: "",
  id: "",
  idCard: "",
  jobLevelId: "",
  jobLevelName: "",
  name: "",
  nationId: "",
  nationName: "",
  nativePlace: "",
  notWorkDate: null,
  phone: "",
  politicId: "",
  politicName: "",
  posId: "",
  posName: "",
  salary: {},
  school: "",
  specialty: "",
  tiptopDegree: "",
  wedlock: "",
  workAge: null,
  workID: "",
  workState: ""
};

export default {
  data() {
    return {
      // imageUrl:'',
      importButton: "导入数据",
      submitButton: "立即创建",
      defaultProps: {
        children: "children",
        isLeaf: "leaf",
        label: "name"
      },
      beginDateScope: "",
      pickerOptions: {
        shortcuts: [
          {
            text: "最近一周",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近一个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
              picker.$emit("pick", [start, end]);
            }
          },
          {
            text: "最近三个月",
            onClick(picker) {
              const end = new Date();
              const start = new Date();
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
              picker.$emit("pick", [start, end]);
            }
          }
        ]
      },
      nations: [],
      politics: [],
      departments: [],
      jobLevels: [],
      positions: [],
      tiptopDegrees: [
        "博士",
        "硕士",
        "本科",
        "大专",
        "高中",
        "初中",
        "小学",
        "其他"
      ],

      employee: Object.assign({}, defaultEmployee),
      searchShow: false,
      dataList: [],
      headers: [
        { label: "姓名", prop: "name", fixed: "left" },
        { label: "工号", prop: "workID", fixed: null },
        { label: "性别", prop: "gender", fixed: null },
        { label: "出生日期", prop: "birthday", fixed: null },
        { label: "籍贯", prop: "nativePlace", fixed: null },
        { label: "民族", prop: "nationName", fixed: null },
        { label: "电话", prop: "phone", fixed: null },
        { label: "电子邮箱", prop: "email", fixed: null },
        { label: "身份证号", prop: "idCard", fixed: null },
        { label: "婚姻状况", prop: "wedlock", fixed: null },
        { label: "联系地址", prop: "address", fixed: null },
        { label: "政治面貌", prop: "politicName", fixed: null },
        { label: "所属部门", prop: "departmentName", fixed: null },
        { label: "职位", prop: "posName", fixed: null },
        { label: "职称", prop: "jobLevelName", fixed: null },
        { label: "聘用形式", prop: "engageForm", fixed: null },
        {
          label: "入职日期",
          prop: "beginDate",
          fixed: null
        },
        {
          label: "转正日期",
          prop: "conversionTime",
          fixed: null
        },
        {
          label: "合同起始日期",
          prop: "beginContract",
          fixed: null
        },
        {
          label: "合同终止日期",
          prop: "endContract",
          fixed: null
        },
        { label: "合同期限", prop: "contractTerm", fixed: null },
        { label: "学历", prop: "tiptopDegree", fixed: null }
      ],
      dialogVisible: false,
      treeShow: false,
      loading: false,
      dialogType: "new",
      search: "",
      pagination: {
        page: 1,
        rowsPerPage: 10
      },
      totlaEmployees: 0,
      multipleSelection: [],

      rules: {
        phone: [
          {
            required: true,
            message: "请输入手机号码",
            trigger: "blur"
          },
          {
            validator: function(rule, value, callback) {
              if (/^1[34578]\d{9}$/.test(value) == false) {
                callback(new Error("请输入正确的手机号"));
              } else {
                callback();
              }
            },
            trigger: "blur"
          },
          {
            pattern: /^1[34578]\d{9}$/,
            message: "目前只支持中国大陆的手机号码"
          }
        ],
        posId: [{ required: true, message: "必填:职位", trigger: "blur" }],
        school: [{ required: true, message: "必填:毕业院校", trigger: "blur" }],
        specialty: [
          { required: true, message: "必填:所属专业", trigger: "blur" }
        ],
        tiptopDegree: [
          { required: true, message: "必选:最高学历", trigger: "change" }
        ],
        wedlock: [
          { required: true, message: "必选:婚姻状况", trigger: "change" }
        ],
        workAge: [{ required: true, message: "必填:工龄", trigger: "blur" }],
        workID: [{ required: true, message: "必填:工号", trigger: "blur" }],
        workState: [
          { required: true, message: "必填:在职状态", trigger: "blur" }
        ],
        name: [{ required: true, message: "必填:姓名", trigger: "blur" }],
        address: [{ required: true, message: "必填:地址", trigger: "blur" }],
        nativePlace: [
          { required: true, message: "必填:籍贯", trigger: "blur" }
        ],

        idCard: [
          {
            required: true,
            message: "请输入身份证ID",
            trigger: "blur"
          },
          {
            pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
            message: "你的身份证格式不正确"
          }
        ],
        beginContract: [
          {
            required: true,
            message: "请选择日期",
            trigger: "change"
          }
        ],
        beginDate: [
          {
            required: true,
            message: "请选择时间",
            trigger: "change"
          }
        ],
        birthday: [
          {
            required: true,
            message: "请选择时间",
            trigger: "change"
          }
        ],
        conversionTime: [
          {
            message: "请选择时间",
            trigger: "change"
          }
        ],
        endContract: [
          {
            required: true,
            message: "请选择时间",
            trigger: "change"
          }
        ],
        notWorkDate: [
          {
            message: "请选择时间",
            trigger: "change"
          }
        ],
        engageForm: [
          {
            required: true,
            message: "请选择聘用形式",
            trigger: "change"
          }
        ],
        gender: [
          {
            required: true,
            message: "请选择性别",
            trigger: "change"
          }
        ],
        email: [
          { required: true, message: "请输入邮箱地址", trigger: "blur" },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        departmentId: [
          { required: true, message: "必选:部门", trigger: "change" }
        ],
        jobLevelId: [
          { required: true, message: "必选:职称", trigger: "change" }
        ],
        nationId: [{ required: true, message: "必选:民族", trigger: "change" }],
        politicId: [
          { required: true, message: "必选:政治面貌", trigger: "change" }
        ]
      }
    };
  },
  mounted() {
    // Mock: get all routes and roles list from server
    this.loading = true;
    this.initOptions();
    this.getEmployees();
  },
  watch: {
    pagination: {
      // 监视pagination属性的变化
      deep: true, // deep为true，会监视pagination的属性及属性中的对象属性变化
      handler() {
        // 变化后的回调函数，这里我们再次调用getDataFromServer即可
        this.getEmployees();
      }
    },
    search: {
      // 监视搜索字段
      handler() {
        this.getEmployees();
      }
    }
  },
  computed: {
    beginDateScopeFormated() {
      let beginDateScope = this.beginDateScope;
      if (beginDateScope == null || beginDateScope == "") {
        return "";
      }
      let statr = parseTime(beginDateScope[0], "{y}-{m}-{d}");
      let end = parseTime(beginDateScope[1], "{y}-{m}-{d}");
      return statr + "," + end;
    },
    upOrDown() {
      if (this.searchShow == false) {
        return "el-icon-arrow-down el-icon--right";
      } else {
        return "el-icon-arrow-up el-icon--right";
      }
    },
    moreSearchText() {
      if (this.searchShow == false) {
        return "高级搜索";
      } else {
        return "收起";
      }
    }
  },
  methods: {
    // showUpdateView(row){
    //   this.sid = row.salary.id;
    // },
    updateEmployeeSalary(row) {
      this.$http
        .put(
          "/salary/sob/cfg",
          this.$qs.stringify({
            eid: row.id,
            sid: row.salary.id
          })
        )
        .then(() => {
          this.$message({ message: "修改成功", type: "success" });
          this.employee = Object.assign({}, defaultEmployee);
          this.getEmployees();
        });
    },
    handleDetail() {
      window.open("/employee/basic/exportEmp", "_parent");
    },
    exportEmployees() {
      //打开网页
      //  var elemIF = document.createElement('iframe');
      // elemIF.src = "/employee/basic/exportEmp";
      // elemIF.style.display = 'none';
      // document.body.appendChild(elemIF)
      window.open("/employee/basic/export", "_parent");
    },
    handleAvatarSuccess(res, file) {
      // this.imageUrl = URL.createObjectURL(file.raw);
      const { data } = res;
      this.$notify({
        title: "上传成功！",
        dangerouslyUseHTMLString: true,
        message: `
              <div>上传文件: ${data}</div>
            `,
        // <div>Description: ${description}</div>
        type: "success"
      });
      this.importButton = "导入数据";
    },
    beforeAvatarUpload(file) {
      this.importButton = "导入中..";
    },
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          this.loading = true;

          const isEdit = this.dialogType === "edit";

          if (isEdit) {
            this.submitButton = "确认修改";
            this.$http.put("/employee/basic", this.employee).then(() => {
              this.getEmployees();
              this.$message({
                type: "success",
                message: "修改成功"
              });
            });
          } else {
            //     // this.role.key = data.key
            this.submitButton = "立即创建";
            this.$http
              .post("/employee/basic", this.employee)
              .then(() => {
                this.getEmployees();
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
          const { name, gender } = this.employee;
          this.employee = Object.assign({}, defaultEmployee);
          this.dialogVisible = false;
          this.$notify({
            title: "成功！",
            dangerouslyUseHTMLString: true,
            message: `
              <div>员工名称: ${name}</div>
              <div>性别: ${gender}}</div>
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
    addEmployee() {
      this.employee = Object.assign({}, defaultEmployee);
      this.$http.get("/employee/basic/workID").then(({ data }) => {
        this.employee.workID = data;
      });
      this.dialogType = "new";
      this.dialogVisible = true;
    },
    deleteSelection() {
      this.$confirm("Confirm to remove the role?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          let ids = "";
          this.multipleSelection.forEach(sel => {
            ids = ids + sel.id + ",";
          });
          ids = ids.substring(0, ids.length - 1);
          this.doDelete(ids);
        })
        .catch(err => {
          console.error(err);
        });
    },
    doDelete(ids) {
      this.$http.delete("/employee/basic/" + ids).then(() => {
        this.$message({
          type: "success",
          message: "删除成功!"
        });
        this.getEmployees();
      });
    },
    showTree() {
      this.treeShow = !this.treeShow;
    },
    handleNodeClick(data) {
      this.employee.departmentId = data.id;
      this.employee.departmentName = data.name;
      this.treeShow = false;
    },
    highSearch() {
      this.searchShow = !this.searchShow;
      if (!this.searchShow) {
        this.employee = Object.assign({}, defaultEmployee);
        // this.employee = null;
        this.beginDateScope = "";
        this.getEmployees();
      }
    },
    initOptions() {
      this.$http.get("/employee/basic/Options").then(({ data }) => {
        (this.nations = data.nations),
          (this.politics = data.politics),
          (this.departments = data.departments),
          (this.jobLevels = data.jobLevels),
          (this.positions = data.positions);
      });
    },
    handleConditionalSearch() {
      this.getEmployees();
    },
    handleCancel() {
      this.searchShow = !this.searchShow;
      if (!this.searchShow) {
        this.employee = Object.assign({}, defaultEmployee);
        // this.employee = null;
        this.beginDateScope = "";
        this.getEmployees();
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    getEmployees() {
      this.$http
        .get("/employee/basic/page", {
          params: {
            key: this.search, // 搜索条件
            page: this.pagination.page, // 当前页
            rows: this.pagination.rowsPerPage, // 每页大小
            sortBy: this.pagination.sortBy, // 排序字段
            desc: this.pagination.descending, // 是否降序
            nationId: this.employee.nationId,
            politicId: this.employee.politicId,
            departmentId: this.employee.departmentId,
            jobLevelId: this.employeejobLevelId,
            posId: this.employee.posId,
            engageForm: this.employee.engageForm,
            beginDateScope: this.beginDateScopeFormated
          }
        })
        .then(resp => {
          let items = resp.data.items;
          for (let i = 0; i < items.length; i++) {
            items[i].birthday = parseTime(items[i].birthday, "{y}-{m}-{d}");
            items[i].beginDate = parseTime(items[i].beginDate, "{y}-{m}-{d}");
            items[i].conversionTime = parseTime(
              items[i].conversionTime,
              "{y}-{m}-{d}"
            );
            items[i].beginContract = parseTime(
              items[i].beginContract,
              "{y}-{m}-{d}"
            );
            items[i].endContract = parseTime(
              items[i].endContract,
              "{y}-{m}-{d}"
            );
          }
          this.totlaEmployees = resp.data.total;
          this.dataList = items;
          this.loading = false;
        });
    },

    async handleAddRole() {
      //
    },

    handleEdit(scope) {
      //   await this.getTreeDataAndKesy(scope.row);
      this.employee = scope.row;
      this.dialogType = "edit";
      this.dialogVisible = true;
    },
    async handleFetchDetail(row) {
      //   await this.getTreeDataAndKesy(row);

      this.dialogDetailVisible = true;
    },
    handleDelete({ $index, row }) {
      this.$confirm("Confirm to remove the role?", "Warning", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(async () => {
          //   await this.$http
          //     .delete("/system/basicMsg/role/" + row.id)
          //     .then(() => {
          //       this.dataList.splice($index, 1);
          //       this.$message({
          //         type: "success",
          //         message: "删除成功!"
          //       });
          //     });
        })
        .catch(err => {
          console.error(err);
        });
    },
    async confirmRole() {}
  }
};
</script>

<style>
.transition-box {
  margin-top: 20px;
  /* border-radius: 4px;
  border-style: solid;
  border-color: cornflowerblue; */
  padding: 4px 0px;
  box-sizing: border-box;
  margin-bottom: 10px;
}
</style>

