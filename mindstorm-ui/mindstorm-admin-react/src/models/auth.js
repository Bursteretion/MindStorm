import { stringify } from 'querystring';
import { history } from 'umi';
import { login, logout } from '@/services/auth';
import { removeToken, setToken } from '@/utils/authority';
import { getPageQuery } from '@/utils/utils';
import { message } from 'antd';

const AuthModel = {
  namespace: 'auth',
  state: {
    status: undefined,
  },
  effects: {
    *login({ payload }, { call, put }) {
      const response = yield call(login, payload);
      yield put({
        type: 'changeLoginStatus',
        payload: response.data,
      }); // Login successfully

      if (response.code === 20000) {
        const urlParams = new URL(window.location.href);
        const params = getPageQuery();
        message.success('🎉 🎉 🎉  登录成功！');
        let { redirect } = params;

        if (redirect) {
          const redirectUrlParams = new URL(redirect);

          if (redirectUrlParams.origin === urlParams.origin) {
            redirect = redirect.substr(urlParams.origin.length);

            if (redirect.match(/^\/.*#/)) {
              redirect = redirect.substr(redirect.indexOf('#') + 1);
            }
          } else {
            window.location.href = '/';
            return;
          }
        }

        history.replace(redirect || '/');
      }
    },

    *logout({ payload }, { call, put }) {
      const response = yield call(logout);

      yield put({
        type: 'changeLogoutStatus',
        payload: response.data,
      }); // Logout successfully

      if (response.code === 20000) {
        message.success('🎉 🎉 🎉  退出成功！');
        if (window.location.pathname !== '/user/login') {
          history.replace({
            pathname: '/user/login',
            search: stringify({
              redirect: window.location.href,
            })
          })
        }
      }
    }
  },
  reducers: {
    changeLoginStatus(state, { payload }) {
      setToken(payload.access_token);
      return { ...state, status: payload.status, type: payload.type };
    },
    changeLogoutStatus(state, { payload }) {
      removeToken()
      return { ...state, status: payload.status, type: payload.type };
    }
  }
}

export default AuthModel
