import request from "@/util/request";

export default {

    getPageList(params){
        return request({
            url: `/admin/tag/page`,
            method: 'GET',
            params : params
        })
    },

    saveOrUpdate(tag){
        return request({
            url: `/admin/tag/saveOrUpdate`,
            method: 'POST',
            data: tag
        })
    },

    deleteById(id){
        return request({
            url: `/admin/tag/${id}`,
            method: 'DELETE'
        })
    },

    deleteBatch(ids){
        return request({
            url: `/admin/tag/delete/batch`,
            method: 'POST',
            data: ids
        })
    },

    queryAll(){
        return request({
            url: `/admin/tag/list`,
            method: 'GET'
        })
    }
}