import Vue from 'vue'
import Vuex from 'vuex'
import router, {resetRouter} from "@/router";

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        currentPathName: ''
    },
    getters: {},
    mutations: {
        setPath(state) {
            this.state.currentPathName = sessionStorage.getItem("currentPathName")
        },
        logout() {
            localStorage.removeItem('token')
            sessionStorage.removeItem('user')
            sessionStorage.removeItem('menus')
            router.push("/")
            resetRouter()
        }
    },
    actions: {},
    modules: {}
})
