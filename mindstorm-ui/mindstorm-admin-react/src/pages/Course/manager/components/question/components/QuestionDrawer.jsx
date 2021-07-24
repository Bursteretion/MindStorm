import React, { useEffect, useMemo, useRef, useState } from 'react';
import { FrownOutlined, PlusOutlined } from '@ant-design/icons';
import { Drawer, Skeleton, Space, Tabs, Collapse, Radio, Button } from 'antd';
import styles from '@/pages/Course/manager/style.less';
import ProCard from '@ant-design/pro-card';
import { listQuestionTypes } from '@/services/questiontype';
import { Editor } from '@tinymce/tinymce-react';
import TopicForm from '@/pages/Course/manager/components/question/components/TopicForm';

const { TabPane } = Tabs;
const { Panel } = Collapse;

const QuestionDrawer = (props) => {
  const { isDrawerVisible, setDrawerVisible, courseId, actionRef, userId } = props;
  const [questionTypes, setQuestionTypes] = useState(undefined);
  const [currentTopics, setCurrentTopics] = useState(undefined);
  const [currentQuestionType, setCurrentQuestionType] = useState('0');
  const [isTopicFormVisible, setTopicFormVisible] = useState(false);
  const editorRef = useRef(null);
  const questionVO = {
    courseId,
    userId,
    questionTypeId: '',
    content: '',
    difficulty: 0,
    isFolder: false,
    options: [],
    answerIndex: 0,
    answerValue: '',
    answerAnalyze: '',
    topicIds: [],
  };

  const fetchQuestionTypes = async () => {
    const res = await listQuestionTypes();
    if (res.success) {
      setQuestionTypes(res.data.questionTypes);
    }
  };

  useEffect(() => {
    fetchQuestionTypes();
  }, []);

  const extraSlot = {
    left: <Space style={{ marginRight: 20 }}>题型</Space>,
    right: '',
  };

  const position = ['left', 'right'];

  const slot = useMemo(() => {
    if (position.length === 0) return null;

    return position.reduce((acc, direction) => ({ ...acc, [direction]: extraSlot[direction] }), {});
  }, [position]);

  return (
    <Drawer
      destroyOnClose
      getContainer={false}
      className={styles.drawer}
      title={
        <span>
          <FrownOutlined style={{ marginRight: 10 }} />
          新增题目
        </span>
      }
      width="100%"
      height="100%"
      placement="bottom"
      onClose={() => {
        setDrawerVisible(false);
        actionRef?.current.reset();
      }}
      visible={isDrawerVisible}
    >
      <div style={{ width: '80%', margin: '0 auto' }}>
        <ProCard>
          {questionTypes === undefined ? (
            <Skeleton active />
          ) : (
            <Tabs
              onTabClick={(key = '') => setCurrentQuestionType(key.substr(key.length - 1))}
              defaultActiveKey="1"
              tabBarExtraContent={slot}
            >
              {questionTypes.map((item) => (
                <TabPane tab={item.name} key={item.id + '-' + item.type}></TabPane>
              ))}
            </Tabs>
          )}
        </ProCard>
        <Collapse style={{ marginTop: 20 }}>
          <Panel header="题干" key="1">
            <Editor
              onInit={(evt, editor) => (editorRef.current = editor)}
              apiKey="nzmlokkyb5avilcxl9sdfudwrnb818m5ovuc4c7av96gwue9"
              init={{
                language: 'zh_CN',
                min_height: 400,
                plugins:
                  'preview searchreplace autolink directionality visualblocks visualchars fullscreen image link template code codesample table charmap hr pagebreak nonbreaking anchor insertdatetime advlist lists wordcount imagetools textpattern help emoticons autosave autoresize formatpainter',
                toolbar:
                  'code undo redo restoredraft |' +
                  ' cut copy paste pastetext | ' +
                  'forecolor backcolor bold italic underline strikethrough link anchor |' +
                  ' alignleft aligncenter alignright alignjustify outdent indent |' +
                  ' styleselect formatselect fontselect fontsizeselect |' +
                  ' bullist numlist | blockquote subscript superscript removeformat | ' +
                  'table image media charmap emoticons hr pagebreak insertdatetime print preview | ' +
                  'fullscreen | bdmap indent2em lineheight formatpainter axupimgs',
                fontsize_formats: '12px 14px 16px 18px 24px 36px 48px 56px 72px',
              }}
            />
          </Panel>
          <Panel header="选项" key="2"></Panel>
          <Panel header="答案、解析" key="3"></Panel>
          <Panel header="难度、知识点" key="4">
            <Space direction="vertical">
              <Space>
                题目难度：
                <Radio.Group
                  value={questionVO.difficulty}
                  onChange={(e) => (questionVO.difficulty = e.target.value)}
                >
                  <Radio value={0}>简单</Radio>
                  <Radio value={1}>中等</Radio>
                  <Radio value={2}>困难</Radio>
                </Radio.Group>
              </Space>
              <Space>
                知识点：
                <Button
                  onClick={() => {
                    setTopicFormVisible(true);
                  }}
                  type="link"
                  icon={<PlusOutlined />}
                >
                  关联知识点
                </Button>
                {!isTopicFormVisible ? (
                  ''
                ) : (
                  <TopicForm
                    isModalVisible={isTopicFormVisible}
                    setModalVisible={setTopicFormVisible}
                    setCurrentTopics={setCurrentTopics}
                  />
                )}
              </Space>
            </Space>
          </Panel>
        </Collapse>
      </div>
    </Drawer>
  );
};

export default QuestionDrawer;
