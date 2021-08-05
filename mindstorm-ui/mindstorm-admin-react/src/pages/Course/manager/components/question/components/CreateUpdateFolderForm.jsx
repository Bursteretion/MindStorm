import React, { useEffect, useState } from 'react';
import { Form, message, Modal, Skeleton } from 'antd';
import { ProFormText } from '@ant-design/pro-form';
import { createQuestion, infoFolder, renameFolder } from '@/services/question';
import { history } from 'umi';

const CreateUpdateFolderForm = (props) => {
  const { courseId } = history.location.query;
  const { isModalVisible, setModalVisible, userId, pid, actionRef, questionId } = props;
  const [FolderForm] = Form.useForm();
  const [folder, setFolder] = useState(undefined);

  useEffect(() => {
    async function fetchFolder() {
      if (questionId !== undefined) {
        const res = await infoFolder(questionId);
        if (res.success) {
          setFolder(res.data.folder);
        }
      }
    }

    fetchFolder();
  }, [questionId]);

  const handleSubmitForm = async (values) => {
    const folderVO = { ...folder, ...values };
    const tip = questionId === undefined ? '添加' : '重命名';
    const hide = message.loading(`正在${tip}文件夹【${values.originalContent}】`);
    try {
      if (questionId === undefined) {
        await createQuestion({ ...folderVO, courseId, pid, userId, isFolder: true });
      } else {
        await renameFolder(folderVO);
      }
      hide();
      message.success(`${tip}成功！`);
      actionRef.current.reset();
      return true;
    } catch (e) {
      hide();
      message.error(`${tip}失败！`);
      return false;
    }
  };

  const modalTitle = questionId === undefined ? '添加文件夹' : '重命名';
  const modalOk = questionId === undefined ? '添加' : '保存';

  return (
    <>
      <Modal
        style={{ top: 40 }}
        okText={modalOk}
        destroyOnClose={true}
        maskClosable={false}
        width={500}
        title={modalTitle}
        visible={isModalVisible}
        onCancel={() => setModalVisible(false)}
        onOk={() => {
          FolderForm.validateFields().then(async (values) => {
            await handleSubmitForm(values);
            FolderForm.resetFields();
            setModalVisible(false);
          });
        }}
      >
        {folder === undefined && questionId !== undefined ? (
          <Skeleton active />
        ) : (
          <Form
            key="form"
            form={FolderForm}
            preserve={false}
            layout="horizontal"
            labelCol={{ span: 5 }}
            initialValues={folder}
          >
            <ProFormText
              name="originalContent"
              label="文件夹名称"
              placeholder="请输入文件夹名称"
              rules={[{ required: true, message: '文件夹名称不能为空！' }]}
            />
          </Form>
        )}
      </Modal>
    </>
  );
};

export default CreateUpdateFolderForm;
