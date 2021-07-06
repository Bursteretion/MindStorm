export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        path: '/user',
        routes: [
          {
            name: 'login',
            path: '/user/login',
            component: './User/Login',
          },
        ],
      },
    ],
  },
  {
    path: '/',
    redirect: '/dashboard/analysis',
  },
  {
    name: 'dashboard',
    path: '/dashboard',
    routes: [
      {
        path: '/dashboard',
        redirect: '/dashboard/analysis',
      },
      {
        name: 'analysis',
        path: '/dashboard/analysis',
        component: './Dashboard/analysis',
        access: 'hasRouter',
      },
    ],
  },
  {
    path: '/permission',
    name: 'permission',
    routes: [
      {
        path: '/permission',
        redirect: '/permission/menu',
      },
      {
        name: 'menu',
        path: '/permission/menu',
        component: './Permission/menu',
        access: 'hasRouter',
      },
      {
        name: 'role',
        path: '/permission/role',
        component: './Permission/role',
        access: 'hasRouter',
      },
      {
        name: 'user',
        path: '/permission/user',
        component: './Permission/user',
        access: 'hasRouter',
      },
    ],
  },
  {
    path: '/education',
    name: 'education',
    routes: [
      {
        path: '/education',
        redirect: '/education/academy',
      },
      {
        name: 'academy',
        path: '/education/academy',
        component: './Education/academy',
        access: 'hasRouter',
      },
    ],
  },
  {
    component: '404',
  },
];
