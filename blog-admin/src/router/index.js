import Vue from 'vue'
import store from '../store/index'
import VueRouter from 'vue-router'


Vue.use(VueRouter)

const routes = [
    {
        path: "/",
        redirect: '/login'
    },
    {
        path: '/login',
        name: '登录',
        component: () => import('../views/login/login'),
    },
    {
        path: '/404',
        name: '404',
        component: () => import('../views/404/notFind')
    },
    {
        path: '/register',
        name: '注册',
        component: () => import('../views/register/register')
    },
    {
        path: '/ForgotPassword',
        name: '忘记密码',
        component: () => import('../views/ForgotPassword/ForgotPassword')
    },


]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export const resetRouter = () => {
    router.matcher = new VueRouter({
        mode: 'history',
        base: process.env.BASE_URL,
        routes
    })
}

export const setRoutes = () => {
    //拼装动态路由
    const storeMenus = sessionStorage.getItem("menus")
    if (storeMenus) {
        const currentRouteNames = router.getRoutes().map(v => v.name)
        if (!currentRouteNames.includes('Layout')) {
            const managerRote = {
                path: '/Layout',
                name: 'Layout',
                redirect: '/home',
                component: () => import("../components/Layout"),
                children: [
                    {
                        path: '/setting',
                        name: '个人中心',
                        component: () => import('../views/setting/setting')
                    },
                ]
            }
            const menus = JSON.parse(storeMenus)
            menus.forEach(item => {
                if (item.path) {   //当前仅当path不为空的时候才去设置
                    let itemMenu = {
                        path: item.path.replace("/Layout", ""),
                        name: item.name,
                        component: () => import('../views/' + item.pagePath + '.vue'),
                    }
                    managerRote.children.push(itemMenu)
                } else if (item.children.length) {
                    item.children.forEach(item => {
                        if (item.path) {   //当前仅当path不为空的时候才去设置
                            let itemMenu = {
                                path: item.path.replace("/Layout", ""),
                                name: item.name,
                                component: () => import('../views/' + item.pagePath + '.vue'),
                            }
                            managerRote.children.push(itemMenu)
                        }
                    })
                }
            })
            //动态添加到现在的路由对象去
            router.addRoute(managerRote)
        }
    }
}
setRoutes()

//路由守卫
router.beforeEach((to, from, next) => {
    sessionStorage.setItem("currentPathName", to.name)  //设置路由名称
    store.commit("setPath")  //触发store的数据更新
    //未找到路由的情况
    if (!to.matched.length) {
        const storeMenus = sessionStorage.getItem("menus")
        if (storeMenus == null) {
            next("/404")
        }
    }
    next() //放行路由
})

export default router
