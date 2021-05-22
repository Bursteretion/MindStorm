<template>
  <div class="app-container">
    <div>
      <el-form ref="roleForm" :inline="true" :model="searchRoleVO">
        <el-form-item label="名称" prop="name">
          <el-input size="small" v-model="searchRoleVO.name" placeholder="名称"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select size="small" v-model="searchRoleVO.status" placeholder="请选择">
            <el-option
              v-for="item in RoleStatus"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="btn-item">
          <el-button icon="el-icon-search" size="small" type="primary" @click="roleSearchSubmit">查询</el-button>
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
        @click="openDialogRoleAdd">
        添加
      </el-button>
    </div>

    <div style="margin-top: 15px">
      <el-table
        :data="roles"
        style="width: 100%">
        <el-table-column
          type="index"
          label="序号"
          width="50">
        </el-table-column>
        <el-table-column
          prop="roleName"
          label="名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="remark"
          label="备注">
        </el-table-column>
        <el-table-column
          prop="sort"
          label="排序">
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              :active-value="0"
              :inactive-value="1"
              @click.native="changeRoleStatus(scope.row)"
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
              @click="handleEdit(scope.row)">修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleSetPermission(scope.row)">设置权限
            </el-button>
            <el-popconfirm
              class="delete_btn"
              confirm-button-text='确认'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="你确定要删除这个角色吗？"
              @onConfirm="handleDelete(scope.row.id)"
            >
              <el-button
                slot="reference"
                size="mini"
                icon="el-icon-delete"
                type="text">删除
              </el-button>
            </el-popconfirm>
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
        :title="dialogTitle"
        :visible.sync="addRoleDialogVisible"
        width="30%"
        center>
        <el-form ref="diaRoleForm" :rules="rules" :model="addRoleForm">
          <el-form-item label="名称" label-width="70px" prop="roleName">
            <el-input v-model="addRoleForm.roleName" autocomplete="off"></el-input>
          </el-form-item>
          <el-form-item label="备注" label-width="70px" prop="remark">
            <el-input type="textarea" :autosize="{ minRows: 2, maxRows: 4}" v-model="addRoleForm.remark"></el-input>
          </el-form-item>
          <el-form-item label="排序" label-width="70px">
            <el-input-number v-model="addRoleForm.sort" :min="0"></el-input-number>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="handleQuit">取 消</el-button>
          <el-button type="primary" @click="handleRole">确 定</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  RoleStatus,
  pageByRoles,
  createRole,
  deleteRole,
  updateRole,
  changeRoleStatus,
  searchRolesPage
} from '@/api/role'

export default {
  name: "RoleList",
  data() {
    return {
      searchRoleVO: {
        name: '',
        status: ''
      },
      RoleStatus,
      roles: [],
      role: {
        id: '',
        status: true
      },
      pagination: {
        currentPage: 1,
        pageSize: 5,
        total: 0
      },
      addRoleDialogVisible: false,
      addRoleForm: {
        id: '',
        roleName: '',
        remark: '',
        sort: 0
      },
      dialogTitle: '添加角色',
      rules: {
        roleName: [
          {required: true, message: '请输入角色名称', trigger: 'blur'}
        ],
        remark: [
          {required: true, message: '请输入角色备注信息', trigger: 'blur'}
        ]
      }
    }
  },
  created() {
    this.loadRoles()
  },
  methods: {
    loadRoles() {
      pageByRoles(this.pagination.currentPage, this.pagination.pageSize).then((res) => {
        if (res && res.code === 20000) {
          const {pageRoles} = res.data
          this.roles = pageRoles.records
          this.pagination.total = pageRoles.total
        }
      })
    },
    roleSearchSubmit() {
      this.searchRoleVO.pageIndex = this.pagination.currentPage
      this.searchRoleVO.pageSize = this.pagination.pageSize
      searchRolesPage(this.searchRoleVO).then((res) => {
        if (res && res.code === 20000) {
          const {searchRoles} = res.data
          this.roles = searchRoles.records
          this.pagination.total = searchRoles.total
        }
      })
    },
    changeRoleStatus(role) {
      let text = role.status === 0 ? '禁用' : '启用'
      this.$confirm(`是否${text}此角色?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        changeRoleStatus(role.id, role.status).then((res) => {
          if (res && res.code === 20000 && res.data.change) {
            this.$message({
              type: 'success',
              message: `${text}成功!`
            });
          }
        })
      }).catch(() => {
        this.loadRoles()
        this.$message({
          type: 'info',
          message: '已取消更改'
        });
      });
    },
    handleEdit(role) {
      this.dialogTitle = '编辑角色'
      this.addRoleDialogVisible = true
      this.addRoleForm.id = role.id
      this.addRoleForm.roleName = role.roleName
      this.addRoleForm.remark = role.remark
      this.addRoleForm.sort = role.sort
    },
    openDialogRoleAdd() {
      this.dialogTitle = '添加角色'
      this.addRoleDialogVisible = true
      this.addRoleForm.id = ''
      this.addRoleForm.roleName = ''
      this.addRoleForm.remark = ''
      this.addRoleForm.sort = 0
    },
    handleDelete(roleId) {
      deleteRole(roleId).then((res) => {
        if (res && res.code === 20000 && res.data.delete) {
          this.loadRoles()
          this.$message({
            message: '删除成功！',
            type: 'success'
          })
        }
      })
    },
    handleSetPermission(roleId) {
      console.log(roleId)
    },
    handleQuit() {
      this.$refs['diaRoleForm'].resetFields()
      this.addRoleDialogVisible = false
    },
    resetForm() {
      this.reset()
      this.loadRoles()
    },
    handleSizeChange(val) {
      this.pagination.pageSize = val
      this.loadRoles()
    },
    handleCurrentChange(val) {
      this.pagination.currentPage = val;
      this.loadRoles()
    },
    handleRole() {
      this.$refs['diaRoleForm'].validate((valid) => {
        if (valid) {
          if (this.dialogTitle === '添加角色') {
            createRole(this.addRoleForm).then((res) => {
              if (res && res.code === 20000) {
                this.addRoleDialogVisible = false;
                this.loadRoles()
                this.$message({
                  message: '添加角色成功!',
                  type: 'success'
                });
              }
            })
          } else if (this.dialogTitle === '编辑角色') {
            updateRole(this.addRoleForm).then((res) => {
              if (res && res.code === 20000) {
                this.addRoleDialogVisible = false;
                this.loadRoles()
                this.$message({
                  message: '角色信息修改成功!',
                  type: 'success'
                });
              }
            })
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
      this.reset()
    },
    reset() {
      this.$refs['roleForm'].clearValidate()
      this.$refs['roleForm'].resetFields()
    }
  }
}
</script>

<style lang="scss" scoped>
.btn-item {
  margin-left: 10px;
}

.delete_btn {
  margin-left: 13px;
  margin-right: 10px;
}
</style>
