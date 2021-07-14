import React, { useEffect, useRef, useState } from 'react';
import { Button, message, Popconfirm, Switch, Tag, TreeSelect } from 'antd';
import { changeTeacherStatus, deleteTeacher, queryTeachers } from '@/services/user/teacher';
import { AcademyStatus, queryAcademies } from '@/services/education/academy';
import { PlusOutlined } from '@ant-design/icons';
import ProTable from '@ant-design/pro-table';
import TeacherForm from './TeacherForm';
import { useModel } from 'umi';

const TeacherTable = () => {
  const actionRef = useRef();
  const [isModalVisible, setModalVisible] = useState(false);
  const [teacherId, setTeacherId] = useState(undefined);
  const {
    academyTree = [],
    setAcademyTree,
    generateAcademyTreeSelect,
  } = useModel('academy', (res) => ({
    academyTree: res.academyTree,
    setAcademyTree: res.setAcademyTree,
    generateAcademyTreeSelect: res.generateAcademyTreeSelect,
  }));

  const handleQueryTeachers = async (params) => {
    const res = await queryTeachers({
      ...params,
      pageIndex: params.current,
      pageSize: params.pageSize,
    });
    const { records = [], total } = res.data.pageTeachers;
    return {
      data: records,
      total,
      success: res.success,
    };
  };

  const handleChangeTeacherStatus = async (checked, teacher) => {
    const tip = checked ? '启用' : '禁用';
    const hide = message.loading(`正在${tip}教师【${teacher.realName}】`);
    try {
      await changeTeacherStatus(teacher.id, checked ? 1 : 0);
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

  const handleDeleteTeacher = async (teacher) => {
    const hide = message.loading(`正在删除教师【${teacher.realName}】`);
    try {
      await deleteTeacher(teacher.id);
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

  const fetchAcademies = async () => {
    if (academyTree === [] || academyTree.length === 0) {
      const res = await queryAcademies({});
      setAcademyTree(res.data.academyTree);
      generateAcademyTreeSelect();
    }
  };

  useEffect(() => {
    fetchAcademies();
  }, []);

  const columns = [
    {
      title: '所属院系',
      dataIndex: 'academyName',
      search: false,
    },
    {
      title: '所属院系',
      dataIndex: 'academyId',
      hideInTable: true,
      renderFormItem: (item, { type }) => {
        if (type === 'form') {
          return null;
        }
        return (
          <TreeSelect
            allowClear
            showArrow
            placeholder="请选择所属院系/部门"
            treeData={academyTree}
          />
        );
      },
    },
    {
      title: '用户名',
      dataIndex: 'username',
    },
    {
      title: '真实姓名',
      dataIndex: 'realName',
    },
    {
      title: '性别',
      dataIndex: 'sex',
      render: (_, render) => {
        const { sex } = render;
        return <Tag color={sex === 1 ? 'gold' : 'purple'}>{sex === 1 ? '男' : '女'}</Tag>;
      },
    },
    {
      title: '电话',
      dataIndex: 'phone',
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
            onChange={(checked) => handleChangeTeacherStatus(checked, record)}
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
            setTeacherId(record.id);
          }}
        >
          编辑
        </a>,
        record.pid === '0' ? (
          ''
        ) : (
          <Popconfirm
            key="delete"
            title={`你确定要删除【${record.name}】这个院系吗？`}
            onConfirm={() => handleDeleteTeacher(record)}
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
        request={(params) => handleQueryTeachers(params)}
        rowKey={(record) => record.id}
        dateFormatter="string"
        headerTitle="教师列表"
        pagination={null}
        options={{ fullScreen: true }}
        toolBarRender={() => [
          <Button
            key="button"
            icon={<PlusOutlined />}
            type="primary"
            onClick={() => {
              setModalVisible(true);
              setTeacherId(undefined);
            }}
          >
            新增
          </Button>,
        ]}
      />
      {!isModalVisible ? (
        ''
      ) : (
        <TeacherForm
          actionRef={actionRef}
          teacherId={teacherId}
          isModalVisible={isModalVisible}
          setModalVisible={setModalVisible}
        />
      )}
    </>
  );
};

export default TeacherTable;
