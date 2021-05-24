import {constantRoutes} from '@/router'
import Layout from '@/layout'
import {getRouters} from '@/api/menu'

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  // 生成路由
  generateRoutes({commit}, roleIds) {
    return new Promise(resolve => {
      // 向后端请求路由数据
      getRouters(roleIds).then((res) => {
        if (res && res.code === 20000) {
          const routes = res.data.routes
          const accessedRoutes = filterAsyncRoutes(routes)
          // 需要在动态路由生成之后加入404，否则会出现刷新404
          accessedRoutes.push({path: '*', redirect: '/404', hidden: true})
          commit('SET_ROUTES', accessedRoutes)
          resolve(accessedRoutes)
        }
      })
    })
  }
}

// 遍历后台传来的路由字符串，转换为组件对象
export function filterAsyncRoutes(asyncRouterMap) {
  return asyncRouterMap.sort((a, b) => a.sort - b.sort).map(route => {
    const currentRouter = {
      // 路由地址 动态拼接生成如 /dashboard/workplace
      path: route.path,
      // 路由名称，建议唯一
      name: route.alias || route.id || '',
      // meta: 页面标题, 菜单图标
      meta: {title: route.name, icon: route.icon || undefined}
    }

    if (route.component === 'Layout') {
      currentRouter.component = Layout
    } else {
      currentRouter.component = loadView(route.component)
    }

    currentRouter.path = currentRouter.path.replace('//', '/')
    route.redirect && (currentRouter.redirect = route.redirect)

    if (route.children && route.children.length > 0) {
      currentRouter.children = filterAsyncRoutes(route.children)
    }

    return currentRouter
  })
}

export const loadView = (view) => { // 路由懒加载
  return (resolve) => require([`@/views/${view}`], resolve)
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
