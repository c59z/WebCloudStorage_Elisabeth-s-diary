import Vue from 'vue'
import Vuex from 'vuex';

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        token:'',
        userInfo:JSON.parse(sessionStorage.getItem("userInfo"))
    },
    mutations: {
        SET_TOKEN:(state,token)=>{
            state.token=token//将传入的token赋值 给state的token
            //同时可以存入浏览器的localStorage里面
            localStorage.setItem("token",token)
        },
        SET_USERINFO:(state,userInfo)=>{
            state.userInfo=userInfo//将传入的tuserInfo赋值 给state的userInfo
            //同时可以存入会话的sessionStorage里面 sessionStorage中只能存字符串 不能存入对象所以我们存入序列化 jons串
            // sessionStorage.setItem("userInfo",JSON.stringify(userInfo))
            localStorage.setItem("userInfo",JSON.stringify(userInfo))
        },
        REMOVE_INFO:(state)=>{
            state.token = '';
            state.userInfo = {};
            localStorage.setItem("token",'')
            // sessionStorage.setItem("userInfo",JSON.stringify(''))
            localStorage.setItem("userInfo",JSON.stringify(''))
        }
    },
    getters: {
        //相当于get
        //配置一个getUser可以直接获取已经反序列化对象的一个userInfo
        getUserInfo: state=>{
            return state.userInfo;
        },
        getToken: state=>{
            return state.token;
        }
    },
    actions: {

    },
    modules: {

    }
})
