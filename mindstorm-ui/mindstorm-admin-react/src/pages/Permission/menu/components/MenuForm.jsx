import React, { useState } from 'react';
import { ModalForm } from "@ant-design/pro-form";
import { Form, message, TreeSelect, Radio, Input, InputNumber } from "antd";
import { insertMenu, listMenusByType, MenuType, updateMenu } from "@/services/menu";
import IconSelector from "@/components/IconSelector";
import { MenuOutlined } from "@ant-design/icons";
import * as Icons from "@ant-design/icons"
import '../index.less'

const convertFatherMenu = (menus = []) => {
  if (menus.length <= 0) return
  menus.forEach(v => {
    const menu = v
    menu.title = v.name
    menu.value = v.id
    if (menu.children.length <= 0) {
      menu.children = undefined
    } else {
      convertFatherMenu(menu.children)
    }
  })
}

const MenuForm = props => {
  const { menuFormSetting, handleMenuFormSetting, currentMenu, handleChangeCurrentMenu, tableActionRef } = props
  const [menuForm] = Form.useForm()
  const [fatherMenus, handleChangeFatherMenus] = useState([])

  const [iconSelectSetting, handleChangeIconSelectSetting] = useState({
    visible: false,
    iconName: currentMenu.icon,
    icon: <MenuOutlined/>
  })

  const handleMenuSubmit = async menuVO => {
    const menu = { ...menuVO, pid: menuVO.pid === '0' ? '' : menuVO.pid }
    const tip = currentMenu.id === undefined ? '添加' : '更新'
    const hide = message.loading(`正在${ tip }菜单【${ menuVO.name }】`);
    try {
      if (currentMenu.id === undefined) {
        await insertMenu(menu)
      } else {
        await updateMenu({ ...menu, id: currentMenu.id })
      }
      hide()
      message.success(`${ tip }成功！`)
      tableActionRef.current.reset()
      return true
    } catch (error) {
      hide()
      message.error(`${ tip }失败请重试！`)
      return false
    }
  }

  // 当对话框visible属性改变时
  const visibleChange = visible => {
    handleMenuFormSetting({ ...menuFormSetting, visible })
    if (visible) {
      listMenusByType([MenuType["0"].value, MenuType["1"].value]).then(res => {
        const menu = { value: '0', title: '主类目', children: [] }
        const menus = res.data.treeMenus
        convertFatherMenu(menus)
        menu.children = menus
        handleChangeFatherMenus([menu])
      })

      let icon
      if (currentMenu.icon !== undefined && currentMenu.icon !== '') {
        icon = React.createElement(Icons[currentMenu.icon])
        handleChangeIconSelectSetting({ ...iconSelectSetting, icon })
      }
    }
  }

  return (
    <ModalForm
      modalProps={
        {
          style: { top: 40 },
          okText: currentMenu.id === '' || currentMenu.id === undefined ? '添加' : '提交',
          destroyOnClose: true,
          maskClosable: false,
          afterClose: () => menuForm.resetFields()
        }
      }
      form={ menuForm }
      preserve={ false }
      layout="horizontal"
      labelCol={ { span: 4 } }
      width={ 600 }
      title={ menuFormSetting.title }
      visible={ menuFormSetting.visible }
      onVisibleChange={ visibleChange }
      onFinish={ handleMenuSubmit }
      initialValues={
        {
          type: currentMenu.type,
          pid: currentMenu.pid === '' || currentMenu.pid === undefined ? '0' : currentMenu.pid,
          icon: currentMenu.icon,
          name: currentMenu.name,
          alias: currentMenu.alias,
          permissionValue: currentMenu.permissionValue,
          component: currentMenu.component,
          redirect: currentMenu.redirect,
          path: currentMenu.path,
          sort: currentMenu.sort || 0,
          status: currentMenu.status || 1,
        }
      }
    >
      <Form.Item label="上级菜单" name="pid" rules={ [{ required: true, message: '上级菜单必选！' }] }>
        <TreeSelect
          allowClear
          showArrow
          placeholder="请选择上级菜单"
          treeData={ fatherMenus }
          treeDefaultExpandedKeys={ [currentMenu.pid] }
        />
      </Form.Item>
      <Form.Item name="type" label="菜单类型" rules={ [{ required: true, message: '菜单类型必选！' }] }>
        <Radio.Group
          onChange={ e => handleChangeCurrentMenu({ ...currentMenu, type: e.target.value }) }
          value={ currentMenu.type }>
          <Radio value={ 0 }>目录</Radio>
          <Radio value={ 1 }>菜单</Radio>
          <Radio value={ 2 }>按钮</Radio>
        </Radio.Group>
      </Form.Item>
      {
        currentMenu.type !== 2 && (
          [
            <Form.Item key="icon" name="icon" label="菜单图标">
              <Input allowClear
                     placeholder="选择图标"
                     prefix={ iconSelectSetting.icon && iconSelectSetting.icon }
                     onClick={ () => handleChangeIconSelectSetting({ ...iconSelectSetting, visible: true }) }
              />
            </Form.Item>,
            <Form.Item key="alias" label="菜单别名" name="alias" rules={ [{ required: true, message: '菜单别名不能为空！' }] }>
              <Input placeholder="请输入菜单别名"/>
            </Form.Item>,
            <Form.Item key="component" name="component" label="组件" rules={ [{ required: true, message: '组件不能为空！' }] }>
              <Input allowClear placeholder="请填写组件路径，eg：Permission/Menu"/>
            </Form.Item>,
            <Form.Item key="path" name="path" label="路由地址" rules={ [{ required: true, message: '路由地址不能为空！' }] }>
              <Input allowClear placeholder="请填写路由地址，eg：permission/menu"/>
            </Form.Item>,
            <Form.Item key="redirect" name="redirect" label="重定向地址">
              <Input allowClear placeholder="请填写菜单重定向地址"/>
            </Form.Item>
          ]
        )
      }
      <Form.Item label="菜单名称" name="name" rules={ [{ required: true, message: '菜单名称不能为空' }] }>
        <Input placeholder="请输入菜单名称"/>
      </Form.Item>
      {
        currentMenu.type !== 0 && (
          <Form.Item name="permissionValue" label="权限标识" rules={ [{ required: true, message: '权限标识不能为空！' }] }>
            <Input allowClear placeholder="请输入权限标识"/>
          </Form.Item>
        )
      }
      <Form.Item className="clearMrBottom" label="菜单状态" required>
        <Form.Item
          style={ { display: 'inline-flex', width: '45%' } }
          name="status" rules={ [{ required: true, message: '菜单状态必选！' }] }>
          <Radio.Group value={ currentMenu.status }>
            <Radio value={ 1 }>正常</Radio>
            <Radio value={ 0 }>禁用</Radio>
          </Radio.Group>
        </Form.Item>
        <Form.Item
          label="显示排序"
          style={ { display: 'inline-flex', width: '45%' } }
          name="sort" rules={ [{ required: true, message: '菜单排序必选！' }] }>
          <InputNumber/>
        </Form.Item>
      </Form.Item>
      <IconSelector
        menuForm={ menuForm }
        iconSelectSetting={ iconSelectSetting }
        handleChangeIconSelectSetting={ handleChangeIconSelectSetting }/>
    </ModalForm>
  )
}

export default MenuForm
