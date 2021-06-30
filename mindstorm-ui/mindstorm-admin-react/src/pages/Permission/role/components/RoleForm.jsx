import React, { useEffect, useState } from 'react';
import { Form, message, Skeleton } from "antd";
import { ProFormDigit, ProFormText, ProFormTextArea, ProFormRadio } from "@ant-design/pro-form";
import { createRole, updateRole, infoRole } from "@/services/role";
import Modal from "antd/es/modal/Modal";

const RoleForm = props => {
  const { isModalVisible, setModalVisible, roleId, actionRef } = props
  const [roleForm] = Form.useForm()
  const [initialValues, setInitialValues] = useState(undefined)

  useEffect(() => {
    async function fetchData() {
      if (roleId !== undefined) {
        const res = await infoRole(roleId)
        const { role } = res.data
        setInitialValues({
          roleName: role.roleName,
          status: role.status,
          remark: role.remark,
          sort: role.sort
        })
      }
    }
    fetchData()
  }, [])

  const handleSubmitForm = async roleVO => {
    const tip = roleId === undefined ? '添加' : '更新'
    const hide = message.loading(`正在${ tip }角色【${ roleVO.roleName }】`);
    try {
      if (roleId === undefined) {
        await createRole(roleVO)
      } else {
        await updateRole({ ...roleVO, id: roleId })
      }
      hide()
      message.success(`${ tip }成功！`)
      setInitialValues(undefined)
      actionRef.current.reset()
      return true
    } catch (error) {
      hide()
      message.error(`${ tip }失败请重试！`)
      return false
    }
  }

  const modalTitle = roleId === undefined ? '添加角色' : '编辑角色'
  const modalOkText = roleId === undefined ? '添加' : '提交'

  return (
    <Modal
      style={ { top: 40 } }
      okText={ modalOkText }
      destroyOnClose={ true }
      maskClosable={ false }
      width={ 500 }
      title={ modalTitle }
      visible={ isModalVisible }
      onCancel={ () => setModalVisible(false) }
      onOk={ () => {
        roleForm
        .validateFields()
        .then(async values => {
          await handleSubmitForm(values)
          roleForm.resetFields()
          setModalVisible(false)
        })
      } }
    >
      {
        initialValues === undefined && roleId !== undefined ? <Skeleton active/> :
          (
            <Form
              form={ roleForm }
              preserve={ false }
              layout="horizontal"
              labelCol={ { span: 4 } }
              initialValues={ initialValues }
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
            </Form>
          )
      }
    </Modal>
  )
}

export default RoleForm
