import React, { useEffect, useState } from 'react';
import { Button, Space, Input } from 'antd';
import { PlusCircleOutlined, SettingOutlined } from '@ant-design/icons';
import { queryCourseClass } from '@/services/courseclass';
import '../index.less';
import RecordDropDown from './RecordDropDown';
import ClassForm from '@/pages/Course/manager/components/class/components/ClassForm';

const { Search } = Input;

const CourseClassList = (props) => {
  const { courseId, setCurrentClassId, classId } = props;

  const [loading, setLoading] = useState(false);
  const [courseClasses, setCourseClasses] = useState([]);
  const [isModalVisible, setModalVisible] = useState(false);

  const handleListItemClick = (e) => {
    e.preventDefault();
    const liItems = document.getElementById('classList').children;
    let item = e.target;
    if (item.tagName === 'SPAN') {
      item = item.parentNode;
    }
    for (let i = 0; i < liItems.length; i++) {
      liItems[i].classList.remove('selected');
    }
    item.classList.add('selected');
    setCurrentClassId(item.getAttribute('data-classid'));
  };

  const handleQueryCourseClass = async (params) => {
    setLoading(true);
    const res = await queryCourseClass(params);
    if (res.code === 20000) {
      setCourseClasses(res.data.courseClasses);
      setLoading(false);
    }
  };

  const fetchCourseClass = () => {
    handleQueryCourseClass({ courseId, className: '' });
  };

  useEffect(() => {
    if (courseId !== undefined) {
      fetchCourseClass();
    }
  }, []);

  return (
    <>
      <Space className="space">
        <Button
          onClick={() => {
            setModalVisible(true);
          }}
          className="button"
          type="link"
          icon={<PlusCircleOutlined />}
        >
          新建班级
        </Button>
        <Button className="button" type="link" icon={<SettingOutlined />}>
          管理班级
        </Button>
      </Space>
      <Space>
        <Search
          placeholder="搜索班级"
          loading={loading}
          onSearch={(value) => handleQueryCourseClass({ courseId, className: value })}
          allowClear
        />
      </Space>
      <div className="list">
        <ul id="classList" onClick={handleListItemClick} className="ant-list">
          {courseClasses === undefined
            ? ''
            : courseClasses.map((item) => (
                <li
                  className={item.id === classId ? 'selected' : ''}
                  key={item.id}
                  data-classid={item.id}
                >
                  <span>{item.className}</span>
                  <RecordDropDown fetchCourseClass={fetchCourseClass} courseClass={item} />
                </li>
              ))}
        </ul>
      </div>
      {!isModalVisible ? (
        ''
      ) : (
        <ClassForm
          isModalVisible={isModalVisible}
          setModalVisible={setModalVisible}
          handleQueryCourseClass={handleQueryCourseClass}
          courseId={courseId}
        />
      )}
    </>
  );
};

export default CourseClassList;
