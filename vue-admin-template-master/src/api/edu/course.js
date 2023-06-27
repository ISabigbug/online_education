import request from '@/utils/request'

export default {

    //1.添加课程信息
    saveCourseInfo(courseVo) {
        return request({
            url: `/eduservice/course/saveCourseInfo`,
            method: 'post',
            data: courseVo
        })
    },

    //EduTeacherController 1.查询所有讲师数据
    findAllTeacher() {
        return request({
            url: `/eduservice/teacher/findAll`,
            method: 'get',
        })
    },

    //2.根据课程ID查询课程基本信息
    findCourseInfo(cid) {
        return request({
            url: `/eduservice/course/findCourseInfo/${cid}`,
            method: 'get',
        })
    },

    //3.修改课程信息
    updateConseInfo(courseVo) {
        return request({
            url: `/eduservice/course/updateConseInfo`,
            method: 'post',
            data: courseVo
        })
    },

    //4.通过课程ID查询课程发布信息
    findCoursePublishVo(cid) {
        return request({
            url: `/eduservice/course/findCoursePublishVo/${cid}`,
            method: 'get'
        })
    },

    //5.课程最终发布
    publishCourse(cid) {
        return request({
            url: `/eduservice/course/publishCourse/${cid}`,
            method: 'post'
        })
    },

    //6.条件查询分页
    pageCourseCondition(current, limit, courseQuery) {
        return request({
            url: `/eduservice/course/pageCourseCondition/${current}/${limit}`,
            method: 'post',
            //后端使用RequestBody获取数据
            data: courseQuery
        })
    },

    //7.根据课程ID删除课程信息
    removeCourseByCid(cid) {
        return request({
            url: `/eduservice/course/removeCourseByCid/${cid}`,
            method: 'delete'
        })
    },
}