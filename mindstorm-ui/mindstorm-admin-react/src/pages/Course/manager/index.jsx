import React, { useEffect, useState } from 'react';
import { Card, Menu, Avatar, Skeleton } from 'antd';
import styles from './style.less';
import ClassList from './components/class';
import { menuMap } from './menuMap';
import { FrownOutlined } from '@ant-design/icons';
import { history } from 'umi';
import { infoCourse } from '@/services/course';

const { Item } = Menu;

const CourseManager = () => {
  const [selectKey, setSelectKey] = useState('class');
  const [course, setCourse] = useState(undefined);

  useEffect(() => {
    async function fetchCourse() {
      const { courseId } = history.location.query;
      if (courseId !== undefined) {
        const res = await infoCourse(courseId);
        if (res.code === 20000) {
          setCourse(res.data.course);
        }
      }
    }

    fetchCourse();
  }, []);

  const getMenu = () => {
    return Object.keys(menuMap).map((item) => {
      if (item === 'divider1' || item === 'divider2') {
        return menuMap[item];
      }
      const { key, icon } = menuMap[item];
      return (
        <Item style={{ height: 50 }} key={item} icon={icon}>
          {key}
        </Item>
      );
    });
  };

  const renderChildren = () => {
    switch (selectKey) {
      case 'class':
        return <ClassList courseId={course.id} />;

      default:
        break;
    }

    return null;
  };

  return (
    <>
      <div className={styles.wrapper}>
        <div className={styles.header}>
          <div className={styles.title}>
            <span>
              <FrownOutlined style={{ marginRight: 10 }} />
              课程详情
            </span>
          </div>
        </div>
        <div className={styles.main}>
          <div className={styles.leftMenu}>
            {course === undefined ? (
              <Skeleton active />
            ) : (
              [
                <Avatar
                  key="courseCover"
                  className={styles.courseCover}
                  shape="square"
                  src={course.thumbnail}
                />,
                <div key="courseName" className={styles.courseName}>
                  {course.name}
                </div>,
              ]
            )}
            <Menu mode="inline" selectedKeys={[selectKey]} onClick={({ key }) => setSelectKey(key)}>
              {getMenu()}
            </Menu>
          </div>
          <Card className={styles.right}>
            {course === undefined ? <Skeleton active /> : renderChildren()}
          </Card>
        </div>
      </div>
    </>
  );
};

export default CourseManager;
