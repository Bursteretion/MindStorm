import React from 'react';
import { PageContainer } from '@ant-design/pro-layout'

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
        用户管理
      </PageContainer>
    </div>
  )
}
