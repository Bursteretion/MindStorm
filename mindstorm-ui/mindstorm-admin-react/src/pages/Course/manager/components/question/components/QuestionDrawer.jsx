import React, { useEffect, useMemo, useRef, useState } from 'react';
import {
  CheckOutlined,
  CloseOutlined,
  FrownOutlined,
  MinusCircleOutlined,
  PlusOutlined,
} from '@ant-design/icons';
import {
  Drawer,
  Skeleton,
  Space,
  Tabs,
  Collapse,
  Radio,
  Button,
  Tag,
  Form,
  Input,
  Checkbox,
  Switch,
  Alert,
} from 'antd';
import styles from '@/pages/Course/manager/style.less';
import ProCard from '@ant-design/pro-card';
import { listQuestionTypes } from '@/services/questiontype';
import { Editor } from '@tinymce/tinymce-react';
import TopicForm from '@/pages/Course/manager/components/question/components/TopicForm';
import { uploadQuestionImage } from '@/services/attachment';

const { TabPane } = Tabs;
const { Panel } = Collapse;

const QuestionDrawer = (props) => {
  const { isDrawerVisible, setDrawerVisible, courseId, actionRef, userId } = props;
  const [questionTypes, setQuestionTypes] = useState(undefined);
  const [currentTopics, setCurrentTopics] = useState([]);
  const [currentQuestionType, setCurrentQuestionType] = useState(0);
  const [isTopicFormVisible, setTopicFormVisible] = useState(false);
  const [extraOptions, setExtraOptions] = useState([]);
  const editorRef = useRef(null);
  const questionVO = {
    courseId,
    userId,
    questionTypeId: '',
    content: '',
    difficulty: 0,
    isFolder: false,
    options: [],
    answerIndex: [0],
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

  const handleExtraOptions = (fields = [], type) => {
    const options = fields.map((item) => ({
      value: item.name + 4,
      name: String.fromCharCode(item.name + 69),
    }));
    if (type === 1) {
      setExtraOptions([
        ...options,
        {
          value: fields.length + 4,
          name: String.fromCharCode(fields.length + 69),
        },
      ]);
    } else if (fields.length === 1) {
      setExtraOptions([]);
    } else {
      setExtraOptions(options.slice(0, fields.length - 1));
    }
  };

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
              onTabClick={(key = '') => setCurrentQuestionType(Number(key.substr(key.length - 1)))}
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
                images_upload_handler: async (blobInfo, successFun, failFun) => {
                  const file = blobInfo.blob();
                  const formData = new FormData();
                  formData.append('image', file);
                  const res = await uploadQuestionImage(formData);
                  if (res.success) {
                    const { questionAttachment } = res.data;
                    successFun(questionAttachment.path);
                  } else {
                    failFun(res.message);
                  }
                },
              }}
            />
          </Panel>
          {!(currentQuestionType === 0 || currentQuestionType === 1) ? (
            ''
          ) : (
            <Panel header="选项" key="2">
              <Form preserve={false} autoComplete="off" labelCol={{ span: 2 }}>
                <Form.Item
                  key="A"
                  label="选项 A"
                  name="A"
                  rules={[{ required: true, message: `选项 A不能为空！` }]}
                >
                  <Input onClick={(e) => console.log((e.target.value = 'xxx'))} />
                </Form.Item>
                <Form.Item
                  key="B"
                  label="选项 B"
                  name="B"
                  rules={[{ required: true, message: `选项 B不能为空！` }]}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  key="C"
                  label="选项 C"
                  name="C"
                  rules={[{ required: true, message: `选项 C不能为空！` }]}
                >
                  <Input />
                </Form.Item>
                <Form.Item
                  key="D"
                  label="选项 D"
                  name="D"
                  rules={[{ required: true, message: `选项 D不能为空！` }]}
                >
                  <Input />
                </Form.Item>
                <Form.List name="options">
                  {(fields, { add, remove }) => (
                    <>
                      {fields.map((field) => {
                        const optionName = String.fromCharCode(field.name + 69);
                        return (
                          <Form.Item
                            {...field}
                            key={field.key}
                            label={`选项 ${optionName}`}
                            name={optionName}
                            fieldKey={[field.fieldKey, 'options']}
                            rules={[{ required: true, message: `选项 ${optionName}不能为空！` }]}
                          >
                            <Input
                              addonAfter={
                                <MinusCircleOutlined
                                  onClick={() => {
                                    remove(field.name);
                                    handleExtraOptions(fields, 0);
                                  }}
                                />
                              }
                            />
                          </Form.Item>
                        );
                      })}

                      <Form.Item>
                        <Button
                          type="dashed"
                          onClick={() => {
                            add();
                            handleExtraOptions(fields, 1);
                          }}
                          block
                          icon={<PlusOutlined />}
                        >
                          添加选项
                        </Button>
                      </Form.Item>
                    </>
                  )}
                </Form.List>
              </Form>
            </Panel>
          )}
          <Panel header="答案、解析" key="3">
            <div>
              答案：
              {currentQuestionType === 0 && (
                <Radio.Group
                  onChange={(e) => (questionVO.answerIndex[0] = e.target.value)}
                  defaultValue={questionVO.answerIndex[0]}
                >
                  <Radio value={0}>A</Radio>
                  <Radio value={1}>B</Radio>
                  <Radio value={2}>C</Radio>
                  <Radio value={3}>D</Radio>
                  {extraOptions.map((item) => (
                    <Radio key={item.value + item.name} value={item.value}>
                      {item.name}
                    </Radio>
                  ))}
                </Radio.Group>
              )}
              {currentQuestionType === 1 && (
                <Checkbox.Group
                  defaultValue={questionVO.answerIndex}
                  onChange={(checkedValue) => (questionVO.answerIndex = checkedValue)}
                >
                  <Checkbox value={0}>A</Checkbox>
                  <Checkbox value={1}>B</Checkbox>
                  <Checkbox value={2}>C</Checkbox>
                  <Checkbox value={3}>D</Checkbox>
                  {extraOptions.map((item) => (
                    <Checkbox key={item.value + item.name} value={item.value}>
                      {item.name}
                    </Checkbox>
                  ))}
                </Checkbox.Group>
              )}
              {currentQuestionType === 3 && (
                <Switch checkedChildren={<CheckOutlined />} unCheckedChildren={<CloseOutlined />} />
              )}
              {currentQuestionType === 2 && [
                <Form
                  key="fillAnswerForm"
                  preserve={false}
                  autoComplete="off"
                  labelCol={{ span: 2 }}
                >
                  <Form.Item
                    key="answer1"
                    label="第1空答案"
                    name="answer1"
                    rules={[{ required: true, message: `第1空答案不能为空！` }]}
                  >
                    <Input />
                  </Form.Item>
                  <Form.List name="answers">
                    {(fields, { add, remove }) => (
                      <>
                        {fields.map((field) => {
                          return (
                            <Form.Item
                              {...field}
                              key={field.key}
                              label={`第${field.name + 2}空答案`}
                              name={`answer${field.name + 2}`}
                              fieldKey={[field.fieldKey, 'options']}
                              rules={[
                                { required: true, message: `第${field.name + 2}空答案不能为空！` },
                              ]}
                            >
                              <Input
                                addonAfter={
                                  <MinusCircleOutlined
                                    onClick={() => {
                                      remove(field.name);
                                    }}
                                  />
                                }
                              />
                            </Form.Item>
                          );
                        })}

                        <Form.Item>
                          <Button
                            type="dashed"
                            onClick={() => {
                              add();
                            }}
                            block
                            icon={<PlusOutlined />}
                          >
                            添加选项
                          </Button>
                        </Form.Item>
                      </>
                    )}
                  </Form.List>
                </Form>,
                <Alert
                  message="提示"
                  description={
                    <div>
                      <div>1、每空答案支持多种表述方式，答案设置时请用分号“;”隔开。如：水；H2O</div>
                      <div>
                        2、如果试题答案是数字，可以设置一个范围，两个数字之间必须有减号“-”。如1-9,学生填写1到9之间的数字都算正确（包括1和9）
                      </div>
                    </div>
                  }
                  type="info"
                  showIcon
                />,
              ]}
              {currentQuestionType === 4 && (
                <div style={{ marginTop: 10 }}>
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
                      images_upload_handler: async (blobInfo, successFun, failFun) => {
                        const file = blobInfo.blob();
                        const formData = new FormData();
                        formData.append('image', file);
                        const res = await uploadQuestionImage(formData);
                        if (res.success) {
                          const { questionAttachment } = res.data;
                          successFun(questionAttachment.path);
                        } else {
                          failFun(res.message);
                        }
                      },
                    }}
                  />
                </div>
              )}
            </div>
          </Panel>
          <Panel header="难度、知识点" key="4">
            <Space direction="vertical">
              <Space>
                题目难度：
                <Radio.Group
                  defaultValue={questionVO.difficulty}
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
                {currentTopics === []
                  ? ''
                  : currentTopics.map((item) => (
                      <Space key={item.id}>
                        <Tag
                          closable
                          onClose={() =>
                            setCurrentTopics([...currentTopics.filter((v) => v.id !== item.id)])
                          }
                          color="magenta"
                        >
                          {item.name}
                        </Tag>
                      </Space>
                    ))}
                {!isTopicFormVisible ? (
                  ''
                ) : (
                  <TopicForm
                    isModalVisible={isTopicFormVisible}
                    setModalVisible={setTopicFormVisible}
                    currentTopics={currentTopics}
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
