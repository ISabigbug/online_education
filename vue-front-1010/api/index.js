import request from '@/utils/request'
export default {
    index() {
        return request({
            url: `/eduservice/index/index`,
            method: 'get'
        })
    }
}