import React, { useRef, useState } from 'react';
import { Button, Drawer, Dropdown, Menu, Popconfirm, Space } from 'antd';
import { FolderOpenTwoTone, PlusOutlined, ToTopOutlined } from '@ant-design/icons';
import ProTable from '@ant-design/pro-table';
import styles from '@/pages/Course/manager/style.less';
import { queryExamPaper } from '@/services/exampaper';
import { useModel } from 'umi';

const ExamPaperTable = (props) => {
  const { courseId, isDrawerVisible, setDrawerVisible } = props;
  const actionRef = useRef();
  const [examPaperId, setExamPaperId] = useState(undefined);
  const [examPaperPid, setExamPaperPid] = useState(undefined);
  const [paths, setPaths] = useState([{ name: '全部试卷', value: '0' }]);
  const [pid, setPid] = useState('0');
  const { userId = '' } = useModel('@@initialState', (res) => ({
    userId: res.initialState.currentUser.id,
  }));

  /**
   * 生成文件路径
   */
  const generatePath = () => {
    const node = [];
    for (let i = 0; i < paths.length; i++) {
      node.push(
        <Space>
          <a
            key={Math.random()}
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

  const handleQueryExamPapers = async (params) => {
    const res = await queryExamPaper({
      ...params,
      pageIndex: params.current,
      pageSize: params.pageSize,
      courseId,
      pid,
    });
    const { records, total } = res.data.examPaperPage;
    return {
      data: records,
      total,
      success: res.success,
    };
  };

  const columns = [
    {
      title: '文件夹/试卷',
      dataIndex: 'title',
      hideInSearch: true,
      render: (_, record) => (
        <Space>
          {!record.isFolder ? '' : <FolderOpenTwoTone style={{ fontSize: 17 }} />}
          <a
            onClick={() => {
              if (record.isFolder) {
                if (!paths.find((item) => item === record.title)) {
                  setPaths([...paths, { name: record.title, value: record.id }]);
                  setPid(record.id);
                }
              }
            }}
          >
            {record.title}
          </a>
        </Space>
      ),
    },
    {
      title: '题目',
      dataIndex: 'title',
      hideInTable: true,
    },
    {
      title: '题量',
      dataIndex: 'questionCount',
      search: false,
    },
    {
      title: '难度',
      dataIndex: 'difficulty',
      search: false,
    },
    {
      title: '创建者',
      dataIndex: 'userRealName',
      search: false,
    },
    {
      title: '发放次数',
      dataIndex: 'publishCount',
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
        <a
          onClick={() => {
            setExamPaperId(record.id);
            setExamPaperPid(record.pid);
            // setQuestionFolderMoveModalVisible(true);
          }}
          key="move"
        >
          移动
        </a>,
        !record.isFolder && (
          <a
            onClick={() => {
              setExamPaperId(record.id);
              // setQuestionUpdateDrawerVisible(true);
            }}
            key="edit"
          >
            编辑
          </a>
        ),
        record.isFolder && (
          <a
            onClick={() => {
              setExamPaperId(record.id);
              // setCreateFolderModalVisible(true);
            }}
            key="rename"
          >
            重命名
          </a>
        ),
        <Popconfirm
          key="delete"
          title={`你确定要删除这个${record.isFolder ? '文件夹' : '试卷'}吗？`}
          // onConfirm={() => handleDeleteQuestion(record)}
          okText="确定"
          cancelText="取消"
        >
          <a>删除</a>
        </Popconfirm>,
      ],
    },
  ];

  return (
    <>
      <Drawer
        destroyOnClose
        getContainer={false}
        className={styles.drawer}
        title={
          <div style={{ textAlign: 'center' }}>
            <span>试卷库</span>
          </div>
        }
        width="100%"
        height="100%"
        placement="bottom"
        onClose={() => {
          setDrawerVisible(false);
        }}
        visible={isDrawerVisible}
      >
        {paths && (
          <ProTable
            className={styles.table}
            columns={columns}
            actionRef={actionRef}
            rowKey={(record) => record.id}
            request={(params) => handleQueryExamPapers(params)}
            dateFormatter="string"
            headerTitle={generatePath()}
            pagination={{ defaultCurrent: 1, defaultPageSize: 10 }}
            options={{ fullScreen: true }}
            toolBarRender={() => [
              <Dropdown
                trigger={['click']}
                key="create"
                overlay={
                  <Menu>
                    <Menu.Item key="createBySelf">
                      <a>手动创建试卷</a>
                    </Menu.Item>
                    <Menu.Item key="createAuto">
                      <a>自动随机组卷</a>
                    </Menu.Item>
                  </Menu>
                }
                placement="bottomCenter"
                arrow
              >
                <Button shape="round" icon={<PlusOutlined />} type="primary">
                  新建考试
                </Button>
              </Dropdown>,
              <Button
                key="import"
                shape="round"
                onClick={() => {
                  // setImportModalVisible(true);
                }}
              >
                导入试卷
              </Button>,
              <Button
                key="folder"
                shape="round"
                onClick={() => {
                  setExamPaperId(undefined);
                  // setCreateFolderModalVisible(true);
                }}
              >
                新建文件夹
              </Button>,
              <Button
                key="export"
                icon={<ToTopOutlined />}
                type="link"
                onClick={() => {
                  // setQuestionCreateDrawerVisible(true);
                }}
              >
                导出全部
              </Button>,
            ]}
          />
        )}
      </Drawer>
    </>
  );
};

export default ExamPaperTable;
