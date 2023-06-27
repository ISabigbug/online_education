import request from '@/utils/request'

export default {

    //1.统计某天的注册人数并加入到数据库中
    saveRegister(day) {
        return request({
            url: `/edusta/sta/saveRegister/${day}`,
            method: 'get',
        })
    },

    //2.生成图表
    showChart(searchObj) {
        return request({
            url: `/edusta/sta/showChart/${searchObj.type}/${searchObj.begin}/${searchObj.end}`,
            method: 'get',
        })
    },
}