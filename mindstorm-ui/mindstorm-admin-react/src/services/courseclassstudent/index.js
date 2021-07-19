import request from '@/utils/request';

export async function listCourseClassStudent(classId) {
  return request(`/mindstorm-education/education/course-class-student/list/${classId}`);
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

export async function deleteCourseClassStudent(courseClassId) {
  return request(`/mindstorm-education/education/course-class-student/delete/${courseClassId}`, {
    method: 'DELETE',
  });
}
