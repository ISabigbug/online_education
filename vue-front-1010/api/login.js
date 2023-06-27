import request from '@/utils/request'
export default {
    //1.用户登录
    loginUser(ucenterMember) {
        return request({
            url: `/educenter/menmber/login`,
            method: 'post',
            data: ucenterMember
        })
    },

    //3.根据token获取用户信息
    findUserInfo() {
        return request({
            url: `/educenter/menmber/findUserInfo`,
            method: 'get'
        })
    },
}