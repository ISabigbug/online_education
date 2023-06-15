import request from '@/utils/request'
export default {
    //1.讲师分页显示
    pageTeacherFront(page, limit) {
        return request({
            url: `/eduservice/teacherFront/pageTeacherFront/${page}/${limit}`,
            method: 'get'
        })
    },

    //2.根据讲师ID查询讲师详情
    getInfoByTeacherId(id) {
        return request({
            url: `/eduservice/teacherFront/getInfoByTeacherId/${id}`,
            method: 'get'
        })
    }
}