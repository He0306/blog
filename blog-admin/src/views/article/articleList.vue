<template>
  <div>
    <div style="padding: 10px 0">
      <el-input style="width:250px" suffix-icon="el-icon-search" placeholder="请输入文章标题" v-model="title"
                clearable></el-input>
      <el-input style="width:250px" suffix-icon="el-icon-search" placeholder="请输入文章摘要" v-model="summary"
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
      <el-table-column prop="nickName" label="发布人">
      </el-table-column>
      <el-table-column prop="title" label="标题">
      </el-table-column>
      <el-table-column prop="summary" label="摘要">
      </el-table-column>
      <el-table-column prop="content" label="文章内容" width="120px">
        <template slot-scope="scope">
          <el-button type="success" @click="view(scope.row.content)">查看内容</el-button>
        </template>
      </el-table-column>
      <el-table-column label="是否发布">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status" active-color="#13ce66" inactive-color="#ff4949" @change="changeEnable(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" width="150px">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="200">
        <template slot-scope="scope">
          <el-button type="warning" size="small" v-if="scope.row.status === true" @click="handleEdit(scope.row.id)">编辑<i class="el-icon-edit"></i>
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

    <!--  文章预览弹窗  -->
    <el-dialog title="文章详情" :visible.sync="viewDialogVisible" width="60%" top="20px">
      <!-- 预览 -->
      <mavon-editor
          class="md"
          :value="content"
          :subfield="false"
          :defaultOpen="'preview'"
          :toolbarsFlag="false"
          :editable="false"
          :scrollStyle="true"
          :ishljs="true"
      />
    </el-dialog>
  </div>
</template>

<script>
import articleApi from "@/api/article/article";

export default {
  name: "articleList",
  data() {
    return {
      title: '',
      summary: '',
      delBtlStatus: true,
      pageNum: 1,
      total: 0,
      pageSize: 10,
      tableData: [],
      loading: false,
      headerBg: 'headerBg',
      viewDialogVisible: false,
      content: '',
    }
  },
  created() {
    this.load()
  },
  methods: {
    //修改状态
    changeEnable(row){
      articleApi.updateStatus(row).then(res=>{
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
            message: '操作失败',
            type: 'error',
            duration: 1000
          })
        }
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
      this.$confirm('确定要批量删除您选中的文章吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        articleApi.deleteArticleByBatchId(ids).then(res=>{
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
    //删除
    handleDelete(id) {
      this.$confirm('确定要删除您选中的文章吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        articleApi.deleteArticleById(id).then(res=>{
          if (res.code === 200){
            this.$message({
              showClose: true,
              type: 'success',
              message: '删除文章成功',
              duration: 1000
            })
            this.load()
          }else {
            this.$message({
              showClose: true,
              type: 'error',
              message: '删除文章失败',
              duration: 1000
            })
          }
        })
      })
    },
    //编辑
    handleEdit(id) {
      this.$router.push("/addArticle?id=" + id)
    },
    //新增
    handleAdd() {
      this.$router.push("/addArticle")
    },
    //查看文章详情
    view(content) {
      console.log(content)
      this.content = content
      this.viewDialogVisible = true
    },
    //查询全部
    load() {
      this.loading = true
      let params = {pageNum: this.pageNum, pageSize: this.pageSize, title: this.title, summary: this.summary}
      articleApi.getArticlePage(params).then(res => {
        this.total = Number(res.data.total)
        this.tableData = res.data.records
        this.loading = false
      })
    },
    //重置
    reset() {
      this.title = ''
      this.summary = ''
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

<style scoped>
.headerBg {
  background: #eee !important;
}
</style>