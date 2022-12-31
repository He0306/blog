<template>
  <div class="wrapper">

    <!--  人机验证  -->
   <el-card class="cover" v-if="loginAdmin.id">
      <slide-verify :l="42"
                    :r="10"
                    :w="310"
                    :h="155"
                    :imgs="['https://cdn.pixabay.com/photo/2022/11/09/12/23/lotus-7580478_960_720.jpg',
                    'https://cdn.pixabay.com/photo/2017/08/29/12/07/adult-2693054_960_720.jpg',
                    'https://cdn.pixabay.com/photo/2022/11/16/15/52/mushrooms-7596258_960_720.jpg']"
                    slider-text="向右滑动"
                    @success="onSuccess"
                    @fail="onFail"
                    @refresh="onRefresh"
      ></slide-verify>
      <div style="margin-left: 120px;color: red">{{msg}}</div>
    </el-card>

    <div style="width: 500px;height: 350px;margin: 150px auto" class="box">
      <div style="text-align: center;font-size: 30px;padding: 30px">用户登录</div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账  号" prop="username">
          <el-input prefix-icon="el-icon-user" v-model="ruleForm.username" placeholder="请输入登录账号"></el-input>
        </el-form-item>
        <el-form-item label="密  码" prop="password">
          <el-input prefix-icon="el-icon-lock" type="password" show-password v-model="ruleForm.password"
                    placeholder="请输入密码"></el-input>
        </el-form-item>
        <div class="register">
          <router-link to="/register">免费注册</router-link>
          <router-link to="/ForgotPassword" style="white-space: normal;margin-left: 250px">忘记密码</router-link>
        </div>

        <el-form-item>
          <el-button type="primary" @click.native.prevent="submitForm()" class="btnLongin" round>登录</el-button>&nbsp;&nbsp;
          <el-button @click="resetForm('ruleForm')" class="btnReset" round>重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import login from "@/api/login/login";
import {setRoutes} from "@/router";

export default {
  name: "login",
  data() {
    return {
      loginAdmin: {},
      msg: '',
      ruleForm: {
        username: 'admin',
        password: 'admin'
      },
      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'change'},
          {min: 5, max: 15, message: '长度在 5 到 15 个字符', trigger: 'blur'}
        ],
      }
    }
  },
  methods: {
    onSuccess(times) {
      this.msg = 'login success, 耗时${(times / 1000).toFixed(1)}s'
      sessionStorage.setItem('user', JSON.stringify(this.loginAdmin))
      this.$notify.success({
        title: "登录成功",
        message: `欢迎回来~ 亲爱的${this.loginAdmin.nickName}`
      })
      this.$router.push('/Layout')
      setRoutes()
    },
    onFail() {
      this.msg = '验证失败'
    },
    onRefresh() {
      console.log('refresh')
    },
    submitForm() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          login.login(this.ruleForm.username, this.ruleForm.password).then(res => {
            if (res.code === 200) {
              localStorage.setItem('token', JSON.stringify(res.data.token))
              sessionStorage.setItem('menus', JSON.stringify(res.data.menus))
              this.loginAdmin = res.data.userInfoVo
              /*sessionStorage.setItem('user', JSON.stringify(this.loginAdmin))
              this.$notify.success({
                title: "登录成功",
                message: `欢迎回来~ 亲爱的${this.loginAdmin.nickName}`
              })
              this.$router.push('/Layout')
              setRoutes()*/
            } else {
              this.$message({
                showClose: true,
                type: 'error',
                message: res.msg
              })
            }
          })
        }
      })
    },
    resetForm(ruleForm) {
      this.$refs['ruleForm'].resetFields();
    },
  }
}
</script>

<style scoped>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, rgba(252, 70, 107, 0.87), rgba(63, 94, 251, 0.88));
  overflow: hidden;
}

.box {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}

.demo-ruleForm {
  width: 450px;
}

.btnLongin {
  width: 100px;
  height: 35px;
  margin-top: 20px;
  margin-left: 50px;
}

.btnReset {
  width: 100px;
  height: 35px;
  margin-top: 20px;
  margin-left: -2px;
}

.register {
  font-size: 10px;
  margin-left: 100px;
}


a {
  text-decoration: none;
  color: #cccccc;
}

.cover {
  width: fit-content;
  background-color: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
}
</style>