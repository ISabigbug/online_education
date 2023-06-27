import request from '@/utils/request'
export default {
    //1.根据课程ID和用户ID生成订单
    saveOrderByCourseId(courseId) {
        return request({
            url: `/eduorder/order/saveOrderByCourseId/${courseId}`,
            method: 'get'
        })
    },

    //2.根据订单号查询订单信息
    getOrderInfo(orderNo) {
        return request({
            url: `/eduorder/order/getOrderInfo/${orderNo}`,
            method: 'get'
        })
    },

    //1.生成二维码
    createNative(orderNo) {
        return request({
            url: `/eduorder/payLog/createNative/${orderNo}`,
            method: 'get'
        })
    },

    //2.查看订单支付状态
    getPayStatus(orderNo) {
        return request({
            url: `/eduorder/payLog/getPayStatus/${orderNo}`,
            method: 'get'
        })
    },
}