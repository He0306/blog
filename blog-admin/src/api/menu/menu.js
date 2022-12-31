import request from "@/util/request";

export default {

    getFindAll(name){
        return request({
            url: `/admin/menu/${name}`,
            method: 'GET',
        })
    },

    getFindAllIds(){
        return request({
            url: `/admin/menu/ids`,
            method: 'GET'
        })
    },

    getFindFunction(){
        return request({
            url: `/admin/menu/findAllParentMenu`,
            method: 'GET'
        })
    },

    save(menu){
        return request({
            url: `/admin/menu/save`,
            method: 'POST',
            data: menu
        })
    },

    deleteByMenuId(id){
        return request({
            url: `/admin/menu/${id}`,
            method: 'DELETE'
        })
    },

    deleteBatch(ids){
        return request({
            url: `/admin/menu/delete/batch`,
            method: 'POST',
            data: ids
        })
    }
}