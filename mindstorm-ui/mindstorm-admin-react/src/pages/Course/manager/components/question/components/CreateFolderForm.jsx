import React from 'react';
import { Form, message, Modal } from 'antd';
import { ProFormText } from '@ant-design/pro-form';
import { createQuestion } from '@/services/question';

const CreateFolderForm = (props) => {
  const { isModalVisible, setModalVisible, courseId, userId, pid, actionRef } = props;
  const [createFolderForm] = Form.useForm();

  const handleSubmitForm = async (values) => {
    const submit = { ...values, isFolder: true, courseId, userId, pid };
    const res = await createQuestion(submit);
    if (res.success) {
      message.success(`添加成功！`);
      actionRef.current.reset();
      return true;
    }
    message.error(res.message);
    return false;
  };

  return (
    <>
      <Modal
        style={{ top: 40 }}
        okText="添加"
        destroyOnClose={true}
        maskClosable={false}
        width={500}
        title="添加文件夹"
        visible={isModalVisible}
        onCancel={() => setModalVisible(false)}
        onOk={() => {
          createFolderForm.validateFields().then(async (values) => {
            await handleSubmitForm(values);
            createFolderForm.resetFields();
            setModalVisible(false);
          });
        }}
      >
        <Form
          key="form"
          form={createFolderForm}
          preserve={false}
          layout="horizontal"
          labelCol={{ span: 5 }}
        >
          <ProFormText
            name="content"
            label="文件夹名称"
            placeholder="请输入文件夹名称"
            rules={[{ required: true, message: '文件夹名称不能为空！' }]}
          />
        </Form>
      </Modal>
    </>
  );
};

export default CreateFolderForm;
