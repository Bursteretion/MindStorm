import React, { useEffect, useRef, useState } from 'react';
import { Button, message, Popconfirm, Switch, Tree } from 'antd';
import { DownOutlined, PlusOutlined } from '@ant-design/icons';
import ProTable from '@ant-design/pro-table';
import ProfessionForm from './ProfessionForm';
import { useModel } from 'umi';
import '../index.less';
import {
  changeProfessionStatus,
  deleteProfession,
  ProfessionStatus,
  queryProfessions,
} from '@/services/profession';
import ProCard from '@ant-design/pro-card';
import { queryAcademies } from '@/services/academy';

const ProfessionTable = (props) => {
  const { currentSelectedAcademyId } = props;
  const actionRef = useRef();
  const [isModalVisible, setModalVisible] = useState(false);
  const [professionId, setProfessionId] = useState(undefined);

  const handleQueryProfessions = async (params) => {
    const res = await queryProfessions({
      academyId: currentSelectedAcademyId,
      ...params,
      professionName: params.name,
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

  useEffect(() => {
    actionRef.current?.reload();
  }, [currentSelectedAcademyId]);

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
        headerTitle="专业列表"
        pagination={{
          defaultCurrent: 1,
          defaultPageSize: 10,
        }}
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

const ProfessionMain = () => {
  const [currentSelectedAcademyId, setCurrentSelectedAcademyId] = useState(undefined);
  const [defaultSelectedKeys, setDefaultSelectedKeys] = useState(undefined);
  const {
    academyTree = [],
    setAcademyTree,
    generateAcademyTreeSelect,
  } = useModel('academy', (res) => ({
    academyTree: res.academyTree,
    setAcademyTree: res.setAcademyTree,
    generateAcademyTreeSelect: res.generateAcademyTreeSelect,
  }));

  useEffect(() => {
    async function fetchAcademies() {
      if (academyTree === [] || academyTree.length === 0) {
        const res = await queryAcademies({});
        setAcademyTree(res.data.academyTree);
        generateAcademyTreeSelect();
      }
    }

    fetchAcademies();
  }, []);

  return (
    <ProCard split="vertical">
      <ProCard className="card" colSpan="20%" ghost title="院系">
        <Tree
          defaultSelectedKeys={defaultSelectedKeys}
          onSelect={(selectedKeys) => {
            setCurrentSelectedAcademyId(selectedKeys[0]);
            setDefaultSelectedKeys(selectedKeys[0]);
          }}
          className="academyTree"
          defaultExpandAll
          switcherIcon={<DownOutlined />}
          treeData={academyTree}
        />
      </ProCard>
      <ProCard>
        <ProfessionTable currentSelectedAcademyId={currentSelectedAcademyId} />
      </ProCard>
    </ProCard>
  );
};

export default ProfessionMain;
