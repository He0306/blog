import request from "@/util/request";

export default {

    getUserInfo(){
        return request({
            url: `/admin/user/getInfo`,
            method: 'GET'
        })
    },

    getPageList(parms) {
        return request({
            url: `/admin/user/page`,
            method: 'GET',
            params : parms
        })
    },

    save(user){
        return request({
            url: `/admin/user/save`,
            method: 'POST',
            data: user
        })
    },

    update(user){
        return request({
            url: `/admin/user/update`,
            method: 'POST',
            data: user
        })
    },

    resetPassword(id){
        return request({
            url: `/admin/user/resetPassword/${id}`,
            method: 'GET'
        })
    },

    deleteById(id){
        return request({
            url: `/admin/user/${id}`,
            method: 'DELETE'
        })
    },

    deleteBatch(ids){
        return request({
            url: `/admin/user/delete/batch`,
            method: 'POST',
            data: ids
        })
    },

    getUserId(id){
        return request({
            url: `/admin/user/${id}`,
            method: 'GET'
        })
    },

    updatePassword(passwordForm){
        return request({
            url: `/admin/user/updatePassword`,
            method: 'POST',
            data: passwordForm
        })
    },

    getUser(){
        return request({
            url: `/admin/user/getUserInfo`,
            method: 'GET'
        })
    },

    updateUserInfo(userInfo){
        return request({
            url: `/admin/user/updateUser`,
            method: 'POST',
            data: userInfo
        })
    }
}