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

export function deleteShopCar(shopCarId) {
  return request({
    url: `/mindstorm-shop/shop/shop-car/delete/${shopCarId}`,
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

export function updateCount(shopCarId, price, count) {
  return request({
    url: `/mindstorm-shop/shop/shop-car/updateCount/${shopCarId}/${price}/${count}`,
    method: 'put'
  })
}

export function insertOrder(orderVO) {
  return request({
    url: `/mindstorm-shop/shop/order/create`,
    method: 'post',
    data: orderVO
  })
}

export function orderDetail() {
  return request({
    url: `/mindstorm-shop/shop/order/detail`,
    method: 'get'
  })
}

export function insertOrderItem(orderItemVO) {
  return request({
    url: `/mindstorm-shop/shop/order-item/create`,
    method: 'post',
    data: orderItemVO
  })
}

export function deleteOrder(orderId) {
  return request({
    url: `/mindstorm-shop/shop/order/delete/${orderId}`,
    method: 'delete',
  })
}

export function searchOrder(orderId) {
  return request({
    url: `/mindstorm-shop/shop/order/search`,
    method: 'get',
    params: { orderId }
  })
}
