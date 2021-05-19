<template>
  <div class="app-container">
    <div>
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
      <el-button
        plain
        icon="el-icon-circle-plus-outline"
        size="mini"
        type="primary"
        @click="openDialogMenuAdd">
        添加
      </el-button>
    </div>

    <div style="margin-top: 20px">
      <el-table
        :data="menus"
        row-key="id"
        :tree-props="{children: 'children', hasChildren: 'hasChildren'}">
        <el-table-column
          prop="name"
          label="菜单名称"
          :show-overflow-tooltip="true"
          width="120">
        </el-table-column>
        <el-table-column
          prop="icon"
          label="图标"
          align="center" width="100">
          <template slot-scope="scope">
            <svg-icon :icon-class="scope.row.icon"/>
          </template>
        </el-table-column>
        <el-table-column
          prop="sort"
          label="排序"
          width="60">
        </el-table-column>
        <el-table-column
          prop="permissionValue"
          :show-overflow-tooltip="true"
          label="权限标识">
        </el-table-column>
        <el-table-column
          prop="component"
          :show-overflow-tooltip="true"
          label="组件路径">
        </el-table-column>
        <el-table-column
          prop="status"
          label="状态">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.status === 1" size="medium">正常</el-tag>
            <el-tag v-if="scope.row.status === 0" type="danger" size="medium">禁用</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          prop="gmtCreate"
          label="创建时间">
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
              icon="el-icon-plus"
              @click="handleAddMenu(scope.row)">新增
            </el-button>
            <el-popconfirm
              style="margin-left: 13px"
              confirm-button-text='确认'
              cancel-button-text='取消'
              icon="el-icon-info"
              icon-color="red"
              title="你确定要删除这个菜单吗？"
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

    <el-dialog
      :title="dialogMenuTitle"
      :visible.sync="dialogMenuVisible"
      @closed="resetMenuForm"
      width="600px"
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
              <el-radio border size="medium" v-model="menuForm.type" label="1">菜单</el-radio>
              <el-radio border size="medium" v-model="menuForm.type" label="2">按钮</el-radio>
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
                  <i v-else slot="prefix" class="el-icon-search el-input__icon"/>
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
            <el-form-item label="显示排序" prop="sort">
              <el-input-number v-model="menuForm.sort" controls-position="right" :min="0"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="menuForm.type === '1'" label="路由地址" prop="path">
              <el-input v-model="menuForm.path" placeholder="请输入路由地址"/>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="menuForm.type !== '2'">
            <el-form-item label="组件路径" prop="component">
              <el-input v-model="menuForm.component" placeholder="请输入组件路径"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="权限标识">
              <el-input v-model="menuForm.permissionValue" placeholder="请输入权限标识" maxlength="50"/>
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
  insertMenu
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
      menuForm: {
        pid: '',
        name: '',
        type: '1',
        icon: '',
        path: '',
        component: '',
        permissionValue: '',
        status: 1,
        sort: 0
      },
      // 表单校验
      rules: {
        name: [
          {required: true, message: "菜单名称不能为空", trigger: "blur"}
        ],
        sort: [
          {required: true, message: "菜单顺序不能为空", trigger: "blur"}
        ],
        path: [
          {required: true, message: "路由地址不能为空", trigger: "blur"}
        ]
      }
    }
  },
  created() {
    this.listFatherMenus()
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
      listMenusByType(MenuType.menu).then((res) => {
        if (res && res.code === 20000) {
          this.fatherMenus = []
          const menu = { id: '', name: '主类目', children: [] };
          let menus = res.data.menus
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
      this.dialogMenuTitle = '添加菜单'
      this.dialogMenuVisible = true
    },
    menuSearchSubmit() {
    },
    handleDialogMenuCancel() {
      this.dialogMenuVisible = false
      this.resetForm()
    },
    handleDialogMenuSubmit() {
      this.$refs['menuFormSubmit'].validate((valid) => {
        if (valid) {
          insertMenu(this.menuForm).then((res) => {
            if (res && res.code === 20000) {
              this.$message.success("菜单添加成功！")
              this.listFatherMenus()
              this.listMenus()
            }
          })
        }
      })
      this.dialogMenuVisible = false
      this.resetForm()
    },
    resetForm() {
      this.$refs['searchMenuForm'].resetFields()

    },
    resetMenuForm() {
      this.menuForm = {
        pid: '',
        type: '1',
        icon: '',
        path: '',
        component: '',
        permissionValue: '',
        status: 1
      }
      this.$refs['menuFormSubmit'].resetFields()
    },
    handleEdit() {

    },
    handleAddMenu() {

    },
    handleDelete() {

    }
  }
}
</script>

<style lang="scss" scoped>
</style>
