import React from 'react';
import styles from '@/pages/Course/manager/style.less';
import { Col, Drawer, Input, Row, Select } from 'antd';
import { CloseOutlined } from '@ant-design/icons';
import ProCard from '@ant-design/pro-card';

const { Option } = Select;

const CreateUpdateExamPaperSelfDrawer = (props) => {
  const { isDrawerVisible, setDrawerVisible } = props;

  return (
    <>
      <Drawer
        headerStyle={{ backgroundColor: '#384B66' }}
        closeIcon={<CloseOutlined style={{ color: '#ffffff' }} />}
        destroyOnClose
        getContainer={false}
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
              <Input placeholder="请输入试卷标题" />
              <div style={{ marginTop: 20 }}>
                <span>难度：</span>
                <Select size="small" defaultValue="0" style={{ width: 80 }}>
                  <Option value="0">简单</Option>
                  <Option value="1">中等</Option>
                  <Option value="2">困难</Option>
                </Select>
              </div>
            </ProCard>
          </Col>
        </Row>
        <Row style={{marginTop: 20}} justify="center">
          <Col span={20}>
            <Row gutter={20} justify="space-between">
              <Col span={6}>
                <ProCard title={"题量 0，总分：0.0"}>xx</ProCard>
              </Col>
              <Col span={18}>
                <ProCard>xx</ProCard>
              </Col>
            </Row>
          </Col>
        </Row>
      </Drawer>
    </>
  );
};

export default CreateUpdateExamPaperSelfDrawer;
