<template>
  <div>
    <div style="padding: 10px 0">
      <el-input style="width:250px" suffix-icon="el-icon-search" placeholder="请输入名称" v-model="name"
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
      <el-table-column prop="name" label="名称">
      </el-table-column>
      <el-table-column label="图标" class-name="fontSize18" align="center" label-class-name="fontSize12">
        <template slot-scope="scope">
          <i :class="scope.row.name"></i>
        </template>
      </el-table-column>
      <el-table-column prop="value" label="内容">
      </el-table-column>
      <el-table-column prop="type" label="类型">
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
    <el-dialog title="图标信息" :visible.sync="dialogFormVisible" width="30%">
      <el-form label-width="80px" :model="form" ref="form" :rules="formRlues">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="内容" prop="value">
          <el-input v-model="form.value" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-input v-model="form.type" autocomplete="off"></el-input>
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
import icon from "@/api/icon/icon";

export default {
  name: "icon",
  data() {
    return {
      tableData: [],
      pageNum: 1,
      pageSize: 10,
      total: 0,
      name: '',
      headerBg: 'headerBg',
      loading: false,
      form: {},
      formRlues: {
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'}
        ],
        value: [
          {required: true, message: '请输入内容', trigger: 'blur'}
        ],
        type: [
          {required: true, message: '请输入类型', trigger: 'blur'}
        ]
      },
      dialogFormVisible: false,
      delBtlStatus: true
    }
  },
  created() {
    this.load()
  },
  methods: {
    //删除单个
    handleDelete(id) {
      this.$confirm('确定要删除您选中的图标吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        icon.deleteIconById(id).then(res => {
          if (res.code === 200) {
            this.$message({
              showClose: true,
              type: "success",
              message: "删除成功",
              duration: 1000
            })
            this.load()
          } else {
            this.$message({
              showClose: true,
              type: "error",
              message: "删除失败",
              duration: 1000
            })
          }
        })
      })
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
      this.$confirm('确定要删除您选中的图标吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        icon.deleteIconByBatchId(ids).then(res => {
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
    //新增或修改确定
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          icon.saveOrUpdate(this.form).then(res => {
            if (res.code === 200) {
              this.$message({
                showClose: true,
                type: "success",
                message: "保存成功",
                duration: 1000
              })
              this.load()
              this.dialogFormVisible = false
            } else {
              this.$message({
                showClose: true,
                type: "error",
                message: "保存失败",
                duration: 1000
              })
            }
          })
        }
      })
    },
    //打开新增弹窗
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    //编辑
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))  //深拷贝
      this.dialogFormVisible = true
      this.$refs['form'].resetFields()
    },
    //分页查询全部数据
    load() {
      this.loading = true
      let params = {pageNum: this.pageNum, pageSize: this.pageSize, name: this.name}
      icon.getPageList(params).then(res => {
        this.tableData = res.data.records
        this.total = Number(res.data.total)
        this.loading = false
      })
    },
    //重置
    reset() {
      this.name = ''
      this.load()
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
.fontSize18 {
  font-size: 18px;
}

.fontSize12 {
  font-size: 12px;
}
</style>