import React, { useEffect, useState } from 'react';
import { Modal, Form, Skeleton, message } from 'antd';
import {
  ProFormText,
  ProFormRadio,
  ProFormDateTimePicker,
  ProFormDigit,
  ProFormSelect,
} from '@ant-design/pro-form';
import { createUser, getUserById, updateUser } from '@/services/permission/user';
import { listUnDisableRoles } from '@/services/permission/role';
import { distributeRole, userRole } from '@/services/permission/userRole';

const UserForm = (props) => {
  const [userForm] = Form.useForm();
  const { isModalVisible, setModalVisible, userId, actionRef } = props;
  const [selectedRole, setSelectedRole] = useState([]);
  const [selectOptions, setSelectOptions] = useState([]);
  const [initialValues, setInitialValues] = useState(undefined);

  /**
   * 初始化用户信息
   */
  const handleFetchUser = async () => {
    if (userId !== undefined) {
      const res = await getUserById(userId);
      const { user } = res.data;
      setInitialValues({
        username: user.username,
        realName: user.realName,
        birthDay: user.birthDay,
        email: user.email,
        phone: user.phone,
        sex: user.sex,
        age: user.age,
        status: user.status,
      });
    }
  };
  const handleFetchUnDisableRoles = async () => {
    const res = await listUnDisableRoles();
    if (res.code === 20000) {
      const { roles = [] } = res.data;
      setSelectOptions(roles);
    }
  };
  const handleFetchUserRole = async () => {
    if (userId !== undefined) {
      const res = await userRole(userId);
      if (res.code === 20000) {
        const {
          userRoleDTO: { roles = [] },
        } = res.data;
        setSelectedRole(roles);
      }
    }
  };

  useEffect(() => {
    handleFetchUser();
    handleFetchUnDisableRoles();
    handleFetchUserRole();
  }, []);

  const handleSubmitForm = async (userVO) => {
    const tip = userId === undefined ? '添加' : '更新';
    const hide = message.loading(`正在${tip}用户【${userVO.username}】`);
    try {
      if (userId === undefined) {
        await createUser(userVO);
      } else {
        await updateUser({ ...userVO, id: userId });
      }
      const { userRoles = [] } = userVO;
      if (userRoles.length >= 0) {
        await distributeRole(userId, userRoles);
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

  const modalTitle = userId === undefined ? '添加用户' : '编辑用户';
  const modalOkText = userId === undefined ? '添加' : '提交';

  return (
    <Modal
      style={{ top: 40 }}
      okText={modalOkText}
      destroyOnClose={true}
      maskClosable={false}
      width={600}
      title={modalTitle}
      visible={isModalVisible}
      onCancel={() => setModalVisible(false)}
      onOk={() => {
        userForm.validateFields().then(async (values) => {
          await handleSubmitForm(values);
          userForm.resetFields();
          setModalVisible(false);
        });
      }}
    >
      {initialValues === undefined && userId !== undefined ? (
        <Skeleton active />
      ) : (
        <Form
          key="form"
          form={userForm}
          preserve={false}
          layout="horizontal"
          labelCol={{ span: 4 }}
          initialValues={initialValues}
        >
          {initialValues && initialValues.username === 'admin' ? (
            ''
          ) : (
            <ProFormText
              name="username"
              label="用户名"
              placeholder="请输入用户名"
              rules={[{ required: true, message: '用户名不能为空！' }]}
            />
          )}
          {userId !== undefined ? (
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
            name="realName"
            label="真实姓名"
            placeholder="请输入真实姓名"
            rules={[{ required: true, message: '真实姓名不能为空！' }]}
          />
          <ProFormText
            name="phone"
            label="电话"
            placeholder="请输入电话"
            rules={[{ len: 11, message: '电话号码格式不正确' }]}
          />
          <ProFormText name="email" label="邮箱" placeholder="请输入邮箱" />
          <ProFormDateTimePicker name="birthDay" label="生日" placeholder="请选择出生日期" />
          <ProFormDigit label="年龄" name="age" width="sm" min={1} max={120} />
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
          />
          <ProFormRadio.Group
            name="status"
            label=" 用户状态"
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
            rules={[{ required: true, message: '用户状态必选！' }]}
          />
          <ProFormSelect
            name="userRoles"
            fieldProps={{
              mode: 'multiple',
              maxTagCount: 'responsive',
              value: selectedRole,
              onChange: (value) => setSelectedRole(value),
            }}
            label="用户角色"
            options={selectOptions}
            placeholder="请为该用户分配角色"
          />
        </Form>
      )}
    </Modal>
  );
};

export default UserForm;
