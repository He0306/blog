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
      <el-table-column prop="roleName" label="名称">
      </el-table-column>
      <el-table-column prop="description" label="描述">
      </el-table-column>
      <el-table-column prop="roleCode" label="角色编码">
      </el-table-column>
      <el-table-column
          fixed="right"
          label="操作"
          width="280">
        <template slot-scope="scope">
          <el-button type="success" size="small" @click="selectMenu(scope.row)">分配权限<i class="el-icon-s-tools"></i>
          </el-button>
          <el-button type="warning" size="small" @click="handleEdit(scope.row)">编辑<i class="el-icon-edit"></i>
          </el-button>
          <el-button type="danger" size="small" slot="reference" @click="handleDelete(scope.row.id)">删除<i
              class="el-icon-delete"></i>
          </el-button>
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
    <!--新增和编辑弹窗-->
    <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%" >
      <el-form label-width="80px" :model="form" ref="form" :rules="formRlues">
        <el-form-item label="名称" prop="roleName">
          <el-input v-model="form.roleName" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色编码" prop="roleCode">
          <el-input v-model="form.roleCode" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>
    <!--设置菜单弹窗-->
    <el-dialog title="设置菜单" :visible.sync="menuDialogVisible" width="35%" top="20px">
      <el-tree
          :props="props"
          :data="menuData"
          ref="tree"
          show-checkbox
          destroy-on-close
          node-key="id"
          :check-strictly=true
          :default-expanded-keys="expends"
          :default-checked-keys="checks">
        <span class="custom-tree-node" slot-scope="{ node, data }">
          <span><i :class="data.icon"></i>{{ data.name }}</span>
        </span>
      </el-tree>
      <div slot="footer" class="dialog-footer">
        <el-button @click="menuDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import role from "@/api/role/role";
import menu from "@/api/menu/menu";
import roleMenu from "@/api/roleMenu/roleMenu";

export default {
  name: "role",
  data() {
    return {
      tableData: [],
      headerBg: 'headerBg',
      name: '',
      pageNum: 1,
      pageSize: 10,
      total: 0,
      form: {},
      loading: false,
      dialogFormVisible: false,
      formRlues: {
        roleName: [
          {required: true, message: '请输入角色名称', trigger: 'blur'}
        ]
      },
      delBtlStatus: true,
      menuDialogVisible: false,
      menuData: [],
      expends: [],
      checks: [],
      roleId: '',
      roleCode: '',
      props: {
        children: 'children',
        label: 'name',
      },
    }
  },
  created() {
    this.load()
  },
  methods: {
    //绑定角色和菜单
    saveRoleMenu(){
      roleMenu.saveRoleMenu(this.roleId,this.$refs.tree.getCheckedKeys()).then(res=>{
        if (res.code === 200) {
          this.$message({
            showClose: true,
            type: 'success',
            message: '绑定成功',
            duration: 1000
          })
          this.menuDialogVisible = false
          //操作管理员角色后需要重新登录
          if (this.roleCode === 'ROLE_ADMIN') {
            this.$store.commit("logout")
          }
        } else {
          this.$message({
            showClose: true,
            type: 'error',
            message: '绑定失败',
            duration: 1000
          })
        }
      })
    },
    //设置菜单按钮
    selectMenu(row){
      this.roleId = row.id
      this.roleCode = row.roleCode
      this.menuDialogVisible = true
      //树型菜单数据
      menu.getFindAll("").then(res=>{
        this.menuData = res.data

        //把后台返回的菜单数据处理成 id 数组 展开
        this.expends = this.menuData.map(v => v.id)
      })

      //查询角色菜单表，关联的菜单id  打勾
      roleMenu.getRoleMenuById(this.roleId).then(res=>{
        this.checks = res.data
      })

      //将所有菜单ID赋值给树
      menu.getFindAllIds().then(res=>{
        this.menuDialogVisible = true
        const ids = res.data
        ids.forEach(id => {
          if (!this.checks.includes(id)){
            this.$refs.tree.setChecked(id,false)
          }
        })
      })

    },
    //批量删除按钮
    deleteBatch(id) {
      let ids = []
      if (id) {
        ids.push(id)
      } else {
        this.multipleSelection.forEach(row => {
          ids.push(row.id)
        })
      }
      this.$confirm('确定要删除您选中的角色吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        role.deleteBatch(ids).then(res => {
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
    //删除按钮
    handleDelete(id) {
      this.$confirm('确定要删除您选中的角色吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        role.deleteRoleById(id).then(res => {
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
    //新增或修改按钮
    save() {
      this.$refs['form'].validate((valid) => {
        if (valid) {
          role.saveOrUpdate(this.form).then(res => {
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
    //编辑按钮
    handleEdit(row) {
      this.form = JSON.parse(JSON.stringify(row))
      this.dialogFormVisible = true
    },
    //新增按钮弹窗
    handleAdd() {
      this.dialogFormVisible = true
      this.form = {}
    },
    //查询全部
    load() {
      this.loading = true
      let params = {pageNum: this.pageNum, pageSize: this.pageSize, name: this.name}
      role.getRoleList(params).then(res => {
        this.total = Number(res.data.total)
        this.tableData = res.data.records
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