import React from 'react';
import { PageContainer } from '@ant-design/pro-layout'
import RoleTable from "./components/RoleTable";

const RoleList = () => {
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
        <RoleTable/>
      </PageContainer>
    </div>
  )
}

export default RoleList

