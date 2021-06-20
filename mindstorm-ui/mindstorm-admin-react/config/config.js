import { defineConfig } from 'umi';
import defaultSettings from './defaultSettings';
import proxy from './proxy';

const { REACT_APP_ENV } = process.env;
export default defineConfig({
  hash: true,
  antd: {},
  dva: {
    hmr: true,
  },
  history: {
    type: 'browser',
  },
  locale: {
    default: 'zh-CN',
    antd: true,
    baseNavigator: true,
  },
  dynamicImport: {
    loading: '@/components/PageLoading/index',
  },
  targets: {
    ie: 11,
  },
  routes: [
    {
      path: '/',
      component: '../layouts/BlankLayout',
      routes: [
        {
          path: '/user',
          component: '../layouts/UserLayout',
          routes: [
            {
              path: '/user',
              redirect: '/user/login'
            },
            {
              path: '/user/login',
              name: 'login',
              component: './User/login',
            },
            {
              component: '404',
            }
          ],
        },
        {
          path: '/',
          wrappers: ['@/wrappers/auth'],
          component: '../layouts/BasicLayout',
          routes: [
            {
              path: '/',
              redirect: '/dashboard/analysis',
            },
            {
              name: 'dashboard',
              icon: 'dashboard',
              path: '/dashboard/analysis',
              component: './Dashboard/analysis'
            },
            {
              path: '/permission',
              icon: 'control',
              name: 'permission',
              routes: [
                {
                  path: '/permission',
                  redirect: '/permission/menu'
                },
                {
                  name: 'menu',
                  path: '/permission/menu',
                  component: './Permission/menu'
                },
                {
                  name: 'role',
                  path: '/permission/role',
                  component: './Permission/role'
                },
                {
                  name: 'user',
                  path: '/permission/user',
                  component: './Permission/user'
                },
                {
                  component: '404'
                }
              ]
            },
            {
              component: '404'
            }
          ]
        }
      ],
    }
  ],
  theme: {
    'primary-color': defaultSettings.primaryColor,
  },
  title: false,
  ignoreMomentLocale: true,
  proxy: proxy[REACT_APP_ENV || 'dev'],
  manifest: {
    basePath: '/',
  },
  esbuild: {},
});
