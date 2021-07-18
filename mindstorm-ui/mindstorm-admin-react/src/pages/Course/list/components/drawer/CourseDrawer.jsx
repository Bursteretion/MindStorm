import React, { useEffect, useState } from 'react';
import { Card, Drawer, Menu, Avatar } from 'antd';
import styles from './style.less';
import ClassList from './components/Class';
import {
  AppstoreOutlined,
  BarsOutlined,
  CommentOutlined,
  createFromIconfontCN,
  FolderOpenOutlined,
  NotificationOutlined,
  PieChartOutlined,
  ReconciliationOutlined,
  SettingOutlined,
  FrownOutlined,
} from '@ant-design/icons';

const IconFont = createFromIconfontCN({
  scriptUrl: ['//at.alicdn.com/t/font_2559650_2qb531fzjuc.js'],
});

const { Item, Divider } = Menu;

const CourseDrawer = (props) => {
  const { isDrawerVisible, setDrawerVisible, courseId } = props;
  const [selectKey, setSelectKey] = useState('class');

  const menuMap = {
    class: { key: '班级活动', icon: <AppstoreOutlined /> },
    divider1: <Divider key="divider1" />,
    lessonPlan: { key: '教案', icon: <ReconciliationOutlined /> },
    chapter: { key: '章节', icon: <BarsOutlined /> },
    data: { key: '资料', icon: <FolderOpenOutlined /> },
    notice: { key: '通知', icon: <NotificationOutlined /> },
    discuss: { key: '讨论', icon: <CommentOutlined /> },
    work: { key: '作业', icon: <IconFont type="icon-zuoye" /> },
    exam: { key: '考试', icon: <IconFont type="icon-kaoshi" /> },
    question: { key: '题库', icon: <IconFont type="icon-exam_item_bank" /> },
    divider2: <Divider key="divider2" />,
    statistics: { key: '统计', icon: <PieChartOutlined /> },
    setting: { key: '管理', icon: <SettingOutlined /> },
  };

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
        return <ClassList />;

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
        onClose={() => setDrawerVisible(false)}
        visible={isDrawerVisible}
      >
        <div className={styles.main}>
          <div className={styles.leftMenu}>
            <Avatar
              className={styles.courseCover}
              shape="square"
              src="https://www.lwjppz.cn/upload/2021/02/1410480970-3c4c82dd0212492b90219e38ae50fd91.jpeg"
            />
            <div className={styles.courseName}>测试课程</div>
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
