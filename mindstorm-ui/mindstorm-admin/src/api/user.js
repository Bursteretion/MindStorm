import request from '@/utils/request'

export function listUsers(pageIndex, pageSize, userType) {
  return request({
    url: `/mindstorm-permission/permission/user/list/${pageIndex}/${pageSize}/${userType}`,
    method: 'get'
  })
}

export function createStudent(studentVO) {
  return request({
    url: '/mindstorm-permission/permission/user/create/student',
    method: 'post',
    data: studentVO
  })
}

export function createTeacher(teacherVO) {
  return request({
    url: '/mindstorm-permission/permission/user/create/teacher',
    method: 'post',
    data: teacherVO
  })
}

export function createAdmin(adminVO) {
  return request({
    url: '/mindstorm-permission/permission/user/create/admin',
    method: 'post',
    data: adminVO
  })
}

export function changeUserStatus(userId, status) {
  return request({
    url: '/mindstorm-permission/permission/user/change',
    method: 'get',
    params: {userId, status}
  })
}

export function getUserById(userId) {
  return request({
    url: `/mindstorm-permission/permission/user/infoById/${userId}`,
    method: 'get'
  })
}

export function updateUser(userVO) {
  return request({
    url: '/mindstorm-permission/permission/user/update',
    method: 'post',
    data: userVO
  })
}

export function deleteUser(userId) {
  return request({
    url: `/mindstorm-permission/permission/user/delete/${userId}`,
    method: 'delete'
  })
}

export function searchUsers(pageIndex, pageSize, searchUserVO) {
  return request({
    url: `/mindstorm-permission/permission/user/search/${pageIndex}/${pageSize}`,
    method: 'post',
    data: searchUserVO
  })
}

export const UserStatus = [
  {
    value: 1,
    name: '正常'
  },
  {
    value: 0,
    name: '禁止'
  }
]

export const UserStudentLevels = [
  {
    value: 1,
    label: '一年级'
  },
  {
    value: 2,
    label: '二年级'
  },
  {
    value: 3,
    label: '三年级'
  },
  {
    value: 4,
    label: '四年级'
  },
  {
    value: 5,
    label: '五年级'
  },
  {
    value: 6,
    label: '六年级'
  },
  {
    value: 7,
    label: '初一'
  },
  {
    value: 8,
    label: '初二'
  },
  {
    value: 9,
    label: '初三'
  },
  {
    value: 10,
    label: '高一'
  },
  {
    value: 11,
    label: '高二'
  },
  {
    value: 12,
    label: '高三'
  },
  {
    value: 13,
    label: '大一'
  },
  {
    value: 14,
    label: '大二'
  },
  {
    value: 15,
    label: '大三'
  }, {
    value: 16,
    label: '大四'
  }
]
