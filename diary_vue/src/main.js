import Vue from 'vue'
import App from './App.vue'
import store from "@/stroe/index"; // 引入 store 文件

// 导入部分element ui 组件
import {Message,Upload} from "element-ui";
import 'element-ui/lib/theme-chalk/index.css';

// 导入bootstrap css/js
import 'bootstrap/dist/js/bootstrap.min'
import 'bootstrap/dist/css/bootstrap.min.css'

import router from "@/router/router.js";
import globalFun from "@/assets/js/globalScreenListener";
import GlobalUserUtils from "@/utils/globalUserUtils"; "@/Utils/globalUserUtils"

import contentmenu from 'v-contextmenu'
import 'v-contextmenu/dist/index.css'
Vue.use(contentmenu)

import request from "@/utils/request";
Vue.prototype.request = request


Vue.component(Message)
Vue.use(Upload)
Vue.prototype.$message = Message;


Vue.mixin(globalFun)
Vue.mixin(GlobalUserUtils)

Vue.config.productionTip = false


new Vue({
  store,
  render: h => h(App),
  router
}).$mount('#app')
