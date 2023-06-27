import request from '@/utils/request'
export default {
    //1.分页显示所有评论信息
    pageCommentFront(page, limit, courseId) {
        return request({
            url: `/eduservice/commentFront/pageCommentFront/${page}/${limit}/${courseId}`,
            method: 'get'
        })
    },

    //2.添加评论数据到数据库
    saveComment(eduComment) {
        return request({
            url: `/eduservice/commentFront/saveComment`,
            method: 'post',
            data: eduComment
        })
    }
}