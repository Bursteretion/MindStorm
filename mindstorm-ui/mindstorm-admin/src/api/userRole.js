import request from '@/utils/request'

export function userRole(userId) {
  return request({
    url: `/mindstorm-permission/permission/user-role/userRole/${userId}`,
    method: 'get'
  })
}

export function distributeRole(userId, roles) {
  return request({
    url: `/mindstorm-permission/permission/user-role/distribute/${userId}`,
    method: 'post',
    data: roles
  })
}
