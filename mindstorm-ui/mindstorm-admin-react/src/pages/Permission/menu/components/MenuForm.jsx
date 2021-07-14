import React, { useEffect, useState } from 'react';
import { Form, message, TreeSelect, Radio, Input, InputNumber, Skeleton, Modal } from 'antd';
import {
  insertMenu,
  listMenusByType,
  MenuType,
  updateMenu,
  getMenuById,
} from '@/services/permission/menu';
import IconSelector from '@/components/IconSelector';
import { MenuOutlined } from '@ant-design/icons';
import * as Icons from '@ant-design/icons';
import convertToTreeMenus from '@/utils/treeSelect';
import '../index.less';

const MenuForm = (props) => {
  const { menuId, isModalVisible, setModalVisible, tableActionRef } = props;
  const [currentMenu, setCurrentMenu] = useState({
    type: 0,
    status: 1,
    sort: 0,
  });
  const [menuType, setMenuType] = useState(0);
  const [menuForm] = Form.useForm();
  const [fatherMenus, setFatherMenus] = useState([]);

  const [iconSelectSetting, setIconSelectSetting] = useState({
    visible: false,
    iconName: '',
    icon: <MenuOutlined />,
  });

  useEffect(() => {
    async function fetchData() {
      const menuRes = await listMenusByType([MenuType['0'].value, MenuType['1'].value]);
      const fatherMenu = { value: '0', title: '主类目', children: [] };
      const menus = menuRes.data.treeMenus;
      convertToTreeMenus(menus);
      fatherMenu.children = menus;
      setFatherMenus([fatherMenu]);
      if (menuId !== undefined) {
        const res = await getMenuById(menuId);
        const { menu } = res.data;
        if (menu.pid === '') menu.pid = '0';
        setCurrentMenu(menu);

        let icon;
        if (menu.icon !== undefined && menu.icon !== '' && icon) {
          icon = React.createElement(Icons[menu.icon]);
          setIconSelectSetting({ ...iconSelectSetting, icon });
        }
      }
    }

    fetchData();
  }, []);

  const handleMenuSubmit = async (menuVO) => {
    const menu = { ...menuVO, pid: menuVO.pid === '0' ? '' : menuVO.pid };
    const tip = currentMenu.id === undefined ? '添加' : '更新';
    const hide = message.loading(`正在${tip}菜单【${menuVO.name}】`);
    try {
      if (currentMenu.id === undefined) {
        await insertMenu(menu);
      } else {
        await updateMenu({ ...menu, id: currentMenu.id });
      }
      hide();
      message.success(`${tip}成功！`);
      tableActionRef.current.reset();
      return true;
    } catch (error) {
      hide();
      message.error(`${tip}失败请重试！`);
      return false;
    }
  };

  return (
    <Modal
      style={{ top: 40 }}
      okText={menuId === undefined ? '添加' : '提交'}
      destroyOnClose={true}
      maskClosable={false}
      afterClose={() => menuForm.resetFields()}
      width={600}
      title={menuId === undefined ? '添加菜单' : '编辑菜单'}
      visible={isModalVisible}
      onCancel={() => setModalVisible(false)}
      onOk={() => {
        menuForm.validateFields().then(async (values) => {
          await handleMenuSubmit(values);
          menuForm.resetFields();
          setModalVisible(false);
        });
      }}
    >
      {currentMenu.id === undefined && menuId !== undefined ? (
        <Skeleton active />
      ) : (
        <Form
          form={menuForm}
          preserve={false}
          layout="horizontal"
          labelCol={{ span: 4 }}
          initialValues={currentMenu}
        >
          <Form.Item
            label="上级菜单"
            name="pid"
            rules={[{ required: true, message: '上级菜单必选！' }]}
          >
            <TreeSelect
              allowClear
              showArrow
              placeholder="请选择上级菜单"
              treeData={fatherMenus}
              treeDefaultExpandedKeys={[currentMenu.pid]}
            />
          </Form.Item>
          <Form.Item
            name="type"
            label="菜单类型"
            rules={[{ required: true, message: '菜单类型必选！' }]}
          >
            <Radio.Group onChange={(e) => setMenuType(e.target.value)}>
              <Radio value={0}>目录</Radio>
              <Radio value={1}>菜单</Radio>
              <Radio value={2}>按钮</Radio>
            </Radio.Group>
          </Form.Item>
          {menuType !== 2 && [
            <Form.Item key="icon" name="icon" label="菜单图标">
              <Input
                allowClear
                placeholder="选择图标"
                prefix={iconSelectSetting.icon && iconSelectSetting.icon}
                onClick={() => setIconSelectSetting({ ...iconSelectSetting, visible: true })}
              />
            </Form.Item>,
            <Form.Item
              key="alias"
              label="菜单别名"
              name="alias"
              rules={[{ required: true, message: '菜单别名不能为空！' }]}
            >
              <Input placeholder="请输入菜单别名" />
            </Form.Item>,
            <Form.Item
              key="component"
              name="component"
              label="组件"
              rules={[{ required: true, message: '组件不能为空！' }]}
            >
              <Input allowClear placeholder="请填写组件路径，eg：Permission/Menu" />
            </Form.Item>,
            <Form.Item
              key="path"
              name="path"
              label="路由地址"
              rules={[{ required: true, message: '路由地址不能为空！' }]}
            >
              <Input allowClear placeholder="请填写路由地址，eg：permission/menu" />
            </Form.Item>,
            <Form.Item key="redirect" name="redirect" label="重定向地址">
              <Input allowClear placeholder="请填写菜单重定向地址" />
            </Form.Item>,
          ]}
          <Form.Item
            label="菜单名称"
            name="name"
            rules={[{ required: true, message: '菜单名称不能为空' }]}
          >
            <Input placeholder="请输入菜单名称" />
          </Form.Item>
          {menuType !== 0 && (
            <Form.Item
              name="permissionValue"
              label="权限标识"
              rules={[{ required: true, message: '权限标识不能为空！' }]}
            >
              <Input allowClear placeholder="请输入权限标识" />
            </Form.Item>
          )}
          <Form.Item className="clearMrBottom" label="菜单状态" required>
            <Form.Item
              style={{ display: 'inline-flex', width: '45%' }}
              name="status"
              rules={[{ required: true, message: '菜单状态必选！' }]}
            >
              <Radio.Group>
                <Radio value={1}>正常</Radio>
                <Radio value={0}>禁用</Radio>
              </Radio.Group>
            </Form.Item>
            <Form.Item
              label="显示排序"
              style={{ display: 'inline-flex', width: '45%' }}
              name="sort"
              rules={[{ required: true, message: '菜单排序必选！' }]}
            >
              <InputNumber />
            </Form.Item>
          </Form.Item>
          <IconSelector
            menuForm={menuForm}
            iconSelectSetting={iconSelectSetting}
            setIconSelectSetting={setIconSelectSetting}
          />
        </Form>
      )}
    </Modal>
  );
};

export default MenuForm;
