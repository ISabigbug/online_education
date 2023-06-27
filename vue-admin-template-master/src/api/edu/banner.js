import request from '@/utils/request'
export default {
    //后台分页查询幻灯片列表
    listBanner(page, limit, bannerQuery) {
        return request({
            url: `/educms/adminbanner/pageBanner/${page}/${limit}`,
            method: 'post',
            data: bannerQuery
        })
    },

    // 删除幻灯片
    delBanner(bid) {
        return request({
            url: `/educms/adminbanner/removeBanner/${bid}`,
            method: 'delete'
        })
    },

    // 批量删除幻灯片
    delBannerList(bidList) {
        return request({
            url: `/educms/adminbanner/removeBannerList`,
            method: 'delete',
            data: bidList
        })
    },

    // 根据幻灯片ID查询幻灯片
    getBanner(bid) {
        return request({
            url: `/educms/adminbanner/findBanner/${bid}`,
            method: 'get'
        })
    },

    // 添加幻灯片
    addBanner(banner) {
        return request({
            url: '/educms/adminbanner/saveBanner',
            method: 'post',
            data: banner
        })
    },

    // 修改幻灯片信息
    updateBanner(banner) {
        return request({
            url: '/educms/adminbanner/updateBanner',
            method: 'post',
            data: banner
        })
    },
}