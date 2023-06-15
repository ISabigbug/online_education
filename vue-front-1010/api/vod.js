import request from '@/utils/request'
export default {
    //1.讲师分页显示
    getPlayAuth(vodId) {
        return request({
            url: `/eduvod/vod/getPlayAuth/${vodId}`,
            method: 'get'
        })
    },
}