import React from 'react';
import { Dropdown, Menu, message, Popconfirm } from "antd";
import { DeleteOutlined, DownOutlined, EyeInvisibleOutlined, EyeOutlined } from "@ant-design/icons";
import { changeMenuStatus, deleteMenuBeId } from "@/services/menu";

const RecordDropDown = props => {
  const { menu, tableActionRef } = props

  const handleDeleteMenu = async () => {
    const hide = message.loading(`正在删除菜单【${ menu.name }】`)
    try {
      await deleteMenuBeId(menu.id)
      hide()
      message.success('删除成功！')
      tableActionRef.current.reset()
      return true
    } catch (error) {
      hide()
      message.error('删除失败请重试！')
      return false
    }
  }

  const handleChangeMenuStatus = async () => {
    const hide = message.loading(`正在${ menu.status === 1 ? '禁用' : '启用' }菜单【${ menu.name }】`)
    try {
      await changeMenuStatus(menu.id)
      hide()
      message.success('更改成功！')
      tableActionRef.current.reset()
      return true
    } catch (error) {
      hide()
      message.error('更改失败请重试！')
      return false
    }
  }

  const dropDownMenu = (
    <Menu>
      <Menu.Item key="0">
        <DeleteOutlined/>
        <Popconfirm
          title={ `你确定要删除【${ menu.name }】这个菜单吗？提示：此操作不可逆！` }
          onConfirm={ handleDeleteMenu }
          okText="确定"
          cancelText="取消"
        >
          <a style={ { marginLeft: '4px' } }>删除</a>
        </Popconfirm>
      </Menu.Item>
      {
        menu.status === 0 ?
          <Menu.Item key="1">
            <EyeOutlined/> <a onClick={ handleChangeMenuStatus }>启用</a>
          </Menu.Item> :
          <Menu.Item key="2">
            <EyeInvisibleOutlined/> <a onClick={ handleChangeMenuStatus }>禁用</a>
          </Menu.Item>
      }
    </Menu>
  )

  return (
    <Dropdown key="options" overlay={ dropDownMenu } trigger={ ['click'] } arrow>
      <a className="ant-dropdown-link" onClick={ e => e.preventDefault() }>
        更多 <DownOutlined/>
      </a>
    </Dropdown>
  )
}

export default RecordDropDown
