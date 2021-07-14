import React from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import StudentTable from './components/StudentTable';

const StudentList = () => {
  return (
    <>
      <PageContainer
        header={{
          ghost: true,
          title: '',
        }}
      >
        <StudentTable />
      </PageContainer>
    </>
  );
};

export default StudentList;
