import request from '@/utils/request';

export async function query() {
  return request('/users');
}

export async function queryCurrent() {
  return request('/mindstorm-auth/auth/info');
}

export async function queryNotices() {
  return request('/notices');
}

export async function listUserSelects() {
  return request(`/mindstorm-permission/permission/user/list/select`);
}

export async function listUsers(pageIndex, pageSize) {
  return request(`/mindstorm-permission/permission/user/list/${pageIndex}/${pageSize}`);
}

export async function createUser(userVO) {
  return request('/mindstorm-permission/permission/user/create/admin', {
    method: 'POST',
    data: userVO,
  });
}

export async function changeUserStatus(userId, status) {
  return request('/mindstorm-permission/permission/user/change', {
    method: 'GET',
    params: { userId, status },
  });
}

export async function getUserById(userId) {
  return request(`/mindstorm-permission/permission/user/infoById/${userId}`);
}

export async function updateUser(userVO) {
  return request('/mindstorm-permission/permission/user/update', {
    method: 'POST',
    data: userVO,
  });
}

export async function deleteUser(userId) {
  return request(`/mindstorm-permission/permission/user/delete/${userId}`, {
    method: 'DELETE',
  });
}

export async function searchUsers(searchUserVO) {
  return request(`/mindstorm-permission/permission/user/search`, {
    method: 'post',
    data: searchUserVO,
  });
}

export const UserStatus = {
  1: { text: '启用', status: 1 },
  0: { text: '禁用', status: 0 },
};
