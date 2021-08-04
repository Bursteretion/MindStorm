import React, { useState } from 'react';
import { Button, Col, Dropdown, Input, Menu, Row, Space } from 'antd';
import { PlusOutlined } from '@ant-design/icons';
import ExamPaperTable from './components/exampaper';

const { Search } = Input;

const ExamManager = (props) => {
  const { courseId } = props;
  const [loading, setLoading] = useState(false);
  const [isDrawerVisible, setDrawerVisible] = useState(false);

  return (
    <>
      <Row justify="space-between">
        <Col span={4}>
          <Space size="large">
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
            </Dropdown>
            <Button shape="round" onClick={() => {setDrawerVisible(true)}}>试卷库</Button>
          </Space>
        </Col>
        <Col span={4}>
          <Search placeholder="试卷名" style={{ width: 200 }} loading={loading} />
        </Col>
      </Row>
      {!isDrawerVisible ? (
        ''
      ) : (
        <ExamPaperTable
          courseId={courseId}
          isDrawerVisible={isDrawerVisible}
          setDrawerVisible={setDrawerVisible}
        />
      )}
    </>
  );
};

export default ExamManager;
