import React from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import TeacherTable from './components/TeacherTable';

const TeacherList = () => {
  return (
    <>
      <PageContainer
        header={{
          ghost: true,
          title: '',
        }}
      >
        <TeacherTable />
      </PageContainer>
    </>
  );
};

export default TeacherList;
