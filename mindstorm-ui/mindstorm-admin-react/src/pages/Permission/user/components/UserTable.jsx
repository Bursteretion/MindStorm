import React, { useRef, useState } from 'react';
import { Button, message, Popconfirm, Space, Switch, Table, Tag } from "antd";
import { PlusOutlined } from "@ant-design/icons";
import ProTable from "@ant-design/pro-table";
import { changeUserStatus, deleteUser, searchUsers, UserStatus } from "@/services/user";
import UserForm from "./UserForm";

const UserTable = () => {
  const actionRef = useRef();
  const [isModalVisible, setModalVisible] = useState(false);
  const [userId, setUserId] = useState(undefined);
  const [selectedRowsState, setSelectedRows] = useState([]);

  const handleQueryUsers = async params => {
    const res = await searchUsers({ ...params, pageIndex: params.current, pageSize: params.pageSize });
    const { records = [] } = res.data.page;
    return {
      data: records,
      total: records.length,
      success: res.success
    };
  };

  /**
   * 删除用户
   */
  const handleDeleteUser = async userVO => {
    const hide = message.loading(`正在删除用户【${ userVO.username }】`);
    try {
      await deleteUser(userVO.id);
      hide();
      message.success(`删除成功！`);
      actionRef?.current.reset();
      return true;
    } catch (error) {
      hide();
      message.error(`删除失败请重试！`);
      return false;
    }
  };

  /**
   * 更改用户状态
   */
  const handleChangeUserStatus = async (checked, userVO) => {
    const tip = checked ? '启用' : '禁用'
    const hide = message.loading(`正在${ tip }用户【${ userVO.username }】`)
    try {
      await changeUserStatus(userVO.id, checked ? 1 : 0)
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

  // 表格列定义
  const columns = [
    {
      title: '用户名',
      dataIndex: 'username',
    },
    {
      title: '真实姓名',
      dataIndex: 'realName',
    },
    {
      title: '电话',
      dataIndex: 'phone',
      search: false
    },
    {
      title: '生日',
      dataIndex: 'birthDay',
      search: false
    },
    {
      title: '邮箱',
      dataIndex: 'email',
      search: false
    },
    {
      title: '性别',
      dataIndex: 'sex',
      search: false,
      render: (_, render) => {
        const { sex } = render
        return <Tag color={ sex === 1 ? 'gold' : 'purple' }>{ sex === 1 ? '男' : '女' }</Tag>
      }
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueType: 'select',
      valueEnum: UserStatus,
      render: (_, record) => {
        const { status } = record
        return (
          <Switch
            checked={ status === 1 }
            onChange={ checked => handleChangeUserStatus(checked, record) }
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
        <a key="edit" onClick={ () => {
          setModalVisible(true)
          setUserId(record.id)
        } }>编辑</a>,
        <Popconfirm
          key="delete"
          title={ `你确定要删除【${ record.username }】这个用户吗？` }
          onConfirm={ () => handleDeleteUser(record) }
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
        request={ params => handleQueryUsers(params) }
        rowKey={ record => record.id }
        dateFormatter="string"
        headerTitle="用户列表"
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
              onClick={ () => {
                setModalVisible(true)
                setUserId(undefined)
              } }>
              新增
            </Button>
          ]
        }
      />
      {
        !isModalVisible ? '' :
          <UserForm
            isModalVisible={ isModalVisible }
            setModalVisible={ setModalVisible }
            userId={ userId }
            actionRef={ actionRef }
          />
      }
    </>
  )
}

export default UserTable
