<template>
  <div>
    <el-card class="box-card">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <!--   修改信息   -->
        <el-tab-pane label="修改信息" name="info">
          <el-form label-width="80px" :model="userInfo" ref="userInfo" :rules="userInfoRlues"
                   style="width:350px;margin:20px auto 0">
            <el-form-item label="头像" prop="avatar">
              <el-upload
                  action=""
                  name="img"
                  class="avatar-uploader"
                  :http-request="uploadUpdateAvatar"
                  :show-file-list="false"
                  :before-upload="beforeUpload"
                  :on-success="handleAvatarSuccess"
                  ref="upload">
                <img v-if="userInfo.avatar" :src="userInfo.avatar" class="avatar" alt="">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="账号" prop="username">
              <el-input v-model="userInfo.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="昵称" prop="nickName">
              <el-input v-model="userInfo.nickName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="邮箱" prop="email">
              <el-input v-model="userInfo.email" autocomplete="off"></el-input>
            </el-form-item>
            <el-button type="primary" @click="updateUser" round
                       style="margin-left: 100px;margin-top: 20px;width: 150px;height: 35px">确 定
            </el-button>
          </el-form>
        </el-tab-pane>

        <!--   修改密码   -->
        <el-tab-pane label="修改密码" name="password">
          <el-form label-width="80px" :model="passwordForm" ref="passwordForm" :rules="formRlues"
                   style="width:350px;margin:50px auto 0">
            <el-form-item label="旧密码" prop="oldPassword">
              <el-input v-model="passwordForm.oldPassword" autocomplete="off" type="password" show-password></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
              <el-input v-model="passwordForm.newPassword" autocomplete="off" type="password" show-password></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="confirmPassword">
              <el-input v-model="passwordForm.confirmPassword" autocomplete="off" type="password"
                        show-password></el-input>
            </el-form-item>
            <el-button type="primary" @click="updatePassword" round
                       style="margin-left: 100px;margin-top: 20px;width: 150px;height: 35px">确 定
            </el-button>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
import userApi from "@/api/user/user";
import uploadApi from "@/api/upload/upload";

export default {
  name: "setting",
  data() {
    return {
      activeName: 'info',
      passwordForm: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      },
      formRlues: {
        oldPassword: [
          {required: true, message: '请输入旧密码', trigger: 'blur'},
          {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}],
        newPassword: [
          {required: true, message: '请输入新密码', trigger: 'blur'},
          {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}],
        confirmPassword: [
          {required: true, message: '请确认密码', trigger: 'blur'},
          {min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur'}],
      },
      userInfo: {
        avatar: '',
        username: '',
        nickName: '',
        email: ''
      },
      userInfoRlues: {
        username: [{required: true, message: '请输入账号', trigger: 'blur'}],
        nickName: [{required: true, message: '请输入昵称', trigger: 'blur'}],
        email: [{required: true, message: '请输入旧邮箱', trigger: 'blur'}],
        avatar: [{required: true, message: '选择头像上传', trigger: 'blur'}]
      }
    }
  },
  created() {
    this.getUserInfo().then(res => {
      this.userInfo = res
    })
  },
  methods: {
    //修改个人信息
    updateUser() {
      this.$refs['userInfo'].validate((valid) => {
        if (valid) {
            userApi.updateUserInfo(this.userInfo).then(res=>{
              if (res.code === 200){
                this.$message({
                  showClose: true,
                  type: 'success',
                  message: '个人信息修改成功',
                  duration: 1000
                })
                //触发父级更新User方法
                this.$emit("refreshUser")
                //更新浏览器存储的用户信息
                this.getUserInfo().then(res=>{
                  sessionStorage.setItem("user",JSON.stringify(res))
                })
              }else {
                this.$message({
                  showClose: true,
                  message: '修改个人信息失败',
                  type: 'error',
                  duration: 1000
                })
              }
            })
        }
      })
    },
    //异步加载
    async getUserInfo() {
      return (await userApi.getUser()).data
    },
    //头像回显
    handleAvatarSuccess(res) {
      this.userInfo.avatar = res
    },
    //上传头像
    uploadUpdateAvatar(img) {
      uploadApi.uploadAvatar(img.file).then(res => {
        if (res.code === 200) {
          this.$message({
            showClose: true,
            type: 'success',
            message: '头像上传成功',
            duration: 1000
          })
          this.userInfo.avatar = res.data
        } else {
          this.$message({
            showClose: true,
            type: 'error',
            message: '头像上传失败，请重新上传',
            duration: 1000
          })
        }
      })
    },
    //设置上传规则
    beforeUpload(file) {
      if (file) {
        const suffix = file.name.split('.')[1]
        const size = file.size / 1024 / 1024 < 2
        if (['png', 'jpeg', 'jpg'].indexOf(suffix) < 0) {
          this.$message.warning('上传图片只支持 png、jpeg、jpg 格式！')
          this.$refs.upload.clearFiles()
          return false
        }
        if (!size) {
          this.$message.error('上传文件大小不能超过 2MB!')
          return false
        }
        return file
      }
    },
    //修改密码
    updatePassword() {
      this.$refs['passwordForm'].validate((valid) => {
        if (valid) {
          if (this.passwordForm.newPassword !== this.passwordForm.confirmPassword) {
            this.$message({
              showClose: true,
              type: 'error',
              message: '两次输入的新密码不相同',
              duration: 1000
            })
            return false
          }
          userApi.updatePassword(this.passwordForm).then(res => {
            if (res.code === 200) {
              this.$message({
                showClose: true,
                type: 'success',
                message: '修改密码成功',
                duration: 1000
              })
              this.$router.push("/login")
              this.$store.commit("logout")
            } else {
              this.$message({
                showClose: true,
                type: 'error',
                message: 'res.msg',
                duration: 1000
              })
            }
          })
        }
      })
    },
    handleClick(tab, event) {
      console.log(tab, event)
    }
  }
}
</script>

<style>
.box-card {
  height: 620px;
}

.avatar-uploader {
  margin-top: 5px;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 138px;
  height: 138px;
  line-height: 138px;
  text-align: center;
}

.avatar {
  width: 148px;
  height: 148px;
  display: block;
}

.avatar-uploader .el-icon-plus:before {
  content: "上传头像" !important;
  font-size: 12px;
  color: #000;
}
</style>