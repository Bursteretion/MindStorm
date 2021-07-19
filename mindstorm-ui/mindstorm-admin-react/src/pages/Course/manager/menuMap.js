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
} from '@ant-design/icons';
import React from 'react';
import { Menu } from 'antd';

const IconFont = createFromIconfontCN({
  scriptUrl: ['//at.alicdn.com/t/font_2559650_2qb531fzjuc.js'],
});

const { Divider } = Menu;

export const menuMap = {
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
