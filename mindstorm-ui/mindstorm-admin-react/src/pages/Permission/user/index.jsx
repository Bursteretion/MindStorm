import React from 'react';
import { PageContainer } from '@ant-design/pro-layout'
import UserTable from "@/pages/Permission/user/components/UserTable";

export default () => {
  return (
    <div>
      <PageContainer
        header={
          {
            title: '',
            ghost: true
          }
        }
      >
        <UserTable/>
      </PageContainer>
    </div>
  )
}
