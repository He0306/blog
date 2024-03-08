import request from "@/util/request";

export default{

    login(username,password){
        return request({
            url: `/login`,
            method: 'POST',
            data: {
                username,
                password
            }
        })
    },

    logout(){
        return request({
            url: `/admin/logout`,
            method: 'post'
        })
    },

    register(user){
        return request({
            url: `/register`,
            method: 'POST',
            data: user
        })
    },

}