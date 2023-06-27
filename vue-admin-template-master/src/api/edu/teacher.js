import request from '@/utils/request'

export default {

    //4.条件查询分页
    pageTeacherCondition(current, limit, teacherQuery) {
        return request({
            url: `/eduservice/teacher/pageTeacherCondition/${current}/${limit}`,
            method: 'post',
            //后端使用RequestBody获取数据
            data: teacherQuery
        })
    },

    //2.逻辑删除讲师
    removeTeacher(id) {
        return request({
            url: `/eduservice/teacher/${id}`,
            method: 'delete',
        })
    },

    //5.添加讲师
    addTeacher(eduTeacher) {
        return request({
            url: `/eduservice/teacher/addTeacher`,
            method: 'post',
            data: eduTeacher
        })
    },

    //6.根据id查询讲师
    getTeacher(id) {
        return request({
            url: `/eduservice/teacher/getTeacher/${id}`,
            method: 'get',
        })
    },

    //7.根据id修改讲师信息
    updateTeacher(eduTeacher) {
        return request({
            url: `/eduservice/teacher/updateTeacher`,
            method: 'post',
            data: eduTeacher
        })
    },
}