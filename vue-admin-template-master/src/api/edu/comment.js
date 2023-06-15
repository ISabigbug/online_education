import request from '@/utils/request'
export default {
    //后台分页查询幻灯片列表
    listComment(page, limit, commentQuery) {
        return request({
            url: `/eduservice/comment/pageComment/${page}/${limit}`,
            method: 'post',
            data: commentQuery
        })
    },

    // 批量删除评论
    delCommentList(cidList) {
        return request({
            url: `/eduservice/comment/removeCommentList`,
            method: 'delete',
            data: cidList
        })
    },

    // 删除评论
    delComment(cid) {
        return request({
            url: `/eduservice/comment/removeComment/${cid}`,
            method: 'delete'
        })
    },
}