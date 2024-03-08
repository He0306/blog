import request from "@/util/request";

export default {

    getFindAll(params){
        return request({
            url: '/admin/optLog/page',
            methods: 'GET',
            params : params
        })
    },

    deleteById(id) {
        return request({
            url: `/admin/optLog/${id}`,
            method: 'DELETE'
        })
    },

    deleteBatch(ids){
        return request({
            url: `/admin/optLog/delete/batch`,
            method: 'POST',
            data: ids
        })
    },

    exportLog(){
        return request({
            url: `/export`,
            method: 'GET'
        })
    }

}
