import React, { useEffect, useState } from 'react';
import { Modal, Form, message, TreeSelect, Upload } from 'antd';
import ImgCrop from 'antd-img-crop';
import { ProFormText, ProFormRadio, ProFormTextArea } from '@ant-design/pro-form';
import { createCourse } from '@/services/course';
import { useModel } from 'umi';
import ProCard from '@ant-design/pro-card';
import { getToken } from '@/utils/authority';
import '../index.less';

const CourseForm = (props) => {
  const [courseForm] = Form.useForm();
  const { isModalVisible, setModalVisible, actionRef } = props;
  const [fileList, setFileList] = useState([]);
  const [imageUrl, setImageUrl] = useState(
    'https://p.ananas.chaoxing.com/star3/origin/ebe4b550f689a518631aa3f61a36c10e.jpg',
  );
  const { academyTree = [] } = useModel('academy', (res) => ({
    academyTree: res.academyTree,
  }));
  const { userId = '' } = useModel('@@initialState', (res) => ({
    userId: res.initialState.currentUser.id,
  }));

  const handleSubmitForm = async (courseVO) => {
    const course = { ...courseVO, thumbnail: imageUrl, userId };
    const hide = message.loading(`正在创建课程【${courseVO.name}】`);
    try {
      await createCourse(course);
      hide();
      message.success(`创建成功！`);
      actionRef.current.reset();
      return true;
    } catch (error) {
      hide();
      message.error(`$创建失败请重试！`);
      return false;
    }
  };

  function beforeUpload(file) {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
    if (!isJpgOrPng) {
      message.error('你只能上传JPG/PNG格式的图片!');
    }
    const isLt5M = file.size / 1024 / 1024 < 5;
    if (!isLt5M) {
      message.error('图片大小不能超过5m');
    }
    return isJpgOrPng && isLt5M;
  }

  const onChange = (info) => {
    if (info.file.status !== 'uploading') {
      setFileList(info.fileList);
    }
    if (info.file.status === 'done') {
      message.success(`${info.file.name} 上传成功！`);
      const { path } = info.file.response.data.courseCover;
      setImageUrl(path);
    } else if (info.file.status === 'error') {
      message.error(`${info.file.name} 上传失败.`);
    }
  };

  const modalTitle = '创建课程';
  const modalOkText = '创建';

  return (
    <Modal
      style={{ top: 40 }}
      okText={modalOkText}
      destroyOnClose={true}
      maskClosable={false}
      width={700}
      title={modalTitle}
      visible={isModalVisible}
      onCancel={() => setModalVisible(false)}
      onOk={() => {
        courseForm.validateFields().then(async (values) => {
          await handleSubmitForm(values);
          courseForm.resetFields();
          setModalVisible(false);
        });
      }}
    >
      <Form
        key="form"
        form={courseForm}
        preserve={false}
        layout="horizontal"
        labelCol={{ span: 6 }}
        wrapperCol={{ span: 16 }}
      >
        <ProCard>
          <ProCard style={{ padding: 0, textAlign: 'center' }} colSpan="30%">
            <ImgCrop rotate>
              <Upload
                style={{ width: 200 }}
                action="http://localhost:8000/mindstorm-file/file/course-cover/upload"
                headers={{
                  Authorization: getToken(),
                }}
                name="image"
                maxCount={1}
                beforeUpload={beforeUpload}
                listType="picture-card"
                onChange={onChange}
                progress={{
                  strokeColor: {
                    '0%': '#108ee9',
                    '100%': '#87d068',
                  },
                  strokeWidth: 3,
                  format: (percent) => `${parseFloat(percent.toFixed(2))}%`,
                }}
              >
                {fileList.length < 1 && '上传课程封面'}
              </Upload>
            </ImgCrop>
          </ProCard>
          <ProCard>
            <ProFormText
              name="name"
              label="课程名称"
              placeholder="请输入课程名称"
              rules={[{ required: true, message: '课程名称不能为空！' }]}
            />
            <ProFormText
              name="teacherName"
              label="教师姓名"
              placeholder="教师姓名"
              rules={[{ required: true, message: '教师姓名不能为空！' }]}
            />
            <Form.Item
              label="所属院系"
              name="academyId"
              rules={[{ required: true, message: '所属院系必选！' }]}
            >
              <TreeSelect
                allowClear
                showArrow
                placeholder="请选择所属院系/部门"
                treeData={academyTree}
              />
            </Form.Item>
            <ProFormRadio.Group
              name="status"
              label=" 课程状态"
              options={[
                {
                  label: '正常',
                  value: 1,
                },
                {
                  label: '禁用',
                  value: 0,
                },
              ]}
              rules={[{ required: true, message: '课程状态必选！' }]}
            />
            <ProFormTextArea
              name="text"
              label="课程说明"
              placeholder="请输入课程说明"
              fieldProps={{
                showCount: true,
                allowClear: true,
              }}
            />
          </ProCard>
        </ProCard>
      </Form>
    </Modal>
  );
};

export default CourseForm;
