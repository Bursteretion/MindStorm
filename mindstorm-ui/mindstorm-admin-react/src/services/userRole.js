import request from '@/utils/request'

export async function userRole(userId) {
  return request(`/api/mindstorm-permission/permission/user-role/userRole/${userId}`)
}

export function distributeRole(userId, roles) {
  return request(`/api/mindstorm-permission/permission/user-role/distribute/${userId}`, {
    method: 'POST',
    data: roles
  })
}
