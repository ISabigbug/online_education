import request from '@/utils/request'

export default {

    //1.根据课程ID获取章节和小节列表
    findChapterVideo(cid) {
        return request({
            url: `/eduservice/chapter/findChapterVideo/${cid}`,
            method: 'get',
        })
    },

    //2.添加章节信息
    saveChapter(eduChapter) {
        return request({
            url: `/eduservice/chapter/saveChapter`,
            method: 'post',
            data: eduChapter
        })
    },

    //3.根据章节ID获取章节信息
    findChapterById(chapterId) {
        return request({
            url: `/eduservice/chapter/findChapterById/${chapterId}`,
            method: 'get',
        })
    },

    //4.删除章节
    deleteChapterById(chapterId) {
        return request({
            url: `/eduservice/chapter/deleteChapterById/${chapterId}`,
            method: 'delete',
        })
    },

    //5.修改章节信息
    updateChapter(eduChapter) {
        return request({
            url: `/eduservice/chapter/updateChapter`,
            method: 'post',
            data: eduChapter
        })
    },
}