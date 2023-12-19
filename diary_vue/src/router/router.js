// router/index.js
import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login/Login'
import Register from "@/views/Login/Register";
import Index from "@/views/Index";
import Main from "@/views/Home/Main";
import userCenter from "@/views/User/UserCenter";
import userInfoTable from "@/views/User/UserInfoTable";
import changeUserInfo from "@/views/User/ChangeUserInfo";
import RecycleBin from "@/views/Home/RecycleBin";

Vue.use(VueRouter)

const routes = [
    // 进入该web后第一时间跳转页面
    {
        path: '/',
        redirect: '/main' // 重定向到 /login
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/register',
        name: 'Register',
        component: Register
    },
    {
        path: '/index',
        name: 'Index',
        component: Index,
        meta: { requiresAuth: true }, // 需要验证
        children: [
            {
                path: '/main',
                name: 'Main',
                component: Main,
            },
            {
                path: '/userCenter',
                redirect: '/userCenter/userInfoTable' // 重定向到 /login
            },
            {
                path: '/userCenter',
                name: 'userCenter',
                component: userCenter,
                children: [
                    {
                        path: '/userCenter/changeUserInfo',
                        name: 'changeUserInfo',
                        component: changeUserInfo,
                    },
                    {
                        path: '/userCenter/userInfoTable',
                        name: 'userInfoTable',
                        component: userInfoTable,
                    },
                ],
            },
            {
                path: '/recyclebin',
                name: 'RecycleBin',
                component: RecycleBin,
            },
        ],
    },


]

const router = new VueRouter({
    mode: 'history',
    routes
})

router.beforeEach((to,from,next)=>{
    if(to.path==='/login'|| to.path==='/register') return next();
    //获取名字为名字为token的token令牌
    const token = window.localStorage.getItem("token")
    //不存在则直接跳转到登录界面
    if(!token) return next('/login')
    //否则放行
    next()
})

export default router
