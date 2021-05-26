<template>
  <div class="app-container">
    <div v-hasPermission="['system:student:query']">
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
      <el-button
        plain
        icon="el-icon-circle-plus-outline"
        size="mini"
        type="primary"
        v-hasPermission="['system:student:add']"
        @click="openStudentAddDialog">
        添加
      </el-button>
    </div>

    <div style="margin-top: 15px">
      <el-table
        :data="students"
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
          label="真实姓名">
        </el-table-column>
        <el-table-column
          prop="userLevel"
          label="年级">
          <template slot-scope="scope">
            <span>{{ getLevel(scope.row.userLevel) }}</span>
          </template>
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
          v-hasPermission="['system:student:status']"
          label="状态">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="0"
              :inactive-value="1"
              @click.native="changeStudentStatus(scope.row)"
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
              v-hasPermission="['system:student:update']"
              @click="handleStudentEdit(scope.row.id)">
              修改
            </el-button>
            <el-button
              slot="reference"
              size="mini"
              icon="el-icon-delete"
              type="text"
              v-hasPermission="['system:student:delete']"
              @click="handleStudentDelete(scope.row)">
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
        :title="studentDialogTitle"
        :visible.sync="studentAddDialogVisible"
        @close="resetStudentForm"
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
      studentForm: {},
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
    this.loadStudents()
  },
  computed: {},
  methods: {
    getLevel(userLevel) {
      const level = userLevel
      return UserStudentLevels.find(v => v.value === level).label
    },
    // 加载学生
    loadStudents() {
      listUsers(this.pagination.currentPage, this.pagination.pageSize, 1).then((res) => {
        if (res && res.code === 20000) {
          this.students = res.data.page.records
          this.pagination.total = res.data.page.total
        }
      })
    },
    // 点击添加按钮
    openStudentAddDialog() {
      this.studentDialogTitle = '添加学生'
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
            this.$message({
              type: 'success',
              message: `${text}成功!`
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
            this.$message({
              type: 'success',
              message: '删除成功!'
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
                this.$message.success("添加学生成功！")
                this.studentAddDialogVisible = false
                this.loadStudents()
              }
            })
          } else {
            updateUser(this.studentForm).then((res) => {
              if (res && res.code === 20000) {
                this.$message.success('修改学生信息成功');
                this.studentAddDialogVisible = false
                this.loadStudents()
              }
            })
          }
        }
      })
    },
    // 分页大小改变
    handleSizeChange(val) {
      this.pagination.pageSize = val
      this.loadStudents()
    },
    handleCurrentChange(val) {
      this.pagination.currentPage = val;
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
      this.studentForm = {}
      this.$nextTick(() => {
        if (this.$refs['studentForm'] !== undefined) {
          this.$refs['studentForm'].resetFields()
        }
      })
    }
  }
}
</script>

<style>

</style>
