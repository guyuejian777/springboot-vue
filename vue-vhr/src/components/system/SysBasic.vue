<template>
  <div style="margin-top: 10px">
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="部门管理" name="departmentMana">
        <department-mana></department-mana>
      </el-tab-pane>
      <el-tab-pane label="职位管理" name="jobMana">
        <job-mana state="position"></job-mana>
      </el-tab-pane>
      <el-tab-pane label="职称管理" name="jobTitleMana">
        <job-mana state="jobTitle"></job-mana>
      </el-tab-pane>
      <el-tab-pane label="奖惩规则" name="rpRules">
        <rp-rules></rp-rules>
      </el-tab-pane>
      <el-tab-pane label="权限组" name="permission">
        <menu-role></menu-role>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import DepartmentMana from './basicTemplate/DepartmentMana'
import JobMana from './basicTemplate/JobMana'
import RpRules from './basicTemplate/RpRules'
import MenuRole from './basicTemplate/MenuRole'

export default {
  data () {
    return {
      activeName: 'departmentMana'
    }
  },
  methods: {
    handleClick (tab, event) {
      // console.log(tab, event);
    }
  },
  components: {
    DepartmentMana,
    JobMana,
    RpRules,
    MenuRole
  }
}
</script>
