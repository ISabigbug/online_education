import request from '@/utils/request'

export default {

  // 查询文章列表
  articleList(page, limit, articleQuery) {
    return request({
      url: `/eduservice/articleFront/pageArticleFront/${page}/${limit}`,
      method: 'post',
      data: articleQuery
    })
  },

  //获取文章详情
  getArticle(aid) {
    return request({
      url: `/eduservice/articleFront/getArticleDetail/${aid}`,
      // headers: {
      //   isToken: false
      // },
      method: 'get'
    })
  },

  updateViewCount(aid) {
    return request({
      url: `/eduservice/articleFront/updateViewCount/${aid}`,
      // headers: {
      //   isToken: false
      // },
      method: 'put'
    })
  }
}
