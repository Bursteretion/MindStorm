import React from 'react';
import { message, Modal, Upload } from 'antd';
import { InboxOutlined } from '@ant-design/icons';
import { importQuestion } from '@/services/question';
import { history } from 'umi';

const { Dragger } = Upload;

const CreateUpdateFolderForm = () => {
  const { courseId } = history.location.query;
  const { isModalVisible, setModalVisible, userId, actionRef } = props;

  const draggerProps = {
    name: 'importFile',
    onChange(info) {
      if (info.file.status === 'done') {
        message.success(`题目导入成功！`);
        actionRef.current.reset();
      } else if (info.file.status === 'error') {
        message.error(`导入失败.`);
      }
    },
  };

  return (
    <>
      <Modal
        style={{ top: 40 }}
        destroyOnClose={true}
        maskClosable={false}
        width={500}
        title="题目导入"
        visible={isModalVisible}
        footer={null}
        onCancel={() => setModalVisible(false)}
      >
        <Dragger
          {...draggerProps}
          customRequest={({ file }) => {
            const formData = new FormData();
            formData.append('courseId', courseId);
            formData.append('userId', userId);
            formData.append('importFile', file);
            importQuestion(formData)
              .then((res) => {
                message.success(`题目导入成功！`);
                actionRef.current.reset();
                setModalVisible(false);
              })
              .catch((err) => {
                message.error(err.message);
              });
          }}
        >
          <p className="ant-upload-drag-icon">
            <InboxOutlined />
          </p>
          <p className="ant-upload-text">将Excel文件拖拽至此区域或者点击选择文件</p>
        </Dragger>
      </Modal>
    </>
  );
};

export default CreateUpdateFolderForm;
