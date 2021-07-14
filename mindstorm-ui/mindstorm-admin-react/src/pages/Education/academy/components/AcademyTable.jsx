import React, { useRef, useState } from 'react';
import { Button, message, Popconfirm, Switch } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import ProTable from '@ant-design/pro-table';
import {
  AcademyStatus,
  changeAcademyStatus,
  deleteAcademy,
  queryAcademies,
} from '@/services/education/academy';
import AcademyForm from './AcademyForm';
import { useModel } from 'umi';

const AcademyTable = () => {
  const actionRef = useRef();
  const [isModalVisible, setModalVisible] = useState(false);
  const [academyId, setAcademyId] = useState(undefined);
  const { setAcademyTree, generateAcademyTreeSelect } = useModel('academy', (res) => ({
    setAcademyTree: res.setAcademyTree,
    generateAcademyTreeSelect: res.generateAcademyTreeSelect,
  }));

  const handleQueryAcademies = async (params) => {
    const res = await queryAcademies({
      ...params,
      pageIndex: params.current,
      pageSize: params.pageSize,
    });
    const { academyTree = [] } = res.data;
    // processingAcademies(academyTree);
    setAcademyTree(academyTree);
    generateAcademyTreeSelect();
    return {
      data: academyTree,
      total: academyTree.length,
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

  const handleDeleteAcademy = async (academy) => {
    const hide = message.loading(`正在删除院系【${academy.name}】`);
    try {
      await deleteAcademy(academy.id);
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
        const { status, pid } = record;
        // eslint-disable-next-line no-nested-ternary
        return pid === '0' ? (
          status === 1 ? (
            '正常'
          ) : (
            '禁用'
          )
        ) : (
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
        <a
          key="edit"
          onClick={() => {
            setModalVisible(true);
            setAcademyId(record.id);
          }}
        >
          编辑
        </a>,
        <a
          key="create"
          onClick={() => {
            setModalVisible(true);
          }}
        >
          新增
        </a>,
        record.pid === '0' ? (
          ''
        ) : (
          <Popconfirm
            key="delete"
            title={`你确定要删除【${record.name}】这个院系吗？`}
            onConfirm={() => handleDeleteAcademy(record)}
            okText="确定"
            cancelText="取消"
          >
            <a>删除</a>
          </Popconfirm>
        ),
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
        headerTitle="院系列表"
        pagination={null}
        options={{ fullScreen: true }}
        toolBarRender={() => [
          <Button
            key="button"
            icon={<PlusOutlined />}
            type="primary"
            onClick={() => {
              setModalVisible(true);
              setAcademyId(undefined);
            }}
          >
            新增
          </Button>,
        ]}
      />
      {!isModalVisible ? (
        ''
      ) : (
        <AcademyForm
          academyId={academyId}
          isModalVisible={isModalVisible}
          setModalVisible={setModalVisible}
          actionRef={actionRef}
        />
      )}
    </>
  );
};

export default AcademyTable;
