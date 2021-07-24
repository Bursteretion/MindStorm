import React, { useEffect, useState } from 'react';
import { Button, Modal, Skeleton, Space, Transfer } from 'antd';
import { listTopics } from '@/services/topic';
import { PlusOutlined } from '@ant-design/icons';
import CreateUpdateTopicForm from './CreateUpdateTopicForm';
import '../style.less';

const TopicForm = (props) => {
  const { isModalVisible, setModalVisible, questionId, setCurrentTopics } = props;
  const [topics, setTopics] = useState(undefined);
  const [questionTopics, setQuestionTopics] = useState([]);
  const [isCreateUpdateTopicFormVisible, setCreateUpdateTopicFormVisible] = useState(false);
  const [currentTopicId, setCurrentTopicId] = useState(undefined);

  const renderItem = (item) => {
    const customLabel = (
      <div className="transfer-item">
        <div>
          <span>{item.name}</span>
        </div>
        <Space className="operate">
          <a
            onClick={(e) => {
              e.stopPropagation();
              setCurrentTopicId(item.id);
              setCreateUpdateTopicFormVisible(true);
            }}
          >
            编辑
          </a>
          <a>删除</a>
        </Space>
      </div>
    );

    return {
      label: customLabel,
      value: item.id,
    };
  };

  const handleChange = (targetKeys, direction, moveKeys) => {
    console.log(targetKeys, direction, moveKeys);
    setQuestionTopics(targetKeys);
  };

  const fetchTopics = async () => {
    const res = await listTopics();
    if (res.success) {
      setTopics(res.data.topics);
    }
  };

  useEffect(() => {
    fetchTopics();
  }, []);

  return (
    <>
      <Modal
        style={{ top: 100 }}
        getContainer={false}
        okText="确定"
        destroyOnClose
        maskClosable={false}
        width={600}
        title="关联知识点"
        visible={isModalVisible}
        onCancel={() => setModalVisible(false)}
        onOk={() => {
          setCurrentTopics(topics);
        }}
      >
        {topics === undefined ? (
          <Skeleton active />
        ) : (
          <Transfer
            rowKey={(record) => record.id}
            showSearch
            titles={[
              <Button
                key="create"
                type="link"
                icon={<PlusOutlined />}
                onClick={() => {
                  setCurrentTopicId(undefined);
                  setCreateUpdateTopicFormVisible(true);
                }}
              >
                新建知识点
              </Button>,
              '',
            ]}
            dataSource={topics}
            listStyle={{
              width: 300,
              height: 300,
            }}
            targetKeys={questionTopics}
            onChange={handleChange}
            render={(item) => renderItem(item)}
          />
        )}
        {!isCreateUpdateTopicFormVisible ? (
          ''
        ) : (
          <CreateUpdateTopicForm
            isCreateUpdateTopicFormVisible={isCreateUpdateTopicFormVisible}
            setCreateUpdateTopicFormVisible={setCreateUpdateTopicFormVisible}
            topicId={currentTopicId}
            fetchTopics={fetchTopics}
          />
        )}
      </Modal>
    </>
  );
};

export default TopicForm;
