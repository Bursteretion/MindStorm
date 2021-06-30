import request from '@/utils/request'

export async function listRoles() {
  return request('/api/mindstorm-permission/permission/role/list', {
    method: 'GET'
  })
}

export async function listUnDisableRoles() {
  return request('/api/mindstorm-permission/permission/role/list/un-disable', {
    method: 'GET'
  })
}

export async function pageByRoles(pageIndex, pageSize) {
  return request(`/api/mindstorm-permission/permission/role/page/${ pageIndex }/${ pageSize }`, {
    method: 'GET'
  })
}

export async function createRole(role) {
  return request('/api/mindstorm-permission/permission/role/create', {
    method: 'POST',
    data: role
  })
}

export async function infoRole(roleId) {
  return request(`/api/mindstorm-permission/permission/role/info/${ roleId }`, {
    method: 'GET'
  })
}

export async function deleteRole(roleId) {
  return request(`/api/mindstorm-permission/permission/role/delete/${ roleId }`, {
    method: 'DELETE'
  })
}

export async function changeRoleStatus(roleId, status) {
  return request(`/api/mindstorm-permission/permission/role/change/${ roleId }/${ status }`, {
    method: 'GET'
  })
}

export async function searchRolesPage(searchRoleForm) {
  return request('/api/mindstorm-permission/permission/role/search', {
    method: 'POST',
    data: searchRoleForm
  })
}

export async function updateRole(role) {
  return request('/api/mindstorm-permission/permission/role/update', {
    method: 'POST',
    data: role
  })
}

export const RoleStatus = {
  1: { text: '启用', status: 1 },
  0: { text: '禁用', status: 0 }
}
