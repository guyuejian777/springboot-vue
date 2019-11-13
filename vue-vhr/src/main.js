import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import './utils/http'
import './utils/filter'
import qs from 'qs'
import { initMenu } from './utils/common'
import 'font-awesome/css/font-awesome.css'
import Fragment from 'vue-fragment'

Vue.use(ElementUI)
Vue.use(Fragment.Plugin)

Vue.config.productionTip = false
Vue.prototype.$qs = qs

router.beforeEach((to, from, next) => {
  if (to.name == 'Login') {
    next()
    return
  }
  var name = store.state.user.name
  if (name == '未登录') {
    if (to.meta.requireAuth || to.name == null) {
      next({ path: '/', query: { redirect: to.path } })
    } else {
      next()
    }
  } else {
    initMenu(router, store)
    next()
  }
}
)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
