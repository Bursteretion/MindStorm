import request from '@/utils/request';

export async function listCourse(params) {
  return request('/mindstorm-education/education/course/query', {
    method: 'POST',
    data: params,
  });
}

export async function queryCourse(params) {
  return request('/mindstorm-education/education/course/query', {
    method: 'POST',
    data: params,
  });
}

export async function createCourse(courseVO) {
  return request('/mindstorm-education/education/course/create', {
    method: 'POST',
    data: courseVO,
  });
}

export async function updateCourse(courseVO) {
  return request('/mindstorm-education/education/course/update', {
    method: 'POST',
    data: courseVO,
  });
}

export async function deleteCourse(courseId) {
  return request(`/mindstorm-education/education/course/delete/${courseId}`, {
    method: 'DELETE',
  });
}

export async function infoCourse(courseId) {
  return request(`/mindstorm-education/education/course/info/${courseId}`);
}

export async function changeCourseStatus(courseId, status) {
  return request(`/mindstorm-education/education/course/change/${courseId}/${status}`, {
    method: 'PUT',
  });
}

export const CourseStatus = {
  1: { text: '启用', status: 1 },
  0: { text: '禁用', status: 0 },
};
