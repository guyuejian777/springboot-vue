import { Message } from 'element-ui'
import './http'
import axios from 'axios'

export const initMenu = (router, store) => {
  if (store.state.routes.length > 0) {
    return
  }
  axios.get('/config/sysmenu').then(resp => {
    if (resp && resp.status == 200) {
      var fmtRoutes = formatRoutes(resp.data)
      router.addRoutes(fmtRoutes) // 动态添加到路由中
      store.commit('initMenu', fmtRoutes)
    }
  })
}
export const formatRoutes = (routes) => {
  let fmRoutes = []
  routes.forEach(router => {
    let {
      path,
      component,
      name,
      menuMeta,
      iconCls,
      children
    } = router
    if (children && children instanceof Array) {
      children = formatRoutes(children)
    }
    let fmRouter = {
      path: path,
      component (resolve) {
        if (component.startsWith('Home')) {
          require(['../components/' + component + '.vue'], resolve)
        } else if (component.startsWith('Emp')) {
          require(['../components/emp/' + component + '.vue'], resolve)
        } else if (component.startsWith('Per')) {
          require(['../components/personnel/' + component + '.vue'], resolve)
        } else if (component.startsWith('Sal')) {
          require(['../components/salary/' + component + '.vue'], resolve)
        } else if (component.startsWith('Sta')) {
          require(['../components/statistics/' + component + '.vue'], resolve)
        } else if (component.startsWith('Sys')) {
          require(['../components/system/' + component + '.vue'], resolve)
        }
      },
      name: name,
      iconCls: iconCls,
      menuMeta: menuMeta,
      children: children
    }
    fmRoutes.push(fmRouter)
  })
  return fmRoutes
}
