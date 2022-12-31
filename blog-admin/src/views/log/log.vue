<template>
  <div>

    <div style="padding: 10px 0">
      <el-input style="width:250px" suffix-icon="el-icon-search" placeholder="请输入模块名称" v-model="optModule"
                clearable></el-input>
      <el-button style="margin-left: 3px" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 3px" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 5px 0">
      <el-button type="danger" :disabled="delBtlStatus" slot="reference" @click="deleteBatch(null)">批量删除<i
          class="el-icon-delete"></i></el-button>
      <el-button type="primary" @click="exportLog">导出日志<i class="el-icon-download"></i></el-button>
    </div>

    <el-table :data="tableData" v-loading="loading" border stripe :header-cell-class-name="headerBg"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column prop="optModule" label="系统模块">
      </el-table-column>
      <el-table-column prop="optType" label="操作类型">
      </el-table-column>
      <el-table-column prop="optDesc" label="操作描述">
      </el-table-column>
      <el-table-column prop="requestMethod" label="请求方式">
        <template slot-scope="scope">
          <el-tag type="danger" v-if="scope.row.requestMethod === 'DELETE'">DELETE</el-tag>
          <el-tag type="success" v-if="scope.row.requestMethod === 'POST'">POST</el-tag>
          <el-tag type="warning" v-if="scope.row.requestMethod === 'PUT'">PUT</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="nickName" label="操作人员">
      </el-table-column>
      <el-table-column prop="ipAddress" label="登录IP">
      </el-table-column>
      <el-table-column prop="ipSource" label="登录地址" width="170px">
      </el-table-column>
      <el-table-column prop="createTime" label="操作时间" width="150px">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="200">
        <template slot-scope="scope">
          <el-button type="success" size="small" @click="see(scope.row)">查看<i class="el-icon-view"></i>
          </el-button>
          <el-button type="danger" size="small" slot="reference" @click="handleDelete(scope.row.id)">删除<i
              class="el-icon-delete"></i></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--弹窗-->
    <el-dialog :visible.sync="dialogFormVisible" width="35%" top="50px">
      <el-descriptions title="日志信息" :column="1" style="margin-top: -35px" size="medium">
        <el-descriptions-item label="操作模块">{{ formData.optModule }}</el-descriptions-item>
        <el-descriptions-item label="请求地址">{{ formData.optUrl }}</el-descriptions-item>
        <el-descriptions-item label="请求方式">
          <el-tag type="danger" v-if="formData.requestMethod === 'DELETE'">{{ formData.requestMethod }}</el-tag>
          <el-tag type="success" v-if="formData.requestMethod === 'POST'">{{ formData.requestMethod }}</el-tag>
          <el-tag type="warning" v-if="formData.requestMethod === 'PUT'">{{ formData.requestMethod }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="操作方法">{{ formData.optMethod }}</el-descriptions-item>
        <el-descriptions-item label="请求参数">{{ formData.requestParam }}</el-descriptions-item>
        <el-descriptions-item label="返回数据">{{ formData.responseData }}</el-descriptions-item>
        <el-descriptions-item label="操作人员">{{ formData.nickName }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>

    <div style="padding-left: 400px;padding-top: 15px">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[10,20,30]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

  </div>
</template>

<script>
import logApi, {exportLog} from "@/api/log/log";

export default {
  name: "log",
  data() {
    return {
      pageNum: 1,
      pageSize: 10,
      total: 0,
      delBtlStatus: true,
      optModule: '',
      tableData: [],
      loading: false,
      dialogFormVisible: false,
      formData: {},
      headerBg: 'headerBg'
    }
  },
  created() {
    this.load()
  },
  methods: {
    //导出日志
    exportLog() {
      window.open("http://127.0.0.1:8888/export")
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
      this.$confirm('确定要删除您选中的日志吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        logApi.deleteBatch(ids).then(res => {
          if (res.code === 200) {
            this.$message({
              showClose: true,
              type: 'success',
              message: '批量删除成功',
              duration: 1000
            })
            this.load()
          } else {
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
    //删除单个
    handleDelete(id) {
      this.$confirm('确定要删除您选中的日志吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        logApi.deleteById(id).then(res => {
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
    //查看详情
    see(row) {
      this.dialogFormVisible = true
      this.formData = JSON.parse(JSON.stringify(row))
    },
    //分页查询全部数据
    load() {
      this.loading = true
      let params = {pageNum: this.pageNum, pageSize: this.pageSize, optModule: this.optModule}
      logApi.getFindAll(params).then(res => {
        this.tableData = res.data.records
        this.total = Number(res.data.total)
        this.loading = false
      })
    },
    //重置
    reset() {
      this.optModule = ''
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
</style>