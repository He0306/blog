import request from "@/util/request";

export default {

    home(){
        return request({
            url: `/admin/index`,
            method: 'GET'
        })
    }
}