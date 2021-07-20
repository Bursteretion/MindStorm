import React, { useEffect, useState } from 'react';
import { Form, message, Modal, Skeleton, Space, Table, Tabs } from 'antd';
import { ProFormText } from '@ant-design/pro-form';
import {
  createBatchCourseClassStudent,
  createCourseClassStudent,
  listStudentAndCheckedIds,
} from '@/services/courseclassstudent';
import '../index.less';
import ProTable from '@ant-design/pro-table';

const { TabPane } = Tabs;

const StudentCreateForm = (props) => {
  const { isModalVisible, setModalVisible, classId, actionRef } = props;
  const [studentCreateForm] = Form.useForm();
  const [currentType, setCurrentType] = useState('1');
  const [students, setStudents] = useState(undefined);
  const [selectedKeys, setSelectedKeys] = useState([]);

  const fetchStudents = async (params) => {
    const res = await listStudentAndCheckedIds({ ...params, classId });
    if (res.success) {
      setStudents(res.data.students);
    }
  };

  useEffect(() => {
    if (currentType === '2') {
      fetchStudents({});
    }
  }, [currentType]);

  const handleSubmitForm = async (courseClassStudentVO) => {
    if (currentType === '1') {
      const courseClassStudent = { ...courseClassStudentVO, classId };
      const res = await createCourseClassStudent(courseClassStudent);
      if (res.success) {
        message.success(`添加成功！`);
        actionRef.current.reset();
        return true;
      }
      message.error(res.message);
      return false;
    } else {
      const courseClassStudentBatch = { classId, studentIds: selectedKeys };
      const res = await createBatchCourseClassStudent(courseClassStudentBatch);
      if (res.success) {
        message.success(`添加成功！`);
        actionRef.current.reset();
        return true;
      }
      message.error(res.message);
      return false;
    }
  };

  const columns = [
    {
      title: '真实姓名',
      dataIndex: 'realName',
    },
    {
      title: '学号',
      dataIndex: 'sno',
    },
    {
      title: '院系',
      dataIndex: 'academyName',
      search: false,
    },
    {
      title: '专业',
      dataIndex: 'professionName',
      search: false,
    },
  ];

  return (
    <>
      <Modal
        style={{ top: 40 }}
        okText="添加"
        destroyOnClose={true}
        maskClosable={false}
        width={700}
        title="添加学生"
        visible={isModalVisible}
        onCancel={() => setModalVisible(false)}
        onOk={() => {
          studentCreateForm.validateFields().then(async (values) => {
            await handleSubmitForm(values);
            studentCreateForm.resetFields();
            setModalVisible(false);
          });
        }}
      >
        <div className="card-container">
          <Tabs onTabClick={(key) => setCurrentType(key)} type="card">
            <TabPane tab="手动添加" key="1">
              {currentType !== '1' ? (
                ''
              ) : (
                <Form
                  key="form"
                  form={studentCreateForm}
                  preserve={false}
                  layout="horizontal"
                  labelCol={{ span: 4 }}
                >
                  <ProFormText
                    name="realName"
                    label="姓名"
                    placeholder="请输入学生姓名"
                    rules={[{ required: true, message: '学生姓名不能为空！' }]}
                  />
                  <ProFormText
                    name="phoneOrSno"
                    label="电话/学号"
                    placeholder="请输入学生电话或学号"
                    rules={[{ required: true, message: '电话/学号不能为空！' }]}
                  />
                </Form>
              )}
            </TabPane>
            <TabPane tab="从学生库添加" key="2">
              {students === undefined ? (
                <Skeleton active />
              ) : (
                <ProTable
                  rowSelection={{
                    selections: [Table.SELECTION_ALL, Table.SELECTION_INVERT],
                    onChange: (selectedRowKeys) => setSelectedKeys(selectedRowKeys),
                  }}
                  toolbar={{
                    title: '学生库',
                    tooltip: '注意：已经加入该班级的学生不会显示',
                  }}
                  columns={columns}
                  actionRef={actionRef}
                  dataSource={students}
                  rowKey={(record) => record.id}
                  dateFormatter="string"
                  pagination={false}
                  headerTitle="全部学生"
                  search={false}
                  tableAlertRender={({ selectedRowKeys, selectedRows, onCleanSelected }) => (
                    <Space size={24}>
                      <span>
                        已选 {selectedRowKeys.length} 人
                        <a style={{ marginLeft: 8 }} onClick={onCleanSelected}>
                          取消选择
                        </a>
                      </span>
                    </Space>
                  )}
                  tableAlertOptionRender={false}
                />
              )}
            </TabPane>
          </Tabs>
        </div>
      </Modal>
    </>
  );
};

export default StudentCreateForm;
