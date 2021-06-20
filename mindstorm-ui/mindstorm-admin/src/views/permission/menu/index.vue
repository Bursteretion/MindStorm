<template>
  <div class="app-container">
    <div v-hasPermission="['permission:menu:query']">
      <el-form ref="searchMenuForm" :inline="true" :model="searchMenuVO">
        <el-form-item label="菜单名称" prop="name">
          <el-input size="small" v-model="searchMenuVO.name" placeholder="菜单名称"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select size="small" v-model="searchMenuVO.status" placeholder="请选择">
            <el-option
              v-for="item in MenuStatus"
              :key="item.value"
              :label="item.name"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="btn-item">
          <el-button icon="el-icon-search" size="small" type="primary" @click="menuSearchSubmit">查询</el-button>
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
        ref="adminToolBar"
        :refresh="{query: listMenus}">
        <template #buttons>
          <vxe-button
            size="small"
            status="primary"
            icon="el-icon-circle-plus-outline"
            v-hasPermission="['permission:menu:add']"
            @click="openDialogMenuAdd">
            添加
          </vxe-button>
        </template>
      </vxe-toolbar>
      <vxe-table
        ref="menuTable"
        round
        show-overflow
        row-id="id"
        size="small"
        :tree-config="{children: 'children'}"
        :loading="loading"
        :print-config="{}"
        :data="menus">
        <vxe-table-column type="checkbox" width="60"></vxe-table-column>
        <vxe-table-column field="name" title="菜单名称" width="15%" tree-node></vxe-table-column>
        <vxe-table-column field="icon" width="60" title="图标">
          <template #default="{ row }">
            <svg-icon :icon-class="row.icon"/>
          </template>
        </vxe-table-column>
        <vxe-table-column width="60" field="sort" title="排序"></vxe-table-column>
        <vxe-table-column field="permissionValue" title="权限标识"></vxe-table-column>
        <vxe-table-column width="15%" field="component" title="组件路径"></vxe-table-column>
        <vxe-table-column width="5%" field="status" title="状态">
          <template #default="{ row }">
            <el-tag v-if="row.status === 1" size="mini">正常</el-tag>
            <el-tag v-if="row.status === 0" type="danger" size="mini">禁用</el-tag>
          </template>
        </vxe-table-column>
        <vxe-table-column width="5%" field="type" title="类型">
          <template #default="{ row }">
            <el-tag effect="plain" v-if="row.type === 0" size="mini">目录</el-tag>
            <el-tag effect="plain" v-if="row.type === 1" size="mini" type="info">菜单</el-tag>
            <el-tag effect="plain" v-if="row.type === 2" size="mini" type="warning">按钮</el-tag>
          </template>
        </vxe-table-column>
        <vxe-table-column field="gmtCreate" title="创建时间"></vxe-table-column>
        <vxe-table-column width="15%" title="操作">
          <template #default="{ row }">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              v-hasPermission="['permission:menu:update']"
              @click="handleEdit(row.id)">修改
            </el-button>
            <el-button
              v-if="row.type !== 2"
              size="mini"
              type="text"
              icon="el-icon-plus"
              v-hasPermission="['permission:menu:add']"
              @click="handleAddMenu(row.id)">新增
            </el-button>
            <el-popconfirm
              style="margin-left: 13px"
              confirm-button-text='确认'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="你确定要删除这个菜单吗？"
              v-hasPermission="['permission:menu:delete']"
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
    </div>

    <el-dialog
      :title="dialogMenuTitle"
      :visible.sync="dialogMenuVisible"
      append-to-body
      @close="resetMenuForm"
      width="700px"
      top="10vh">
      <el-form ref="menuFormSubmit" :rules="rules" :model="menuForm" label-width="80px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级菜单">
              <treeselect
                v-model="menuForm.pid"
                :options="fatherMenus"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="选择上级菜单"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="菜单类型">
              <el-radio border size="medium" v-model.number="menuForm.type" :label="0">目录</el-radio>
              <el-radio border size="medium" v-model.number="menuForm.type" :label="1">菜单</el-radio>
              <el-radio border size="medium" v-model.number="menuForm.type" :label="2">按钮</el-radio>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item v-if="menuForm.type !== '2'" label="菜单图标" label-width="80px">
              <el-popover
                placement="bottom-start"
                width="460"
                trigger="click"
                @show="$refs['iconSelect'].reset()"
              >
                <IconSelect ref="iconSelect" @selected="selected"/>
                <el-input slot="reference" v-model="menuForm.icon" placeholder="点击选择图标" readonly>
                  <svg-icon
                    v-if="menuForm.icon"
                    slot="prefix"
                    :icon-class="menuForm.icon"
                    class="el-input__icon"
                    style="height: 32px;width: 16px;"
                  />
                  <em v-else slot="prefix" class="el-icon-search el-input__icon"/>
                </el-input>
              </el-popover>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="name">
              <el-input v-model="menuForm.name" placeholder="请输入菜单名称"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="menuForm.type === 0 || menuForm.type === 1" label="菜单别名">
              <el-input v-model="menuForm.alias" placeholder="请输入菜单别名"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="sort">
              <el-input-number v-model="menuForm.sort" controls-position="right" :min="0"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="menuForm.type !== 2" label="路由地址" prop="path">
              <el-input v-model="menuForm.path" placeholder="请输入路由地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="menuForm.type !== 2">
            <el-form-item label="组件路径">
              <el-input v-model="menuForm.component" placeholder="请输入组件路径"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="权限标识">
              <el-input v-model="menuForm.permissionValue" placeholder="请输入权限标识" maxlength="50"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="menuForm.type === 0 || menuForm.type === 1" label="重定向">
              <el-input v-model="menuForm.redirect" placeholder="请输入菜单重定向地址" maxlength="50"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="menuForm.type !== '2'" label="菜单状态">
              <el-radio-group v-model="menuForm.status">
                <el-radio
                  v-for="dict in MenuStatus"
                  :key="dict.value"
                  v-model="menuForm.status"
                  :label="dict.value"
                >{{ dict.name }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="handleDialogMenuCancel">取 消</el-button>
        <el-button type="primary" @click="handleDialogMenuSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  MenuStatus,
  MenuType,
  listMenus,
  listMenusByType,
  insertMenu,
  updateMenu,
  getMenuById,
  deleteBeId,
  searchMenus
} from '@/api/menu'

import IconSelect from '@/components/IconSelect'
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "MenuList",
  components: {
    IconSelect,
    Treeselect
  },
  data() {
    return {
      loading: false,
      // 菜单搜索表单
      searchMenuVO: {
        name: '',
        status: ''
      },
      menus: [],
      // 上级菜单
      fatherMenus: [],
      // 菜单状态
      MenuStatus,
      dialogMenuTitle: '添加菜单',
      dialogMenuVisible: false,
      // 表单参数
      menuForm: {},
      // 表单校验
      rules: {
        name: [
          {required: true, message: "菜单名称不能为空", trigger: "blur"}
        ],
        sort: [
          {required: true, message: "菜单排序不能为空", trigger: "blur"}
        ],
        path: [
          {required: true, message: "路由地址不能为空", trigger: "blur"}
        ]
      }
    }
  },
  created() {
    this.listMenus()
  },
  methods: {
    listMenus() {
      listMenus().then((res) => {
        if (res && res.code === 20000) {
          this.menus = res.data.menus
        }
      })
    },
    listFatherMenus() {
      const types = [MenuType.catalog, MenuType.menu]
      listMenusByType(types).then((res) => {
        if (res && res.code === 20000) {
          this.fatherMenus = []
          const menu = {id: '', name: '主类目', children: []};
          let menus = res.data.treeMenus
          menu.children = menus
          this.convertFatherMenu(menus)
          this.fatherMenus.push(menu)
        }
      })
    },
    convertFatherMenu(menus) {
      if (menus.length <= 0) return
      menus.forEach(v => {
        v.value = v.id
        v.label = v.name
        if (v.children.length <= 0) {
          v.children = undefined
        } else {
          this.convertFatherMenu(v.children)
        }
      })
    },
    normalizer(node) {
      return {
        id: node.id,
        label: node.name,
        children: node.children,
      }
    },
    // 选择图标
    selected(name) {
      this.menuForm.icon = name;
    },
    openDialogMenuAdd() {
      this.listFatherMenus()
      this.dialogMenuTitle = '添加菜单'
      this.dialogMenuVisible = true
    },
    menuSearchSubmit() {
      searchMenus(this.searchMenuVO).then((res) => {
        if (res && res.code === 20000) {
          this.menus = res.data.searchMenus
        }
      })
    },
    handleDialogMenuCancel() {
      this.resetMenuForm()
      this.dialogMenuVisible = false
    },
    handleDialogMenuSubmit() {
      this.$refs['menuFormSubmit'].validate((valid) => {
        if (valid) {
          if (this.dialogMenuTitle === '添加菜单') {
            insertMenu(this.menuForm).then((res) => {
              if (res && res.code === 20000) {
                this.$XModal.message({
                  content: '菜单添加成功!',
                  status: 'success'
                })
                this.listFatherMenus()
                this.listMenus()
              }
            })
          } else if (this.dialogMenuTitle === '修改菜单') {
            updateMenu(this.menuForm).then((res) => {
              if (res && res.code === 20000) {
                this.$XModal.message({
                  content: '菜单信息修改成功!',
                  status: 'success'
                })
                this.listFatherMenus()
                this.listMenus()
              }
            })
          }
        } else {
          return false
        }
      })
      this.resetMenuForm()
      this.dialogMenuVisible = false
    },
    resetForm() {
      this.$refs['searchMenuForm'].resetFields()
      this.listMenus()
    },
    resetMenuForm() {
      this.menuForm = {
        id: '',
        pid: '',
        type: 0,
        name: '',
        alias: '',
        icon: '',
        path: '',
        redirect: '',
        component: '',
        permissionValue: '',
        status: 1,
        sort: undefined
      }
      this.resetValid()
    },
    resetValid() {
      this.$nextTick(() => {
        if (this.$refs['menuFormSubmit'] !== undefined) {
          this.$refs['menuFormSubmit'].resetFields()
        }
      })
    },
    handleEdit(menuId) {
      this.listFatherMenus()
      getMenuById(menuId).then((res) => {
        if (res && res.code === 20000) {
          this.dialogMenuTitle = '修改菜单'
          this.dialogMenuVisible = true
          this.resetMenuForm()
          this.$nextTick(() => {
            this.menuForm = res.data.menu
          })
        }
      })
    },
    handleAddMenu(menuId) {
      this.resetMenuForm()
      this.menuForm.pid = menuId
      this.openDialogMenuAdd()
    },
    handleDelete(menuId) {
      deleteBeId(menuId).then((res) => {
        if (res && res.code === 20000) {
          this.$XModal.message({
            content: '菜单删除成功!',
            status: 'success'
          })
          this.listFatherMenus()
          this.listMenus()
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
</style>
