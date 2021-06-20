import React from 'react';
import { PageContainer } from '@ant-design/pro-layout';

export default () => {
  return (
    <div>
      <PageContainer
        header={
          {
            ghost: true,
            breadcrumb: {
              routes: [
                {
                  path: '/',
                  breadcrumbName: '首页',
                },
                {
                  path: '/dashboard',
                  breadcrumbName: '仪表盘',
                }
              ]
            }
          }
        }
      >
        <div>DashBoard 仪表盘首页</div>
      </PageContainer>
    </div>
  )
};
