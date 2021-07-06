import React, { useRef, useState } from 'react';
import { Button, message, Popconfirm, Space, Switch, Table } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import ProTable from '@ant-design/pro-table';
import { AcademyStatus, changeAcademyStatus, queryAcademies } from '@/services/academy';

const AcademyTable = () => {
  const actionRef = useRef();

  const handleQueryAcademies = async (params) => {
    const res = await queryAcademies({
      ...params,
      pageIndex: params.current,
      pageSize: params.pageSize,
    });
    const { records = [] } = res.data.queryAcademyPage;
    return {
      data: records,
      total: records.length,
      success: res.success,
    };
  };

  const handleChangeAcademyStatus = async (checked, academy) => {
    const tip = checked ? '启用' : '禁用';
    const hide = message.loading(`正在${tip}院系【${academy.name}】`);
    try {
      await changeAcademyStatus(academy.id, checked ? 1 : 0);
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

  const handleDeleteAcademy = (academy) => {};

  const columns = [
    {
      title: '序号',
      dataIndex: 'index',
      valueType: 'indexBorder',
      width: 48,
    },
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
      valueEnum: AcademyStatus,
      render: (_, record) => {
        const { status } = record;
        return (
          <Switch
            checked={status === 1}
            onChange={(checked) => handleChangeAcademyStatus(checked, record)}
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
        request={(params) => handleQueryAcademies(params)}
        rowKey={(record) => record.id}
        dateFormatter="string"
        headerTitle="系统菜单"
        pagination={{ defaultCurrent: 1, defaultPageSize: 5 }}
        options={{ fullScreen: true }}
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
