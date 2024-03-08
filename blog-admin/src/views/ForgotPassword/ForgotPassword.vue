<template>
  <div class="wrapper">
    <div style="width: 520px;height: 380px;margin: 130px auto" class="box">
      <div style="text-align: center;font-size: 30px;padding: 30px">重置密码</div>
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
        <el-form-item label="邮箱号" prop="email">
          <el-input v-model="ruleForm.email" placeholder="请输入邮箱号">
            <el-button slot="append" @click="send"><span
                style="color: #67C23A">{{ lastTimeContent }}</span></el-button>
          </el-input>
        </el-form-item>
        <el-form-item label="验证码" prop="code">
          <el-input v-model="ruleForm.code" placeholder="请输入6位验证码"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="password">
          <el-input type="password" v-model="ruleForm.password" placeholder="请输入新密码" show-password></el-input>
        </el-form-item>
        <div class="goLogin">
          <router-link to="/login">返回登录</router-link>
        </div>
        <el-form-item>
          <el-button type="primary" @click="forgotPassword" class="btn1" round>确定重置</el-button>&nbsp;&nbsp;
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import userApi from "@/api/user/user";

export default {
  name: "ForgotPassword",
  data() {
    let validateEmail = (rule, value, callback) => {
      if (value === "") {
        callback(new Error('邮箱不能为空'));
      } else {
        if (value !== "") {
          let regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/;
          if (!regEmail.test(value)) {
            callback(new Error('请输入有效的邮箱'));
          }
        }
        callback();
      }
    };
    return {
      active: 0,
      ruleForm: {
        email: '',
        code: '',
        password: ''
      },
      rules: {
        email: [{required: true, validator: validateEmail, trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}],
        code: [{required: true, message: '验证码不能为空', trigger: 'blur'},
          {min: 6, max: 6, type: 'number', message: '长度在6个数字', trigger: 'blur'}],
        password: [{required: true, message: '新密码不能为空', trigger: 'blur'}]
      },
      lastTimeContent: '获取验证码',
      lastTime: 60
    }
  },
  methods: {
    // 发送验证码
    send() {
      let regex = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;
      if (this.ruleForm.email !== '') {
        if (regex.test(this.ruleForm.email)) {
          if (this.lastTimeContent === '获取验证码') {
            let params = {email: this.ruleForm.email}
            userApi.sendCode(params).then(res => {
              if (res.code === 200) {
                this.$message({
                  type: 'success',
                  message: '验证码码发送成功',
                  duration: 1000,
                  showClose: true
                })
                this.countDown()
              } else {
                this.$message({
                  type: 'error',
                  message: res.msg,
                  showClose: true,
                  duration: 1000
                })
              }
            })
          } else {
            this.$message({
              type: 'warning',
              message: '请不要频繁点击',
              duration: 1000,
              showClose: true
            })
          }
        }
      } else {
        this.$message({
          type: 'error',
          message: '邮箱不能为空',
          showClose: true,
          duration: 1000
        })
      }
    },
    countDown() {
      let interval = setInterval(() => {
        this.lastTimeContent = (this.lastTime < 10 ? '0' + this.lastTime : this.lastTime) + '秒后重新发送'
        --this.lastTime
        if (this.lastTime < 0) {
          this.lastTime = 60
          this.lastTimeContent = '发送验证码'
          clearInterval(interval)
        }
      }, 1000)
    },
    // 确定修改
    forgotPassword() {
      this.$refs['ruleForm'].validate((valid) => {
        if (valid) {
          userApi.retrievePassword(this.ruleForm).then(res => {
            if (res.code === 200) {
              this.$message({
                showClose: true,
                duration: 1000,
                type: 'success',
                message: '操作成功，请重新登录',
              })
              this.$router.push("/login")
            } else {
              this.$message({
                showClose: true,
                duration: 1000,
                type: 'error',
                message: res.msg,
              })
            }
          })
        }
      })
    }
  }
}
</script>

<style>
.wrapper {
  height: 100vh;
  background-image: linear-gradient(to bottom right, rgba(252, 70, 107, 0.87), rgba(63, 94, 251, 0.88));
  overflow: hidden;
}

.box {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)
}

.goLogin {
  font-size: 12px;
  margin-left: 400px;
}

.demo-ruleForm {
  width: 450px;
}

.btn1 {
  width: 200px;
  height: 40px;
  margin-left: 50px;
  margin-top: 25px;
}

a {
  text-decoration: none;
  color: #cccccc;
}

</style>