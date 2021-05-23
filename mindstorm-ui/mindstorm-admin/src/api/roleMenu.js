import request from '@/utils/request'

export function roleMenu(roleId) {
  return request({
    url: `/mindstorm-permission/permission/role-menu/roleMenuTreeSelect/${roleId}`,
    method: 'get'
  })
}

export function distributeMenu(roleId, menus) {
  return request({
    url: `/mindstorm-permission/permission/role-menu/distribute/${roleId}`,
    method: 'post',
    data: menus
  })
}
