<template>

  <el-menu style="min-height: 100%;overflow-x: hidden"
           background-color="#333744"
           text-color="#fff"
           active-text-color="#FFD700"
           :collapse-transition="false"
           :collapse="isCollapse"
           :default-active="$route.path"
           router
  >
    <div style="height: 60px;line-height: 60px;text-align: center">
<!--      <img src="../assets/logo.png" alt="" style="width: 20px;position: relative;top: 5px;margin-right: 5px">-->
      <b style="color: #fff" v-show="logoTextShow">博客后台系统</b>
    </div>

    <div v-for="item in menus" :key="item.id">
      <div v-if="item.path">
        <el-menu-item :index="item.path">
          <i :class="item.icon"></i>
          <span slot="title">{{ item.name }}</span>
        </el-menu-item>
      </div>

      <div v-else>
        <el-submenu :index="item.id+''">
          <template slot="title">
            <i :class="item.icon"></i>
            <span>{{ item.name }}</span>
          </template>
          <div v-for="subItem in item.children" :key="subItem.id">
            <el-menu-item :index="subItem.path">
<!--              <i :class="subItem.icon"></i>-->
              <span slot="title">{{ subItem.name }}</span>
            </el-menu-item>
          </div>
        </el-submenu>
      </div>
    </div>
    <div>
      <el-menu-item index="/setting">
        <i class="el-icon-user"></i>
        <span slot="title">个人中心</span>
      </el-menu-item>
    </div>
  </el-menu>

</template>

<script>
export default {
  name: "Aside",
  props: {
    isCollapse: Boolean,
    logoTextShow: Boolean
  },
  data() {
    return {
      menus: sessionStorage.getItem("menus") ? JSON.parse(sessionStorage.getItem("menus")) : [],
    }
  },
}
</script>

<style>
/*解决收缩菜单文字不消失问题*/
.el-menu--collapse span {
  visibility: hidden;
}

</style>