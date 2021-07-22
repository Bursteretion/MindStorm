import React from 'react';
import { FrownOutlined } from '@ant-design/icons';
import { Drawer } from 'antd';
import styles from '@/pages/Course/manager/style.less';

const QuestionDrawer = (props) => {
  const { isDrawerVisible, setDrawerVisible, courseId, actionRef } = props;

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
      xxx
    </Drawer>
  );
};

export default QuestionDrawer;
