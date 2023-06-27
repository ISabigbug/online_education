import request from '@/utils/request'

export default {
  // 添加文章列表
  addArticle(article) {
    return request({
      url: '/eduservice/article/saveArticle',
      method: 'post',
      data: article
    })
  },

  // 查询文章列表
  listArticle(page, limit, articleQuery) {
    return request({
      url: `/eduservice/article/pageArticle/${page}/${limit}`,
      method: 'post',
      data: articleQuery
    })
  },

  // 删除文章
  delArticle(aid) {
    return request({
      url: `/eduservice/article/removeArticle/${aid}`,
      method: 'delete'
    })
  },

  // 批量删除文章
  delArticleList(aidList) {
    return request({
      url: `/eduservice/article/removeArticleList`,
      method: 'delete',
      data: aidList
    })
  },

  // 根据文章ID查询文章详细
  getArticle(aid) {
    return request({
      url: `/eduservice/article/findArticleById/${aid}`,
      method: 'get'
    })
  },

  // 修改文章信息
  updateArticle(article) {
    return request({
      url: '/eduservice/article/updateArticle',
      method: 'post',
      data: article
    })
  },
}
