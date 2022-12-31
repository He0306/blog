import request from "@/util/request"

export default {

    pageList(param){
        return request({
            url: `/admin/link/page`,
            method: 'GET',
            params: param
        })
    },

    saveOrUpdate(link){
        return request({
            url: `/admin/link/saveOrUpdate`,
            method: 'POST',
            data: link
        })
    },

    deleteById(id){
        return request({
            url: `/admin/link/${id}`,
            method: 'DELETE'
        })
    },

    deleteByIdBatch(ids){
        return request({
            url: `/admin/link/delete/batch`,
            method: 'POST',
            data: ids
        })
    }
}