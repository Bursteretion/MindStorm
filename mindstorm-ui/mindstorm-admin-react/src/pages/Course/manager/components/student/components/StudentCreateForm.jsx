import React, { useState } from 'react';
import { Form, message, Modal, Tabs } from 'antd';
import { ProFormDigit, ProFormText } from '@ant-design/pro-form';
import { createCourseClassStudent } from '@/services/courseclassstudent';

const { TabPane } = Tabs;

const StudentCreateForm = (props) => {
  const { isModalVisible, setModalVisible, classId, actionRef } = props;
  const [studentCreateForm] = Form.useForm();
  const [currentType, setCurrentType] = useState(1);

  const handleSubmitForm = async (courseClassStudentVO) => {
    const courseClassStudent = { ...courseClassStudentVO, classId };
    const hide = message.loading(`正在添加学生【${courseClassStudent.realName}】`);
    try {
      await createCourseClassStudent(courseClassStudent);
      hide();
      message.success(`添加成功！`);
      actionRef?.current.reset();
      return true;
    } catch (error) {
      hide();
      message.error(`添加失败请重试！`);
      return false;
    }
  };

  return (
    <>
      <Modal
        style={{ top: 40 }}
        okText="添加"
        destroyOnClose={true}
        maskClosable={false}
        width={600}
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
        <Tabs>
          <TabPane onClick={() => console.log(111)} tab="手动添加" key="1">
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
          </TabPane>
          <TabPane tab="Tab Title 2" key="2">
            <p>Content of Tab Pane 2</p>
            <p>Content of Tab Pane 2</p>
            <p>Content of Tab Pane 2</p>
          </TabPane>
          <TabPane tab="Tab Title 3" key="3">
            <p>Content of Tab Pane 3</p>
            <p>Content of Tab Pane 3</p>
            <p>Content of Tab Pane 3</p>
          </TabPane>
        </Tabs>
      </Modal>
    </>
  );
};

export default StudentCreateForm;
