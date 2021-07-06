import React from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import AcademyTable from './components/AcademyTable';

const AcademyList = () => {
  return (
    <div>
      <PageContainer
        header={{
          ghost: true,
          title: '',
        }}
      >
        <AcademyTable />
      </PageContainer>
    </div>
  );
};

export default AcademyList;
