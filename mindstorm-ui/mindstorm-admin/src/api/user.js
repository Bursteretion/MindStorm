import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/mindstorm-auth/auth/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/mindstorm-auth/auth/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/mindstorm-auth/auth/logout',
    method: 'delete'
  })
}
