import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import router from './router'
import VueWechatTitle from 'vue-wechat-title'
import Vue2Editor from "vue2-editor"
import axios from 'axios'
import iView from 'iview'
import 'iview/dist/styles/iview.css'
import VueCookies from 'vue-cookies'

Vue.config.productionTip = false
Vue.use(VueRouter)
Vue.use(iView)
Vue.use(VueWechatTitle)
Vue.use(Vue2Editor)
Vue.use(VueCookies)
//axios.defaults.headers.common['token'] = sessionStorage.getItem("token")

axios.interceptors.request.use((config) => {
  if (['/admin'].indexOf(config.url) === -1) {
    const token = sessionStorage.getItem('token')
    if (token) {
      //config.headers.Authorization = token
      config.headers.token=token
    }
  }
  return config
})


new Vue({
  render: h => h(App),
  router: router,
  beforeCreate(){
    Vue.prototype.$bus = this //安装全局事件总线(bus加$是为了符合Vue的代码设计规范)
    //Vue.prototype.$axios = axios
 }
}).$mount('#app')
