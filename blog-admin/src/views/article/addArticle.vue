<template>
  <div>
    <el-form ref="form" :model="form" :rules="formRlues" label-width="90px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="文章标题" prop="title">
            <el-input
                v-model="form.title"
                placeholder="请输入文章标题"
                maxlength="30"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="分类" prop="categoryId">
            <el-select v-model="form.categoryId" placeholder="请选择">
              <el-option
                  v-for="category in categoryList"
                  :key="category.id"
                  :label="category.categoryName"
                  :value="category.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="标签" prop="tags">
            <el-select v-model="form.tags" placeholder="请选择" multiple>
              <el-option
                  v-for="tag in tagList"
                  :key="tag.id"
                  :label="tag.tagName"
                  :value="tag.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="文章摘要" prop="summary">
            <el-input v-model="form.summary" type="textarea"/>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="是否置顶" prop="isTop">
            <el-radio-group v-model="form.isTop">
              <el-radio :key="1" :label="1">是</el-radio>
              <el-radio :key="0" :label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>

      </el-row>
      <el-row :gutter="20"/>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="缩略图">
            <el-upload
                :file-list="fileList"
                class="upload-demo"
                list-type="picture"
                drag
                name="img"
                action="upload"
                :on-remove="fileRemove"
                :limit="1"
                :http-request="handleUpload"
                :on-exceed="onExceed"
            >
              <i class="el-icon-upload"/>
              <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item>
            <el-button type="primary" size="medium" @click="handleSubmit">{{ aId ? "更新" : "发布" }}</el-button>
          </el-form-item>
          <el-form-item>
            <el-button v-if="!aId" type="info" @click="handleSave">保存到草稿箱</el-button>
          </el-form-item>

        </el-col>
      </el-row>
      <el-row>
        <mavon-editor ref="md" v-model="form.content" @imgAdd="addImg"/>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import categoryApi from "@/api/category/category"
import tagApi from "@/api/tag/tag"
import uploadApi from "@/api/upload/upload"
import articleApi from "@/api/article/article";

export default {
  name: "addArticle",
  data() {
    return {
      form: {
        cover: '',
        status: '',
      },
      formRlues:{
        title: [{required: true, message: '请输入标题', trigger: 'blur'}],
        summary: [{required: true, message: '请输入文章摘要', trigger: 'blur'}],
        isTop: [{required: true, message: '请选择是否置顶', trigger: 'blur'}],
        categoryId: [{required: true, message: '请选择分类', trigger: 'blur'}],
        tags: [{required: true, message: '请选择标签', trigger: 'blur'}]
      },
      categoryList: [],
      tagList: [],
      aId: -1,
      fileList: [],
      cover: '',
    }
  },
  watch: {
    $route: {
      handler: function (route) {
        this.aId = route.query && route.query.id
      },
      immediate: true
    }
  },
  created() {
    this.getCategoryAndTag()
    if (this.aId) {
      this.getArticle()
    }
  },
  methods: {
    //保存到草稿箱
    handleSave(){
      this.form.status = false
      this.$refs['form'].validate((valid) => {
        if (valid) {
          articleApi.saveDrafts(this.form).then(res=>{
            if (res.code === 200){
              this.$message({
                showClose: true,
                type: 'success',
                message: '保存草稿成功',
                duration: 1000
              })
              this.$router.push("/articleList")
            }else {
              this.$message({
                showClose: true,
                type: 'error',
                message: '保存草稿失败',
                duration: 1000
              })
            }
          })
        }
      })
    },
    //更新发布
    handleSubmit(){
      if (!this.aId){
        //新增
        this.form.status = true
        this.$refs['form'].validate((valid) => {
          if (valid) {
            articleApi.addArticle(this.form).then(res=>{
              if (res.code === 200){
                this.$message({
                  showClose: true,
                  type: 'success',
                  message: '博客发布成功',
                  duration: 1000
                })
                this.$router.push("/articleList")
              }else {
                this.$message({
                  showClose: true,
                  type: 'error',
                  message: '博客发布失败',
                  duration: 1000
                })
              }
            })
          }
        })
      }else {
        this.$refs['form'].validate((valid) => {
          if (valid) {
            //修改
            articleApi.updateArticle(this.form).then(res=>{
              if (res.code === 200){
                this.$message({
                  showClose: true,
                  type: 'success',
                  message: '博客更新成功',
                  duration: 1000
                })
                this.$router.push("/articleList")
              }else {
                this.$message({
                  showClose: true,
                  type: 'error',
                  message: '博客更新失败',
                  duration: 1000
                })
              }
            })
          }
        })
      }
    },
    //头像上传
    handleUpload(img) {
      uploadApi.uploadAvatar(img.file).then(res => {
        this.form.cover = res.data
        this.fileList.push({name: img.file.name, url: res.data})
      }).catch(error => {
        this.$message({
          showClose: true,
          type: 'error',
          message: error.msg,
          duration: 1000
        })
      })
    },
    //获取修改的数据
    getArticle() {
      articleApi.getByArticleId(this.aId).then(res => {
        this.form = res.data
        if (res.data.cover) {
          this.fileList.push({name: '缩略图', url: res.data.cover})
        } else {
          this.fileList = []
        }

      })
    },
    fileRemove(file, fileList) {
      this.fileList.pop()
    },
    onExceed() {
      this.$message({
        showClose: true,
        type: 'warning',
        message: '只能上传一个图片',
        duration: 1000
      })
    },
    //上传图片
    addImg(pos, img) {
      let $vm = this.$refs.md
      uploadApi.uploadAvatar(img).then(res => {
        $vm.$img2Url(pos, res.data)
      })
    },
    //查询标签和分类
    getCategoryAndTag() {
      categoryApi.queryAll().then(res => {
        this.categoryList = res.data
      })
      tagApi.queryAll().then(res => {
        this.tagList = res.data
      })
    }
  }
}
</script>

<style scoped>

</style>