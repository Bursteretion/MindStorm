import React, { useEffect } from 'react';
import Modal from "antd/es/modal/Modal";
import { roleMenu } from "@/services/roleMenu";

const RoleMenuForm = props => {
  const { isModalVisible, setModalVisible, roleId } = props

  useEffect(() => {
    async function fetchData() {
      if (roleId !== undefined) {
        const res = await roleMenu(roleId)
        console.log(res.data)
      }
    }
    fetchData()
  }, [])


  return (
    <Modal
      title="分配权限"
      style={ { top: 40 } }
      visible={ isModalVisible }
      maskClosable={ false }
      destroyOnClose={ true }
      onCancel={ () => setModalVisible(false) }
    >

    </Modal>
  )
}
export default RoleMenuForm
