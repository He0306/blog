import axios from 'axios'
import {Message, MessageBox} from "element-ui";
import {resetRouter} from "@/router";

const request = axios.create({
    baseURL: 'http://localhost:8888',
    timeout: 18000
})

//请求拦截器
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8'
    let token = localStorage.getItem('token') ? JSON.parse(localStorage.getItem('token')) : null
    let user = sessionStorage.getItem('user') ? JSON.parse(sessionStorage.getItem('user')) : null
    if (token != null && user != null) {
        config.headers['token'] = token
    }

    return config
}, error => {
    return Promise.reject(error)
});

//响应拦截器
request.interceptors.response.use(response => {

        const res = response.data
        console.log(res.msg)
        if (res.code === 401) {
            MessageBox.confirm('登录状态已过期，请重新登录', '系统提示', {
                confirmButtonText: '重新登录',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                location.href = '/'
                resetRouter()
                sessionStorage.clear()
                localStorage.clear()
            })
        } else if (res.code === 407) {
            MessageBox.confirm('登录状态已过期，请重新登录', '系统提示', {
                confirmButtonText: '重新登录',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                location.href = '/'
                resetRouter()
                sessionStorage.clear()
                localStorage.clear()
            })
        }
            return response.data

    },
    error => {

        let {message} = error
        if (message === 'Network Error') {
            message = '后端接口连接异常'
        } else if (message.includes('timeout')) {
            message = '系统接口请求超时'
        }
        Message({
            showClose: true,
            message: message,
            type: 'error',
            duration: 5 * 1000
        })
        return Promise.reject(error)
    })

export default request