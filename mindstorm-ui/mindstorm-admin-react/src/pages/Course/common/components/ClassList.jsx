import React, { useEffect, useRef, useState } from 'react';
import ProList from '@ant-design/pro-list';
import { Tag, message, Popconfirm, Row, Col, Input, Button } from 'antd';
import { deleteCourseClass, queryCourseClass, updateCourseClass } from '@/services/courseclass';
import { PlusOutlined } from '@ant-design/icons';

const { Search } = Input;

const ClassList = (props) => {
  const { courseId } = props;
  const actionRef = useRef();
  const [courseClasses, setCourseClasses] = useState([]);
  const [loading, setLoading] = useState(false);

  const handleQueryCourseClass = async (params) => {
    const res = await queryCourseClass(params);
    if (res.code === 20000) {
      setCourseClasses(res.data.courseClasses);
    }
  };

  useEffect(() => {
    if (courseId !== undefined) {
      handleQueryCourseClass({ courseId, className: '' });
    }
  }, [courseId]);

  const handleDeleteCourseClass = async (courseClass) => {
    const hide = message.loading(`正在删除班级【${courseClass.className}】`);
    try {
      await deleteCourseClass(courseClass.id);
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

  return (
    <>
      <Row>
        <Col span={8}>
          <Button type="primary" icon={<PlusOutlined />}>
            新建班级
          </Button>
        </Col>
        <Col span={8} offset={8}>
          <Search
            placeholder="搜索班级"
            loading={loading}
            onSearch={(value) => handleQueryCourseClass({ courseId, className: value })}
            enterButton
          />
        </Col>
      </Row>
      <ProList
        rowKey={(record) => record.id}
        headerTitle="班级列表"
        showActions="hover"
        dataSource={courseClasses}
        split={true}
        actionRef={actionRef}
        size="large"
        tooltip="该课程所有班级"
        editable={{
          onSave: async (key, record) => {
            const hide = message.loading('正在保存班级信息');
            const { className, sort } = record;
            try {
              await updateCourseClass({ id: key, className, sort });
              hide();
              message.success(`保存成功！`);
              return true;
            } catch (error) {
              hide();
              message.error(`保存失败请重试！`);
              return false;
            }
          },
        }}
        metas={{
          title: {
            dataIndex: 'className',
          },
          description: {
            dataIndex: 'studentCount',
            editable: false,
            render: (_, { studentCount }) => {
              return <span>学生人数：{studentCount}</span>;
            },
          },
          subTitle: {
            dataIndex: 'sort',
            valueType: 'digit',
            render: (_, { sort }) => <Tag color="success">排序：{sort}</Tag>,
          },
          extra: {
            editable: false,
            render: (_, { invitationCode }) => (
              <span style={{ marginLeft: 20 }}>邀请码：{invitationCode}</span>
            ),
          },
          actions: {
            render: (text, row, index, action) => [
              <a target="_blank" key="link">
                管理
              </a>,
              <a key="edit" onClick={() => action?.startEditable(row.id)}>
                编辑
              </a>,
              <Popconfirm
                key="delete"
                title={`你确定要删除【${row.className}】这个班级吗？`}
                onConfirm={() => handleDeleteCourseClass(row)}
                okText="确定"
                cancelText="取消"
              >
                删除
              </Popconfirm>,
            ],
          },
        }}
      />
    </>
  );
};

export default ClassList;
