import React from 'react';
import { Form, message } from "antd";
import { ModalForm, ProFormDigit, ProFormText, ProFormTextArea, ProFormRadio } from "@ant-design/pro-form";
import { createRole, updateRole } from "@/services/role";

const RoleForm = props => {
  const { isModalVisible, setModalVisible, currentRole, actionRef } = props
  const [roleForm] = Form.useForm()

  const visibleChange = visible => {
    if (!visible) {
      setModalVisible(false)
    }
  }

  const handleSubmitForm = async roleVO => {
    const tip = currentRole.id === undefined ? '添加' : '更新'
    const hide = message.loading(`正在${ tip }角色【${ roleVO.roleName }】`);
    try {
      if (currentRole.id === undefined) {
        await createRole(roleVO)
      } else {
        await updateRole({ ...roleVO, id: currentRole.id })
      }
      hide()
      message.success(`${ tip }成功！`)
      actionRef.current.reset()
      return true
    } catch (error) {
      hide()
      message.error(`${ tip }失败请重试！`)
      return false
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
      width={ 500 }
      title={ currentRole.id === undefined ? '添加角色' : '编辑角色' }
      visible={ isModalVisible }
      onVisibleChange={ visibleChange }
      onFinish={ handleSubmitForm }
      initialValues={
        {
          roleName: currentRole.roleName,
          status: currentRole.status === undefined ? 1 : currentRole.status,
          remark: currentRole.remark,
          sort: currentRole.sort === undefined ? 0 : currentRole.sort,
        }
      }
    >
      <ProFormText
        name="roleName"
        label="角色名称"
        placeholder="请输入角色名称"
        rules={ [{ required: true, message: '角色名称不能为空！' }] }/>
      <ProFormDigit
        label="角色排序"
        name="sort"
        width="sm"
        min={ 0 }
      />
      <ProFormRadio.Group
        name="status"
        label="角色状态"
        options={ [
          {
            label: '正常',
            value: 1,
          },
          {
            label: '禁用',
            value: 0,
          }
        ] }
        rules={ [{ required: true, message: '角色状态必选！' }] }
      />
      <ProFormTextArea
        name="remark"
        label="角色备注"
        placeholder="请输入角色备注"
      />
    </ModalForm>
  )
}

export default RoleForm
