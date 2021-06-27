import { reloadAuthorized } from './Authorized';

const TokenKey = 'mindstorm-token'

function setItem(key, value, expire) {
  const obj = {
    data: value,
    time: Date.now(),
    expire
  }
  localStorage.setItem(key, JSON.stringify(obj));
}

function getItem(key) {
  let val = localStorage.getItem(key);
  if (!val) {
    return val;
  }
  val = JSON.parse(val);
  if (Date.now() - val.time > val.expire) {
    localStorage.removeItem(key);
    return null;
  }
  return val.data;
}

function removeItem(key) {
  localStorage.removeItem(key)
}

export function getToken(str) {
  return typeof str === 'undefined' ? getItem(TokenKey) : str;
}

export function setToken(token) {
  setItem(TokenKey, token, 1000 * 60 * 60 * 24)

  reloadAuthorized()
}

export function removeToken() {
  removeItem(TokenKey)
}

