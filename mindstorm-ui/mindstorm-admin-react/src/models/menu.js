import {listMenusByType, querySearchMenus } from "@/services/menu";

const MenuModel = {
  namespace: 'menu',
  state: {
    menus: [],
    fatherMenus: []
  },
  effects: {
    *listMenusByTypes({ payload }, { call, put }) {
      const response = yield call(listMenusByType, payload)
      yield put({
        type: 'setFatherMenus',
        payload: response.data
      })
    },
    *searchMenus({ payload }, { call, put }) {
      const response = yield call(querySearchMenus, payload)
      yield put({
        type: 'setMenus',
        payload: response.data
      })
    }
  },
  reducers: {
    setMenus(state, { payload }) {
      return { ...state, menus: payload.menus || [] }
    },
    // setFatherMenus(state, { payload }) {
    //   const menu = { value: '0', title: '主类目', children: [] }
    //   const menus = payload.treeMenus
    //   menu.children = menus
    //   return { ...state, fatherMenus: [menu] }
    // }
  }
}

export default MenuModel
