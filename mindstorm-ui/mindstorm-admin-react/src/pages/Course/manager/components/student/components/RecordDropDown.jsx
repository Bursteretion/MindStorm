import React from 'react';
import { DownOutlined } from '@ant-design/icons';
import { Dropdown, Menu, message, Popconfirm } from 'antd';
import { deleteCourseClass } from '@/services/courseclass';

const RecordDropDown = (props) => {
  const { courseClass, fetchCourseClass } = props;

  const handleDeleteCourseClass = async () => {
    const hide = message.loading(`正在删除班级【${courseClass.className}】`);
    try {
      await deleteCourseClass(courseClass.id);
      hide();
      message.success(`删除成功！`);
      fetchCourseClass();
      return true;
    } catch (error) {
      hide();
      message.error(`删除失败请重试！`);
      return false;
    }
  };

  const menu = (
    <Menu>
      <Menu.Item key="resetClassName">
        <a>重命名</a>
      </Menu.Item>
      <Menu.Item key="settingClass">
        <a>设置</a>
      </Menu.Item>
      <Menu.Item key="deleteClass">
        <Popconfirm
          key="delete"
          title={`你确定要删除【${courseClass.className}】这个班级吗？`}
          onConfirm={() => handleDeleteCourseClass()}
          okText="确定"
          cancelText="取消"
        >
          <a>删除</a>
        </Popconfirm>
      </Menu.Item>
    </Menu>
  );

  return (
    <>
      <Dropdown overlay={menu}>
        <a className="ant-dropdown-link" onClick={(e) => e.preventDefault()}>
          更多 <DownOutlined />
        </a>
      </Dropdown>
    </>
  );
};

export default RecordDropDown;
