import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/gloable.css'
import request from "@/util/request"
import SlideVerify from 'vue-monoplasty-slide-verify'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import ECharts from 'vue-echarts'
import "echarts"
import tagCloud from "../src/util/tag-cloud"

Vue.use(tagCloud)
Vue.component("v-chart", ECharts)
Vue.use(mavonEditor)
Vue.use(SlideVerify)
Vue.use(ElementUI,{size: "small"})
Vue.config.productionTip = false
Vue.prototype.request=request

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
