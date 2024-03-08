import request from "@/util/request";

export default {

    getArticlePage(params) {
        return request({
            url: `/admin/article/page`,
            method: 'GET',
            params: params
        })
    },

    getByArticleId(id) {
        return request({
            url: `/admin/article/${id}`,
            method: 'GET'
        })
    },

    addArticle(article) {
        return request({
            url: `/admin/article`,
            method: 'POST',
            data: article
        })
    },

    updateArticle(article) {
        return request({
            url: `/admin/article`,
            method: 'PUT',
            data: article
        })
    },

    deleteArticleById(id){
        return request({
            url: `/admin/article/{id}`,
            method: 'DELETE'
        })
    },

    deleteArticleByBatchId(ids){
        return request({
            url: `/admin/article/delete/batch`,
            method: 'POST',
            data: ids
        })
    },

    updateStatus(article){
        return request({
            url: `/admin/article/updateStatus`,
            method: 'POST',
            data: article
        })
    },

    saveDrafts(article){
        return request({
            url: `/admin/article/drafts`,
            method: 'POST',
            data: article
        })
    }

}