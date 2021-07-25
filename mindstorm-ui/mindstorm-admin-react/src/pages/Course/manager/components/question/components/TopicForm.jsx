import React, { useEffect, useState } from 'react';
import { Button, message, Modal, Skeleton, Space, Transfer } from 'antd';
import { deleteTopic, listTopics } from '@/services/topic';
import { PlusOutlined } from '@ant-design/icons';
import CreateUpdateTopicForm from './CreateUpdateTopicForm';
import '../style.less';

const TopicForm = (props) => {
  const { isModalVisible, setModalVisible, questionId, setCurrentTopics, currentTopics } = props;
  const [topics, setTopics] = useState(undefined);
  const [questionTopics, setQuestionTopics] = useState([...currentTopics.map((topic) => topic.id)]);
  const [isCreateUpdateTopicFormVisible, setCreateUpdateTopicFormVisible] = useState(false);
  const [currentTopicId, setCurrentTopicId] = useState(undefined);
  const [selectedTopics, setSelectedTopics] = useState([]);

  const fetchTopics = async () => {
    const res = await listTopics();
    if (res.success) {
      setTopics(res.data.topics);
    }
  };

  useEffect(() => {
    fetchTopics();
  }, []);

  const handleDeleteTopic = async (topic) => {
    const hide = message.loading(`正在删除知识点【${topic.name}】`);
    const res = await deleteTopic(topic.id);
    if (res.success) {
      hide();
      message.success('删除成功!');
    } else {
      hide();
      message.error(res.message);
    }
    fetchTopics();
  };

  const renderItem = (item) => {
    return (
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
          <a
            onClick={(e) => {
              e.stopPropagation();
              handleDeleteTopic(item);
            }}
          >
            删除
          </a>
        </Space>
      </div>
    );
  };

  const handleChange = (targetKeys, direction, moveKeys) => {
    setQuestionTopics(targetKeys);

    setSelectedTopics(topics.filter((item) => targetKeys.find((t) => t === item.id)));
  };

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
          setCurrentTopics(selectedTopics);
          setModalVisible(false);
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
