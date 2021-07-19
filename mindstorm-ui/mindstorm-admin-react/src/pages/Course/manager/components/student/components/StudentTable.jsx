import React from 'react';
import styles from '@/pages/Course/common/style.less';
import { FrownOutlined } from '@ant-design/icons';
import { Avatar, Card, Drawer, Menu } from 'antd';

const StudentTable = (props) => {
  const { isDrawerVisible, setDrawerVisible, classId } = props;

  return (
    <>
      <Drawer
        destroyOnClose
        getContainer={false}
        className={styles.drawer}
        title={
          <span>
            <FrownOutlined style={{ marginRight: 10 }} />
            班级管理
          </span>
        }
        width="100%"
        placement="right"
        onClose={() => {
          setDrawerVisible(false);
        }}
        visible={isDrawerVisible}
      ></Drawer>
    </>
  );
};

export default StudentTable;
