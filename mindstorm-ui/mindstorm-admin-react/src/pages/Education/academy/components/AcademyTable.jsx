import React, { useRef, useState } from 'react';
import { Button, Popconfirm, Space, Table, Tag } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import ProTable from '@ant-design/pro-table';

const AcademyTable = () => {
  const actionRef = useRef();
  const [selectedRowsState, setSelectedRows] = useState([]);

  const handleQueryAcademies = (params) => {};

  const handleDeleteAcademy = (academy) => {};

  const columns = [
    {
      title: '院系名称',
      dataIndex: 'name',
      width: 200,
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
      valueEnum: {
        1: { text: '正常', status: 1 },
        0: { text: '禁用', status: 0 },
      },
      render: (_, record) => {
        const { status } = record;
        return <Space>{status === 1 ? '正常' : '禁用'}</Space>;
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
        <a key="edit" onClick={() => {}}>
          编辑
        </a>,
        <a key="create" onClick={() => {}}>
          新增
        </a>,
        <Popconfirm
          key="delete"
          title={`你确定要删除【${record.name}】这个院系吗？`}
          onConfirm={() => handleDeleteAcademy(record)}
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
        request={(params) => handleQueryAcademies(params)}
        rowKey={(record) => record.id}
        dateFormatter="string"
        headerTitle="系统菜单"
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
          <Button key="button" icon={<PlusOutlined />} type="primary" onClick={() => {}}>
            新增
          </Button>,
        ]}
      />
    </>
  );
};
export default AcademyTable;
