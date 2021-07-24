import React, { useEffect, useState } from 'react';
import { Form, message, Modal, Skeleton } from 'antd';
import { ProFormText, ProFormRadio } from '@ant-design/pro-form';
import {
  createQuestionType,
  infoQuestionType,
  QuestionType,
  updateQuestionType,
} from '@/services/questiontype';

const CreateUpdateQuestionTypeForm = (props) => {
  const { isCreateUpdateFormVisible, setCreateUpdateFormVisible, questionTypeId, actionRef } =
    props;
  const [initialValues, setInitialValues] = useState(undefined);
  const [questionTypeForm] = Form.useForm();

  useEffect(() => {
    async function fetchQuestionType() {
      if (questionTypeId !== undefined) {
        const res = await infoQuestionType(questionTypeId);
        if (res.success) {
          const { questionType } = res.data;
          setInitialValues(questionType);
        }
      }
    }

    fetchQuestionType();
  }, []);

  const handleSubmitForm = async (values) => {
    const tip = questionTypeId === undefined ? '添加' : '重命名';
    const hide = message.loading(`正在${tip}题型【${values.name}】`);
    const questionType = { ...values, isCustomize: 1 };
    try {
      if (questionTypeId === undefined) {
        await createQuestionType(questionType);
      } else {
        await updateQuestionType({ ...questionType, id: questionTypeId });
      }
      hide();
      message.success(`${tip}成功！`);
      setInitialValues(undefined);
      actionRef.current.reset();
      return true;
    } catch (error) {
      hide();
      message.error(`${tip}失败请重试！`);
      return false;
    }
  };

  const okText = questionTypeId === undefined ? '新增' : '修改';
  const title = questionTypeId === undefined ? '新增题型' : '重命名';

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
        visible={isCreateUpdateFormVisible}
        onCancel={() => setCreateUpdateFormVisible(false)}
        onOk={() => {
          questionTypeForm.validateFields().then(async (values) => {
            await handleSubmitForm(values);
            questionTypeForm.resetFields();
            setCreateUpdateFormVisible(false);
          });
        }}
      >
        {initialValues === undefined && questionTypeId !== undefined ? (
          <Skeleton active />
        ) : (
          <Form
            key="form"
            form={questionTypeForm}
            preserve={false}
            layout="horizontal"
            labelCol={{ span: 5 }}
            initialValues={initialValues}
          >
            <ProFormText
              name="name"
              label="题型名称"
              placeholder="请输入题型名称"
              rules={[{ required: true, message: '题型名称不能为空！' }]}
            />
            {questionTypeId === undefined ? (
              <ProFormRadio.Group name="type" label="题目类型" options={QuestionType} />
            ) : (
              ''
            )}
          </Form>
        )}
      </Modal>
    </>
  );
};

export default CreateUpdateQuestionTypeForm;
