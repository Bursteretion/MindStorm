import request from '@/utils/request'

export function listMenus() {
  return request({
    url: '/mindstorm-permission/permission/menu/list',
    method: 'get'
  })
}

export function listMenusByType(types) {
  return request({
    url: `/mindstorm-permission/permission/menu/list/type`,
    method: 'post',
    data: types
  })
}

export function insertMenu(menuVO) {
  return request({
    url: '/mindstorm-permission/permission/menu/create',
    method: 'post',
    data: menuVO
  })
}

export function updateMenu(menuVO) {
  return request({
    url: '/mindstorm-permission/permission/menu/update',
    method: 'post',
    data: menuVO
  })
}

export function getMenuById(menuId) {
  return request({
    url: `/mindstorm-permission/permission/menu/info/${menuId}`,
    method: 'get'
  })
}

export function deleteBeId(menuId) {
  return request({
    url: `/mindstorm-permission/permission/menu/delete/${menuId}`,
    method: 'delete'
  })
}

export function searchMenus(searchMenuVO) {
  return request({
    url: '/mindstorm-permission/permission/menu/search',
    method: 'post',
    data: searchMenuVO
  })
}

export const MenuStatus = [
  {
    name: '正常',
    value: 1
  },
  {
    name: '禁用',
    value: 0
  }
]

export const MenuType = {
  catalog: 0,
  menu: 1,
  button: 2
}
