import request from "@/util/request";

export default {

    getRoleMenuById(id) {
        return request({
            url: `/admin/roleMenu/${id}`,
            method: 'GET'
        })
    },

    saveRoleMenu(roleId, menuIds) {
        return request({
            url: `/admin/roleMenu/${roleId}`,
            method: 'POST',
            data: menuIds
        })
    }
}