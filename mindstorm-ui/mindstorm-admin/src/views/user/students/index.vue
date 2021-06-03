<template>
  <div class="app-container">
    <div v-hasPermission="['user:student:query']">
      <el-form ref="searchStudentForm" :inline="true" :model="searchStudentVO">
        <el-form-item label="用户名" prop="username">
          <el-input size="small" v-model="searchStudentVO.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input size="small" v-model="searchStudentVO.realName" placeholder="真实姓名"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select size="small" v-model="searchStudentVO.status" placeholder="请选择">
            <el-option
              v-for="item in UserStatus"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="btn-item">
          <el-button icon="el-icon-search" size="small" type="primary" @click="handleStudentSearch">查询</el-button>
        </el-form-item>
        <el-form-item class="btn-item">
          <el-button icon="el-icon-refresh" size="small" type="default" @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <div>
      <vxe-toolbar
        custom
        print
        ref="studentToolBar"
        :refresh="{query: loadStudents}">
        <template #buttons>
          <vxe-button
            size="small"
            status="primary"
            icon="el-icon-circle-plus-outline"
            v-hasPermission="['user:student:add']"
            @click="openStudentAddDialog">
            添加
          </vxe-button>
        </template>
      </vxe-toolbar>
      <vxe-table
        ref="studentTable"
        round
        show-overflow
        height="200"
        row-id="id"
        size="small"
        :loading="loading"
        :print-config="{}"
        :data="students">
        <vxe-table-column type="checkbox" width="60"></vxe-table-column>
        <vxe-table-column type="seq" title="序号" width="60"></vxe-table-column>
        <vxe-table-column field="username" title="用户名" width="100"></vxe-table-column>
        <vxe-table-column field="realName" width="120" title="真实姓名"></vxe-table-column>
        <vxe-table-column field="userLevel" title="年级">
          <template #default="{ row }">
            <span>{{ getLevel(row.userLevel) }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column field="sex" title="性别">
          <template #default="{ row }">
            <el-tag v-if="row.sex === 1" size="mini" type="success">男</el-tag>
            <el-tag v-if="row.sex === 2" size="mini" type="info">女</el-tag>
          </template>
        </vxe-table-column>
        <vxe-table-column width="13%" field="phone" title="手机号"></vxe-table-column>
        <vxe-table-column width="13%" field="email" title="邮箱"></vxe-table-column>
        <vxe-table-column
          v-hasPermission="['user:student:status']"
          field="status" width="100" title="状态">
          <template #default="{ row }">
            <vxe-switch
              v-model="row.status"
              :open-value="1"
              :close-value="0"
              @click.native="changeStudentStatus(row)">
            </vxe-switch>
          </template>
        </vxe-table-column>
        <vxe-table-column width="15%" field="gmtCreate" title="创建时间"></vxe-table-column>
        <vxe-table-column width="15%">
          <template #default="{ row }" title="操作">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              v-hasPermission="['user:student:update']"
              @click="handleStudentEdit(row.id)">修改
            </el-button>
            <el-button
              size="mini"
              icon="el-icon-delete"
              type="text"
              v-hasPermission="['user:student:delete']"
              @click="handleStudentDelete(row)">
              删除
            </el-button>
          </template>
        </vxe-table-column>
      </vxe-table>

      <vxe-pager
        background
        size="small"
        :loading="loading"
        :current-page="pagination.currentPage"
        :page-size="pagination.pageSize"
        :total="pagination.total"
        :page-sizes="[2, 5, 10, {label: '大量数据', value: 100}, {label: '全量数据', value: -1}]"
        :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
        @page-change="handlePageChange">
      </vxe-pager>
    </div>

    <div>
      <el-dialog
        :title="studentDialogTitle"
        :visible.sync="studentAddDialogVisible"
        @close="resetStudentForm"
        destroy-on-close
        width="40%"
        top="10vh"
        append-to-body>
        <el-form ref="studentForm" :rules="rules" :model="studentForm" label-width="80px">
          <el-row>
            <el-col :span="12">
              <el-form-item label="用户名" prop="username">
                <el-input v-model="studentForm.username" autocomplete="off" placeholder="请输入用户名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="真实姓名" prop="realName">
                <el-input v-model="studentForm.realName" autocomplete="off" placeholder="请输入真实姓名"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="学号" prop="sno">
                <el-input v-model="studentForm.sno" autocomplete="off" placeholder="请输入学号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item v-if="studentForm.id === undefined || studentForm.id === ''" label="密码" prop="password">
                <el-input v-model="studentForm.password" show-password placeholder="请输入密码"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="手机">
                <el-input v-model="studentForm.phone" autocomplete="off" placeholder="请输入手机号"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="邮箱">
                <el-input v-model="studentForm.email" autocomplete="off" placeholder="请输入邮箱"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="年龄">
                <el-input v-model="studentForm.age" autocomplete="off" placeholder="请输入年龄"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="年级" prop="userLevel">
                <el-select v-model="studentForm.userLevel" placeholder="请选择年级">
                  <el-option
                    v-for="item in UserStudentLevels"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="出生日期">
                <el-date-picker
                  v-model="studentForm.birthDay"
                  format="yyyy 年 MM 月 dd 日"
                  value-format="yyyy-MM-dd"
                  type="date"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="状态" prop="status">
                <el-select v-model="studentForm.status" placeholder="请选择状态">
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
                <el-radio v-model="studentForm.sex" :label="1">男</el-radio>
                <el-radio v-model="studentForm.sex" :label="2">女</el-radio>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="handleCancel">取 消</el-button>
          <el-button type="primary" @click="handleDialogSubmitBtn">{{ dialogBtnTitle }}</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>

import {
  UserStatus,
  UserStudentLevels,
  listUsers,
  createStudent,
  changeUserStatus,
  getUserById,
  updateUser,
  searchUsers,
  deleteUser
} from '@/api/user'

export default {
  name: 'StudentsList',
  data() {
    return {
      loading: false,
      // 用户状态
      UserStatus,
      // 学生年级
      UserStudentLevels,
      // 搜索表单
      searchStudentVO: {
        username: '',
        realName: '',
        status: '',
        userType: 1
      },
      // 学生列表
      students: [],
      // 分页信息
      pagination: {
        currentPage: 1,
        pageSize: 5,
        total: 0
      },
      // 对话框标题
      studentDialogTitle: '添加学生',
      // 对话框是否显示
      studentAddDialogVisible: false,
      // 对话框添加（修改）按钮标题
      dialogBtnTitle: '添加',
      // 学生表单
      studentForm: {
        id: '',
        username: '',
        realName: '',
        sno: '',
        password: '',
        phone: '',
        email: '',
        age: 0,
        userLevel: 1,
        birthDay: '',
        status: 1,
        sex: ''
      },
      rules: {
        username: [
          {required: true, message: '请输入用户名', trigger: 'blur'}
        ],
        realName: [
          {required: true, message: '请输入真实姓名', trigger: 'blur'}
        ],
        sno: [
          {required: true, message: '请输入学号', trigger: 'blur'}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        userLevel: [
          {required: true, message: '请选择年级', trigger: 'change'}
        ],
        status: [
          {required: true, message: '请选择状态', trigger: 'change'}
        ]
      }
    }
  },
  created() {
    this.$nextTick(() => {
      this.$refs.studentTable.connect(this.$refs.studentToolBar)
    })
    this.loadStudents()
  },
  methods: {
    getLevel(userLevel) {
      const level = userLevel
      return UserStudentLevels.find(v => v.value === level).label
    },
    // 加载学生
    loadStudents() {
      this.loading = true
      setTimeout(() => {
        listUsers(this.pagination.currentPage, this.pagination.pageSize, 1).then((res) => {
          if (res && res.code === 20000) {
            this.students = res.data.page.records
            this.pagination.total = res.data.page.total
            this.loading = false
          }
        })
      }, 300)
    },
    // 点击添加按钮
    openStudentAddDialog() {
      this.studentDialogTitle = '添加学生'
      this.dialogBtnTitle = '添加'
      this.studentAddDialogVisible = true
    },
    // 更改学生状态
    changeStudentStatus(student) {
      let text = student.status === 0 ? '禁用' : '启用'
      this.$confirm(`是否${text}此角色?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeUserStatus(student.id, student.status).then((res) => {
          if (res && res.code === 20000 && res.data.change) {
            this.$XModal.message({
              status: 'success',
              content: `${text}成功!`
            });
          }
        })
      }).catch(() => {
        this.loadStudents()
      });
    },
    handleStudentEdit(studentId) {
      getUserById(studentId).then((res) => {
        if (res && res.code === 20000) {
          this.studentForm = res.data.user
          this.studentDialogTitle = '修改学生信息'
          this.dialogBtnTitle = '修改'
          this.studentAddDialogVisible = true
        }
      })
    },
    handleStudentDelete(student) {
      this.$confirm(`你确定要删除学生：${student.username}吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteUser(student.id).then((res) => {
          if (res && res.code === 20000) {
            this.$XModal.message({
              status: 'success',
              content: '删除成功!'
            })
            this.loadStudents()
          }
        })
      })
    },
    handleCancel() {
      this.resetStudentForm()
      this.studentAddDialogVisible = false
    },
    // 添加/修改表单提交
    handleDialogSubmitBtn() {
      this.$refs['studentForm'].validate((valid) => {
        if (valid) {
          // 添加学生
          if (this.studentForm.id === undefined || this.studentForm.id === '') {
            createStudent(this.studentForm).then((res) => {
              if (res && res.code === 20000) {
                this.$XModal.message({
                  status: 'success',
                  content: '添加学生成功！'
                })
                this.studentAddDialogVisible = false
                this.loadStudents()
              }
            })
          } else {
            updateUser(this.studentForm).then((res) => {
              if (res && res.code === 20000) {
                this.$XModal.message({
                  status: 'success',
                  content: '修改学生信息成功！'
                })
                this.studentAddDialogVisible = false
                this.loadStudents()
              }
            })
          }
        }
      })
    },
    // 分页大小改变
    handlePageChange({currentPage, pageSize}) {
      this.pagination.currentPage = currentPage
      this.pagination.pageSize = pageSize
      this.loadStudents()
    },
    // 多条件查询学生信息
    handleStudentSearch() {
      searchUsers(this.pagination.currentPage, this.pagination.pageSize, this.searchStudentVO).then((res) => {
        if (res && res.code === 20000) {
          this.students = res.data.page.records
          this.pagination.total = res.data.page.total
        }
      })
    },
    // 清除搜索表单信息
    resetForm() {
      this.loadStudents()
      this.$nextTick(() => {
        if (this.$refs['searchStudentForm'] !== undefined) {
          this.$refs['searchStudentForm'].resetFields()
        }
      })
    },
    resetStudentForm() {
      this.$nextTick(() => {
        if (this.$refs['studentForm'] !== undefined) {
          this.$refs['studentForm'].resetFields()
        }
      })
      this.studentForm = {
        id: '',
        username: '',
        realName: '',
        sno: '',
        password: '',
        phone: '',
        email: '',
        age: 0,
        userLevel: 1,
        birthDay: '',
        status: 1,
        sex: ''
      }
    }
  }
}
</script>

<style>

</style>
