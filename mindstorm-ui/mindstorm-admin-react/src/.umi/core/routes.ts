// @ts-nocheck
import React from 'react';
import { ApplyPluginsType, dynamic } from 'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/node_modules/@umijs/runtime';
import * as umiExports from './umiExports';
import { plugin } from './plugin';
import LoadingComponent from '@/components/PageLoading/index';

export function getRoutes() {
  const routes = [
  {
    "path": "/",
    "component": dynamic({ loader: () => import(/* webpackChunkName: 'layouts__BlankLayout' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/layouts/BlankLayout'), loading: LoadingComponent}),
    "routes": [
      {
        "path": "/user",
        "component": dynamic({ loader: () => import(/* webpackChunkName: 'layouts__UserLayout' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/layouts/UserLayout'), loading: LoadingComponent}),
        "routes": [
          {
            "path": "/user",
            "redirect": "/user/login",
            "exact": true
          },
          {
            "path": "/user/login",
            "name": "login",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__User__login' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/User/login'), loading: LoadingComponent}),
            "exact": true
          },
          {
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__404' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/404'), loading: LoadingComponent}),
            "exact": true
          }
        ]
      },
      {
        "path": "/",
        "wrappers": [dynamic({ loader: () => import(/* webpackChunkName: 'wrappers' */'@/wrappers/auth'), loading: LoadingComponent})],
        "component": dynamic({ loader: () => import(/* webpackChunkName: 'layouts__BasicLayout' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/layouts/BasicLayout'), loading: LoadingComponent}),
        "routes": [
          {
            "path": "/",
            "redirect": "/dashboard/analysis",
            "exact": true
          },
          {
            "name": "dashboard",
            "icon": "dashboard",
            "path": "/dashboard/analysis",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__Dashboard__analysis' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/Dashboard/analysis'), loading: LoadingComponent}),
            "exact": true
          },
          {
            "path": "/permission",
            "icon": "control",
            "name": "permission",
            "routes": [
              {
                "path": "/permission",
                "redirect": "/permission/menu",
                "exact": true
              },
              {
                "name": "menu",
                "path": "/permission/menu",
                "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__Permission__menu' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/Permission/menu'), loading: LoadingComponent}),
                "exact": true
              },
              {
                "name": "role",
                "path": "/permission/role",
                "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__Permission__role' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/Permission/role'), loading: LoadingComponent}),
                "exact": true
              },
              {
                "name": "user",
                "path": "/permission/user",
                "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__Permission__user' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/Permission/user'), loading: LoadingComponent}),
                "exact": true
              },
              {
                "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__404' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/404'), loading: LoadingComponent}),
                "exact": true
              }
            ]
          },
          {
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__404' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/404'), loading: LoadingComponent}),
            "exact": true
          }
        ]
      }
    ]
  }
];

  // allow user to extend routes
  plugin.applyPlugins({
    key: 'patchRoutes',
    type: ApplyPluginsType.event,
    args: { routes },
  });

  return routes;
}
