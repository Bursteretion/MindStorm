import React, {useRef} from 'react';
import { PlusOutlined } from '@ant-design/icons';
import { Button, Space, Tag } from 'antd';
import ProTable from '@ant-design/pro-table';
import { PageContainer } from '@ant-design/pro-layout';
import { listMenus, MenuType } from "@/services/menu";

const columns = [
  {
    title: '菜单名称',
    dataIndex: 'name',
    width: 200,
    formItemProps: {
      rules: [
        {
          required: true,
          message: '此项为必填项',
        }
      ]
    }
  },
  {
    title: '图标',
    dataIndex: 'icon',
    search: false
  },
  {
    title: '排序',
    dataIndex: 'sort',
    search: false
  },
  {
    title: '权限标识',
    dataIndex: 'permission_value',
    search: false
  },
  {
    title: '组件路径',
    dataIndex: 'component',
    search: false
  },
  {
    title: '菜单类型',
    dataIndex: 'type',
    filters: true,
    onFilter: true,
    valueType: 'select',
    render: (_, record) => {
      const menuType = MenuType[record.type]
      return (
        <Space>
          {
            <Tag color={ menuType.color } key={ menuType.text }>
              { menuType.text }
            </Tag>
          }
        </Space>
      )
    }
  },
  {
    title: '创建时间',
    dataIndex: 'gmtCreate',
    valueType: 'dateTime',
    hideInSearch: true,
  },
  {
    title: '创建时间',
    dataIndex: 'gmtCreate',
    valueType: 'dateRange',
    hideInTable: true,
    search: {
      transform: (value) => {
        return {
          startTime: value[0],
          endTime: value[1],
        };
      },
    },
  },
  {
    title: '操作',
    valueType: 'option',
    render: (text, record, _, action) => [
      <a
        key="editable"
        onClick={() => {
          action?.startEditable?.(record.id);
        }}
      >
        编辑
      </a>
    ]
  }
]

function processingMenus(menus = []) {
  if (menus.length <= 0) return
  menus.forEach(v => {
    const backup = v
    if (backup.children.length <= 0) {
      backup.children = undefined
    }
    processingMenus(backup.children)
  })
}

export default () => {
  const actionRef = useRef();

  return (
    <div>
      <PageContainer
        header={
          {
            ghost: true,
            title: ''
          }
        }
      >
        <ProTable
          columns={ columns }
          actionRef={ actionRef }
          request={
            () => listMenus().then(res => {
              const { menus } = res.data
              processingMenus(menus)
              console.log(menus)
              return {
                data: menus,
                total: res.data.menus.length,
                success: res.data.success,
              }
            })
          }
          rowKey="id"
          search={{ labelWidth: 'auto' }}
          form={{
            syncToUrl: (values, type) => {
              if (type === 'get') {
                return {...values, gmtCreate: [values.startTime, values.endTime]};
              }
              return values;
            },
          }}
          dateFormatter="false"
          headerTitle="系统菜单"
          options={{ fullScreen: true }}
          toolBarRender={
            () => [
              <Button key="button" icon={ <PlusOutlined /> } type="primary">
                新建
              </Button>
            ]
          }
        />
      </PageContainer>
    </div>
  )
};
