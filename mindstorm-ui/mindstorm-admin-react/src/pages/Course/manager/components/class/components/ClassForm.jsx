import React from 'react';
import { Form, message, Modal } from 'antd';
import { ProFormDigit, ProFormText } from '@ant-design/pro-form';
import { createCourseClass } from '@/services/courseclass';

const ClassForm = (props) => {
  const [courseClassForm] = Form.useForm();
  const { isModalVisible, setModalVisible, handleQueryCourseClass, courseId } = props;

  const handleSubmitForm = async (courseClassVO) => {
    const courseClass = { ...courseClassVO, courseId };
    const hide = message.loading(`正在创建班级【${courseClassVO.className}】`);
    try {
      await createCourseClass(courseClass);
      hide();
      message.success(`创建成功！`);
      handleQueryCourseClass({ courseId, className: '' });
      return true;
    } catch (error) {
      hide();
      message.error(`创建失败请重试！`);
      return false;
    }
  };

  return (
    <Modal
      style={{ top: 40 }}
      okText="创建"
      destroyOnClose={true}
      maskClosable={false}
      width={500}
      title="新建班级"
      visible={isModalVisible}
      onCancel={() => setModalVisible(false)}
      onOk={() => {
        courseClassForm.validateFields().then(async (values) => {
          await handleSubmitForm(values);
          courseClassForm.resetFields();
          setModalVisible(false);
        });
      }}
    >
      <Form
        key="form"
        form={courseClassForm}
        preserve={false}
        layout="horizontal"
        labelCol={{ span: 4 }}
      >
        <ProFormText
          name="className"
          label="班级名称"
          placeholder="请输入班级名称"
          rules={[{ required: true, message: '班级名称不能为空！' }]}
        />
        <ProFormDigit
          name="sort"
          label="班级排序"
          rules={[{ required: true, message: '班级排序不能为空！' }]}
        />
      </Form>
    </Modal>
  );
};

export default ClassForm;
