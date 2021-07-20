import request from '@/utils/request';

export async function listStudentAndCheckedIds(params) {
  return request(`/mindstorm-education/education/course-class-student/list/student`, {
    method: 'POST',
    data: params,
  });
}

export async function queryCourseClassStudent(params) {
  return request('/mindstorm-education/education/course-class-student/query', {
    method: 'POST',
    data: params,
  });
}

export async function createCourseClassStudent(courseClassStudentVO) {
  return request('/mindstorm-education/education/course-class-student/create', {
    method: 'POST',
    data: courseClassStudentVO,
  });
}

export async function createBatchCourseClassStudent(courseClassStudentBatchVO) {
  return request('/mindstorm-education/education/course-class-student/create/batch', {
    method: 'POST',
    data: courseClassStudentBatchVO,
  });
}

export async function deleteCourseClassStudent(courseClassId) {
  return request(`/mindstorm-education/education/course-class-student/delete/${courseClassId}`, {
    method: 'DELETE',
  });
}
