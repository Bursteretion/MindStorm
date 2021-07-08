import React, { useEffect, useState } from 'react';
import { Form, message, Modal, Skeleton } from 'antd';
import { createProfession, infoProfession, updateProfession } from '@/services/profession';
import { ProFormDigit, ProFormRadio, ProFormSelect, ProFormText } from '@ant-design/pro-form';
import { queryAcademySelects } from '@/services/academy';

const ProfessionForm = (props) => {
  const { isModalVisible, setModalVisible, professionId, actionRef } = props;
  const [initialValues, setInitialValues] = useState(undefined);
  const [selectOptions, setSelectOptions] = useState([]);
  const [professionForm] = Form.useForm();

  const fetchProfession = async () => {
    if (professionId !== undefined) {
      const res = await infoProfession(professionId);
      const { professionInfo } = res.data;
      setInitialValues({
        name: professionInfo.name,
        status: professionInfo.status,
        sort: professionInfo.sort,
        academyId: professionInfo.academyId,
      });
    }
  };

  const fetchAcademySelects = async () => {
    const res = await queryAcademySelects();
    if (res.code === 20000) {
      const { academySelects } = res.data;
      setSelectOptions(academySelects);
    }
  };

  useEffect(() => {
    fetchProfession();
    fetchAcademySelects();
  }, []);

  const handleSubmitForm = async (professionVO) => {
    const tip = professionId === undefined ? '添加' : '更新';
    const hide = message.loading(`正在${tip}专业【${professionVO.name}】`);
    try {
      if (professionId === undefined) {
        await createProfession(professionVO);
      } else {
        await updateProfession({ ...professionVO, id: professionId });
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

  const modalOkText = professionId === undefined ? '添加' : '编辑';
  const modalTitle = professionId === undefined ? '添加专业' : '编辑专业';

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
        professionForm.validateFields().then(async (values) => {
          await handleSubmitForm(values);
          professionForm.resetFields();
          setModalVisible(false);
        });
      }}
    >
      {initialValues === undefined && professionId !== undefined ? (
        <Skeleton active />
      ) : (
        <Form
          key="professionForm"
          form={professionForm}
          preserve={false}
          layout="horizontal"
          labelCol={{ span: 4 }}
          initialValues={initialValues}
        >
          <ProFormSelect
            name="academyId"
            label="所属院系"
            placeholder="请选择院系"
            options={selectOptions}
            rules={[{ required: true, message: '请选择院系!' }]}
          />
          <ProFormText
            name="name"
            label="专业名称"
            placeholder="请输入专业名称"
            rules={[{ required: true, message: '专业名称不能为空！' }]}
          />
          <ProFormDigit
            label="院系排序"
            name="sort"
            width="sm"
            min={0}
            rules={[{ required: true, message: '专业排序不能为空！' }]}
          />
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
        </Form>
      )}
    </Modal>
  );
};
export default ProfessionForm;
