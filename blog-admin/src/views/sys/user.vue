<template>
  <div>
    <!--  搜索框、搜索按钮、重置按钮  -->
    <div style="padding: 10px 0">
      <el-input style="width:250px" suffix-icon="el-icon-search" placeholder="请输入账号" v-model="username"
                clearable></el-input>
      <el-input style="width:250px;margin-left: 3px" suffix-icon="el-icon-message" placeholder="请输入邮箱" v-model="email"
                clearable></el-input>
      <el-button style="margin-left: 3px" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 3px" type="warning" @click="reset">重置</el-button>
    </div>

    <!-- 操作 -->
    <div style="margin: 5px 0">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button type="danger" :disabled="delBtlStatus" slot="reference" @click="deleteBatch(null)">批量删除<i
          class="el-icon-delete"></i></el-button>
    </div>

    <!-- 表格数据 -->
    <el-table :data="tableData" v-loading="loading" border stripe :header-cell-class-name="headerBg"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="username" label="账号">
      </el-table-column>
      <el-table-column prop="nickName" label="昵称">
      </el-table-column>
      <el-table-column prop="avatar" label="头像" width="100">
        <template slot-scope="scope">
          <el-image style="width: 50px; height: 50px; border:none;cursor: pointer;"
                    :src="scope.row.avatar"
                    :preview-src-list="[scope.row.avatar]">
            <div slot="error" class="image-slot" style="text-align: center;">
              <i class="el-icon-picture-outline"></i>
            </div>
          </el-image>
        </template>
      </el-table-column>
      <el-table-column prop="roleName" label="角色">
      </el-table-column>
      <el-table-column prop="email" label="邮箱">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="300">
        <template slot-scope="scope">
          <el-button type="warning" size="small" @click="handleEdit(scope.row.id)">编辑<i class="el-icon-edit"></i>
          </el-button>
          <el-button type="danger" size="small" slot="reference" @click="handleDelete(scope.row.id)" v-if="scope.row.id !== currentUser.id">删除<i
              class="el-icon-delete"></i></el-button>
          <el-button type="success" size="small" @click="resetPassword(scope.row.id)">重置密码<i
              class="el-icon-refresh"></i>
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--  分页  -->
    <div style="padding-left: 400px;padding-top: 15px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[5,10,15,20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <!--编辑用户信息 弹窗-->
    <el-dialog title="编辑用户信息" :visible.sync="dialogUpdateVisible" width="35%" top="20px">
      <el-form label-width="80px" :model="form" ref="form" :rules="formRlues">
        <el-form-item label="账号" prop="username">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="form.nickName" autocomplete="off"></el-input>
        </el-form-item>
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
            <img v-if="form.avatar" :src="form.avatar" class="avatar" alt="">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="角色" prop="roleId">
          <el-select v-model="form.roleId" placeholder="请选择角色" @change="changeGame">
            <el-option v-for="item in roleData" :key="item.id" :label="item.roleName" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogUpdateVisible = false">取 消</el-button>
        <el-button type="primary" @click="update">确 定</el-button>
      </div>
    </el-dialog>

    <!--新增用户 弹窗-->
    <el-dialog title="新增用户" :visible.sync="dialogSaveVisible" width="35%" top="20px">
      <el-form label-width="80px" :model="formAdd" ref="formAdd" :rules="formAddRlues">
        <el-form-item label="账号" prop="username">
          <el-input v-model="formAdd.username" autocomplete="off"></el-input>
          <el-alert title="初始密码为123456" :closable="false" type="info" style="line-height: 12px;"></el-alert>
        </el-form-item>
        <el-form-item label="昵称" prop="nickName">
          <el-input v-model="formAdd.nickName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="头像" prop="avatar">
          <el-upload
              action=""
              name="img"
              class="avatar-uploader"
              :http-request="uploadAddAvatar"
              :show-file-list="false"
              :before-upload="beforeUpload"
              :on-success="handleAddAvatarSuccess"
              ref="upload">
            <img v-if="formAdd.avatar" :src="formAdd.avatar" class="avatar" alt="">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="角色" prop="roleId">
          <el-select v-model="formAdd.roleId" placeholder="请选择角色">
            <el-option v-for="item in roleData" :key="item.id" :label="item.roleName" :value="item.id"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="formAdd.email" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogSaveVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import user from "@/api/user/user";
import role from "@/api/role/role";
import uploadApi from "@/api/upload/upload";

export default {
  name: "user",
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
      currentUser : sessionStorage.getItem('user') ? JSON.parse(sessionStorage.getItem('user')) : null,
      username: '',
      email: '',
      tableData: [],
      loading: false,
      pageNum: 1,
      pageSize: 10,
      total: 0,
      delBtlStatus: true,
      headerBg: 'headerBg',
      dialogUpdateVisible: false,
      dialogSaveVisible: false,
      form: {
        avatar: ''
      },
      formAdd: {
        avatar: ''
      },
      roleData: [],
      formAddRlues: {
        username: [{required: true, message: '请输入账号', trigger: 'blur'}],
        email: [{required: true, validator: validateEmail, trigger: 'blur'}]
      },
      formRlues: {
        username: [{required: true, message: '请输入账号', trigger: 'blur'}],
        email: [{required: true, validator: validateEmail, trigger: 'blur'}]
      },

    }
  },
  created() {
    this.load()
    role.findAll().then(res => {
      this.roleData = res.data
    })
  },
  methods: {
    changeGame(){
      this.$forceUpdate() //在下拉框上绑定change事件 更新视图 这样就不会有视图更新的问题
    },
    handleAddAvatarSuccess(response,file,fileList){
      this.formAdd.avatar = response.data
    },
    //设置上传规则
    beforeUpload (file) {
      if(file){
        const suffix = file.name.split('.')[1]
        const size = file.size / 1024 / 1024 < 2
        if(['png','jpeg','jpg'].indexOf(suffix) < 0){
          this.$message.error('上传图片只支持 png、jpeg、jpg 格式！')
          this.$refs.upload.clearFiles()
          return false
        }
        if(!size){
          this.$message.error('上传文件大小不能超过 2MB!')
          return false
        }
        return file
      }
    },
    uploadAddAvatar(img){
      uploadApi.uploadAvatar(img.file).then(res=>{
        if (res.code === 200){
          this.$message({
            showClose: true,
            type: 'success',
            message: '头像上传成功',
            duration: 1000
          })
          this.$forceUpdate()
          this.formAdd.avatar = res.data
        }else {
          this.$message({
            showClose: true,
            type: 'error',
            message: '头像上传失败，请重新上传',
            duration: 1000
          })
        }
      })
    },
    //上传头像
    uploadUpdateAvatar(img){
      uploadApi.uploadAvatar(img.file).then(res=>{
        if (res.code === 200){
          this.$message({
            showClose: true,
            type: 'success',
            message: '头像上传成功',
            duration: 1000
          })
          this.$forceUpdate()
          this.form.avatar = res.data
        }else {
          this.$message({
            showClose: true,
            type: 'error',
            message: '头像上传失败，请重新上传',
            duration: 1000
          })
        }
      })
    },
    //重置密码
    resetPassword(id) {
      this.$confirm('确定要重置密码为 123456 吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        user.resetPassword(id).then(res => {
          if (res.code === 200) {
            this.$message({
              showClose: true,
              type: 'success',
              message: '重置密码成功',
              duration: 1000
            })
            if (id === this.currentUser.id){
              this.$router.push("/login")
              this.$store.commit("logout")
            }
          } else {
            this.$message({
              showClose: true,
              type: 'error',
              message: '重置密码失败',
              duration: 1000
            })
          }
        })
      })
    },
    //批量删除多个
    deleteBatch(id) {
      let ids = []
      if (id) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.id)
        })
      }
      this.$confirm('确定要批量删除您选中的用户吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        user.deleteBatch(ids).then(res => {
          if (res.code === 200) {
            this.$message({
              showClose: true,
              type: "success",
              message: "批量删除成功",
              duration: 1000
            })
            this.load()
          } else {
            this.$message({
              showClose: true,
              type: "error",
              message: "批量删除失败",
              duration: 1000
            })
          }
        })
      })
    },
    //删除单个
    handleDelete(id) {
      this.$confirm('确定要删除您选中的用户吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        user.deleteById(id).then(res => {
          if (res.code === 200) {
            this.$message({
              showClose: true,
              type: 'success',
              message: '删除成功',
              duration: 1000
            })
            this.load()
          } else {
            this.$message({
              showClose: true,
              type: 'error',
              message: '删除失败',
              duration: 1000
            })
          }
        })
      })
    },
    //修改个人信息
    update() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          user.update(this.form).then(res => {
              if (res.code === 200) {
                this.$message({
                  showClose: true,
                  type: 'success',
                  message: '修改成功',
                  duration: 1000
                })
                this.dialogUpdateVisible = false
                this.form = {}
                this.load()
              } else {
                this.$message({
                  showClose: true,
                  type: 'error',
                  message: '修改失败',
                  duration: 1000
                })
              }
          })
        }
      })
    },
    //新增用户
    save() {
      this.$refs['formAdd'].validate((valid) => {
        if (valid) {
          user.save(this.formAdd).then(res => {
            if (res.code === 200) {
              this.$message({
                showClose: true,
                type: 'success',
                message: '新增用户成功',
                duration: 1000
              })
              this.dialogSaveVisible = false
              this.formAdd = {}
              this.load()
            } else {
              this.$message({
                showClose: true,
                type: 'error',
                message: '新增用户失败',
                duration: 1000
              })
            }
          })
        }
      })
    },
    //头像回显
    handleAvatarSuccess(response, file, fileList) {
      this.form.avatar = response.data
    },
    //新增
    handleAdd() {
      this.formAdd = {}
      this.dialogSaveVisible = true
    },
    //编辑按钮
    handleEdit(id) {
      this.dialogUpdateVisible = true
      user.getUserId(id).then(res => {
        this.form = res.data.user
        //this.roleData = res.data.roles
        this.form.roleId = res.data.roleId
      })
    },
    //分页查询全部数据
    load() {
      this.loading = true
      let params = {pageNum: this.pageNum, pageSize: this.pageSize, username: this.username, email: this.email}
      user.getPageList(params).then(res => {
        this.tableData = res.data.userListVos
        this.total = Number(res.data.total)
        this.loading = false
      })
    },
    //重置
    reset() {
      this.load()
      this.username = ''
      this.email = ''
    },
    //页数
    handleSizeChange(pageSize) {
      this.pageSize = pageSize
      this.load()
    },
    //页码
    handleCurrentChange(pageNum) {
      this.pageNum = pageNum
      this.load()
    },
    //批量删除 val:数组
    handleSelectionChange(val) {
      this.multipleSelection = val
      this.delBtlStatus = val.length === 0
    }
  }
}
</script>

<style>
.headerBg {
  background: #eee !important;
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