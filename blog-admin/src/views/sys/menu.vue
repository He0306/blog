<template>
  <div>
    <div style="padding: 10px 0">
      <el-input style="width:250px" suffix-icon="el-icon-search" placeholder="请输入名称" v-model="name"
                clearable></el-input>
      <el-button style="margin-left: 3px" type="primary" @click="load">搜索</el-button>
      <el-button style="margin-left: 3px" type="warning" @click="reset">重置</el-button>
    </div>

    <div style="margin: 5px 0">
      <el-button type="primary" @click="handleAddMenu(null)">新增<i class="el-icon-circle-plus-outline"></i></el-button>
      <el-button type="danger" slot="reference" :disabled="delBtlStatus" @click="deleteBatch(null)">批量删除<i
          class="el-icon-delete"></i></el-button>
    </div>

    <el-table :data="tableData" v-loading="loading" border stripe :header-cell-class-name="headerBg" row-key="id"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55 "/>
      <el-table-column prop="name" label="名称" width="170">
      </el-table-column>
      <el-table-column prop="perms" label="权限编码" width="180">
      </el-table-column>
      <el-table-column prop="path" label="路径">
      </el-table-column>
      <el-table-column prop="pagePath" label="页面路径" width="150">
      </el-table-column>
      <el-table-column label="图标" class-name="fontSize18" align="center" label-class-name="fontSize12">
        <template slot-scope="scope">
          <i :class="scope.row.icon"></i>
        </template>
      </el-table-column>
      <el-table-column prop="description" label="描述">
      </el-table-column>
      <el-table-column prop="sort" label="顺序" width="50">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="300">
        <template slot-scope="scope">
          <el-button type="primary" size="small" @click="handleAdd(scope.row.id)"
                     v-if="!scope.row.path && !scope.row.perms">新增子菜单<i
              class="el-icon-plus"></i></el-button>
          <el-button type="success" size="small" @click="handleAddFunction(scope.row.id)"
                     v-if="scope.row.children && scope.row.path && scope.row.path !== '/home'">新增功能<i
              class="el-icon-plus"></i></el-button>
          <el-button type="warning" size="small" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i>
          </el-button>
          <el-button type="danger" size="small" slot="reference" @click="handleDelete(scope.row.id)">删除<i
              class="el-icon-delete"></i></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!--添加弹窗-->
    <el-dialog title="菜单信息" :visible.sync="dialogFormVisible" width="35%">
      <el-form label-width="80px" :model="form" ref="form" :rules="formRlues">
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="路径" prop="path">
          <el-input v-model="form.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="页面路径" prop="pagePath">
          <el-input v-model="form.pagePath" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-select clearable v-model="form.icon" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in icon" :key="item.id" :label="item.name" :value="item.id">
              <i :class="item.value"/>{{ item.name }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="顺序" prop="sort">
          <el-input v-model="form.sort" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateOne">确 定</el-button>
      </div>
    </el-dialog>

    <!--添加修改子菜单弹窗-->
    <el-dialog title="菜单信息" :visible.sync="dialogFormUpdateVisible" width="35%">
      <el-form label-width="80px" :model="formUpdate" ref="formUpdate" :rules="formUpdateRlues">
        <el-form-item label="名称" prop="name">
          <el-input v-model="formUpdate.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="一级菜单" prop="pid">
          <el-select clearable v-model="formUpdate.pid" placeholder="请选择一级菜单" style="width: 100%">
            <template v-for="item in ParentMenu">
              <el-option :label="item.name" :value="item.id"></el-option>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="路径" prop="path">
          <el-input v-model="formUpdate.path" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="页面路径" prop="pagePath">
          <el-input v-model="formUpdate.pagePath" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="formUpdate.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormUpdateVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOne">确 定</el-button>
      </div>
    </el-dialog>

    <!--  添加或修改一级菜单  -->
    <el-dialog title="菜单信息" :visible.sync="dialogFormOneVisible" width="35%">
      <el-form label-width="80px" :model="formOne" ref="formOne" :rules="formOneRlues">
        <el-form-item label="名称" prop="name">
          <el-input v-model="formOne.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="图标" prop="icon">
          <el-select clearable v-model="formOne.icon" placeholder="请选择" style="width: 100%">
            <el-option v-for="item in icon" :key="item.name" :label="item.name" :value="item.value">
              <i :class="item.value"/>&nbsp;{{ item.name }}
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input v-model="formOne.sort" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="formOne.description" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormOneVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加或修改功能弹窗 -->
    <el-dialog title="功能信息" :visible.sync="dialogFunctionVisible" width="35%">
      <el-form label-width="80px" :model="formFunction" ref="formFunction" :rules="formFunctionRlues">
        <el-form-item label="权限名称" prop="name">
          <el-input v-model="formFunction.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="上级菜单" prop="fid">
          <el-select clearable v-model="formFunction.fid" placeholder="请选择上级菜单" style="width: 100%">
            <template v-for="item in ParentMenu">
              <el-option :label="item.name" :value="item.id"></el-option>
              <template v-for="child in item.children">
                <el-option :label="child.name" :value="child.id">
                  <span>{{ "- " + child.name }}</span>
                </el-option>
              </template>
            </template>
          </el-select>
        </el-form-item>
        <el-form-item label="权限编码" prop="perms">
          <el-input v-model="formFunction.perms" autocomplete="off" placeholder="比如：sys:user:list"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFunctionVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveFunction">确 定</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import menuApi from "@/api/menu/menu";
import icon from "@/api/icon/icon";

export default {
  name: "Menu",
  data() {
    return {
      name: '',
      loading: false,
      tableData: [],
      delBtlStatus: true,
      form: {},
      formRlues: {
        name: [{required: true, message: '请输入菜单名称', trigger: 'blur'}],
        path: [{required: true, message: '请输入路由', trigger: 'blur'}],
        pagePath: [{required: true, message: '请输入组件路径', trigger: 'blur'}],
        sort: [{required: true, message: '请输入排序序号', trigger: 'blur'}],
        icon: [{required: true, message: '请选择图标', trigger: 'blur'}]
      },
      headerBg: 'headerBg',
      dialogFormVisible: false,
      icon: [],
      dialogFunctionVisible: false,
      formFunction: {},
      ParentMenu: [],
      formFunctionRlues: {
        name: [{required: true, message: '请输入权限名称', trigger: 'blur'}],
        fid: [{required: true, message: '请选择上级菜单', trigger: 'blur'}],
        perms: [{required: true, message: '请输入权限编码名称', trigger: 'blur'}]
      },
      dialogFormUpdateVisible: false,
      formUpdate: {},
      formUpdateRlues: {
        name: [{required: true, message: '请输入菜单名称', trigger: 'blur'}],
        path: [{required: true, message: '请输入路由', trigger: 'blur'}],
        pagePath: [{required: true, message: '请输入组件路径', trigger: 'blur'}],
        pid: [{required: true, message: '请选择一级菜单', trigger: 'blur'}]
      },
      dialogFormOneVisible: false,
      formOne: {},
      formOneRlues: {
        name: [{required: true, message: '请输入菜单名称', trigger: 'blur'}],
        icon: [{required: true, message: '请选择菜单图标', trigger: 'blur'}]
      }
    }
  },
  created() {
    this.load()
  },
  methods: {
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
      this.$confirm('确定要删除您选中的菜单吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        menuApi.deleteBatch(ids).then(res => {
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
    //根据ID删除
    handleDelete(id) {
      this.$confirm('确定要删除您选中的菜单吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        menuApi.deleteByMenuId(id).then(res => {
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
    updateOne() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          menuApi.save(this.form).then(res => {
            if (res.code === 200) {
              this.$message({
                showClose: true,
                type: 'success',
                message: '操作成功',
                duration: 1000
              })
              this.dialogFormOneVisible = false
              this.formOne = {}
              this.load()
            } else {
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
    //新增一级菜单弹窗按钮
    save() {
      this.$refs['formOne'].validate((valid) => {
        if (valid) {
          menuApi.save(this.formOne).then(res => {
            if (res.code === 200) {
              this.$message({
                showClose: true,
                type: 'success',
                message: '操作成功',
                duration: 1000
              })
              this.dialogFormOneVisible = false
              this.formOne = {}
              this.load()
            } else {
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
    //新增
    handleAddMenu() {
      this.dialogFormOneVisible = true
      icon.getFindAll().then(res => {
        this.icon = res.data
      })
    },
    //添加或修改一级菜单
    saveOne() {
      this.$refs['formUpdate'].validate((valid) => {
        if (valid) {
          menuApi.save(this.formUpdate).then(res => {
            if (res.code === 200) {
              this.$message({
                showClose: true,
                type: 'success',
                message: '操作成功',
                duration: 1000
              })
              this.dialogFormUpdateVisible = false
              this.formUpdate = {}
              this.load()
            } else {
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
    //添加功能权限
    saveFunction() {
      this.$refs['formFunction'].validate((valid) => {
        if (valid) {
          menuApi.save(this.formFunction).then(res => {
            if (res.code === 200) {
              this.$message({
                showClose: true,
                type: 'success',
                message: '操作成功',
                duration: 1000
              })
              this.dialogFunctionVisible = false
              this.formFunction = {}
              this.load()
            } else {
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
    //新增功能
    handleAddFunction(id) {
      this.formFunction = {}
      this.dialogFunctionVisible = true
      menuApi.getFindAll("").then(res => {
        this.ParentMenu = res.data
      })
    },
    //新增一级菜单
    handleAdd() {
      this.formUpdate = {}
      this.dialogFormUpdateVisible = true
      menuApi.getFindAll("").then(res => {
        this.ParentMenu = res.data
      })
    },
    //编辑
    handleEdit(row) {
      //有权限编码则弹窗功能
      if (row.perms) {
        this.dialogFunctionVisible = true
        this.formFunction = JSON.parse(JSON.stringify(row))
        menuApi.getFindAll("").then(res => {
          this.ParentMenu = res.data
        })
      }
      //打修改子菜单弹窗
      if (row.pagePath != null && row.path != null && row.pid != null && row.icon == null) {
        this.dialogFormUpdateVisible = true
        this.formUpdate = JSON.parse(JSON.stringify(row))
      }
      //打卡修改一级菜单弹窗
      if (row.path == null && row.pid == null && row.fid == null && row.perms == null && row.icon != null) {
        this.dialogFormOneVisible = true
        this.formOne = JSON.parse(JSON.stringify(row))
        icon.getFindAll().then(res => {
          this.icon = res.data
        })
      }
      //打开首页修改弹窗
      if (row.icon != null && row.path === '/home') {
        this.form = JSON.parse(JSON.stringify(row))
        this.dialogFormVisible = true
        icon.getFindAll().then(res => {
          this.icon = res.data
        })
      }

    },
    //分页查询全部数据
    load() {
      this.loading = true
      menuApi.getFindAll(this.name).then(res => {
        this.tableData = res.data
        this.loading = false
      })
    },
    //搜索重置按钮
    reset() {
      this.name = ""
      this.load()
    },
    //批量删除 val:数组
    handleSelectionChange(val) {
      this.multipleSelection = val
      this.delBtlStatus = val.length === 0
    },
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