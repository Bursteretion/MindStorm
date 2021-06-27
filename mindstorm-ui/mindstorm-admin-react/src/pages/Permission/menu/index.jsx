import React from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import MenuTable from "@/pages/Permission/menu/components/MenuTable";

const MenuList = () => {
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
        <MenuTable/>
      </PageContainer>
    </div>
  )
}

export default MenuList
