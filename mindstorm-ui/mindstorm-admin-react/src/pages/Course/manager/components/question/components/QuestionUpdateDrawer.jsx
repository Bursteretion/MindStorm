import React, { useEffect, useRef, useState } from 'react';
import { FrownOutlined, MinusCircleOutlined, PlusOutlined } from '@ant-design/icons';
import {
  Drawer,
  message,
  Skeleton,
  Space,
  Collapse,
  Radio,
  Button,
  Tag,
  Form,
  Input,
  Checkbox,
  Alert,
} from 'antd';
import styles from '@/pages/Course/manager/style.less';
import { Editor } from '@tinymce/tinymce-react';
import TopicForm from '@/pages/Course/manager/components/question/components/TopicForm';
import { uploadQuestionImage } from '@/services/attachment';
import TinyMceModalEditor from '@/components/TinyMceEditor/modal';
import { infoQuestion, updateQuestion } from '@/services/question';

const { Panel } = Collapse;

const QuestionCreateDrawer = (props) => {
  const { isDrawerVisible, setDrawerVisible, actionRef, questionId } = props;
  const [currentTopics, setCurrentTopics] = useState([]);
  const [isTopicFormVisible, setTopicFormVisible] = useState(false);
  const [currentInputName, setCurrentInputName] = useState(undefined);
  const [currentInputValue, setCurrentInputValue] = useState('');
  const [isEditorModalVisible, setEditorModalVisible] = useState(false);
  const [currentForm, setCurrentForm] = useState(undefined);
  const [optionInitialValue, setOptionInitialValue] = useState({});
  const editorRef = useRef(null);
  const [optionsForm] = Form.useForm();
  const [answerForm] = Form.useForm();
  const [answerAnalyzeForm] = Form.useForm();
  const [question, setQuestion] = useState(undefined);

  useEffect(() => {
    async function fetchQuestion() {
      if (questionId !== undefined) {
        const res = await infoQuestion(questionId);
        if (res.success) {
          console.log(res.data.question);
          const { options = [] } = res.data.question;
          const optionValues = {};
          options.forEach((item) => {
            optionValues[item.name] = item.value;
          });
          setOptionInitialValue(optionValues);
          setQuestion(res.data.question);
          setCurrentTopics(res.data.question.topics);
        }
      }
    }
    fetchQuestion();
  }, [questionId]);

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
    const optionsFormValues = optionsForm.getFieldsValue(true);
    const answersFormValues = answerForm.getFieldsValue(true);
    const answerAnalyze = answerAnalyzeForm.getFieldsValue(true);
    const formatContent = editorRef?.current.getContent();
    const options = Object.keys(optionsFormValues).map((key) => {
      return {
        optionName: key,
        optionValue: optionsFormValues[key],
      };
    });
    const answers = Object.keys(answersFormValues).map((key) => {
      return {
        value: answersFormValues[key],
      };
    });
    const topicIds = currentTopics.map((item) => item.id);
    const questionVO = {
      ...question,
      formatContent,
      options,
      answers,
      topicIds,
      answerAnalyze: answerAnalyze.answerAnalyze,
    };
    console.log(questionVO);

    // const hide = message.loading('正在更新问题');
    // try {
    //   await updateQuestion(questionVO);
    //   hide();
    //   message.success(`更新成功！`);
    //   setDrawerVisible(false);
    //   actionRef.current.reset();
    // } catch (error) {
    //   hide();
    //   message.error(`更新失败请重试！`);
    // }
  };

  return (
    <Drawer
      destroyOnClose
      getContainer={false}
      className={styles.drawer}
      title={
        <span>
          <FrownOutlined style={{ marginRight: 10 }} />
          编辑题目
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
        {question === undefined ? (
          <Skeleton active />
        ) : (
          <Collapse style={{ marginTop: 20 }}>
            <Panel header="题干" key="1">
              <Editor
                initialValue={question.formatContent}
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
            {!(question.questionType === 0 || question.questionType === 1) ? (
              ''
            ) : (
              <Panel header="选项" key="2">
                <Form
                  initialValues={optionInitialValue}
                  form={optionsForm}
                  preserve={false}
                  autoComplete="off"
                  labelCol={{ span: 2 }}
                >
                  {question.options.map((item, index) => {
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
                            index > 3 && (
                              <MinusCircleOutlined
                                onClick={() => {
                                  const newOptions = question.options
                                    .filter((option) => option.name !== item.name)
                                    .map((option, i) => {
                                      return {
                                        name: String.fromCharCode(i + 65),
                                        value: option.value,
                                      };
                                    });
                                  setQuestion({ ...question, options: newOptions });
                                }}
                              />
                            )
                          }
                        />
                      </Form.Item>
                    );
                  })}
                  <Form.Item>
                    <Button
                      type="dashed"
                      onClick={() => {
                        setQuestion({
                          ...question,
                          options: [
                            ...question.options,
                            {
                              name: String.fromCharCode(65 + question.options.length),
                              value: '',
                            },
                          ],
                        });
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
                {question.questionType === 0 && (
                  <Radio.Group
                    onChange={(e) => {
                      setQuestion({ ...question, answerIndex: [e.target.value] });
                    }}
                    defaultValue={question.answerIndex[0]}
                  >
                    {question.options.map((item, index) => (
                      <Radio key={item.value + item.name} value={index}>
                        {item.name}
                      </Radio>
                    ))}
                  </Radio.Group>
                )}
                {question.questionType === 1 && (
                  <Checkbox.Group
                    defaultValue={question.answerIndex}
                    onChange={(checkedValue) =>
                      setQuestion({ ...question, answerIndex: checkedValue })
                    }
                  >
                    {question.options.map((item, index) => (
                      <Checkbox key={item.value + item.name} value={index}>
                        {item.name}
                      </Checkbox>
                    ))}
                  </Checkbox.Group>
                )}
                {question.questionType === 3 && (
                  <Radio.Group
                    onChange={(e) => {
                      setQuestion({ ...question, answerValue: e.target.value });
                    }}
                    defaultValue={question.answerValue}
                  >
                    <Radio value={1}>正确</Radio>
                    <Radio value={0}>错误</Radio>
                  </Radio.Group>
                )}
                {question.questionType === 2 && [
                  <Form
                    form={answerForm}
                    key="fillAnswerForm"
                    preserve={false}
                    autoComplete="off"
                    labelCol={{ span: 2 }}
                  >
                    {question.answers.map((item, i) => {
                      return (
                        <Form.Item
                          key={`answer${i + 1}`}
                          label={`第${i + 1}空答案`}
                          name={`answer${i + 1}`}
                          rules={[{ required: true, message: `第${i + 1}空答案不能为空！` }]}
                        >
                          <Input
                            onClick={(e) => {
                              handleEditorModal(1, item.name, e.target.value);
                            }}
                            addonAfter={
                              i !== 0 && (
                                <MinusCircleOutlined
                                  onClick={() => {
                                    const newAnswers = question.answers
                                      .filter((answer) => answer.id !== item.id)
                                      .map((answer, index) => {
                                        return {
                                          name: `answer${index + 1}`,
                                          value: index + 1,
                                        };
                                      });
                                    setQuestion({ ...question, answers: newAnswers });
                                  }}
                                />
                              )
                            }
                          />
                        </Form.Item>
                      );
                    })}
                    <Form.Item>
                      <Button
                        type="dashed"
                        onClick={() => {
                          setQuestion({
                            ...question,
                            answers: [
                              ...question.answers,
                              {
                                name: `answer${question.answers + 1}`,
                                value: question.answers + 1,
                              },
                            ],
                          });
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
                        <div>
                          1、每空答案支持多种表述方式，答案设置时请用分号“;”隔开。如：水；H2O
                        </div>
                        <div>
                          2、如果试题答案是数字，可以设置一个范围，两个数字之间必须有减号“-”。如1-9,学生填写1到9之间的数字都算正确（包括1和9）
                        </div>
                      </div>
                    }
                    type="info"
                    showIcon
                  />,
                ]}
                {question.questionType === 4 && (
                  <div style={{ marginTop: 10 }}>
                    <Editor
                      onEditorChange={(value, _) =>
                        setQuestion({ ...question, answerValue: value })
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
                    initialValues={{
                      answerAnalyze: question.answerAnalyze,
                    }}
                  >
                    <Form.Item
                      label="答案解析"
                      name="answerAnalyze"
                      rules={[{ required: true, message: `答案解析不能为空！` }]}
                    >
                      <Input
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
                    defaultValue={question.difficulty}
                    onChange={(e) => {
                      setQuestion({ ...question, difficulty: e.target.value });
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
        )}
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
