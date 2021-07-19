// @ts-nocheck
import React from 'react';
import { ApplyPluginsType, dynamic } from 'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/node_modules/umi/node_modules/@umijs/runtime';
import * as umiExports from './umiExports';
import { plugin } from './plugin';
import LoadingComponent from '@ant-design/pro-layout/es/PageLoading';

export function getRoutes() {
  const routes = [
  {
    "path": "/",
    "component": dynamic({ loader: () => import(/* webpackChunkName: '.umi__plugin-layout__Layout' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/.umi/plugin-layout/Layout.tsx'), loading: LoadingComponent}),
    "routes": [
      {
        "path": "/~demos/:uuid",
        "layout": false,
        "wrappers": [dynamic({ loader: () => import(/* webpackChunkName: 'wrappers' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/node_modules/@umijs/preset-dumi/lib/theme/layout'), loading: LoadingComponent})],
        "component": (props) => React.createElement(
        dynamic({
          loader: async () => {
            const { default: getDemoRenderArgs } = await import(/* webpackChunkName: 'dumi_demos' */ 'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/node_modules/@umijs/preset-dumi/lib/plugins/features/demo/getDemoRenderArgs');
            const { default: Previewer } = await import(/* webpackChunkName: 'dumi_demos' */ 'dumi-theme-default/es/builtins/Previewer.js');
            const { default: demos } = await import(/* webpackChunkName: 'dumi_demos' */ '@@/dumi/demos');
            const { usePrefersColor } = await import(/* webpackChunkName: 'dumi_demos' */ 'dumi/theme');

            return props => {
              
      const renderArgs = getDemoRenderArgs(props, demos);

      // for listen prefers-color-schema media change in demo single route
      usePrefersColor();

      switch (renderArgs.length) {
        case 1:
          // render demo directly
          return renderArgs[0];

        case 2:
          // render demo with previewer
          return React.createElement(
            Previewer,
            renderArgs[0],
            renderArgs[1],
          );

        default:
          return `Demo ${props.match.params.uuid} not found :(`;
      }
    
            }
          }
        }), props)
      },
      {
        "path": "/_demos/:uuid",
        "redirect": "/~demos/:uuid"
      },
      {
        "__dumiRoot": true,
        "layout": false,
        "path": "/~docs",
        "wrappers": [dynamic({ loader: () => import(/* webpackChunkName: 'wrappers' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/node_modules/@umijs/preset-dumi/lib/theme/layout'), loading: LoadingComponent}), dynamic({ loader: () => import(/* webpackChunkName: 'wrappers' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/node_modules/dumi-theme-default/es/layout.js'), loading: LoadingComponent})],
        "routes": [
          {
            "path": "/~docs",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'README.md' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/README.md'), loading: LoadingComponent}),
            "exact": true,
            "meta": {
              "locale": "en-US",
              "order": null,
              "filePath": "README.md",
              "updatedTime": 1624794940000,
              "componentName": "mindstorm-admin-react",
              "slugs": [
                {
                  "depth": 1,
                  "value": "Ant Design Pro",
                  "heading": "ant-design-pro"
                },
                {
                  "depth": 2,
                  "value": "Environment Prepare",
                  "heading": "environment-prepare"
                },
                {
                  "depth": 2,
                  "value": "Provided Scripts",
                  "heading": "provided-scripts"
                },
                {
                  "depth": 3,
                  "value": "Start project",
                  "heading": "start-project"
                },
                {
                  "depth": 3,
                  "value": "Build project",
                  "heading": "build-project"
                },
                {
                  "depth": 3,
                  "value": "Check code style",
                  "heading": "check-code-style"
                },
                {
                  "depth": 3,
                  "value": "Test code",
                  "heading": "test-code"
                },
                {
                  "depth": 2,
                  "value": "More",
                  "heading": "more"
                }
              ],
              "title": "Ant Design Pro"
            },
            "title": "Ant Design Pro"
          }
        ],
        "title": "ant-design-pro",
        "component": (props) => props.children
      },
      {
        "path": "/user",
        "layout": false,
        "routes": [
          {
            "path": "/user",
            "routes": [
              {
                "name": "login",
                "path": "/user/login",
                "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__User__Login' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/User/Login'), loading: LoadingComponent}),
                "exact": true
              }
            ]
          }
        ]
      },
      {
        "path": "/",
        "redirect": "/dashboard/analysis",
        "exact": true
      },
      {
        "name": "dashboard",
        "path": "/dashboard",
        "routes": [
          {
            "path": "/dashboard",
            "redirect": "/dashboard/analysis",
            "exact": true
          },
          {
            "name": "analysis",
            "path": "/dashboard/analysis",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__Dashboard__analysis' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/Dashboard/analysis'), loading: LoadingComponent}),
            "access": "hasRouter",
            "exact": true
          }
        ]
      },
      {
        "path": "/permission",
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
            "access": "hasRouter",
            "exact": true
          },
          {
            "name": "role",
            "path": "/permission/role",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__Permission__role' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/Permission/role'), loading: LoadingComponent}),
            "access": "hasRouter",
            "exact": true
          },
          {
            "name": "user",
            "path": "/permission/user",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__Permission__user' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/Permission/user'), loading: LoadingComponent}),
            "access": "hasRouter",
            "exact": true
          }
        ]
      },
      {
        "path": "/education",
        "name": "education",
        "routes": [
          {
            "path": "/education",
            "redirect": "/education/academy",
            "exact": true
          },
          {
            "name": "academy",
            "path": "/education/academy",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__Education__academy' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/Education/academy'), loading: LoadingComponent}),
            "access": "hasRouter",
            "exact": true
          },
          {
            "name": "profession",
            "path": "/education/profession",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__Education__profession' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/Education/profession'), loading: LoadingComponent}),
            "access": "hasRouter",
            "exact": true
          }
        ]
      },
      {
        "path": "/personnel",
        "name": "personnel",
        "routes": [
          {
            "path": "/personnel",
            "redirect": "/personnel/student",
            "exact": true
          },
          {
            "name": "student",
            "path": "/personnel/student",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__Personnel__student' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/Personnel/student'), loading: LoadingComponent}),
            "access": "hasRouter",
            "exact": true
          }
        ]
      },
      {
        "path": "/course",
        "name": "course",
        "routes": [
          {
            "path": "/course",
            "redirect": "/course/my",
            "exact": true
          },
          {
            "name": "courseList",
            "path": "/course/list",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__Course__list' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/Course/list'), loading: LoadingComponent}),
            "access": "hasRouter",
            "exact": true
          },
          {
            "name": "myCourse",
            "path": "/course/my",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__Course__my' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/Course/my'), loading: LoadingComponent}),
            "access": "hasRouter",
            "exact": true
          },
          {
            "name": "courseManager",
            "path": "/course/manager",
            "component": dynamic({ loader: () => import(/* webpackChunkName: 'p__Course__manager' */'D:/project-dev/online-examination/mindstorm/mindstorm-ui/mindstorm-admin-react/src/pages/Course/manager'), loading: LoadingComponent}),
            "layout": false,
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
];

  // allow user to extend routes
  plugin.applyPlugins({
    key: 'patchRoutes',
    type: ApplyPluginsType.event,
    args: { routes },
  });

  return routes;
}
