import request from '@/utils/request'

export default {

    //2.查询所有课程分类
    findAllSubject() {
        return request({
            url: `/eduservice/subject/findAllSubject`,
            method: 'get',
        })
    },
}