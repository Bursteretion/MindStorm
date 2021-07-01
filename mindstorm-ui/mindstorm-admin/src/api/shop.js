import request from '@/utils/request'

export function listCar() {
  return request({
    url: '/mindstorm-shop/shop/user-shop-car/list',
    method: 'get'
  })
}

export function deleteCar(userShopCarId) {
  return request({
    url: `/mindstorm-shop/shop/user-shop-car/delete/${userShopCarId}`,
    method: 'delete'
  })
}

export function batchDeleteCar(userShopCarIds) {
  return request({
    url: '/mindstorm-shop/shop/user-shop-car/batchDelete',
    method: 'post',
    data: userShopCarIds
  })
}

export function updateCount(shopCarId, count) {
  return request({
    url: `/mindstorm-shop/shop/shop-car/${shopCarId}/${count}`,
    method: 'put'
  })
}
