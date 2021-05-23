<template>
  <div class="app-container">
    <div>
      <el-form ref="searchTeacherForm" :inline="true" :model="searchTeacherVO">
        <el-form-item label="用户名" prop="username">
          <el-input size="small" v-model="searchTeacherVO.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input size="small" v-model="searchTeacherVO.realName" placeholder="真实姓名"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select size="small" v-model="searchTeacherVO.status" placeholder="请选择">
            <el-option
              v-for="item in UserStatus"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="btn-item">
          <el-button icon="el-icon-search" size="small" type="primary" @click="handleTeacherSearch">查询</el-button>
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
        @click="openTeacherAddDialog">
        添加
      </el-button>
    </div>

    <div style="margin-top: 15px">
      <el-table
        :data="teachers"
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
          label="状态">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="0"
              :inactive-value="1"
              @click.native="changeTeacherStatus(scope.row)"
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
              @click="handleTeacherEdit(scope.row.id)">
              修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleTeacherAddRole(scope.row.id)">
              分配角色
            </el-button>
            <el-button
              slot="reference"
              size="mini"
              icon="el-icon-delete"
              type="text"
              @click="handleTeacherDelete(scope.row)">
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
        :title="teacherDialogTitle"
        :visible.sync="teacherAddDialogVisible"
        @close="resetTeacherForm"
        width="40%"
        top="10vh"
        append-to-body>
        <el-form ref="teacherForm" :rules="rules" :model="teacherForm" label-width="80px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="用户名" prop="username">
                <el-input v-model="teacherForm.username" autocomplete="off" placeholder="请输入用户名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="真实姓名" prop="realName">
                <el-input v-model="teacherForm.realName" autocomplete="off" placeholder="请输入真实姓名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item v-if="teacherForm.id === undefined || teacherForm.id === ''" label="密码" prop="password">
                <el-input v-model="teacherForm.password" show-password placeholder="请输入密码"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="手机">
                <el-input v-model="teacherForm.phone" autocomplete="off" placeholder="请输入手机号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="年龄">
                <el-input v-model="teacherForm.age" autocomplete="off" placeholder="请输入年龄"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="出生日期">
                <el-date-picker
                  v-model="teacherForm.birthDay"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
                  type="date"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态" prop="status">
                <el-select v-model="teacherForm.status" placeholder="请选择状态">
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
                <el-radio v-model="teacherForm.sex" :label="1">男</el-radio>
                <el-radio v-model="teacherForm.sex" :label="2">女</el-radio>
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
  createTeacher,
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
  name: 'TeachersList',
  data() {
    return {
      // 用户状态
      UserStatus,
      // 搜索表单
      searchTeacherVO: {
        username: '',
        realName: '',
        status: '',
        userType: 1
      },
      // 学生列表
      teachers: [],
      // 分页信息
      pagination: {
        currentPage: 1,
        pageSize: 5,
        total: 0
      },
      // 对话框标题
      teacherDialogTitle: '添加学生',
      // 对话框是否显示
      teacherAddDialogVisible: false,
      // 分配角色对话框是否显示
      addUserRoleDialogVisible: false,
      // 对话框添加（修改）按钮标题
      dialogBtnTitle: '添加',
      // 学生表单
      teacherForm: {},
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
    this.loadTeachers()
  },
  computed: {},
  methods: {
    // 加载教师
    loadTeachers() {
      listUsers(this.pagination.currentPage, this.pagination.pageSize, 2).then((res) => {
        if (res && res.code === 20000) {
          this.teachers = res.data.page.records
          this.pagination.total = res.data.page.total
        }
      })
    },
    // 点击添加按钮
    openTeacherAddDialog() {
      this.teacherDialogTitle = '添加教师'
      this.teacherAddDialogVisible = true
    },
    // 更改学生状态
    changeTeacherStatus(teacher) {
      let text = teacher.status === 0 ? '禁用' : '启用'
      this.$confirm(`是否${text}此角色?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeUserStatus(teacher.id, teacher.status).then((res) => {
          if (res && res.code === 20000 && res.data.change) {
            this.$message({
              type: 'success',
              message: `${text}成功!`
            });
          }
        })
      }).catch(() => {
        this.loadTeachers()
      });
    },
    handleTeacherEdit(teacherId) {
      getUserById(teacherId).then((res) => {
        if (res && res.code === 20000) {
          this.teacherForm = res.data.user
          this.teacherDialogTitle = '修改教师信息'
          this.dialogBtnTitle = '修改'
          this.teacherAddDialogVisible = true
        }
      })
    },
    // 为教师分配角色
    handleTeacherAddRole(teacherId) {
      this.addUserRoleDialogVisible = true
      listUnDisableRoles().then((response) => {
        if (response && response.code === 20000) {
          this.roleOptions = response.data.roles
          this.$nextTick(() => {
            userRole(teacherId).then((res) => {
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
    handleTeacherDelete(teacher) {
      this.$confirm(`你确定要删除教师：${teacher.username}吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteUser(teacher.id).then((res) => {
          if (res && res.code === 20000) {
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.loadTeachers()
          }
        })
      })
    },
    handleCancel() {
      this.resetTeacherForm()
      this.teacherAddDialogVisible = false
    },
    // 添加/修改表单提交
    handleDialogSubmitBtn() {
      this.$refs['teacherForm'].validate((valid) => {
        if (valid) {
          // 添加学生
          if (this.teacherForm.id === undefined || this.teacherForm.id === '') {
            createTeacher(this.teacherForm).then((res) => {
              if (res && res.code === 20000) {
                this.$message.success("添加教师成功！")
                this.teacherAddDialogVisible = false
                this.loadTeachers()
              }
            })
          } else {
            updateUser(this.teacherForm).then((res) => {
              if (res && res.code === 20000) {
                this.$message.success('修改教师信息成功');
                this.teacherAddDialogVisible = false
                this.loadTeachers()
              }
            })
          }
        }
      })
    },
    // 分页大小改变
    handleSizeChange(val) {
      this.pagination.pageSize = val
      this.loadTeachers()
    },
    handleCurrentChange(val) {
      this.pagination.currentPage = val;
      this.loadTeachers()
    },
    // 多条件查询教师信息
    handleTeacherSearch() {
      searchUsers(this.pagination.currentPage, this.pagination.pageSize, this.searchTeacherVO).then((res) => {
        if (res && res.code === 20000) {
          this.teachers = res.data.page.records
          this.pagination.total = res.data.page.total
        }
      })
    },
    // 清除搜索表单信息
    resetForm() {
      this.loadTeachers()
      this.$nextTick(() => {
        if (this.$refs['searchTeacherForm'] !== undefined) {
          this.$refs['searchTeacherForm'].resetFields()
        }
      })
    },
    resetTeacherForm() {
      this.teacherForm = {}
      this.$nextTick(() => {
        if (this.$refs['teacherForm'] !== undefined) {
          this.$refs['teacherForm'].resetFields()
        }
      })
    }
  }
}
</script>

<style>

</style>
