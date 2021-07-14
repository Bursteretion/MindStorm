import React, { useEffect, useState } from 'react';
import { Modal, Form, Skeleton, message, TreeSelect } from 'antd';
import { ProFormText, ProFormRadio, ProFormDigit, ProFormSelect } from '@ant-design/pro-form';
import { createStudent, infoStudent, updateStudent } from '@/services/user/student';
import { useModel } from 'umi';
import { listProfessionsByAcademyId } from '@/services/education/profession';
import ProCard from '@ant-design/pro-card';
import '../index.less';

const StudentForm = (props) => {
  const [studentForm] = Form.useForm();
  const { isModalVisible, setModalVisible, studentId, actionRef } = props;
  const [initialValues, setInitialValues] = useState(undefined);
  const [selectProfessions, setSelectProfessions] = useState([]);
  const { academyTree = [] } = useModel('academy', (res) => ({
    academyTree: res.academyTree,
  }));

  const onChange = async (value) => {
    if (value) {
      const res = await listProfessionsByAcademyId(value);
      if (res.code === 20000) {
        const { professions } = res.data;
        setSelectProfessions(professions);
      }
    }
  };

  /**
   * 初始化学生信息
   */
  const handleFetchStudent = async () => {
    if (studentId !== undefined) {
      const res = await infoStudent(studentId);
      const { student } = res.data;
      await onChange(student.academyId);
      setInitialValues({
        academyId: student.academyId,
        professionId: student.professionId,
        username: student.username,
        realName: student.realName,
        sno: student.sno,
        email: student.email,
        phone: student.phone,
        sex: student.sex,
        age: student.age,
        status: student.status,
      });
    }
  };

  const handleSubmitForm = async (studentVO) => {
    const tip = studentId === undefined ? '添加' : '更新';
    const hide = message.loading(`正在${tip}用户【${studentVO.realName}】`);
    try {
      if (studentId === undefined) {
        await createStudent(studentVO);
      } else {
        await updateStudent({ ...studentVO, id: studentId });
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

  const modalTitle = studentId === undefined ? '添加学生' : '编辑学生';
  const modalOkText = studentId === undefined ? '添加' : '提交';

  useEffect(() => {
    handleFetchStudent();
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
        studentForm.validateFields().then(async (values) => {
          await handleSubmitForm(values);
          studentForm.resetFields();
          setModalVisible(false);
        });
      }}
    >
      {initialValues === undefined && studentId !== undefined ? (
        <Skeleton active />
      ) : (
        <Form
          key="form"
          form={studentForm}
          preserve={false}
          layout="horizontal"
          labelCol={{ span: 6 }}
          wrapperCol={{ span: 16 }}
          initialValues={initialValues}
        >
          <ProCard>
            <ProCard style={{ padding: 0 }} colSpan="50%">
              <ProFormText
                name="sno"
                label="学号"
                placeholder="请输入学号"
                rules={[{ required: true, message: '学号不能为空！' }]}
              />
              {studentId !== undefined ? (
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
              <ProFormDigit
                label="年龄"
                name="age"
                width="sm"
                min={1}
                max={120}
                rules={[{ required: true, message: '年龄不能为空！' }]}
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
                  onChange={(value) => onChange(value)}
                  placeholder="请选择所属院系/部门"
                  treeData={academyTree}
                />
              </Form.Item>
              <ProFormSelect
                showSearch
                allowClear
                filterOption={(input, option) =>
                  option.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
                }
                name="professionId"
                label="所属专业"
                options={selectProfessions}
                placeholder="请选择所属专业"
                rules={[{ required: true, message: '所属专业必选!' }]}
              />
              <ProFormText
                name="username"
                label="用户名"
                placeholder="请输入用户名"
                rules={[{ required: true, message: '用户名不能为空！' }]}
              />
              <ProFormText
                name="realName"
                label="真实姓名"
                placeholder="请输入真实姓名"
                rules={[{ required: true, message: '真实姓名不能为空！' }]}
              />
              <ProFormRadio.Group
                name="status"
                label=" 学生状态"
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
                rules={[{ required: true, message: '学生状态必选！' }]}
              />
            </ProCard>
          </ProCard>
        </Form>
      )}
    </Modal>
  );
};
export default StudentForm;
