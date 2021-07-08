import React from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import ProfessionTable from './components/ProfessionTable';

const ProfessionList = () => {
  return (
    <>
      <PageContainer
        header={{
          ghost: true,
          title: '',
        }}
      >
        <ProfessionTable />
      </PageContainer>
    </>
  );
};

export default ProfessionList;
