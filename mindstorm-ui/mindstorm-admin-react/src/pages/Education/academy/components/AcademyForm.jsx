import React, { useEffect, useState } from 'react';
import { Form, message, Modal, Skeleton, TreeSelect } from 'antd';
import { ProFormDigit, ProFormRadio, ProFormText } from '@ant-design/pro-form';
import { createAcademy, infoAcademy, updateAcademy } from '@/services/academy';
import { useModel } from 'umi';

const AcademyForm = (props) => {
  const { isModalVisible, setModalVisible, academyId, actionRef } = props;
  const [initialValues, setInitialValues] = useState(undefined);
  const [academyForm] = Form.useForm();
  const { academyTree } = useModel('academy', (res) => ({
    academyTree: res.academyTree,
  }));

  useEffect(() => {
    async function fetchData() {
      if (academyId !== undefined) {
        const res = await infoAcademy(academyId);
        const { academyInfo } = res.data;
        setInitialValues({
          name: academyInfo.name,
          status: academyInfo.status,
          sort: academyInfo.sort,
          pid: academyInfo.pid,
        });
      }
    }

    fetchData();
  }, []);

  const handleSubmitForm = async (academyVO) => {
    const tip = academyId === undefined ? '添加' : '更新';
    const hide = message.loading(`正在${tip}院系【${academyVO.name}】`);
    try {
      if (academyId === undefined) {
        await createAcademy(academyVO);
      } else {
        await updateAcademy({ ...academyVO, id: academyId });
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

  const modalOkText = academyId === undefined ? '添加' : '编辑';
  const modalTitle = academyId === undefined ? '添加院系' : '编辑院系';

  return (
    <Modal
      style={{ top: 40 }}
      okText={modalOkText}
      destroyOnClose={true}
      maskClosable={false}
      width={500}
      title={modalTitle}
      visible={isModalVisible}
      onCancel={() => setModalVisible(false)}
      onOk={() => {
        academyForm.validateFields().then(async (values) => {
          await handleSubmitForm(values);
          academyForm.resetFields();
          setModalVisible(false);
        });
      }}
    >
      {initialValues === undefined && academyId !== undefined ? (
        <Skeleton active />
      ) : (
        <Form
          key="academyForm"
          form={academyForm}
          preserve={false}
          layout="horizontal"
          labelCol={{ span: 4 }}
          initialValues={initialValues}
        >
          <Form.Item
            label="上级院系"
            name="pid"
            rules={[{ required: true, message: '上级院系必选！' }]}
          >
            <TreeSelect
              allowClear
              showArrow
              placeholder="请选择上级院系/部门"
              treeData={academyTree}
            />
          </Form.Item>
          <ProFormText
            name="name"
            label="院系名称"
            placeholder="请输入院系名称"
            rules={[{ required: true, message: '院系名称不能为空！' }]}
          />
          <ProFormDigit
            label="院系排序"
            name="sort"
            width="sm"
            min={0}
            rules={[{ required: true, message: '院系排序不能为空！' }]}
          />
          {initialValues !== undefined && initialValues.pid === '0' ? (
            ''
          ) : (
            <ProFormRadio.Group
              name="status"
              label="院系状态"
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
              rules={[{ required: true, message: '院系状态必选！' }]}
            />
          )}
        </Form>
      )}
    </Modal>
  );
};

export default AcademyForm;
