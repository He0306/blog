import request from "@/util/request";

export default {

    getCategoryPage(params){
        return request({
            url: `/admin/category/page`,
            method: 'GET',
            params: params
        })
    },

    saveOrUpdate(category){
        return request({
            url: `/admin/category/saveOrUpdate`,
            method: 'POST',
            data: category
        })
    },

    deleteById(id) {
        return request({
            url: `/admin/category/${id}`,
            method: 'GET'
        })
    },

    deleteBatch(ids){
        return request({
            url: `/admin/category/delete/batch`,
            method: 'POST',
            data: ids
        })
    },

    queryAll(){
        return request({
            url: `/admin/category/list`,
            method: 'GET',
        })
    }
}