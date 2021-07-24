import React, { useEffect, useRef, useState } from 'react';
import { Button, Popconfirm, Select, Space } from 'antd';
import { FolderOpenTwoTone, PlusOutlined, SettingOutlined, ToTopOutlined } from '@ant-design/icons';
import ProTable from '@ant-design/pro-table';
import { queryQuestion, QuestionDifficultyStatus } from '@/services/question';
import { listQuestionTypeSelects } from '@/services/questiontype';
import { listTopicSelects } from '@/services/topic';
import QuestionDrawer from './components/QuestionDrawer';
import { useModel } from 'umi';
import CreateFolderForm from './components/CreateFolderForm';
import styles from '@/pages/Course/manager/style.less';
import QuestionTypeForm from './components/QuestionTypeForm';

const QuestionList = (props) => {
  const { courseId } = props;
  const actionRef = useRef();
  const [questionTypes, setQuestionTypes] = useState(undefined);
  const [topics, setTopics] = useState(undefined);
  const [isDrawerVisible, setDrawerVisible] = useState(false);
  const [isCreateFolderModalVisible, setCreateFolderModalVisible] = useState(false);
  const [isQuestionTypeModalVisible, setQuestionTypeModalVisible] = useState(false);
  const [paths, setPaths] = useState([{ name: '课程题库', value: '0' }]);
  const [pid, setPid] = useState('0');
  const { userId = '' } = useModel('@@initialState', (res) => ({
    userId: res.initialState.currentUser.id,
  }));

  const generatePath = () => {
    const node = [];
    for (let i = 0; i < paths.length; i++) {
      node.push(
        <Space>
          <a
            key={i}
            onClick={() => {
              const newPath = paths.slice(0, i + 1);
              setPaths(newPath);
              setPid(paths[i].value);
            }}
            style={{ fontSize: 12 }}
          >
            {paths[i].name}
          </a>
        </Space>,
      );
      if (i !== paths.length - 1) {
        node.push(<Space>{'>'}</Space>);
      }
    }
    return node;
  };

  const fetchQuestionTypes = async () => {
    const res = await listQuestionTypeSelects();
    if (res.success) {
      const { questionTypeSelects } = res.data;
      setQuestionTypes(questionTypeSelects);
    }
  };

  const fetchTopics = async () => {
    const res = await listTopicSelects();
    if (res.success) {
      const { topicSelects } = res.data;
      setTopics(topicSelects);
    }
  };

  useEffect(() => {
    fetchQuestionTypes();
    fetchTopics();
    actionRef?.current.reset();
  }, [pid]);

  const handleQueryQuestions = async (params) => {
    const res = await queryQuestion({
      ...params,
      pageIndex: params.current,
      pageSize: params.pageSize,
      courseId,
      pid,
    });
    const { records, total } = res.data.queryQuestionPage;
    return {
      data: records,
      total,
      success: res.success,
    };
  };

  const handleDeleteQuestion = async (questionId) => {};

  const columns = [
    {
      title: '文件夹/题目',
      dataIndex: 'content',
      hideInSearch: true,
      render: (_, record) => (
        <Space>
          {!record.isFolder ? '' : <FolderOpenTwoTone style={{ fontSize: 17 }} />}
          <a
            onClick={() => {
              if (record.isFolder) {
                if (!paths.find((item) => item === record.content)) {
                  setPaths([...paths, { name: record.content, value: record.id }]);
                }
              }
              setPid(record.id);
            }}
          >
            {record.content}
          </a>
        </Space>
      ),
    },
    {
      title: '题目',
      dataIndex: 'content',
      hideInTable: true,
    },
    {
      title: '题目类型',
      dataIndex: 'questionType',
      renderFormItem: (item, { type }) => {
        if (type === 'form') {
          return null;
        }
        return <Select name="questionType" allowClear options={questionTypes} />;
      },
    },
    {
      title: '知识点',
      dataIndex: 'topicId',
      hideInTable: true,
      renderFormItem: (item, { type }) => {
        if (type === 'form') {
          return null;
        }
        return <Select name="topicId" allowClear options={topics} />;
      },
    },
    {
      title: '难度',
      dataIndex: 'difficulty',
      valueType: 'select',
      valueEnum: QuestionDifficultyStatus,
    },
    {
      title: '使用量',
      dataIndex: 'usageAmount',
      search: false,
    },
    {
      title: '创建者',
      dataIndex: 'userRealName',
      search: false,
    },
    {
      title: '课程',
      dataIndex: 'courseName',
      search: false,
    },
    {
      title: '创建时间',
      dataIndex: 'gmtCreate',
      valueType: 'dateTime',
      hideInForm: true,
      hideInSearch: true,
    },
    {
      title: '创建时间',
      dataIndex: 'gmtCreate',
      valueType: 'dateTimeRange',
      hideInTable: true,
      search: {
        transform: (value) => {
          return {
            startTime: value[0],
            endTime: value[1],
          };
        },
      },
    },
    {
      title: '操作',
      valueType: 'option',
      render: (_, record) => [
        <Popconfirm
          key="delete"
          title={`你确定要删除这个题目吗？`}
          onConfirm={() => handleDeleteQuestion(record)}
          okText="确定"
          cancelText="取消"
        >
          <a>移除</a>
        </Popconfirm>,
      ],
    },
  ];

  return (
    <>
      {paths && (
        <ProTable
          className={styles.table}
          columns={columns}
          actionRef={actionRef}
          rowKey={(record) => record.id}
          request={(params) => handleQueryQuestions(params)}
          dateFormatter="string"
          headerTitle={generatePath()}
          pagination={{ defaultCurrent: 1, defaultPageSize: 10 }}
          options={{ fullScreen: true }}
          toolBarRender={() => [
            <Button
              key="create"
              shape="round"
              icon={<PlusOutlined />}
              type="primary"
              onClick={() => {
                setDrawerVisible(true);
              }}
            >
              创建题目
            </Button>,
            <Button key="import" shape="round">
              批量导入
            </Button>,
            <Button key="folder" shape="round" onClick={() => setCreateFolderModalVisible(true)}>
              新建文件夹
            </Button>,
            <Button
              key="questionType"
              icon={<SettingOutlined />}
              type="link"
              onClick={() => {
                setQuestionTypeModalVisible(true);
              }}
            >
              题型管理
            </Button>,
            <Button
              key="export"
              icon={<ToTopOutlined />}
              type="link"
              onClick={() => {
                setDrawerVisible(true);
              }}
            >
              导出全部
            </Button>,
          ]}
        />
      )}
      {!isCreateFolderModalVisible ? (
        ''
      ) : (
        <CreateFolderForm
          isModalVisible={isCreateFolderModalVisible}
          setModalVisible={setCreateFolderModalVisible}
          actionRef={actionRef}
          courseId={courseId}
          userId={userId}
          pid={pid}
        />
      )}
      {!isQuestionTypeModalVisible ? (
        ''
      ) : (
        <QuestionTypeForm
          isModalVisible={isQuestionTypeModalVisible}
          setModalVisible={setQuestionTypeModalVisible}
        />
      )}
      {!isDrawerVisible ? (
        ''
      ) : (
        <QuestionDrawer
          isDrawerVisible={isDrawerVisible}
          setDrawerVisible={setDrawerVisible}
          courseId={courseId}
          userId={userId}
          actionRef={actionRef}
        />
      )}
    </>
  );
};

export default QuestionList;
