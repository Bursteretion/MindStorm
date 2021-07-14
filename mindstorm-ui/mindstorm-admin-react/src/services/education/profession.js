import request from '@/utils/request';

export async function listSelectProfessions() {
  return request('/mindstorm-education/education/profession/listSelect');
}

export async function queryProfessions(params) {
  return request('/mindstorm-education/education/profession/query', {
    method: 'POST',
    data: params,
  });
}

export async function listProfessionsByAcademyId(academyId) {
  return request(`/mindstorm-education/education/profession/list/${academyId}`);
}

export async function createProfession(professionVO) {
  return request('/mindstorm-education/education/profession/create', {
    method: 'POST',
    data: professionVO,
  });
}

export async function updateProfession(professionVO) {
  return request('/mindstorm-education/education/profession/update', {
    method: 'POST',
    data: professionVO,
  });
}

export async function deleteProfession(professionId) {
  return request(`/mindstorm-education/education/profession/delete/${professionId}`, {
    method: 'DELETE',
  });
}

export async function infoProfession(professionId) {
  return request(`/mindstorm-education/education/profession/info/${professionId}`, {
    method: 'PUT',
  });
}

export async function changeProfessionStatus(professionId, status) {
  return request(`/mindstorm-education/education/profession/change/${professionId}/${status}`, {
    method: 'PUT',
  });
}

export const ProfessionStatus = {
  1: { text: '启用', status: 1 },
  0: { text: '禁用', status: 0 },
};
