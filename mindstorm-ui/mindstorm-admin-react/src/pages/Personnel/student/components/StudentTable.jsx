import React, { useEffect, useRef, useState } from 'react';
import { Button, message, Popconfirm, Select, Switch, Tag, TreeSelect } from 'antd';
import { changeStudentStatus, deleteStudent, queryStudents } from '@/services/user/student';
import { AcademyStatus, queryAcademies } from '@/services/education/academy';
import { PlusOutlined } from '@ant-design/icons';
import ProTable from '@ant-design/pro-table';
import StudentForm from './StudentForm';
import { useModel } from 'umi';
import { listSelectProfessions } from '@/services/education/profession';
import { ProFormSelect } from '@ant-design/pro-form';

const StudentTable = () => {
  const actionRef = useRef();
  const [isModalVisible, setModalVisible] = useState(false);
  const [studentId, setStudentId] = useState(undefined);
  const [professions, setProfessions] = useState([]);
  const {
    academyTree = [],
    setAcademyTree,
    generateAcademyTreeSelect,
  } = useModel('academy', (res) => ({
    academyTree: res.academyTree,
    setAcademyTree: res.setAcademyTree,
    generateAcademyTreeSelect: res.generateAcademyTreeSelect,
  }));

  const handleQueryStudents = async (params) => {
    const res = await queryStudents({
      ...params,
      pageIndex: params.current,
      pageSize: params.pageSize,
    });
    const { records = [], total } = res.data.pageStudents;
    return {
      data: records,
      total,
      success: res.success,
    };
  };

  const handleChangeStudentStatus = async (checked, student) => {
    const tip = checked ? '启用' : '禁用';
    const hide = message.loading(`正在${tip}学生【${student.realName}】`);
    try {
      await changeStudentStatus(student.id, checked ? 1 : 0);
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

  const handleDeleteStudent = async (student) => {
    const hide = message.loading(`正在删除学生【${student.realName}】`);
    try {
      await deleteStudent(student.id);
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

  const fetchProfessions = async () => {
    if (professions === [] || professions.length === 0) {
      const res = await listSelectProfessions();
      setProfessions(res.data.professions);
    }
  };

  useEffect(() => {
    fetchAcademies();
    fetchProfessions();
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
      title: '专业',
      dataIndex: 'professionName',
      search: false,
    },
    {
      title: '专业',
      dataIndex: 'professionId',
      hideInTable: true,
      renderFormItem: (item, { type }) => {
        if (type === 'form') {
          return null;
        }
        return (
          <Select
            showSearch
            allowClear
            filterOption={(input, option) =>
              option.label.toLowerCase().indexOf(input.toLowerCase()) >= 0
            }
            name="professionId"
            options={professions}
            placeholder="请选择所属专业"
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
      title: '学号',
      dataIndex: 'sno',
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
            onChange={(checked) => handleChangeStudentStatus(checked, record)}
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
            setStudentId(record.id);
          }}
        >
          编辑
        </a>,
        <a
          key="create"
          onClick={() => {
            setModalVisible(true);
            setStudentId(undefined);
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
            onConfirm={() => handleDeleteStudent(record)}
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
        request={(params) => handleQueryStudents(params)}
        rowKey={(record) => record.id}
        dateFormatter="string"
        headerTitle="学生列表"
        pagination={null}
        options={{ fullScreen: true }}
        toolBarRender={() => [
          <Button
            key="button"
            icon={<PlusOutlined />}
            type="primary"
            onClick={() => {
              setModalVisible(true);
              setStudentId(undefined);
            }}
          >
            新增
          </Button>,
        ]}
      />
      {!isModalVisible ? (
        ''
      ) : (
        <StudentForm
          actionRef={actionRef}
          studentId={studentId}
          isModalVisible={isModalVisible}
          setModalVisible={setModalVisible}
        />
      )}
    </>
  );
};

export default StudentTable;
