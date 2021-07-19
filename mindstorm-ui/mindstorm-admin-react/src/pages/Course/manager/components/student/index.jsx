import React, { useState } from 'react';
import { Drawer } from 'antd';
import styles from '@/pages/Course/manager/style.less';
import { FrownOutlined } from '@ant-design/icons';
import ProCard from '@ant-design/pro-card';
import CourseClassList from './components/CourseClassList';

const StudentMain = (props) => {
  const { isDrawerVisible, setDrawerVisible, classId, courseId, handleQueryCourseClass } = props;
  const [currentClassId, setCurrentClassId] = useState(undefined);

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
        height="100%"
        placement="bottom"
        onClose={() => {
          setDrawerVisible(false);
          handleQueryCourseClass({ courseId, className: '' });
        }}
        visible={isDrawerVisible}
      >
        <ProCard className="mainCard" split="vertical">
          <ProCard className="leftCard" colSpan="290px" ghost>
            <CourseClassList
              classId={classId}
              courseId={courseId}
              setCurrentClassId={setCurrentClassId}
            />
          </ProCard>
          <ProCard></ProCard>
        </ProCard>
      </Drawer>
    </>
  );
};

export default StudentMain;
