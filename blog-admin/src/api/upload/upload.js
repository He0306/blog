import request from "@/util/request";

export default {

    uploadAvatar(img){
        const formData = new FormData()
        formData.append('img', img)
        return request({
            url: `/upload`,
            headers: { 'Content-Type': 'multipart/form-data' },
            method: 'POST',
            data: formData
        })
    }
}