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
  message,
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
import TinyMceModalEditor from '@/components/TinyMceEditor/modal';
import { createQuestion } from '@/services/question';

const { TabPane } = Tabs;
const { Panel } = Collapse;

const QuestionCreateDrawer = (props) => {
  const { isDrawerVisible, setDrawerVisible, courseId, actionRef, userId, pid } = props;
  const [questionTypes, setQuestionTypes] = useState(undefined);
  const [currentTopics, setCurrentTopics] = useState([]);
  const [currentQuestionType, setCurrentQuestionType] = useState(0);
  const [isTopicFormVisible, setTopicFormVisible] = useState(false);
  const [extraOptions, setExtraOptions] = useState([]);
  const [extraAnswers, setExtraAnswers] = useState([]);
  const [currentInputName, setCurrentInputName] = useState(undefined);
  const [currentInputValue, setCurrentInputValue] = useState('');
  const [isEditorModalVisible, setEditorModalVisible] = useState(false);
  const [currentForm, setCurrentForm] = useState(undefined);
  const editorRef = useRef(null);
  const [optionsForm] = Form.useForm();
  const [answerForm] = Form.useForm();
  const [answerAnalyzeForm] = Form.useForm();
  const answerRadioRef = useRef();
  const [questionVO, setQuestionVO] = useState({
    courseId,
    userId,
    pid,
    questionTypeId: '',
    questionType: 0,
    formatContent: '',
    difficulty: 0,
    isFolder: false,
    options: [],
    answerIndex: [0],
    answers: [],
    answerValue: '',
    answerAnalyze: '',
    topicIds: [],
  });

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

  const handleEditorModal = (formType = 0, name = '', value = '') => {
    if (formType === 0) {
      setCurrentForm(optionsForm);
    } else if (formType === 1) {
      setCurrentForm(answerForm);
    } else {
      setCurrentForm(answerAnalyzeForm);
    }
    setCurrentInputName(name);
    setCurrentInputValue(value);
    setEditorModalVisible(true);
  };

  const handleSaveQuestion = async () => {
    if (editorRef?.current.getContent() === '') {
      message.warn('请输入题干！');
      return;
    }
    const options = optionsForm.getFieldsValue(true);
    const answers = answerForm.getFieldsValue(true);
    const answerAnalyze = answerAnalyzeForm.getFieldsValue(true);
    questionVO.formatContent = editorRef?.current.getContent();
    questionVO.options = Object.keys(options).map((key) => {
      return {
        optionName: key,
        optionValue: options[key],
      };
    });
    questionVO.answers = Object.keys(answers).map((key) => {
      return {
        value: answers[key],
      };
    });
    if (questionVO.questionTypeId === '') {
      questionVO.questionTypeId = questionTypes[0].id;
    }
    questionVO.questionType = currentQuestionType;
    questionVO.answerAnalyze = answerAnalyze.answerAnalyze;
    questionVO.topicIds = currentTopics.map((item) => item.id);
    console.log(questionVO);

    const hide = message.loading('正在新增问题');
    try {
      await createQuestion(questionVO);
      hide();
      message.success(`新增成功！`);
      setDrawerVisible(false);
      actionRef.current.reset();
    } catch (error) {
      hide();
      message.error(`新增失败请重试！`);
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
      footer={
        <Button
          onClick={() => {
            handleSaveQuestion();
          }}
          style={{ float: 'right' }}
          type="primary"
        >
          保存题目
        </Button>
      }
    >
      <div style={{ width: '80%', margin: '0 auto' }}>
        <ProCard>
          {questionTypes === undefined ? (
            <Skeleton active />
          ) : (
            <Tabs
              onTabClick={(key = '') => {
                setCurrentQuestionType(Number(key.substr(key.length - 1)));
                setQuestionVO({ ...questionVO, questionTypeId: key.substr(0, key.indexOf('-')) });
              }}
              defaultActiveKey="1"
              tabBarExtraContent={slot}
            >
              {questionTypes.map((item) => (
                <TabPane tab={item.name} key={`${item.id}-${item.type}`}></TabPane>
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
              <Form form={optionsForm} preserve={false} autoComplete="off" labelCol={{ span: 2 }}>
                <Form.Item
                  key="A"
                  label="选项 A"
                  name="A"
                  rules={[{ required: true, message: `选项 A不能为空！` }]}
                >
                  <Input
                    onClick={(e) => {
                      handleEditorModal(0, 'A', e.target.value);
                    }}
                  />
                </Form.Item>
                <Form.Item
                  key="B"
                  label="选项 B"
                  name="B"
                  rules={[{ required: true, message: `选项 B不能为空！` }]}
                >
                  <Input
                    onClick={(e) => {
                      handleEditorModal(0, 'B', e.target.value);
                    }}
                  />
                </Form.Item>
                <Form.Item
                  key="C"
                  label="选项 C"
                  name="C"
                  rules={[{ required: true, message: `选项 C不能为空！` }]}
                >
                  <Input
                    onClick={(e) => {
                      handleEditorModal(0, 'C', e.target.value);
                    }}
                  />
                </Form.Item>
                <Form.Item
                  key="D"
                  label="选项 D"
                  name="D"
                  rules={[{ required: true, message: `选项 D不能为空！` }]}
                >
                  <Input
                    onClick={(e) => {
                      handleEditorModal(0, 'D', e.target.value);
                    }}
                  />
                </Form.Item>
                {extraOptions.map((item) => {
                  return (
                    <Form.Item
                      key={item.name}
                      label={`选项 ${item.name}`}
                      name={item.name}
                      rules={[{ required: true, message: `选项 ${item.name}不能为空！` }]}
                    >
                      <Input
                        onClick={(e) => {
                          handleEditorModal(0, item.name, e.target.value);
                        }}
                        addonAfter={
                          <MinusCircleOutlined
                            onClick={() => {
                              const newExtraOptions = extraOptions
                                .filter((option) => option.name !== item.name)
                                .map((option, index) => {
                                  return {
                                    name: String.fromCharCode(index + 69),
                                    value: index + 4,
                                  };
                                });
                              setExtraOptions(newExtraOptions);
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
                      setExtraOptions([
                        ...extraOptions,
                        {
                          name: String.fromCharCode(extraOptions.length + 69),
                          value: extraOptions.length + 4,
                        },
                      ]);
                    }}
                    block
                    icon={<PlusOutlined />}
                  >
                    添加选项
                  </Button>
                </Form.Item>
              </Form>
            </Panel>
          )}
          <Panel header="答案、解析" key="3">
            <div>
              答案：
              {currentQuestionType === 0 && (
                <Radio.Group
                  onChange={(e) => {
                    setQuestionVO({ ...questionVO, answerIndex: [e.target.value] });
                  }}
                  defaultValue={questionVO.answerIndex[0]}
                  ref={answerRadioRef}
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
                  onChange={(checkedValue) =>
                    setQuestionVO({ ...questionVO, answerIndex: checkedValue })
                  }
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
                <Radio.Group
                  onChange={(e) => {
                    setQuestionVO({ ...questionVO, answerValue: e.target.value });
                  }}
                  defaultValue={question.answerValue}
                >
                  <Radio value={1}>正确</Radio>
                  <Radio value={0}>错误</Radio>
                </Radio.Group>
              )}
              {currentQuestionType === 2 && [
                <Form
                  form={answerForm}
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
                    <Input
                      onClick={(e) => {
                        handleEditorModal(1, 'answer1', e.target.value);
                      }}
                    />
                  </Form.Item>
                  {extraAnswers.map((item) => {
                    return (
                      <Form.Item
                        key={item.name}
                        label={`第${item.value}空答案`}
                        name={item.name}
                        rules={[{ required: true, message: `第${item.value}空答案不能为空！` }]}
                      >
                        <Input
                          onClick={(e) => {
                            handleEditorModal(1, item.name, e.target.value);
                          }}
                          addonAfter={
                            <MinusCircleOutlined
                              onClick={() => {
                                const newExtraAnswers = extraAnswers
                                  .filter((answer) => answer.name !== item.name)
                                  .map((answer, index) => {
                                    return {
                                      name: `answer${index + 2}`,
                                      value: index + 2,
                                    };
                                  });
                                setExtraAnswers(newExtraAnswers);
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
                        setExtraAnswers([
                          ...extraAnswers,
                          {
                            name: `answer${extraAnswers.length + 2}`,
                            value: extraAnswers.length + 2,
                          },
                        ]);
                      }}
                      block
                      icon={<PlusOutlined />}
                    >
                      添加答案
                    </Button>
                  </Form.Item>
                </Form>,
                <Alert
                  key="tips"
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
                    onEditorChange={(value, _) =>
                      setQuestionVO({ ...questionVO, answerValue: value })
                    }
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
              <div style={{ marginTop: 20 }}>
                <Form
                  form={answerAnalyzeForm}
                  key="answerAnalyze"
                  preserve={false}
                  autoComplete="off"
                  labelCol={{ span: 2 }}
                >
                  <Form.Item
                    label="答案解析"
                    name="answerAnalyze"
                    rules={[{ required: true, message: `答案解析不能为空！` }]}
                  >
                    <Input
                      value={questionVO.answerAnalyze}
                      onClick={() => {
                        handleEditorModal(2, 'answerAnalyze');
                      }}
                    />
                  </Form.Item>
                </Form>
              </div>
            </div>
          </Panel>
          <Panel header="难度、知识点" key="4">
            <Space direction="vertical">
              <Space>
                题目难度：
                <Radio.Group
                  defaultValue={questionVO.difficulty}
                  onChange={(e) => {
                    setQuestionVO({ ...questionVO, difficulty: e.target.value });
                  }}
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
        {!isEditorModalVisible ? (
          ''
        ) : (
          <TinyMceModalEditor
            isModalVisible={isEditorModalVisible}
            setModalVisible={setEditorModalVisible}
            targetInputName={currentInputName}
            currentForm={currentForm}
            currentValue={currentInputValue}
          />
        )}
      </div>
    </Drawer>
  );
};

export default QuestionCreateDrawer;
