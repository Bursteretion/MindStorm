<template>
  <div class="app-container">
    <div v-hasPermission="['permission:role:query']">
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
      <vxe-toolbar
        custom
        print
        ref="roleToolBar"
        :refresh="{query: loadRoles}">
        <template #buttons>
          <vxe-button
            size="small"
            status="primary"
            icon="el-icon-circle-plus-outline"
            v-hasPermission="['permission:role:add']"
            @click="openDialogRoleAdd">
            添加
          </vxe-button>
        </template>
      </vxe-toolbar>
      <vxe-table
        ref="roleTable"
        round
        show-overflow
        row-id="id"
        size="small"
        :loading="loading"
        :print-config="{}"
        :data="roles">
        <vxe-table-column type="checkbox" width="60"></vxe-table-column>
        <vxe-table-column type="seq" title="序号" width="60"></vxe-table-column>
        <vxe-table-column width="10%" field="roleName" title="名称"></vxe-table-column>
        <vxe-table-column width="20%" field="remark" title="备注"></vxe-table-column>
        <vxe-table-column width="10%" field="sort" title="排序"></vxe-table-column>
        <vxe-table-column
          v-hasPermission="['permission:role:status']"
          width="10%"
          field="status" title="状态">
          <template #default="{ row }">
            <vxe-switch
              v-model="row.status"
              :open-value="1"
              :close-value="0"
              @click.native="changeRoleStatus(row)">
            </vxe-switch>
          </template>
        </vxe-table-column>
        <vxe-table-column width="20%" field="gmtCreate" title="创建时间"></vxe-table-column>
        <vxe-table-column width="20%" title="操作">
          <template #default="{ row }">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              v-hasPermission="['permission:role:update']"
              @click="handleEdit(row)">修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              v-hasPermission="['permission:role:distribute']"
              @click="handleSetPermission(row)">设置权限
            </el-button>
            <el-popconfirm
              class="delete_btn"
              confirm-button-text='确认'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="你确定要删除这个角色吗？"
              v-hasPermission="['permission:role:delete']"
              @onConfirm="handleDelete(row.id)"
            >
              <el-button
                slot="reference"
                size="mini"
                icon="el-icon-delete"
                type="text">删除
              </el-button>
            </el-popconfirm>
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

      <!-- 分配角色菜单权限对话框 -->
      <el-dialog title="为角色分配菜单"
                 :visible.sync="addRoleMenuDialogVisible"
                 width="500px"
                 append-to-body>
        <el-form :model="roleMenuForm" label-width="80px">
          <el-form-item label="角色名称">
            <el-input v-model="roleMenuForm.roleName" :disabled="true"/>
          </el-form-item>
          <el-form-item label="角色备注">
            <el-input v-model="roleMenuForm.remark" :disabled="true"/>
          </el-form-item>
          <el-form-item label="所有菜单">
            <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand($event)">展开/折叠</el-checkbox>
            <el-checkbox v-model="menuNodeAll" @change="handleCheckedTreeNodeAll($event)">全选/全不选</el-checkbox>
            <el-checkbox v-model="menuCheckStrictly" @change="handleCheckedTreeConnect($event)">父子联动
            </el-checkbox>
            <el-tree
              class="tree-border"
              :data="menus"
              show-checkbox
              ref="menu"
              node-key="id"
              :check-strictly="!menuCheckStrictly"
              empty-text="加载中，请稍后"
              :props="defaultProps"
            ></el-tree>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="handleAddRoleMenu">确 定</el-button>
          <el-button @click="handleCancelAddRoleMenu">取 消</el-button>
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

import {
  listMenus
} from '@/api/menu'

import {
  roleMenu,
  distributeMenu
} from '@/api/roleMenu'

export default {
  name: "RoleList",
  data() {
    return {
      loading: false,
      searchRoleVO: {
        name: '',
        status: ''
      },
      RoleStatus,
      roles: [],
      menus: [],
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
      addRoleMenuDialogVisible: false,
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
      },
      roleMenuForm: {
        roleId: '',
        roleName: '',
        remark: '',
        menus: []
      },
      defaultProps: {
        children: "children",
        label: "name"
      },
      menuExpand: false,
      menuNodeAll: false,
      menuCheckStrictly: true
    }
  },
  created() {
    this.$nextTick(() => {
      this.$refs.roleTable.connect(this.$refs.roleToolBar)
    })
    this.loadRoles()
  },
  methods: {
    loadRoles() {
      this.loading = true
      setTimeout(() => {
        pageByRoles(this.pagination.currentPage, this.pagination.pageSize).then((res) => {
          if (res && res.code === 20000) {
            const {pageRoles} = res.data
            this.roles = pageRoles.records
            this.pagination.total = pageRoles.total
            this.loading = false
          }
        })
      }, 300)
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
            this.$XModal.message({
              status: 'success',
              content: `${text}成功!`
            });
          }
        })
      }).catch(() => {
        this.loadRoles()
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
          this.$XModal.message({
            content: '删除成功！',
            status: 'success'
          })
        }
      })
    },
    handleSetPermission(role) {
      this.addRoleMenuDialogVisible = true
      listMenus().then((response) => {
        if (response && response.code === 20000) {
          this.menus = response.data.menus
          this.$nextTick(() => {
            roleMenu(role.id).then((res) => {
              if (res && res.code === 20000) {
                const {roleId, roleName, remark, checkedKeys} = res.data.menus
                this.roleMenuForm.roleId = roleId
                this.roleMenuForm.roleName = roleName
                this.roleMenuForm.remark = remark
                checkedKeys.forEach(v => {
                  this.$nextTick(() => {
                    this.$refs.menu.setChecked(v, true, false);
                  })
                })
              }
            })
          })
        }
      })
    },
    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value) {
      let treeList = this.menus;
      treeList.forEach(v => {
        this.$refs.menu.store.nodesMap[v.id].expanded = value
      })
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value) {
      this.$refs.menu.setCheckedNodes(value ? this.menus : [])
    },
    // 树权限（父子联动）
    handleCheckedTreeConnect(value) {
      this.form.menuCheckStrictly = !!value
    },
    handleAddRoleMenu() {
      const menus = [...this.$refs.menu.getCheckedKeys(), ...this.$refs.menu.getHalfCheckedKeys()]
      distributeMenu(this.roleMenuForm.roleId, menus).then((res) => {
        if (res && res.code === 20000) {
          this.$XModal.message({
            content: '为' + this.roleMenuForm.roleName + '分配菜单成功！',
            status: 'success'
          })
          this.handleCancelAddRoleMenu()
        }
      })
    },
    handleCancelAddRoleMenu() {
      this.addRoleMenuDialogVisible = false
      this.menuExpand = false
      this.menuNodeAll = false
      this.menuCheckStrictly = true
    },
    handleQuit() {
      this.$refs['diaRoleForm'].resetFields()
      this.addRoleDialogVisible = false
    },
    resetForm() {
      this.reset()
      this.loadRoles()
    },
    handlePageChange({currentPage, pageSize}) {
      this.pagination.currentPage = currentPage
      this.pagination.pageSize = pageSize
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
                this.$XModal.message({
                  content: '添加角色成功!',
                  status: 'success'
                });
              }
            })
          } else if (this.dialogTitle === '编辑角色') {
            updateRole(this.addRoleForm).then((res) => {
              if (res && res.code === 20000) {
                this.addRoleDialogVisible = false;
                this.loadRoles()
                this.$XModal.message({
                  content: '角色信息修改成功!',
                  status: 'success'
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
