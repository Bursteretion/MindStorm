import request from '@/utils/request';

export async function roleMenu(roleId) {
  return request(`/mindstorm-permission/permission/role-menu/roleMenuTreeSelect/${roleId}`, {
    method: 'GET',
  });
}

export async function distributeMenu(roleId, menus) {
  return request(`/mindstorm-permission/permission/role-menu/distribute/${roleId}`, {
    method: 'POST',
    data: menus,
  });
}
