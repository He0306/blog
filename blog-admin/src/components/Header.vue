<template>
  <div style="line-height: 60px; display: flex">
    <div style="flex: 1;font-size: 20px">
      <span :class="collapseBtnClass" style="cursor: pointer" @click="collapse">
        <!--    面包屑    -->
      <el-breadcrumb separator-class="el-icon-arrow-right" style="display: inline-block; margin-left: 10px">
        <el-breadcrumb-item :to="{ path: '/home' }">主页</el-breadcrumb-item>
        <el-breadcrumb-item>{{ currentPathName }}</el-breadcrumb-item>
      </el-breadcrumb>
      </span>
    </div>
    <template>
      <div id="full-screen" style="margin-right: 40px;cursor:pointer;">
        <i class="el-icon-full-screen" @click="fullScreen()">全屏</i>
      </div>
    </template>
    <el-dropdown style="width: 100px; cursor: pointer" trigger="click">
      <b class="el-dropdown-link" style="margin-left: -15px">
        <img :src="user.avatar" alt="" style="width: 30px;border-radius: 50%;position: relative;top: 10px;right: 5px">
        {{ user.nickName }}<i class="el-icon-setting" style="margin-left: 5px"></i>
      </b>
      <el-dropdown-menu slot="dropdown" style="width: 100px; text-align: center">
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span @click="person">个人中心</span>
        </el-dropdown-item>
        <el-dropdown-item style="font-size: 14px; padding: 5px 0">
          <span @click="logout">注销</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import screenfull from 'screenfull'
import login from "@/api/login/login";
import {resetRouter} from "@/router";

export default {
  name: "Header",
  props: {
    collapseBtnClass: String,
    user: Object
  },
  data() {
    return {

    }
  },
  computed: {
    currentPathName() {
      return this.$store.state.currentPathName;　　//需要监听的数据
    }
  },
  methods: {
    collapse() {
      this.$emit("asideCollapse")
    },
    logout() {
      this.$confirm('确定要退出登录吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        login.logout().then(res=>{
          if (res.code === 200){
            this.$message({
              showClose: true,
              type: 'success',
              message: '注销成功'
            })
          }
          this.$store.commit("logout")
          resetRouter()
        })
      })
    },
    person() {
      this.$router.push('/setting')
    },
    fullScreen() {
      if (!screenfull.isEnabled) {
        this.$message({
          showClose: true,
          message: 'you browser can not work',
          type: 'warning'
        })
        return false
      }
      screenfull.toggle()
    },
  }
}
</script>

<style scoped>

</style>