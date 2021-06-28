import React from 'react';
import { Form } from "antd";
import { ModalForm } from "@ant-design/pro-form";

const RoleForm = props => {
  const { roleFormSetting, handleRoleFormSetting, currentRole, handleChangeCurrentRole, actionRef } = props
  const [roleForm] = Form.useForm()

  const visibleChange = visible => {
    handleRoleFormSetting({ ...roleFormSetting, visible })
    if (visible) {
      // listMenusByType([MenuType["0"].value, MenuType["1"].value]).then(res => {
      //   const menu = { value: '0', title: '主类目', children: [] }
      //   const menus = res.data.treeMenus
      //   convertFatherMenu(menus)
      //   menu.children = menus
      //   handleChangeFatherMenus([menu])
      // })
    }


  }

  return (
    <ModalForm
      modalProps={
        {
          style: { top: 40 },
          okText: currentRole.id === '' || currentRole.id === undefined ? '添加' : '提交',
          destroyOnClose: true,
          maskClosable: false,
          afterClose: () => roleForm.resetFields()
        }
      }
      form={ roleForm }
      preserve={ false }
      layout="horizontal"
      labelCol={ { span: 4 } }
      width={ 600 }
      title={ roleFormSetting.title }
      visible={ roleFormSetting.visible }
      onVisibleChange={ visibleChange }
      // onFinish={ }
      initialValues={
        {}
      }
    >

    </ModalForm>
  )
}

export default RoleForm
