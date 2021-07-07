import React, { useRef, useState } from 'react';
import ProTable from '@ant-design/pro-table';
import { Button, message, Popconfirm, Space, Switch, Table } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import { changeRoleStatus, searchRolesPage, RoleStatus, deleteRole } from '@/services/role';
import RoleForm from './RoleForm';
import RoleMenuForm from './RoleMenuForm';

const RoleTable = () => {
  const actionRef = useRef();
  const [isRoleFormModalVisible, setRoleFormModalVisible] = useState(false);
  const [isRoleMenuFormModalVisible, setRoleMenuFormModalVisible] = useState(false);
  const [roleId, setRoleId] = useState(undefined);
  const [selectedRowsState, setSelectedRows] = useState([]);

  /**
   * 查询角色列表信息
   * @param params 查询参数
   * @returns {Promise<{total: number, data: *[], success}>}
   */
  const handleQueryRoles = async (params) => {
    const res = await searchRolesPage({
      ...params,
      pageIndex: params.current,
      pageSize: params.pageSize,
    });
    const { records = [] } = res.data.searchRoles;
    return {
      data: records,
      total: records.length,
      success: res.success,
    };
  };

  /**
   * 更改角色状态
   * @param checked 启用或禁用
   * @param role 角色信息
   * @returns {Promise<boolean>}
   */
  const handleChangeRoleStatus = async (checked, role) => {
    const tip = checked ? '启用' : '禁用';
    const hide = message.loading(`正在${tip}角色【${role.roleName}】`);
    try {
      await changeRoleStatus(role.id, checked ? 1 : 0);
      hide();
      message.success(`${tip}成功！`);
      actionRef?.current.reset();
      return true;
    } catch (error) {
      hide();
      message.error(`${tip}失败请重试！`);
      return false;
    }
  };

  /**
   * 删除该角色
   * @param role 角色信息
   * @returns {Promise<boolean>}
   */
  const handleDeleteRole = async (role) => {
    const hide = message.loading(`正在删除角色【${role.roleName}】`);
    try {
      await deleteRole(role.id);
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

  const columns = [
    {
      title: '角色名称',
      dataIndex: 'roleName',
      width: 200,
    },
    {
      title: '备注',
      dataIndex: 'remark',
      search: false,
    },
    {
      title: '排序',
      dataIndex: 'sort',
      search: false,
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueType: 'select',
      valueEnum: RoleStatus,
      render: (_, record) => {
        const { status } = record;
        return (
          <Switch
            checked={status === 1}
            onChange={(checked) => handleChangeRoleStatus(checked, record)}
            checkedChildren="启用"
            unCheckedChildren="禁用"
          />
        );
      },
    },
    {
      title: '创建时间',
      dataIndex: 'gmtCreate',
      valueType: 'dateTime',
      hideInForm: true,
      hideInSearch: true,
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
            endTime: value[1],
          };
        },
      },
    },
    {
      title: '操作',
      valueType: 'option',
      render: (_, record) => [
        <a
          key="edit"
          onClick={() => {
            setRoleFormModalVisible(true);
            setRoleId(record.id);
          }}
        >
          编辑
        </a>,
        <a
          key="distribute"
          onClick={() => {
            setRoleMenuFormModalVisible(true);
            setRoleId(record.id);
          }}
        >
          分配权限
        </a>,
        <Popconfirm
          key="delete"
          title={`你确定要删除【${record.roleName}】这个角色吗？`}
          onConfirm={() => handleDeleteRole(record)}
          okText="确定"
          cancelText="取消"
        >
          <a>删除</a>
        </Popconfirm>,
      ],
    },
  ];

  return (
    <>
      <ProTable
        columns={columns}
        actionRef={actionRef}
        rowSelection={{
          checkStrictly: false,
          selections: [Table.SELECTION_ALL, Table.SELECTION_INVERT],
          onChange: (_, selectedRows) => setSelectedRows(selectedRows),
        }}
        request={(params) => handleQueryRoles(params)}
        rowKey={(record) => record.id}
        dateFormatter="string"
        headerTitle="角色列表"
        pagination={{ defaultCurrent: 1, defaultPageSize: 5 }}
        options={{ fullScreen: true }}
        tableAlertRender={({ selectedRowKeys }) => (
          <Space size={24}>
            <span>已选 {selectedRowKeys.length} 项</span>
          </Space>
        )}
        tableAlertOptionRender={() => {
          return (
            <Space size={16}>
              <a>批量删除</a>
              <a>导出数据</a>
              <a style={{ marginLeft: 8 }}>取消选择</a>
            </Space>
          );
        }}
        toolBarRender={() => [
          <Button
            key="button"
            icon={<PlusOutlined />}
            type="primary"
            onClick={() => {
              setRoleFormModalVisible(true);
              setRoleId(undefined);
            }}
          >
            新增
          </Button>,
        ]}
      />
      {!isRoleFormModalVisible ? (
        ''
      ) : (
        <RoleForm
          isModalVisible={isRoleFormModalVisible}
          setModalVisible={setRoleFormModalVisible}
          roleId={roleId}
          actionRef={actionRef}
        />
      )}
      {!isRoleMenuFormModalVisible ? (
        ''
      ) : (
        <RoleMenuForm
          isModalVisible={isRoleMenuFormModalVisible}
          setModalVisible={setRoleMenuFormModalVisible}
          roleId={roleId}
        />
      )}
    </>
  );
};

export default RoleTable;
