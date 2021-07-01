const convertToTreeMenus = (menus = []) => {
  if (menus.length <= 0) return
  menus.forEach(v => {
    const menu = v
    menu.title = v.name
    menu.value = v.id
    menu.key = v.id
    if (menu.children.length <= 0) {
      menu.children = undefined
    } else {
      convertToTreeMenus(menu.children)
    }
  })
}

export default convertToTreeMenus
