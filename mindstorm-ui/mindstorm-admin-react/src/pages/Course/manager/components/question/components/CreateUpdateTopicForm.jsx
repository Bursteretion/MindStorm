import React, { useEffect, useState } from 'react';
import { Form, message, Modal, Skeleton } from 'antd';
import { ProFormText } from '@ant-design/pro-form';
import { createTopic, infoTopic, updateTopic } from '@/services/topic';

const CreateUpdateTopicForm = (props) => {
  const { isCreateUpdateTopicFormVisible, setCreateUpdateTopicFormVisible, topicId, fetchTopics } =
    props;
  const [initialValues, setInitialValues] = useState(undefined);
  const [topicForm] = Form.useForm();

  useEffect(() => {
    async function fetchTopic() {
      if (topicId !== undefined) {
        const res = await infoTopic(topicId);
        if (res.success) {
          const { topic } = res.data;
          setInitialValues(topic);
        }
      } else {
        setInitialValues({});
      }
    }

    fetchTopic();
  }, []);

  const handleSubmitForm = async (values) => {
    const tip = topicId === undefined ? '添加' : '重命名';
    const hide = message.loading(`正在${tip}知识点【${values.name}】`);
    try {
      if (topicId === undefined) {
        await createTopic(values);
      } else {
        await updateTopic({ ...values, id: topicId });
      }
      hide();
      message.success(`${tip}成功！`);
      setInitialValues(undefined);
      fetchTopics();
      return true;
    } catch (error) {
      hide();
      message.error(`${tip}失败请重试！`);
      return false;
    }
  };

  const okText = topicId === undefined ? '新增' : '修改';
  const title = topicId === undefined ? '新增知识点' : '重命名';

  return (
    <>
      <Modal
        style={{ top: 100 }}
        getContainer={false}
        okText={okText}
        destroyOnClose
        maskClosable={false}
        width={500}
        title={title}
        visible={isCreateUpdateTopicFormVisible}
        onCancel={() => setCreateUpdateTopicFormVisible(false)}
        onOk={() => {
          topicForm.validateFields().then(async (values) => {
            await handleSubmitForm(values);
            topicForm.resetFields();
            setCreateUpdateTopicFormVisible(false);
          });
        }}
      >
        {initialValues === undefined && topicId !== undefined ? (
          <Skeleton active />
        ) : (
          <Form
            key="form"
            form={topicForm}
            preserve={false}
            layout="horizontal"
            labelCol={{ span: 5 }}
            initialValues={initialValues}
          >
            <ProFormText
              name="name"
              label="知识点名称"
              placeholder="请输入知识点名称"
              rules={[{ required: true, message: '知识点名称不能为空！' }]}
            />
          </Form>
        )}
      </Modal>
    </>
  );
};

export default CreateUpdateTopicForm;
