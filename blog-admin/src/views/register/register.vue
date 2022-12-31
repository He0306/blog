<template>
  <div class="wrapper">
    <div style="width: 520px;height: 450px;margin: 130px auto" class="box">
      <div style="text-align: center;font-size: 30px;padding: 30px">用户注册</div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="账  号" prop="username">
          <el-input v-model="ruleForm.username" placeholder="请输入登录账号"></el-input>
        </el-form-item>
        <el-form-item label="密  码" prop="password">
          <el-input type="password" v-model="ruleForm.password" placeholder="请输入登录密码"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirm">
          <el-input type="password" v-model="ruleForm.confirm" placeholder="请确认密码"></el-input>
        </el-form-item>
        <div class="login"><router-link to="/login">已有账号？去登录</router-link></div>
        <el-form-item>
          <el-button type="primary" @click="register" class="btn1" round>注册</el-button>&nbsp;&nbsp;
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import login from "@/api/login/login";

export default {
  name: "register",
  data() {
    return {
      ruleForm: {
        username: '',
        password: '',
        confirm: '',
      },
      department: [],
      rules: {
        username: [
          {required: true, message: '请输入账号', trigger: 'blur'},
          {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'},
          {min: 5, max: 15, message: '长度在 5 到 15 个字符', trigger: 'blur'}
        ],
        confirm: [
          {required: true, message: '请在次输入密码', trigger: 'blur'},
          {min: 5, max: 15, message: '长度在 5 到 15 个字符', trigger: 'blur'}
        ],
      }
    };
  },
  created() {

  },
  methods: {
    register() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          if (this.ruleForm.password !== this.ruleForm.confirm) {
            this.$message({
              showClose: true,
              message: '两次密码不一致',
              type: "error",
            })
            return false
          }
          login.register(this.ruleForm).then(res=>{
            if (res.code === 200){
              this.$message({
                showClose: true,
                type: 'success',
                message: '注册成功',
                duration: 1000
              })
              this.$router.push("/")
            }else {
              this.$message({
                showClose: true,
                type: 'error',
                message: '注册失败',
                duration: 1000
              })
            }
          })
        }
      })
    }
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

.btn1 {
  width: 200px;
  height: 40px;
  margin-left: 50px;
  margin-top: 10px;
}

a {
  text-decoration: none;
  color: #cccccc;
  font-size: 12px;
  margin-left: 350px;
}
</style>