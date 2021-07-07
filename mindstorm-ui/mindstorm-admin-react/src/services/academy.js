import request from '@/utils/request';

export async function queryAcademies(params) {
  return request('/mindstorm-education/education/academy/query', {
    method: 'POST',
    data: params,
  });
}

export async function createAcademy(academyVO) {
  return request('/mindstorm-education/education/academy/create', {
    method: 'POST',
    data: academyVO,
  });
}

export async function updateAcademy(academyVO) {
  return request('/mindstorm-education/education/academy/update', {
    method: 'POST',
    data: academyVO,
  });
}

export async function deleteAcademy(academyId) {
  return request(`/mindstorm-education/education/academy/delete/${academyId}`, {
    method: 'DELETE',
  });
}

export async function infoAcademy(academyId) {
  return request(`/mindstorm-education/education/academy/info/${academyId}`, {
    method: 'PUT',
  });
}

export async function changeAcademyStatus(academyId, status) {
  return request(`/mindstorm-education/education/academy/change/${academyId}/${status}`, {
    method: 'PUT',
  });
}

export const AcademyStatus = {
  1: { text: '启用', status: 1 },
  0: { text: '禁用', status: 0 },
};
