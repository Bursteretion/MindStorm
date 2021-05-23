import request from '@/utils/request'

export function listRoles() {
  return request({
    url: '/mindstorm-permission/permission/role/list',
    method: 'get'
  })
}

export function listUnDisableRoles() {
  return request({
    url: '/mindstorm-permission/permission/role/list/un-disable',
    method: 'get'
  })
}

export function pageByRoles(pageIndex, pageSize) {
  return request({
    url: `/mindstorm-permission/permission/role/page/${pageIndex}/${pageSize}`,
    method: 'get'
  })
}

export function createRole(role) {
  return request({
    url: '/mindstorm-permission/permission/role/create',
    method: 'post',
    data: role
  })
}

export function deleteRole(roleId) {
  return request({
    url: `/mindstorm-permission/permission/role/delete/${roleId}`,
    method: 'delete'
  })
}

export function changeRoleStatus(roleId, status) {
  return request({
    url: `/mindstorm-permission/permission/role/change/${roleId}/${status}`,
    method: 'get'
  })
}

export function searchRolesPage(searchRoleForm) {
  return request({
    url: '/mindstorm-permission/permission/role/search',
    method: 'post',
    data: searchRoleForm
  })
}

export function updateRole(role) {
  return request({
    url: '/mindstorm-permission/permission/role/update',
    method: 'post',
    data: role
  })
}

export const RoleStatus = [
  {
    name: '正常',
    value: 1
  },
  {
    name: '禁用',
    value: 0
  }
]
