import request from '@/utils/request';

export async function login(params) {
  return request('/api/mindstorm-auth/auth/login', {
    method: 'POST',
    data: params,
  });
}

export async function getUserInfo() {
  return request('/api/mindstorm-auth/auth/info');
}

export async function logout() {
  return request('/api/mindstorm-auth/auth/logout', {
    method: 'DELETE'
  })
}

export async function getFakeCaptcha(mobile) {
  return request(`/api/login/captcha?mobile=${mobile}`);
}
