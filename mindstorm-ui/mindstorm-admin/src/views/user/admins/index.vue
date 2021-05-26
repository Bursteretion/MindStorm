<template>
  <div class="app-container">
    <div v-hasPermission="['system:admin:query']">
      <el-form ref="searchAdminForm" :inline="true" :model="searchAdminVO">
        <el-form-item label="用户名" prop="username">
          <el-input size="small" v-model="searchAdminVO.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input size="small" v-model="searchAdminVO.realName" placeholder="真实姓名"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select size="small" v-model="searchAdminVO.status" placeholder="请选择">
            <el-option
              v-for="item in UserStatus"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="btn-item">
          <el-button icon="el-icon-search" size="small" type="primary" @click="handleAdminSearch">查询</el-button>
        </el-form-item>
        <el-form-item class="btn-item">
          <el-button icon="el-icon-refresh" size="small" type="default" @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div>
      <el-button
        plain
        icon="el-icon-circle-plus-outline"
        size="mini"
        type="primary"
        v-hasPermission="['system:admin:add']"
        @click="openAdminAddDialog">
        添加
      </el-button>
    </div>

    <div style="margin-top: 15px">
      <el-table
        :data="admins"
        style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="username"
          label="用户名"
          width="180">
        </el-table-column>
        <el-table-column
          prop="realName"
          width="120"
          label="真实姓名">
        </el-table-column>
        <el-table-column
          prop="sex"
          width="120"
          label="性别">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.sex === 1" type="success">男</el-tag>
            <el-tag v-if="scope.row.sex === 2" type="info">女</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="phone"
          label="手机号">
        </el-table-column>
        <el-table-column
          prop="gmtCreate"
          width="180"
          label="创建时间">
        </el-table-column>
        <el-table-column
          prop="status"
          v-hasPermission="['system:admin:status']"
          label="状态">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="0"
              :inactive-value="1"
              @click.native="changeAdminStatus(scope.row)"
              active-color="#ff4949"
              inactive-color="#13ce66"
              active-text="禁用"
              inactive-text="正常">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              v-hasPermission="['system:admin:update']"
              @click="handleAdminEdit(scope.row.id)">
              修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              v-hasPermission="['system:admin:distribute']"
              @click="handleAdminAddRole(scope.row.id)">
              分配角色
            </el-button>
            <el-button
              slot="reference"
              size="mini"
              icon="el-icon-delete"
              type="text"
              v-hasPermission="['system:admin:delete']"
              @click="handleAdminDelete(scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <div class="pagination">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pagination.currentPage"
        :page-sizes="[1, 2, 5, 10, 20]"
        :page-size="pagination.pageSize"
        :page-count="pagination.pageCount"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.total">
      </el-pagination>
    </div>

    <div>
      <el-dialog
        :title="adminDialogTitle"
        :visible.sync="adminAddDialogVisible"
        @close="resetAdminForm"
        width="40%"
        top="10vh"
        append-to-body>
        <el-form ref="adminForm" :rules="rules" :model="adminForm" label-width="80px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="用户名" prop="username">
                <el-input v-model="adminForm.username" autocomplete="off" placeholder="请输入用户名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="真实姓名" prop="realName">
                <el-input v-model="adminForm.realName" autocomplete="off" placeholder="请输入真实姓名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item v-if="adminForm.id === undefined || adminForm.id === ''" label="密码" prop="password">
                <el-input v-model="adminForm.password" show-password placeholder="请输入密码"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="手机">
                <el-input v-model="adminForm.phone" autocomplete="off" placeholder="请输入手机号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="年龄">
                <el-input v-model="adminForm.age" autocomplete="off" placeholder="请输入年龄"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="出生日期">
                <el-date-picker
                  v-model="adminForm.birthDay"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
                  type="date"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态" prop="status">
                <el-select v-model="adminForm.status" placeholder="请选择状态">
                  <el-option
                    v-for="item in UserStatus"
                    :key="item.value"
                    :label="item.name"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="性别">
                <el-radio v-model="adminForm.sex" :label="1">男</el-radio>
                <el-radio v-model="adminForm.sex" :label="2">女</el-radio>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="handleCancel">取 消</el-button>
          <el-button type="primary" @click="handleDialogSubmitBtn">{{ dialogBtnTitle }}</el-button>
        </div>
      </el-dialog>

      <!-- 分配用户角色对话框 -->
      <el-dialog title="为用户分配角色"
                 :visible.sync="addUserRoleDialogVisible"
                 width="500px"
                 append-to-body>
        <el-form :model="userRoleForm" label-width="80px">
          <el-form-item label="用户名称">
            <el-input v-model="userRoleForm.username" :disabled="true"/>
          </el-form-item>
          <el-form-item label="用户类型">
            <el-input v-model="userRoleForm.userType" :disabled="true"/>
          </el-form-item>
          <el-form-item label="所有角色">
            <el-select
              v-model="userRoleForm.roles"
              multiple
              collapse-tags
              placeholder="请选择角色">
              <el-option
                v-for="item in roleOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="handleAddUserRole">确 定</el-button>
          <el-button @click="handleCancelAddUserRole">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  UserStatus,
  listUsers,
  changeUserStatus,
  getUserById,
  createAdmin,
  updateUser,
  searchUsers,
  deleteUser
} from '@/api/user'

import {
  listUnDisableRoles
} from '@/api/role'

import {
  userRole,
  distributeRole
} from '@/api/userRole'

export default {
  name: 'AdminsList',
  data() {
    return {
      // 用户状态
      UserStatus,
      // 搜索表单
      searchAdminVO: {
        username: '',
        realName: '',
        status: '',
        userType: 1
      },
      // 学生列表
      admins: [],
      // 分页信息
      pagination: {
        currentPage: 1,
        pageSize: 5,
        total: 0
      },
      // 对话框标题
      adminDialogTitle: '添加管理员',
      // 添加/修改对话框是否显示
      adminAddDialogVisible: false,
      // 分配角色对话框是否显示
      addUserRoleDialogVisible: false,
      // 对话框添加（修改）按钮标题
      dialogBtnTitle: '添加',
      // 管理员表单
      adminForm: {},
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        realName: [
          {required: true, message: '请输入真实姓名', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        status: [
          {required: true, message: '请选择状态', trigger: 'change'}
        ]
      },
      userRoleForm: {
        userId: '',
        username: '',
        userType: '',
        roles: []
      },
      roleOptions: []
    }
  },
  created() {
    this.loadAdmins()
  },
  methods: {
    // 加载管理员
    loadAdmins() {
      listUsers(this.pagination.currentPage, this.pagination.pageSize, 3).then((res) => {
        if (res && res.code === 20000) {
          this.admins = res.data.page.records
          this.pagination.total = res.data.page.total
        }
      })
    },
    // 点击添加按钮
    openAdminAddDialog() {
      this.adminDialogTitle = '添加管理员'
      this.adminAddDialogVisible = true
    },
    // 更改管理员状态
    changeAdminStatus(admin) {
      let text = admin.status === 0 ? '禁用' : '启用'
      this.$confirm(`是否${text}此角色?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeUserStatus(admin.id, admin.status).then((res) => {
          if (res && res.code === 20000 && res.data.change) {
            this.$message({
              type: 'success',
              message: `${text}成功!`
            });
          }
        })
      }).catch(() => {
        this.loadAdmins()
      });
    },
    handleAdminEdit(adminId) {
      getUserById(adminId).then((res) => {
        if (res && res.code === 20000) {
          this.adminForm = res.data.user
          this.adminDialogTitle = '修改管理员信息'
          this.dialogBtnTitle = '修改'
          this.adminAddDialogVisible = true
        }
      })
    },
    handleAdminDelete(admin) {
      this.$confirm(`你确定要删除管理员：${admin.username}吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteUser(admin.id).then((res) => {
          if (res && res.code === 20000) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.loadAdmins()
          }
        })
      })
    },
    handleCancel() {
      this.resetAdminForm()
      this.adminAddDialogVisible = false
    },
    // 为管理员分配角色
    handleAdminAddRole(adminId) {
      this.addUserRoleDialogVisible = true
      listUnDisableRoles().then((response) => {
        if (response && response.code === 20000) {
          this.roleOptions = response.data.roles
          this.$nextTick(() => {
            userRole(adminId).then((res) => {
              if (res && res.code === 20000) {
                const {userId, username, userType, roles} = res.data.userRoleDTO
                this.userRoleForm.userId = userId
                this.userRoleForm.username = username
                this.userRoleForm.userType = userType
                this.userRoleForm.roles = roles
              }
            })
          })
        }
      })
    },
    handleAddUserRole() {
      distributeRole(this.userRoleForm.userId, this.userRoleForm.roles).then((res) => {
        if (res && res.code === 20000) {
          this.$message.success('为' + this.userRoleForm.username + '分配角色成功')
          this.handleCancelAddUserRole()
        }
      })
    },
    handleCancelAddUserRole() {
      this.userRoleForm = {
        userId: '',
        username: '',
        userType: '',
        roles: []
      }
      this.addUserRoleDialogVisible = false
    },
    // 添加/修改表单提交
    handleDialogSubmitBtn() {
      this.$refs['adminForm'].validate((valid) => {
        if (valid) {
          // 添加学生
          if (this.adminForm.id === undefined || this.adminForm.id === '') {
            createAdmin(this.adminForm).then((res) => {
              if (res && res.code === 20000) {
                this.$message.success("添加管理员成功！")
                this.adminAddDialogVisible = false
                this.loadAdmins()
              }
            })
          } else {
            updateUser(this.adminForm).then((res) => {
              if (res && res.code === 20000) {
                this.$message.success('修改管理员信息成功');
                this.adminAddDialogVisible = false
                this.loadAdmins()
              }
            })
          }
        }
      })
    },
    // 分页大小改变
    handleSizeChange(val) {
      this.pagination.pageSize = val
      this.loadAdmins()
    },
    handleCurrentChange(val) {
      this.pagination.currentPage = val;
      this.loadAdmins()
    },
    // 多条件查询管理员信息
    handleAdminSearch() {
      searchUsers(this.pagination.currentPage, this.pagination.pageSize, this.searchAdminVO).then((res) => {
        if (res && res.code === 20000) {
          this.admins = res.data.page.records
          this.pagination.total = res.data.page.total
        }
      })
    },
    // 清除搜索表单信息
    resetForm() {
      this.loadAdmins()
      this.$nextTick(() => {
        if (this.$refs['searchAdminForm'] !== undefined) {
          this.$refs['searchAdminForm'].resetFields()
        }
      })
    },
    resetAdminForm() {
      this.adminForm = {}
      this.$nextTick(() => {
        if (this.$refs['adminForm'] !== undefined) {
          this.$refs['adminForm'].resetFields()
        }
      })
    }
  }
}
</script>

<style>

</style>
