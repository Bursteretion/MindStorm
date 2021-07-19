import React from 'react';
import { Drawer } from 'antd';
import styles from '@/pages/Course/common/style.less';
import { FrownOutlined } from '@ant-design/icons';

const StudentMain = (props) => {
  const { isDrawerVisible, setDrawerVisible, classId } = props;

  return (
    <>
      <Drawer
        destroyOnClose
        className={styles.drawer}
        title={
          <span>
            <FrownOutlined style={{ marginRight: 10 }} />
            班级管理
          </span>
        }
        width="100%"
        height="100%"
        placement="bottom"
        onClose={() => {
          setDrawerVisible(false);
        }}
        visible={isDrawerVisible}
      >
        {classId}
      </Drawer>
    </>
  );
};

export default StudentMain;
