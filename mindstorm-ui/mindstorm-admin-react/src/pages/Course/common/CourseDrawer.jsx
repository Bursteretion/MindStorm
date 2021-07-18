import React, { useState } from 'react';
import { Card, Drawer, Menu, Avatar } from 'antd';
import styles from './style.less';
import ClassList from './components/ClassList';
import { menuMap } from './menuMap';
import { FrownOutlined } from '@ant-design/icons';

const { Item } = Menu;

const CourseDrawer = (props) => {
  const { isDrawerVisible, setDrawerVisible, course, setCourse } = props;
  const [selectKey, setSelectKey] = useState('class');

  const getRightTitle = () => {
    return menuMap[selectKey].key;
  };

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
      <Drawer
        destroyOnClose
        getContainer={false}
        className={styles.drawer}
        title={
          <span>
            <FrownOutlined style={{ marginRight: 10 }} />
            课程详情
          </span>
        }
        width="100%"
        placement="right"
        onClose={() => {
          setDrawerVisible(false);
          setCourse(undefined);
        }}
        visible={isDrawerVisible}
      >
        <div className={styles.main}>
          <div className={styles.leftMenu}>
            <Avatar className={styles.courseCover} shape="square" src={course.thumbnail} />
            <div className={styles.courseName}>{course.name}</div>
            <Menu mode="inline" selectedKeys={[selectKey]} onClick={({ key }) => setSelectKey(key)}>
              {getMenu()}
            </Menu>
          </div>
          <Card className={styles.right}>
            <div className={styles.title}>{getRightTitle()}</div>
            {renderChildren()}
          </Card>
        </div>
      </Drawer>
    </>
  );
};

export default CourseDrawer;
