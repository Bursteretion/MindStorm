import React, { useEffect, useRef, useState } from 'react';
import { Button, Popconfirm, Select } from 'antd';
import { PlusOutlined, SettingOutlined, ToTopOutlined } from '@ant-design/icons';
import ProTable from '@ant-design/pro-table';
import { queryQuestion, QuestionDifficultyStatus } from '@/services/question';
import { listQuestionTypes } from '@/services/questiontype';
import { listTopics } from '@/services/topic';
import QuestionDrawer from '@/pages/Course/manager/components/question/components/QuestionDrawer';

const QuestionList = (props) => {
  const { courseId } = props;
  const actionRef = useRef();
  const [questionTypes, setQuestionTypes] = useState(undefined);
  const [topics, setTopics] = useState(undefined);
  const [isDrawerVisible, setDrawerVisible] = useState(false);

  const fetchQuestionTypes = async () => {
    const res = await listQuestionTypes();
    if (res.success) {
      const { questionTypeSelects } = res.data;
      setQuestionTypes(questionTypeSelects);
    }
  };

  const fetchTopics = async () => {
    const res = await listTopics();
    if (res.success) {
      const { topicSelects } = res.data;
      setTopics(topicSelects);
    }
  };

  useEffect(() => {
    fetchQuestionTypes();
    fetchTopics();
  }, []);

  const handleQueryQuestions = async (params) => {
    const res = await queryQuestion({
      ...params,
      pageIndex: params.current,
      pageSize: params.pageSize,
      courseId,
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
          title={`你确定要将【${record.realName}】从这个班级移除吗？`}
          onConfirm={() => handleDeleteQuestion(record.id)}
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
      <ProTable
        columns={columns}
        actionRef={actionRef}
        rowKey={(record) => record.id}
        request={(params) => handleQueryQuestions(params)}
        dateFormatter="string"
        headerTitle="课程题库"
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
          <Button key="folder" shape="round">
            新建文件夹
          </Button>,
          <Button
            key="questionType"
            icon={<SettingOutlined />}
            type="link"
            onClick={() => {
              setDrawerVisible(true);
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
      {!isDrawerVisible && courseId === undefined ? (
        ''
      ) : (
        <QuestionDrawer
          isDrawerVisible={isDrawerVisible}
          setDrawerVisible={setDrawerVisible}
          courseId={courseId}
          actionRef={actionRef}
        />
      )}
    </>
  );
};

export default QuestionList;
