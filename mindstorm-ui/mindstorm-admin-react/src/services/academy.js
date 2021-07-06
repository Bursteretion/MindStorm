import request from '@/utils/request';

export async function queryAcademies(params) {
  return request('/mindstorm-education/education/academy/query', {
    method: 'POST',
    data: params,
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
