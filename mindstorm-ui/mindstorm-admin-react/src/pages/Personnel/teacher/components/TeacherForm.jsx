import React, { useEffect, useState } from 'react';
import { Modal, Form, Skeleton, message, TreeSelect } from 'antd';
import { ProFormText, ProFormRadio, ProFormDigit } from '@ant-design/pro-form';
import { createTeacher, infoTeacher, updateTeacher } from '@/services/user/teacher';
import { useModel } from 'umi';
import ProCard from '@ant-design/pro-card';
import '../index.less';

const TeacherForm = (props) => {
  const [teacherForm] = Form.useForm();
  const { isModalVisible, setModalVisible, teacherId, actionRef } = props;
  const [initialValues, setInitialValues] = useState(undefined);
  const { academyTree = [] } = useModel('academy', (res) => ({
    academyTree: res.academyTree,
  }));

  /**
   * 初始化教师信息
   */
  const handleFetchTeacher = async () => {
    if (teacherId !== undefined) {
      const res = await infoTeacher(teacherId);
      const { teacher } = res.data;
      setInitialValues({
        academyId: teacher.academyId,
        username: teacher.username,
        realName: teacher.realName,
        email: teacher.email,
        phone: teacher.phone,
        sex: teacher.sex,
        age: teacher.age,
        status: teacher.status,
      });
    }
  };

  const handleSubmitForm = async (teacherVO) => {
    const tip = teacherId === undefined ? '添加' : '更新';
    const hide = message.loading(`正在${tip}教师【${teacherVO.realName}】`);
    try {
      if (teacherId === undefined) {
        await createTeacher(teacherVO);
      } else {
        await updateTeacher({ ...teacherVO, id: teacherId });
      }
      hide();
      message.success(`${tip}成功！`);
      setInitialValues(undefined);
      actionRef.current.reset();
      return true;
    } catch (error) {
      hide();
      message.error(`${tip}失败请重试！`);
      return false;
    }
  };

  const modalTitle = teacherId === undefined ? '添加教师' : '编辑教师';
  const modalOkText = teacherId === undefined ? '添加' : '提交';

  useEffect(() => {
    handleFetchTeacher();
  }, []);

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
        teacherForm.validateFields().then(async (values) => {
          await handleSubmitForm(values);
          teacherForm.resetFields();
          setModalVisible(false);
        });
      }}
    >
      {initialValues === undefined && teacherId !== undefined ? (
        <Skeleton active />
      ) : (
        <Form
          key="form"
          form={teacherForm}
          preserve={false}
          layout="horizontal"
          labelCol={{ span: 6 }}
          wrapperCol={{ span: 16 }}
          initialValues={initialValues}
        >
          <ProCard>
            <ProCard style={{ padding: 0 }} colSpan="50%">
              <ProFormText
                name="username"
                label="用户名"
                placeholder="请输入用户名"
                rules={[{ required: true, message: '用户名不能为空！' }]}
              />
              {teacherId !== undefined ? (
                ''
              ) : (
                <ProFormText.Password
                  label="密码"
                  name="password"
                  placeholder="请输入密码"
                  rules={[{ required: true, message: '密码不能为空！' }]}
                />
              )}
              <ProFormText
                name="phone"
                label="电话"
                placeholder="请输入电话"
                rules={[{ len: 11, message: '电话号码格式不正确' }]}
              />
              <ProFormText
                name="email"
                label="邮箱"
                placeholder="请输入邮箱"
                rules={[{ required: true, message: '邮箱不能为空！' }]}
              />
              <ProFormRadio.Group
                name="sex"
                label="性别"
                options={[
                  {
                    label: '男',
                    value: 1,
                  },
                  {
                    label: '女',
                    value: 2,
                  },
                ]}
                rules={[{ required: true, message: '性别必选！' }]}
              />
            </ProCard>
            <ProCard>
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
              <ProFormText
                name="realName"
                label="真实姓名"
                placeholder="请输入真实姓名"
                rules={[{ required: true, message: '真实姓名不能为空！' }]}
              />
              <ProFormDigit
                label="年龄"
                name="age"
                width="sm"
                min={1}
                max={120}
                rules={[{ required: true, message: '年龄不能为空！' }]}
              />
              <ProFormRadio.Group
                name="status"
                label=" 教师状态"
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
                rules={[{ required: true, message: '教师状态必选！' }]}
              />
            </ProCard>
          </ProCard>
        </Form>
      )}
    </Modal>
  );
};

export default TeacherForm;
