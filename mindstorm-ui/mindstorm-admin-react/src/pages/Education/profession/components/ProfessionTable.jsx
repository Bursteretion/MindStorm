import React, { useRef, useState } from 'react';
import { Button, message, Popconfirm, Switch } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import ProTable from '@ant-design/pro-table';
import ProfessionForm from './ProfessionForm';
import {
  changeProfessionStatus,
  deleteProfession,
  ProfessionStatus,
  queryProfessions,
} from '@/services/profession';

const ProfessionTable = () => {
  const actionRef = useRef();
  const [isModalVisible, setModalVisible] = useState(false);
  const [professionId, setProfessionId] = useState(undefined);

  const handleQueryProfessions = async (params) => {
    const res = await queryProfessions({
      ...params,
      pageIndex: params.current,
      pageSize: params.pageSize,
    });
    const { records = [], total } = res.data.queryProfessions;
    return {
      data: records,
      total,
      success: res.success,
    };
  };

  const handleChangeProfessionStatus = async (checked, profession) => {
    const tip = checked ? '启用' : '禁用';
    const hide = message.loading(`正在${tip}专业【${profession.name}】`);
    try {
      await changeProfessionStatus(profession.id, checked ? 1 : 0);
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

  const handleDeleteProfession = async (profession) => {
    const hide = message.loading(`正在删除专业【${profession.name}】`);
    try {
      await deleteProfession(profession.id);
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
      title: '序号',
      dataIndex: 'index',
      valueType: 'indexBorder',
      width: 48,
    },
    {
      title: '所属院系',
      dataIndex: 'academyName',
      width: 200,
      search: false,
    },
    {
      title: '专业名称',
      dataIndex: 'name',
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
      valueEnum: ProfessionStatus,
      render: (_, record) => {
        const { status } = record;
        return (
          <Switch
            checked={status === 1}
            onChange={(checked) => handleChangeProfessionStatus(checked, record)}
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
            setModalVisible(true);
            setProfessionId(record.id);
          }}
        >
          编辑
        </a>,
        <Popconfirm
          key="delete"
          title={`你确定要删除【${record.name}】这个专业吗？`}
          onConfirm={() => handleDeleteProfession(record)}
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
        request={(params) => handleQueryProfessions(params)}
        rowKey={(record) => record.id}
        dateFormatter="string"
        headerTitle="院系列表"
        pagination={{ defaultCurrent: 1, defaultPageSize: 10 }}
        options={{ fullScreen: true }}
        toolBarRender={() => [
          <Button
            key="button"
            icon={<PlusOutlined />}
            type="primary"
            onClick={() => {
              setModalVisible(true);
              setProfessionId(undefined);
            }}
          >
            新增
          </Button>,
        ]}
      />
      {!isModalVisible ? (
        ''
      ) : (
        <ProfessionForm
          professionId={professionId}
          isModalVisible={isModalVisible}
          setModalVisible={setModalVisible}
          actionRef={actionRef}
        />
      )}
    </>
  );
};

export default ProfessionTable;
