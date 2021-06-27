import request from '@/utils/request';

export async function listMenus() {
  return request('/api/mindstorm-permission/permission/menu/list')
}

export async function getRouters(roleIds) {
  return request('/api/mindstorm-permission/permission/menu/routers', {
    method: 'POST',
    data: roleIds
  })
}

export async function listMenusByType(types) {
  return request(`/api/mindstorm-permission/permission/menu/list/type`, {
    method: 'POST',
    data: types
  })
}

export async function querySearchMenus(searchMenuVO) {
  return request(`/api/mindstorm-permission/permission/menu/search`, {
    method: 'POST',
    data: { ...searchMenuVO }
  })
}

export async function insertMenu(menuVO) {
  return request('/api/mindstorm-permission/permission/menu/create', {
    method: 'POST',
    data: menuVO
  })
}

export async function updateMenu(menuVO) {
  return request('/api/mindstorm-permission/permission/menu/update', {
    method: 'POST',
    data: menuVO
  })
}

export async function getMenuById(menuId) {
  return request(`/api/mindstorm-permission/permission/menu/info/${menuId}`)
}

export async function deleteMenuBeId(menuId) {
  return request(`/api/mindstorm-permission/permission/menu/delete/${menuId}`, {
    method: 'DELETE'
  })
}

export async function changeMenuStatus(menuId) {
  return request(`/api/mindstorm-permission/permission/menu/change/${menuId}`, {
    method: 'PUT'
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
  0: {
    color: 'blue',
    text: '目录',
    value: 0
  },
  1: {
    color: 'red',
    text: '菜单',
    value: 1
  },
  2: {
    color: 'volcano',
    text: '按钮',
    value: 2
  }
}
