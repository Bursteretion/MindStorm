import React from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import CourseTable from './components/CourseTable';

const CourseList = () => {
  return (
    <>
      <PageContainer
        header={{
          ghost: true,
          title: '',
        }}
      >
        <CourseTable />
      </PageContainer>
    </>
  );
};

export default CourseList;
