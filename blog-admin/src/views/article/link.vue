<template>
  <div>
    <div style="padding: 10px 0">
      <el-input style="width:250px" suffix-icon="el-icon-search" placeholder="请输入名称" v-model="linkName"
                clearable></el-input>
      <el-button style="margin-left: 3px" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 3px" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 5px 0">
      <el-button type="primary" @click="handleAdd">新增<i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button type="danger" :disabled="delBtlStatus" slot="reference" @click="deleteBatch(null)">批量删除<i
          class="el-icon-delete"></i></el-button>
    </div>

    <el-table :data="tableData" v-loading="loading" border stripe :header-cell-class-name="headerBg"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="logo" label="logo" width="60">
        <template slot-scope="scope">
          <el-image :src="scope.row.logo" :preview-src-list="[scope.row.logo]"/>
        </template>
      </el-table-column>
      <el-table-column prop="name" label="友链名称">
      </el-table-column>
      <el-table-column prop="address" label="友链地址">
      </el-table-column>
      <el-table-column prop="description" label="描述">
      </el-table-column>
      <el-table-column label="是否启用">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" active-color="#13ce66" inactive-color="#ff4949" @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="300">
        <template slot-scope="scope">
          <el-button type="warning" size="small" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i>
          </el-button>
          <el-button type="danger" size="small" slot="reference" @click="handleDelete(scope.row.id)">删除<i
              class="el-icon-delete"></i></el-button>
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

    <!--弹窗-->
    <el-dialog title="友链信息" :visible.sync="dialogFormVisible" width="33%" top="50px">
      <el-form label-width="80px" :model="form" ref="form" :rules="formRlues">
        <el-form-item label="logo" prop="logo">
          <el-upload
              action=""
              name="img"
              class="avatar-uploader"
              :http-request="uploadLogo"
              :show-file-list="false"
              :before-upload="beforeUpload"
              :on-success="handleAvatarSuccess"
              ref="upload">
            <img v-if="form.logo" :src="form.logo" class="avatar" alt="">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="友链名称" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="友链地址" prop="address">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="true">启用</el-radio>
            <el-radio :label="false">未启用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import linkApi from "@/api/link/link";
import uploadApi from "@/api/upload/upload";

export default {
  name: "link",
  data() {
    return {
      linkName: '',
      delBtlStatus: true,
      tableData: [],
      loading: false,
      pageSize: 5,
      pageNum: 1,
      total: null,
      headerBg: 'headerBg',
      dialogFormVisible: false,
      form: {
        logo: ''
      },
      formRlues: {
        name: [{required: true, message: '请输入友链名称', trigger: 'blur'}],
        logo: [{required: true, message: '请输入logo地址', trigger: 'blur'}],
        description: [{required: true, message: '请输入友链描述', trigger: 'blur'}],
        status: [{required: true, message: '请选择状态', trigger: 'blur'}],
        address: [{required: true, message: '请输入友链地址', trigger: 'blur'}]
      },
    }
  },
  created() {
    this.load()
  },
  methods: {
    //上传
    uploadLogo(img){
      uploadApi.uploadAvatar(img.file).then(res=>{
        if (res.code === 200){
          this.$message({
            showClose: true,
            type: 'success',
            message: 'logo上传成功',
            duration: 1000
          })
          this.$forceUpdate()
          this.form.logo = res.data
        }else {
          this.$message({
            showClose: true,
            type: 'error',
            message: 'logo上传失败，请重新上传',
            duration: 1000
          })
        }
      })
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
    //头像回显
    handleAvatarSuccess(res) {
      this.form.logo = res
    },
    //改变状态
    changeEnable(row) {
      linkApi.saveOrUpdate(row).then(res=>{
        if (res.code === 200){
          this.$message({
            showClose: true,
            message: '操作成功',
            type: 'success',
            duration: 1000
          })
          this.load()
        }else {
          this.$message({
            showClose: true,
            type: 'error',
            message: '操作失败',
            duration: 1000
          })
        }
      })
    },
    //确定
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          linkApi.saveOrUpdate(this.form).then(res=>{
            if (res.code === 200){
              this.$message({
                showClose: true,
                message: '操作成功',
                type: 'success',
                duration: 1000
              })
              this.load()
              this.dialogFormVisible = false
            }else {
              this.$message({
                showClose: true,
                type: 'error',
                message: '操作失败',
                duration: 1000
              })
            }
          })
        }
      })
    },
    //编辑
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
      this.$refs['form'].resetFields()
    },
    //删除
    handleDelete(id) {
      this.$confirm('确定要删除您选中的友链吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        linkApi.deleteById(id).then(res=>{
          if (res.code === 200){
            this.$message({
              showClose: true,
              type: 'success',
              message: '删除成功',
              duration: 1000
            })
            this.load()
          }else {
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
    },
    //批量删除
    deleteBatch(id) {
      let ids = []
      if (id) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.id)
        })
      }
      this.$confirm('确定要批量删除您选中的友链吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        linkApi.deleteByIdBatch(ids).then(res=>{
          if (res.code === 200){
            this.$message({
              showClose: true,
              type: 'success',
              message: '批量删除成功',
              duration: 1000
            })
            this.load()
          }else {
            this.$message({
              showClose: true,
              type: 'error',
              message: '批量删除失败',
              duration: 1000
            })
          }
        })
      })
    },
    //新增
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    //查询全部
    load() {
      this.loading = true
      let param = {pageNum: this.pageNum, pageSize: this.pageSize, name: this.linkName}
      linkApi.pageList(param).then(res => {
        this.tableData = res.data.linkList
        this.total = Number(res.data.total)
        this.loading = false
      })
    },
    //重置
    reset() {
      this.linkName = ''
      this.load()
    },
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
  content: "上传logo" !important;
  font-size: 12px;
  color: #000;
}
</style>