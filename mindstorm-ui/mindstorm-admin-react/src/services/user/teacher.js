import request from '@/utils/request';

export async function queryTeachers(params) {
  return request('/mindstorm-user/user/teacher/query', {
    method: 'POST',
    data: params,
  });
}

export async function createTeacher(teacherVO) {
  return request('/mindstorm-user/user/teacher/create', {
    method: 'POST',
    data: teacherVO,
  });
}

export async function updateTeacher(teacherVO) {
  return request('/mindstorm-user/user/teacher/update', {
    method: 'POST',
    data: teacherVO,
  });
}

export async function deleteTeacher(teacherId) {
  return request(`/mindstorm-user/user/teacher/delete/${teacherId}`, {
    method: 'DELETE',
  });
}

export async function infoTeacher(teacherId) {
  return request(`/mindstorm-user/user/teacher/info/${teacherId}`, {
    method: 'PUT',
  });
}

export async function changeTeacherStatus(teacherId, status) {
  return request(`/mindstorm-user/user/teacher/change/${teacherId}/${status}`, {
    method: 'PUT',
  });
}

export const TeacherStatus = {
  1: { text: '启用', status: 1 },
  0: { text: '禁用', status: 0 },
};
