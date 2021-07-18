import request from '@/utils/request';

export async function listCourseClass(courseId) {
  return request(`/mindstorm-education/education/course-class/list/${courseId}`);
}

export async function queryCourseClass(params) {
  return request('/mindstorm-education/education/course-class/query', {
    method: 'GET',
    params,
  });
}

export async function createCourseClass(courseClassVO) {
  return request('/mindstorm-education/education/course-class/create', {
    method: 'POST',
    data: courseClassVO,
  });
}

export async function updateCourseClass(courseClassVO) {
  return request('/mindstorm-education/education/course-class/update', {
    method: 'POST',
    data: courseClassVO,
  });
}

export async function deleteCourseClass(courseClassId) {
  return request(`/mindstorm-education/education/course-class/delete/${courseClassId}`, {
    method: 'DELETE',
  });
}
