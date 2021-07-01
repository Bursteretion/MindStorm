import React, { useEffect, useState } from 'react';
import { listMenus } from "@/services/menu";
import convertToTreeMenus from '@/utils/treeSelect'
import { message, Modal, Skeleton, Tree } from "antd";
import { distributeMenu, roleMenu } from "@/services/roleMenu";

const RoleMenuForm = props => {
  const { isModalVisible, setModalVisible, roleId } = props
  const [currentRole, setCurrentRole] = useState(undefined)
  const [treeMenus, setTreeMenus] = useState(undefined)
  const [expandedKeys, setExpandedKeys] = useState([])
  const [checkedKeys, setCheckedKeys] = useState([])
  const [selectedKeys, setSelectedKeys] = useState([])
  const [autoExpandParent, setAutoExpandParent] = useState(true)

  /**
   * 组件挂载完成时
   */
  useEffect(() => {
    async function fetchData() {
      const menuResponse = await listMenus()
      if (menuResponse.code === 20000) {
        const { menus } = menuResponse.data
        convertToTreeMenus(menus)
        setTreeMenus(menus)
      }
      if (roleId !== undefined) {
        const roleMenuResponse = await roleMenu(roleId)
        if (roleMenuResponse.code === 20000) {
          setCurrentRole(roleMenuResponse.data.menus)
          setCheckedKeys(roleMenuResponse.data.menus.checkedKeys)
        }
      }
    }

    fetchData()
  }, [])

  /**
   * 树形控件相关设置
   */
  const onExpand = expandedKeysValue => {
    setExpandedKeys(expandedKeysValue)
    setAutoExpandParent(false)
  }
  const onSelect = selectedKeysValue => {
    setSelectedKeys(selectedKeysValue)
  }
  const onCheck = checkedKeysValue => {
    setCheckedKeys(checkedKeysValue)
  }

  /**
   * 分配菜单
   * @returns {Promise<boolean>}
   */
  const handleDistributeMenu = async () => {
    const hide = message.loading(`正在为角色【${ currentRole.roleName }】分配菜单`);
    try {
      await distributeMenu(roleId, checkedKeys)
      hide()
      message.success(`成功！`)
      setModalVisible(false)
      return true
    } catch (error) {
      hide()
      message.error(`失败请重试！`)
      return false
    }
  }

  return (
    <Modal
      title="分配菜单"
      style={ { top: 40 } }
      visible={ isModalVisible }
      maskClosable={ false }
      destroyOnClose={ true }
      onOk={ handleDistributeMenu }
      onCancel={ () => setModalVisible(false) }
    >
      {
        treeMenus === undefined ? <Skeleton active/> :
          <Tree
            checkable
            onExpand={ onExpand }
            onSelect={ onSelect }
            onCheck={ onCheck }
            expandedKeys={ expandedKeys }
            checkedKeys={ checkedKeys }
            selectedKeys={ selectedKeys }
            autoExpandParent={ autoExpandParent }
            treeData={ treeMenus }
          />
      }
    </Modal>
  )
}

export default RoleMenuForm
