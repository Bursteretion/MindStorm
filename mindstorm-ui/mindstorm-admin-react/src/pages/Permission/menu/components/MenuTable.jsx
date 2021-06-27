import React, { useRef, useState } from 'react';
import { Button, Space, Table, Tag } from "antd";
import ProTable from '@ant-design/pro-table';
import { MenuType, querySearchMenus } from "@/services/menu";
import { PlusOutlined } from "@ant-design/icons";
import { FooterToolbar } from "@ant-design/pro-layout";
import MenuForm from "./MenuForm";
import RecordDropDown from './RecordDropDown'

function processingMenus(menus = []) {
  if (menus.length <= 0) return
  menus.forEach(v => {
    const backup = v
    if (backup.children.length <= 0) {
      backup.children = undefined
    }
    processingMenus(backup.children)
  })
}

const MenuTable = () => {
  const actionRef = useRef()
  const [selectedRowsState, setSelectedRows] = useState([])
  const [currentMenu, handleChangeCurrentMenu] = useState({})
  const [menuFormSetting, handleMenuFormSetting] = useState({
    visible: false,
    title: '添加菜单'
  })

  const handleFormSetting = title => {
    handleMenuFormSetting({ ...menuFormSetting, visible: true, title })
  }

  const columns = [
    {
      title: '菜单名称',
      dataIndex: 'name',
      width: 200
    },
    {
      title: '图标',
      dataIndex: 'icon',
      search: false
    },
    {
      title: '排序',
      dataIndex: 'sort',
      search: false
    },
    {
      title: '权限标识',
      dataIndex: 'permissionValue',
      search: false
    },
    {
      title: '组件路径',
      dataIndex: 'component',
      search: false
    },
    {
      title: '菜单类型',
      dataIndex: 'type',
      hideInSearch: true,
      valueEnum: {
        0: { text: '目录', status: 0 },
        1: { text: '菜单', status: 1 },
        2: { text: '按钮', status: 2 }
      },
      render: (_, record) => {
        const menuType = MenuType[record.type]
        return (
          <Space>
            {
              <Tag color={ menuType.color } key={ menuType.text }>
                { menuType.text }
              </Tag>
            }
          </Space>
        )
      }
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueType: 'select',
      valueEnum: {
        1: { text: '正常', status: 1 },
        0: { text: '禁用', status: 0 }
      },
      render: (_, record) => {
        const { status } = record
        return (
          <Space>
            { status === 1 ? '正常' : '禁用' }
          </Space>
        )
      },
    },
    {
      title: '创建时间',
      dataIndex: 'gmtCreate',
      valueType: 'dateTime',
      hideInForm: true,
      hideInSearch: true
    },
    {
      title: '创建时间',
      dataIndex: 'gmtCreate',
      valueType: 'dateTimeRange',
      hideInTable: true,
      search: {
        transform: (value) => {
          return {
            startTime: value[0],
            endTime: value[1]
          }
        }
      }
    },
    {
      title: '操作',
      valueType: 'option',
      render: (_, record) => [
        <a key="edit" onClick={
          () => {
            handleFormSetting('编辑菜单')
            handleChangeCurrentMenu(record)
          }
        }>编辑</a>,
        <a key="create" onClick={
          () => {
            handleFormSetting('新增菜单')
            handleChangeCurrentMenu({ pid: record.id, type: 0 })
          }
        }>新增</a>,
        <RecordDropDown tableActionRef={ actionRef } menu={ record } key="dropOperate"/>
      ]
    }
  ]

  return (
    <>
      <ProTable
        columns={ columns }
        actionRef={ actionRef }
        rowSelection={
          {
            checkStrictly: false,
            selections: [Table.SELECTION_ALL, Table.SELECTION_INVERT],
            onChange: (_, selectedRows) => setSelectedRows(selectedRows)
          }
        }
        request={
          params => querySearchMenus(params).then(res => {
            const { searchMenus = [] } = res.data
            processingMenus(searchMenus)
            return {
              data: searchMenus,
              total: searchMenus.length,
              success: res.success
            }
          })
        }
        rowKey={ record => record.id }
        form={
          {
            ignoreRules: false,
            syncToUrl: (values, type) => {
              if (type === 'get') {
                return {
                  ...values,
                  gmtCreate: [values.startTime, values.endTime]
                }
              }
              return values
            }
          }
        }
        dateFormatter="string"
        headerTitle="系统菜单"
        pagination={ false }
        options={ { fullScreen: true } }
        toolBarRender={
          () => [
            <Button
              key="button"
              icon={ <PlusOutlined/> }
              type="primary"
              onClick={
                () => {
                  handleFormSetting('新增菜单')
                  handleChangeCurrentMenu({ type: 0 })
                }
              }>
              新增
            </Button>
          ]
        }
      />
      { selectedRowsState?.length > 0 && (
        <FooterToolbar
          extra={
            <div>
              已选择{ ' ' }
              <a style={ { fontWeight: 600 } }>
                { selectedRowsState.length }
              </a>{ ' ' }
              项，
              <span>
                  目录总计：{ selectedRowsState.filter(item => item.type === 0).length } 个，
                  菜单总计：{ selectedRowsState.filter(item => item.type === 1).length } 个，
                  按钮总计：{ selectedRowsState.filter(item => item.type === 2).length } 个
                </span>
            </div>
          }
        >
          <Button
            danger
            onClick={ async () => {
              setSelectedRows([])
            } }
          >
            批量删除
          </Button>
        </FooterToolbar>
      ) }
      <MenuForm
        tableActionRef={ actionRef }
        currentMenu={ currentMenu }
        handleChangeCurrentMenu={ handleChangeCurrentMenu }
        menuFormSetting={ menuFormSetting }
        handleMenuFormSetting={ handleMenuFormSetting }/>
    </>
  )
}

export default MenuTable
