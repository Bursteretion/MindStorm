import request from '@/utils/request'

export function listUsers(pageIndex, pageSize) {
  return request({
    url: `/mindstorm-permission/permission/user/list/${pageIndex}/${pageSize}`,
    method: 'get'
  })
}

export function createAdmin(adminVO) {
  return request({
    url: '/mindstorm-permission/permission/user/create/admin',
    method: 'post',
    data: adminVO
  })
}

export function changeUserStatus(userId, status) {
  return request({
    url: '/mindstorm-permission/permission/user/change',
    method: 'get',
    params: {userId, status}
  })
}

export function getUserById(userId) {
  return request({
    url: `/mindstorm-permission/permission/user/infoById/${userId}`,
    method: 'get'
  })
}

export function updateUser(userVO) {
  return request({
    url: '/mindstorm-permission/permission/user/update',
    method: 'post',
    data: userVO
  })
}

export function deleteUser(userId) {
  return request({
    url: `/mindstorm-permission/permission/user/delete/${userId}`,
    method: 'delete'
  })
}

export function searchUsers(pageIndex, pageSize, searchUserVO) {
  return request({
    url: `/mindstorm-permission/permission/user/search/${pageIndex}/${pageSize}`,
    method: 'post',
    data: searchUserVO
  })
}

export const UserStatus = [
  {
    value: 1,
    name: '正常'
  },
  {
    value: 0,
    name: '禁止'
  }
]
