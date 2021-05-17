import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/',
    redirect: '/admin/dashboard'
  },

  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/admin',
    component: Layout,
    redirect: '/admin/dashboard',
    children: [{
      path: '/admin/dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'home' }
    }]
  },

  {
    path: '/admin/permission',
    component: Layout,
    redirect: '/admin/permission/roles',
    name: 'Permissions',
    meta: { title: '权限管理', icon: 'lock' },
    children: [
      {
        path: '/admin/permission/role/list',
        name: 'RoleList',
        component: () => import('@/views/permission/role/index'),
        meta: { title: '角色管理' }
      },
      {
        path: '/admin/permission/menu/list',
        name: 'MenuList',
        component: () => import('@/views/permission/menu/index'),
        meta: { title: '菜单管理' }
      }
    ]
  },

  {
    path: '/admin/user',
    component: Layout,
    redirect: '/admin/user/students',
    name: 'Users',
    meta: { title: '用户管理', icon: 'users' },
    children: [
      {
        path: '/admin/user/students/list',
        name: 'StudentsList',
        component: () => import('@/views/user/students/index'),
        meta: { title: '学生列表' }
      },
      {
        path: '/admin/user/students/edit',
        name: 'StudentEdit',
        hidden: true,
        component: () => import('@/views/user/students/edit'),
        meta: { title: '学生创编' }
      },
      {
        path: '/admin/user/teachers/list',
        name: 'TeachersList',
        component: () => import('@/views/user/teachers/index'),
        meta: { title: '教师列表' }
      },
      {
        path: '/admin/user/teachers/edit',
        name: 'TeacherEdit',
        hidden: true,
        component: () => import('@/views/user/teachers/edit'),
        meta: { title: '教师创编' }
      },
      {
        path: '/admin/user/admins/list',
        name: 'AdminsList',
        component: () => import('@/views/user/admins/index'),
        meta: { title: '管理员列表' }
      },
      {
        path: '/admin/user/admins/edit',
        name: 'AdminEdit',
        hidden: true,
        component: () => import('@/views/user/admins/edit'),
        meta: { title: '管理员创编' }
      }
    ]
  },

  {
    path: '/admin/subject',
    component: Layout,
    redirect: '/admin/subject/list',
    name: 'Subjects',
    meta: { title: '学科管理', icon: 'education' },
    children: [
      {
        path: '/admin/subject/list',
        name: 'SubjectList',
        component: () => import('@/views/subject/index'),
        meta: { title: '学科列表' }
      },
      {
        path: '/admin/subject/edit',
        name: 'SubjectEdit',
        component: () => import('@/views/subject/edit'),
        meta: { title: '学科创编' }
      }
    ]
  },

  {
    path: '/admin/knowledge',
    component: Layout,
    redirect: '/admin/knowledge/list',
    name: 'Knowledge',
    meta: { title: '知识点管理', icon: 'star' },
    children: [
      {
        path: '/admin/knowledge/list',
        name: 'KnowledgeList',
        component: () => import('@/views/knowledge/index'),
        meta: { title: '知识点列表' }
      },
      {
        path: '/admin/knowledge/edit',
        name: 'KnowledgeEdit',
        component: () => import('@/views/knowledge/edit'),
        meta: { title: '知识点创编' }
      }
    ]
  },

  {
    path: '/admin/paper',
    component: Layout,
    redirect: '/admin/paper/list',
    name: 'Papers',
    meta: { title: '卷库管理', icon: 'exam' },
    children: [
      {
        path: '/admin/paper/list',
        name: 'PaperList',
        component: () => import('@/views/paper/index'),
        meta: { title: '试卷列表' }
      },
      {
        path: '/admin/paper/edit',
        name: 'PaperEdit',
        component: () => import('@/views/paper/edit'),
        meta: { title: '试卷创编' }
      }
    ]
  },

  {
    path: '/admin/question',
    component: Layout,
    redirect: '/admin/question/list',
    name: 'Questions',
    meta: { title: '题库管理', icon: 'component' },
    children: [
      {
        path: '/admin/question/list',
        name: 'QuestionList',
        component: () => import('@/views/question/index'),
        meta: { title: '题目列表' }
      },
      {
        path: '/admin/question/edit/singleChoice',
        name: 'SingleChoice',
        component: () => import('@/views/question/edit/singleChoice'),
        meta: { title: '单选题创编' }
      },
      {
        path: '/admin/question/edit/multipleChoice',
        name: 'MultipleChoice',
        component: () => import('@/views/question/edit/multipleChoice'),
        meta: { title: '多选题创编' }
      },
      {
        path: '/admin/question/edit/trueFalse',
        name: 'TrueFalse',
        component: () => import('@/views/question/edit/trueFalse'),
        meta: { title: '判断题创编' }
      },
      {
        path: '/admin/question/edit/gapFilling',
        name: 'GapFilling',
        component: () => import('@/views/question/edit/gapFilling'),
        meta: { title: '填空题创编' }
      },
      {
        path: '/admin/question/edit/shortAnswer',
        name: 'ShortAnswer',
        component: () => import('@/views/question/edit/shortAnswer'),
        meta: { title: '简答题创编' }
      }
    ]
  },

  {
    path: '/admin/task',
    component: Layout,
    redirect: '/admin/task/list',
    name: 'Tasks',
    meta: { title: '任务管理', icon: 'task' },
    children: [
      {
        path: '/admin/task/list',
        name: 'TaskList',
        component: () => import('@/views/task/index'),
        meta: { title: '任务列表' }
      },
      {
        path: '/admin/task/edit',
        name: 'TaskEdit',
        component: () => import('@/views/task/edit'),
        meta: { title: '任务创编' }
      }
    ]
  },

  {
    path: '/admin/video',
    component: Layout,
    redirect: '/admin/video/list',
    name: 'Videos',
    meta: { title: '视频管理', icon: 'example' },
    children: [
      {
        path: '/admin/video/list',
        name: 'VideoList',
        component: () => import('@/views/video/index'),
        meta: { title: '视频列表' }
      },
      {
        path: '/admin/video/edit',
        name: 'VideoEdit',
        component: () => import('@/views/video/edit'),
        meta: { title: '视频创编' }
      }
    ]
  },

  {
    path: '/admin/answer',
    component: Layout,
    redirect: '/admin/answer/list',
    name: 'Answer',
    meta: { title: '答卷管理', icon: 'answer' },
    children: [
      {
        path: '/admin/answer/judgeList',
        name: 'JudgeList',
        component: () => import('@/views/answer/judgeList'),
        meta: { title: '批改列表' }
      },
      {
        path: '/admin/answer/completeList',
        name: 'CompleteList',
        component: () => import('@/views/answer/completeList'),
        meta: { title: '批改完成列表' }
      }
    ]
  },

  {
    path: '/admin/message',
    component: Layout,
    redirect: '/admin/message/list',
    name: 'Messages',
    meta: { title: '消息中心', icon: 'message' },
    children: [
      {
        path: '/admin/message/list',
        name: 'MessageList',
        component: () => import('@/views/message/index'),
        meta: { title: '消息列表' }
      },
      {
        path: '/admin/message/send',
        name: 'MessageSend',
        component: () => import('@/views/message/send'),
        meta: { title: '消息发送' }
      }
    ]
  },

  {
    path: '/admin/log',
    component: Layout,
    redirect: '/admin/log/user/list',
    name: 'Logs',
    meta: { title: '日志中心', icon: 'log' },
    children: [
      {
        path: '/admin/log/user/list',
        name: 'UserLogList',
        component: () => import('@/views/log/user/index'),
        meta: { title: '用户日志' }
      },
      {
        path: '/admin/log/sys/list',
        name: 'SysLogList',
        component: () => import('@/views/log/system/index'),
        meta: { title: '系统日志' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

export const asyncRoutes = []

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
