import request from '@/utils/request'

export default {

    //1.添加小节
    saveVideo(eduVideo) {
        return request({
            url: `/eduservice/video/saveVideo`,
            method: 'post',
            data: eduVideo
        })
    },

    //2.删除小节
    removeVideo(videoId) {
        return request({
            url: `/eduservice/video/removeVideo/${videoId}`,
            method: 'delete',
        })
    },

    //3.根据id查询小节
    findVideoById(videoId) {
        return request({
            url: `/eduservice/video/findVideoById/${videoId}`,
            method: 'get',
        })
    },

    //4.修改小节
    updateVideo(eduVideo) {
        return request({
            url: `/eduservice/video/updateVideo`,
            method: 'post',
            data: eduVideo
        })
    },

    //5.删除视频
    removeVod(vodId) {
        return request({
            url: `/eduvod/vod/removeVod/${vodId}`,
            method: 'delete',
        })
    },
}