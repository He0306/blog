import request from "@/util/request";

export default {

    getPageList(parms){
        return request({
            url: `/admin/icon/page`,
            method: 'GET',
            params : parms
        })
    },

    saveOrUpdate(data){
        return request({
            url: `/admin/icon/saveOrUpdate`,
            method: 'POST',
            data: data
        })
    },

    deleteIconById(id){
        return request({
            url: `/admin/icon/${id}`,
            method: 'DELETE'
        })
    },

    deleteIconByBatchId(ids){
        return request({
            url: `/admin/icon/delete/batch`,
            method: 'POST',
            data: ids
        })
    },

    getFindAll() {
        return request({
            url: `/admin/icon/getAll`,
            method: 'GET'
        })
    }
}