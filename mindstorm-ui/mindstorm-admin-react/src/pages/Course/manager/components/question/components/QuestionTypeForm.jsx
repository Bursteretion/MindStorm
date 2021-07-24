import React, { useRef, useState } from 'react';
import { Button, message, Modal, Popconfirm } from 'antd';
import ProTable from '@ant-design/pro-table';
import styles from '@/pages/Course/manager/style.less';
import { deleteQuestionType, listQuestionTypes } from '@/services/questiontype';
import { PlusOutlined } from '@ant-design/icons';
import CreateUpdateQuestionTypeForm from './CreateUpdateQuestionTypeForm';

const QuestionTypeForm = (props) => {
  const { isModalVisible, setModalVisible } = props;
  const [isCreateUpdateFormVisible, setCreateUpdateFormVisible] = useState(false);
  const [currentQuestionTypeId, setCurrentQuestionTypeId] = useState(undefined);
  const actionRef = useRef();

  const handleDeleteQuestionType = async (questionType) => {
    const hide = message.loading(`正在删除题型【${questionType.name}】`);
    const res = await deleteQuestionType(questionType.id);
    if (res.success) {
      hide();
      message.success('删除成功!');
    } else {
      hide();
      message.error(res.message);
    }
    actionRef?.current.reset();
  };

  const handleQueryQuestionTypes = async () => {
    const res = await listQuestionTypes();
    const { questionTypes = [] } = res.data;
    return {
      data: questionTypes,
      total: questionTypes.length,
      success: res.success,
    };
  };

  const columns = [
    {
      title: '题型名称',
      dataIndex: 'name',
      width: 200,
    },
    {
      title: '类型',
      dataIndex: 'typeName',
      width: 170,
    },
    {
      title: '操作',
      valueType: 'option',
      render: (_, record) => [
        <a
          key="edit"
          onClick={() => {
            setCurrentQuestionTypeId(record.id);
            setCreateUpdateFormVisible(true);
          }}
        >
          重命名
        </a>,
        !record.isCustomize ? (
          ''
        ) : (
          <Popconfirm
            key="delete"
            title={`你确定要删除【${record.name}】这个题目类型吗？`}
            onConfirm={() => handleDeleteQuestionType(record)}
            okText="确定"
            cancelText="取消"
          >
            <a>移除</a>
          </Popconfirm>
        ),
      ],
    },
  ];

  return (
    <>
      <Modal
        style={{ top: 40 }}
        bodyStyle={{ paddingTop: 0, paddingLeft: 10, paddingRight: 10 }}
        okText="添加"
        destroyOnClose={true}
        maskClosable={false}
        width={500}
        title="题型管理"
        visible={isModalVisible}
        onCancel={() => setModalVisible(false)}
        footer={[
          <Button
            icon={<PlusOutlined />}
            type="link"
            key="create"
            onClick={() => {
              setCurrentQuestionTypeId(undefined);
              setCreateUpdateFormVisible(true);
            }}
          >
            新增题型
          </Button>,
          <Button key="cancel" onClick={() => setModalVisible(false)}>
            取消
          </Button>,
          <Button
            key="confirm"
            type="primary"
            onClick={() => {
              setModalVisible(false);
            }}
          >
            确定
          </Button>,
        ]}
      >
        <ProTable
          className={styles.table}
          columns={columns}
          actionRef={actionRef}
          rowKey={(record) => record.id}
          request={() => handleQueryQuestionTypes()}
          dateFormatter="string"
          pagination={false}
          toolBarRender={false}
          search={false}
        />
        {!isCreateUpdateFormVisible && currentQuestionTypeId === undefined ? (
          ''
        ) : (
          <CreateUpdateQuestionTypeForm
            actionRef={actionRef}
            isCreateUpdateFormVisible={isCreateUpdateFormVisible}
            setCreateUpdateFormVisible={setCreateUpdateFormVisible}
            questionTypeId={currentQuestionTypeId}
          />
        )}
      </Modal>
    </>
  );
};

export default QuestionTypeForm;
