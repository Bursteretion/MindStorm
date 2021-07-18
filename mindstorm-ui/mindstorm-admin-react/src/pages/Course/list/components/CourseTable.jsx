import React, { useEffect, useRef, useState } from 'react';
import { Button, message, Popconfirm, Select, Switch, TreeSelect, Avatar, Tag } from 'antd';
import { changeCourseStatus, deleteCourse, queryCourse, CourseStatus } from '@/services/course';
import { queryAcademies } from '@/services/education/academy';
import { PlusOutlined } from '@ant-design/icons';
import ProTable from '@ant-design/pro-table';
import CourseForm from './CourseForm';
import { useModel } from 'umi';
import { listUserSelects } from '@/services/permission/user';
import CourseDrawer from '../../common/CourseDrawer';

const CourseTable = () => {
  const actionRef = useRef();
  const [isModalVisible, setModalVisible] = useState(false);
  const [isDrawerVisible, setDrawerVisible] = useState(false);
  const [userSelects, setUserSelects] = useState([]);
  const [course, setCourse] = useState(undefined);
  const {
    academyTree = [],
    setAcademyTree,
    generateAcademyTreeSelect,
  } = useModel('academy', (res) => ({
    academyTree: res.academyTree,
    setAcademyTree: res.setAcademyTree,
    generateAcademyTreeSelect: res.generateAcademyTreeSelect,
  }));

  const handleQueryCourses = async (params) => {
    const res = await queryCourse({
      ...params,
      pageIndex: params.current,
      pageSize: params.pageSize,
    });
    const { records = [], total } = res.data.coursePage;
    return {
      data: records,
      total,
      success: res.success,
    };
  };

  const handleChangeCourseStatus = async (checked, course) => {
    const tip = checked ? '启用' : '禁用';
    const hide = message.loading(`正在${tip}课程【${course.name}】`);
    try {
      await changeCourseStatus(course.id, checked ? 1 : 0);
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

  const handleDeleteCourse = async (course) => {
    const hide = message.loading(`正在删除课程【${course.name}】`);
    try {
      await deleteCourse(course.id);
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

  const fetchUsers = async () => {
    const res = await listUserSelects();
    setUserSelects(res.data.userSelects);
  };

  useEffect(() => {
    fetchAcademies();
    fetchUsers();
  }, []);

  const columns = [
    {
      title: '课程封面',
      dataIndex: 'thumbnail',
      hideInSearch: true,
      render: (_, record) => {
        const { thumbnail } = record;
        return <Avatar shape="square" size={84} src={thumbnail} />;
      },
    },
    {
      title: '课程名称',
      dataIndex: 'name',
    },
    {
      title: '所属院系',
      dataIndex: 'academyName',
      hideInSearch: true,
    },
    {
      title: '课程所有者',
      dataIndex: 'ownerName',
      hideInSearch: true,
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
      title: '课程所有者',
      dataIndex: 'userId',
      hideInTable: true,
      renderFormItem: (item, { type }) => {
        if (type === 'form') {
          return null;
        }
        return <Select allowClear showArrow placeholder="请选择课程所有者" options={userSelects} />;
      },
    },
    {
      title: '教师姓名',
      dataIndex: 'teacherName',
    },
    {
      title: '班级个数',
      dataIndex: 'classCount',
      hideInSearch: true,
      render: (_, record) => {
        const { classCount } = record;
        return <Tag color="#2db7f5">{classCount}</Tag>;
      },
    },
    {
      title: '状态',
      dataIndex: 'status',
      valueType: 'select',
      valueEnum: CourseStatus,
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
            onChange={(checked) => handleChangeCourseStatus(checked, record)}
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
            setDrawerVisible(true);
            setCourse(record);
          }}
        >
          编辑
        </a>,
        record.pid === '0' ? (
          ''
        ) : (
          <Popconfirm
            key="delete"
            title={`你确定要删除【${record.name}】这个课程吗？`}
            onConfirm={() => handleDeleteCourse(record)}
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
        request={(params) => handleQueryCourses(params)}
        rowKey={(record) => record.id}
        dateFormatter="string"
        headerTitle="课程列表"
        pagination={null}
        options={{ fullScreen: true }}
        toolBarRender={() => [
          <Button
            key="button"
            icon={<PlusOutlined />}
            type="primary"
            onClick={() => {
              setModalVisible(true);
            }}
          >
            新增
          </Button>,
        ]}
      />
      {!isModalVisible ? (
        ''
      ) : (
        <CourseForm
          actionRef={actionRef}
          isModalVisible={isModalVisible}
          setModalVisible={setModalVisible}
        />
      )}
      {!isDrawerVisible && course === undefined ? (
        ''
      ) : (
        <CourseDrawer
          course={course}
          actionRef={actionRef}
          isDrawerVisible={isDrawerVisible}
          setDrawerVisible={setDrawerVisible}
        />
      )}
    </>
  );
};

export default CourseTable;
