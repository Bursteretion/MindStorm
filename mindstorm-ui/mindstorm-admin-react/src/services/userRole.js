import request from '@/utils/request';

export async function userRole(userId) {
  return request(`/mindstorm-permission/permission/user-role/userRole/${userId}`);
}

export function distributeRole(userId, roles) {
  return request(`/mindstorm-permission/permission/user-role/distribute/${userId}`, {
    method: 'POST',
    data: roles,
  });
}
