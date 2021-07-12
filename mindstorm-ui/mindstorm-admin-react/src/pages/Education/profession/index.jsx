import React from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import ProfessionMain from './components/ProfessionMain';

const ProfessionList = () => {
  return (
    <>
      <PageContainer
        header={{
          ghost: true,
          title: '',
        }}
      >
        <ProfessionMain />
      </PageContainer>
    </>
  );
};

export default ProfessionList;
