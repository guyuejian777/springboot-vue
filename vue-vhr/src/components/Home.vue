<template>
  <div>
    <el-container class="home-container">
      <el-header class="home-header">
        <span class="home_title">
          <i @click="handleCollapse" style="cursor: pointer;" :class="isCollapse?'el-icon-s-unfold':'el-icon-s-fold'"></i>
        </span>
        <div style="display: flex;align-items: center;margin-right: 7px">
          <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link home_userinfo" style="display: flex;align-items: center">
              {{user.name}}
              <i>
                <img
                  v-if="user.userface || user.userface!=''"
                  :src="user.userface"
                  style="width: 40px;height: 40px;margin-right: 5px;margin-left: 5px;border-radius: 40px"
                />
              </i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item>个人中心</el-dropdown-item>
              <el-dropdown-item>设置</el-dropdown-item>
              <el-dropdown-item command="logout" divided>注销</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </div>
      </el-header>
      <el-container>
        <el-aside :width="isCollapse?'50px':'180px'" class="home-aside">
          <div style="display: flex;justify-content: flex-start;text-align: left;">
            <el-menu
              class="el-menu-vertical-demo"
              router
              :collapse="isCollapse"
              unique-opened
              @open="handleOpen"
              @close="handleClose"
            >
              <!-- <template> -->
              <el-submenu
                v-for="(item, index) in routes"
                v-if="!item.hidden"
                :key="index"
                :index="index+''"
              >
                <template slot="title">
                  <i :class="item.iconCls" style="color: #20a0ff;width: 14px;"></i>
                  <span slot="title" style="margin-left: 10px;">{{item.name}}</span>
                </template>
                <el-menu-item
                  v-for="(child,j) in item.children"
                  :key="j"
                  :index="child.path"
                >{{child.name}}</el-menu-item>
              </el-submenu>
              <!-- </template> -->
            </el-menu>
          </div>
        </el-aside>
        <el-main style="padding: 16px" >
          <el-breadcrumb separator="/">
            <el-breadcrumb-item :to="{path: '/home'}">
              <i class="fa fa-home"></i>首页
            </el-breadcrumb-item>
            <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
          </el-breadcrumb>
          <!-- 开启缓存 -->
          <keep-alive>
            <router-view v-if="this.$route.meta.keepAlive"></router-view>
          </keep-alive>
          <router-view v-if="!this.$route.meta.keepAlive"></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
export default {
  data() {
    return {
      isCollapse: true
    };
  },
  mounted() {
    //
  },
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    handleCollapse() {
      this.isCollapse = !this.isCollapse;
    },
    
    handleCommand(cmd) {
      // if(cmd == 'logout'){
      //   this.$confirm('注销登陆，是否继续？','提示',{
      //     confirmButtonText:'确定',
      //     cancelButtonText:'取消',
      //     type:'warning'
      //   }).then(() => {
      // this.$$http.get("/logout");
      //   }).catch();
    }
  },
  computed: {
    user() {
      return this.$store.state.user;
    },
    routes() {
      return this.$store.state.routes;
    },
  }
};
</script>
<style>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 200px;
  min-height: 400px;
}
.home-container {
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
}

.home-header {
  background-color: #24292e;
  color: #333;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-sizing: content-box;
  padding: 0px;
}

.home-aside {
  background-color: white;
}

.home-main {
  background-color: #fff;
  color: #000;
  text-align: center;
  margin: 0px;
  padding: 0px;
}

.home_title {
  color: #fff;
  font-size: 22px;
  display: inline;
  margin-left: 8px;
}

.home_userinfo {
  color: #fff;
  cursor: pointer;
}

.home_userinfoContainer {
  display: inline;
  margin-right: 20px;
}

.el-submenu .el-menu-item {
  width: 180px;
  min-width: 175px;
}
</style>
