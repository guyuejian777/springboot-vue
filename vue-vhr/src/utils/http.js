import Vue from 'vue'
import axios from 'axios'
import { Message } from 'element-ui'
axios.interceptors.request.use(config => {
  return config
}, err => {
  Message.error({ message: '请求超时!' })
  // return Promise.resolve(err);
})
axios.interceptors.response.use(data => {
  if (data.status && data.status == 200 && data.data.status == 'error') {
    Message.error({ message: data.data.msg })
    return
  }
  if (data.data.status == 'success') {
    Message.success({ message: data.data.welcome })
  }
  return data
}, err => {
  if (err.response.status == 504 || err.response.status == 404) {
    Message.error({ message: '服务器被吃了⊙﹏⊙∥' })
  } else if (err.response.status == 403) {
    Message.error({ message: '权限不足,请联系管理员!' })
  } else if (err.response.status == 401) {
    Message.error({ message: err.response.data.msg })
  } else {
    if (err.response.data.msg) {
      Message.error({ message: err.response.data.msg })
    } else {
      Message.error({ message: '未知错误!' })
    }
  }
  // return Promise.resolve(err);
})
let base = ''

Vue.prototype.$http = axios
