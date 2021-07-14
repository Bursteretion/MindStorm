import React from 'react';
import RightContent from '@/components/RightContent';
import Footer from '@/components/Footer';
import { PageLoading } from '@ant-design/pro-layout';
import defaultSettings from '../config/defaultSettings';
import { history } from 'umi';
import { queryCurrent } from '@/services/permission/user';
import { getToken } from '@/utils/authority';
import { getRouters } from '@/services/permission/menu';
import renderMenuItem from '@/utils/renderMenuItem';

const loginPath = '/system/login';

const generateAuthorityMenus = (routes) => {
  const menus = [];
  routes.forEach((route) => {
    menus.push(route.alias);
    if (route.children && route.children.length > 0) {
      menus.push(...generateAuthorityMenus(route.children));
    }
  });
  return menus;
};

export const initialStateConfig = {
  loading: <PageLoading />,
};

export async function getInitialState() {
  const fetchUserInfo = async () => {
    try {
      // 获取当前用户信息
      const res = await queryCurrent();
      const { user } = res.data;
      return user;
    } catch (error) {
      history.push(loginPath);
    }
    return undefined;
  };

  const isLogin = !!getToken();
  if (isLogin) {
    if (history.location.pathname !== loginPath) {
      // 拉取当前用户信息
      const currentUser = await fetchUserInfo();
      // 拉取当前用户所拥有的菜单路由及权限
      const { roles, permissions } = currentUser;
      const routeRes = await getRouters(roles);
      const { routes } = routeRes.data;
      const authorityRoutes = generateAuthorityMenus(routes);
      return {
        currentUser,
        routes,
        authorityRoutes,
        permissions,
        settings: defaultSettings,
      };
    }
    window.location.href = '/';
  } else {
    history.push(loginPath);
  }
  return {
    fetchUserInfo,
    settings: defaultSettings,
  };
}

/**
 * 请求前拦截器
 */
const authHeaderInterceptor = (url, options) => {
  const authHeader = { Authorization: getToken() };
  return {
    url: `${url}`,
    options: { ...options, interceptors: true, headers: authHeader },
  };
};

export const request = {
  timeout: 1000,
  prefix: '/api',
  errorConfig: {},
  middlewares: [],
  requestInterceptors: [authHeaderInterceptor],
  responseInterceptors: [],
};

export const layout = ({ initialState }) => {
  return {
    rightContentRender: () => <RightContent />,
    disableContentMargin: false,
    waterMarkProps: {
      content: 'MindStorm',
      fontSize: 25,
    },
    menu: {
      params: initialState,
      request: async (params) => {
        return params.routes;
      },
    },
    menuDataRender: () => renderMenuItem(initialState.routes),
    footerRender: () => <Footer />,
    onPageChange: () => {
      const { location } = history;
      // 如果没有登录，重定向到 login
      if (!initialState?.currentUser && location.pathname !== loginPath) {
        history.push(loginPath);
      }
    },
    menuHeaderRender: undefined,
    // unAccessible: <div>unAccessible</div>,
    ...initialState?.settings,
  };
};
