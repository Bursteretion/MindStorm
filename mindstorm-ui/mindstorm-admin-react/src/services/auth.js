import request from '@/utils/request';

export async function login(params) {
  return request('/mindstorm-auth/auth/login', {
    method: 'POST',
    data: params,
  });
}

export async function getUserInfo() {
  return request('/mindstorm-auth/auth/info');
}

export async function logout() {
  return request('/mindstorm-auth/auth/logout', {
    method: 'DELETE',
  });
}

export async function getFakeCaptcha(mobile) {
  return request(`/login/captcha?mobile=${mobile}`);
}
