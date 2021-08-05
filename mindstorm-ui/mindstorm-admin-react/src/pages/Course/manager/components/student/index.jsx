import React, { useEffect, useState } from 'react';
import { Drawer } from 'antd';
import styles from '@/pages/Course/manager/style.less';
import { FrownOutlined } from '@ant-design/icons';
import ProCard from '@ant-design/pro-card';
import CourseClassList from './components/CourseClassList';
import StudentTable from './components/StudentTable';
import { history } from 'umi';

const StudentMain = (props) => {
  const { courseId } = history.location.query;
  const { isDrawerVisible, setDrawerVisible, classId, handleQueryCourseClass } = props;
  const [currentClassId, setCurrentClassId] = useState(classId);
  const childRef = React.createRef();

  useEffect(() => {
    childRef?.current.setValue(currentClassId);
  }, [currentClassId]);

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
            <CourseClassList classId={classId} setCurrentClassId={setCurrentClassId} />
          </ProCard>
          <ProCard>
            <StudentTable childRef={childRef} currentClassId={currentClassId} />
          </ProCard>
        </ProCard>
      </Drawer>
    </>
  );
};

export default StudentMain;
