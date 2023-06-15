import request from '@/utils/request'
export default {
    findAllBanner() {
        return request({
            url: `/educms/frontbanner/findAllBanner`,
            method: 'get'
        })
    }
}