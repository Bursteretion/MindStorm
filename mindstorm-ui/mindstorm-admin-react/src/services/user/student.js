import request from '@/utils/request';

export async function queryStudents(params) {
  return request('/mindstorm-user/user/student/query', {
    method: 'POST',
    data: params,
  });
}

export async function createStudent(studentVO) {
  return request('/mindstorm-user/user/student/create', {
    method: 'POST',
    data: studentVO,
  });
}

export async function updateStudent(studentVO) {
  return request('/mindstorm-user/user/student/update', {
    method: 'POST',
    data: studentVO,
  });
}

export async function deleteStudent(studentId) {
  return request(`/mindstorm-user/user/student/delete/${studentId}`, {
    method: 'DELETE',
  });
}

export async function infoStudent(studentId) {
  return request(`/mindstorm-user/user/student/info/${studentId}`, {
    method: 'PUT',
  });
}

export async function changeStudentStatus(studentId, status) {
  return request(`/mindstorm-user/user/student/change/${studentId}/${status}`, {
    method: 'PUT',
  });
}

export const StudentStatus = {
  1: { text: '启用', status: 1 },
  0: { text: '禁用', status: 0 },
};
