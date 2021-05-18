<template>
  <div class="app-container">
    <div>
      <el-form ref="searchMenuForm" :inline="true" :model="searchMenuVO">
        <el-form-item label="菜单名称" prop="name">
          <el-input size="small" v-model="searchMenuVO.name" placeholder="菜单名称"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select style="width: 100px" size="small" v-model="searchMenuVO.status" placeholder="请选择">
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
              <el-input v-model="menuForm.pid" autocomplete="off"></el-input>
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
  MenuStatus
} from '@/api/menu'

import IconSelect from '@/components/IconSelect'

export default {
  name: "MenuList",
  components: {
    IconSelect
  },
  data() {
    return {
      searchMenuVO: {
        name: '',
        status: ''
      },
      MenuStatus,
      dialogMenuTitle: '添加菜单',
      dialogMenuVisible: false,
      // 表单参数
      menuForm: {
        pid: '',
        type: '1',
        icon: '',
        path: '',
        component: '',
        permissionValue: '',
        status: 1
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
  methods: {
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
      this.dialogMenuVisible = true
      this.resetForm()
    },
    resetForm() {
      this.$refs['searchMenuForm'].resetFields()

    },
    selectIcons() {
      this.$message.info("ssss")
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
    }
  }
}
</script>

<style lang="scss" scoped>
.el-scrollbar__wrap {
  overflow: scroll;
  width: 110%;
  height: 100%;
}

</style>
