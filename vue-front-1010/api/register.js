import request from '@/utils/request'
export default {
    //2.用户注册
    registerUser(RegisterVo) {
        return request({
            url: `/educenter/menmber/register`,
            method: 'post',
            data: RegisterVo
        })
    },

    //根据手机号码发送短信
    send(phone) {
        return request({
            url: `/edusms/sms/send/${phone}`,
            method: 'get'
        })
    },
}