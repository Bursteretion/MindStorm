import React, { useEffect, useMemo, useState } from 'react';
import styles from '@/pages/Course/manager/style.less';
import { Col, Drawer, Input, Row, Select, Skeleton } from 'antd';
import { CloseOutlined } from '@ant-design/icons';
import ProCard from '@ant-design/pro-card';
import { infoExamPaper } from '@/services/exampaper';
import QuestionList from '@/pages/Course/manager/components/question';

const { Option } = Select;

const CreateUpdateExamPaperSelfDrawer = (props) => {
  const { isDrawerVisible, setDrawerVisible, examPaperId } = props;
  const [examPaper, setExamPaper] = useState(undefined);
  const [selectedQuestions, setSelectedQuestions] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      const res = await infoExamPaper(examPaperId);
      if (res.success) {
        setExamPaper(res.data.examPaper);
      }
    };
    fetchData();
  }, [examPaperId]);

  const paperQuestionInfo = useMemo(() => {
    console.log(selectedQuestions);
    return {
      questionCount: selectedQuestions.length,
      totalScore: (selectedQuestions.length * 5.0).toFixed(2),
    };
  }, [selectedQuestions]);

  return (
    <>
      <Drawer
        headerStyle={{ backgroundColor: '#384B66' }}
        closeIcon={<CloseOutlined style={{ color: '#ffffff' }} />}
        destroyOnClose
        getContainer={false}
        push={{ distance: 0 }}
        className={styles.drawer}
        title={
          <div style={{ textAlign: 'center', color: '#AAAEB3' }}>
            <span>新建考试</span>
          </div>
        }
        width="100%"
        height="100%"
        placement="right"
        onClose={() => {
          setDrawerVisible(false);
        }}
        visible={isDrawerVisible}
      >
        <Row justify="center">
          <Col span={20}>
            <ProCard>
              {examPaper === undefined ? (
                <Skeleton active />
              ) : (
                [
                  <Input
                    value={examPaper.title}
                    onChange={(e) => {
                      examPaper.title = e.target.value;
                    }}
                    key="paperTitle"
                    placeholder="请输入试卷标题"
                  />,
                  <div key="paperDifficulty" style={{ marginTop: 20 }}>
                    <span>难度：</span>
                    <Select
                      size="small"
                      onChange={(value) => {
                        examPaper.difficulty = value;
                      }}
                      defaultValue={`${examPaper.difficulty}`}
                      style={{ width: 80 }}
                    >
                      <Option value="0">简单</Option>
                      <Option value="1">中等</Option>
                      <Option value="2">困难</Option>
                    </Select>
                  </div>,
                ]
              )}
            </ProCard>
          </Col>
        </Row>
        <Row style={{ marginTop: 20 }} justify="center">
          <Col span={20}>
            <Row gutter={20} justify="space-between">
              <Col span={6}>
                <ProCard
                  title={`题量 ${paperQuestionInfo.questionCount}，总分：${paperQuestionInfo.totalScore}`}
                >
                  xx
                </ProCard>
              </Col>
              <Col span={18}>
                <ProCard>
                  <QuestionList
                    selectQuestion={true}
                    selectedQuestionIds={[]}
                    setSelectedQuestions={setSelectedQuestions}
                  />
                </ProCard>
              </Col>
            </Row>
          </Col>
        </Row>
      </Drawer>
    </>
  );
};

export default CreateUpdateExamPaperSelfDrawer;
