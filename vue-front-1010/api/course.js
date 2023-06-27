import request from '@/utils/request'
export default {
    //1.课程分页显示
    pageCourseFront(page, limit, searchObj) {
        return request({
            url: `/eduservice/courseFront/pageCourseFront/${page}/${limit}`,
            method: 'post',
            data: searchObj
        })
    },

    //2.查询所有一级分类
    findAllSubject() {
        return request({
            url: `/eduservice/subject/findAllSubject`,
            method: 'get'
        })
    },

    //3.查询当前课程的基本信息
    getBaseCourseInfo(cid) {
        return request({
            url: `/eduservice/courseFront/getBaseCourseInfo/${cid}`,
            method: 'get'
        })
    },

    //4.判断用户是否登录
    isLogin() {
        return request({
            url: `/eduservice/courseFront/isLogin`,
            method: 'get'
        })
    }
}