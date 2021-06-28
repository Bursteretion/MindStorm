import React, { useRef, useState } from 'react';
import ProTable from "@ant-design/pro-table";
import { Button, message, Popconfirm, Space, Switch, Table } from "antd";
import { PlusOutlined } from "@ant-design/icons";
import { changeRoleStatus, searchRolesPage, RoleStatus } from "@/services/role";

const RoleTable = () => {
  const actionRef = useRef()
  const [currentRole, handleChangeRole] = useState({})
  const [selectedRowsState, setSelectedRows] = useState([])

  const handleChangeRoleStatus = async (checked, role) => {
    const tip = checked ? '启用' : '禁用'
    const hide = message.loading(`正在${ tip }角色【${ role.roleName }】`)
    try {
      await changeRoleStatus(role.id, checked ? 1 : 0)
      hide()
      message.success(`${ tip }成功！`)
      actionRef?.current.reset()
      return true
    } catch (error) {
      hide()
      message.error(`${ tip }失败请重试！`)
      return false
    }
  }
  const handleDeleteRole = async role => {

  }

  const columns = [
    {
      title: '角色名称',
      dataIndex: 'roleName',
      width: 200
    },
    {
      title: '备注',
      dataIndex: 'remark',
      search: false
    },
    {
      title: '排序',
      dataIndex: 'sort',
      search: false
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueType: 'select',
      valueEnum: RoleStatus,
      render: (_, record) => {
        const { status } = record
        return (
          <Switch
            checked={ status === 1 }
            onChange={ checked => handleChangeRoleStatus(checked, record) }
            checkedChildren="启用"
            unCheckedChildren="禁用"/>
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
            // handleFormSetting('编辑菜单')
            // handleChangeCurrentMenu(record)
          }
        }>编辑</a>,
        <a key="distribute" onClick={
          () => {
            // handleFormSetting('编辑菜单')
            // handleChangeCurrentMenu(record)
          }
        }>分配权限</a>,
        <Popconfirm
          key="delete"
          title={ `你确定要删除【${ record.roleName }】这个角色吗？` }
          onConfirm={ () => handleDeleteRole(record.id) }
          okText="确定"
          cancelText="取消"
        >
          <a>删除</a>
        </Popconfirm>
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
          params => searchRolesPage({ ...params, pageIndex: params.current, pageSize: params.pageSize }).then(res => {
            const { records = [] } = res.data.searchRoles
            return {
              data: records,
              total: records.length,
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
        pagination={ { defaultCurrent: 1, defaultPageSize: 5 } }
        options={ { fullScreen: true } }
        tableAlertRender={
          (
            { selectedRowKeys }) => (
            <Space size={ 24 }>
              <span>
                已选 { selectedRowKeys.length } 项
              </span>
            </Space>
          )
        }
        tableAlertOptionRender={
          () => {
            return (
              <Space size={ 16 }>
                <a>批量删除</a>
                <a>导出数据</a>
                <a style={ { marginLeft: 8 } }>
                  取消选择
                </a>
              </Space>
            )
          }
        }
        toolBarRender={
          () => [
            <Button
              key="button"
              icon={ <PlusOutlined/> }
              type="primary"
              onClick={
                () => {
                  // handleFormSetting('新增菜单')
                  // handleChangeCurrentMenu({ type: 0 })
                }
              }>
              新增
            </Button>
          ]
        }
      />
    </>
  )
}
export default RoleTable
