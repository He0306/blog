import request from "@/util/request";

export default {

    getRoleList(params) {
        return request({
            url: `/admin/role/page`,
            method: 'GET',
            params: params
        })
    },

    saveOrUpdate(role) {
        return request({
            url: `/admin/role/saveOrUpdate`,
            method: 'POST',
            data: role
        })
    },

    deleteRoleById(id) {
        return request({
            url: `/admin/role/${id}`,
            method: 'DELETE'
        })
    },

    deleteBatch(ids) {
        return request({
            url: `/admin/delete/batch`,
            method: 'POST',
            data: ids
        })
    },

    findAll(){
        return request({
            url: `/admin/role/all`,
            method: 'GET'
        })
    }
}