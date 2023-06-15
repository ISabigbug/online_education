import request from '@/utils/request'

export default {
  // 查询分类列表
  uploadImg(img) {
    const formData = new FormData()
    formData.append('img', img)
    return request({
      url: "http://localhost:9002/eduoss/file/articleImg",
      headers: { 'Content-Type': 'multipart/form-data' },
      method: 'post',
      data: formData
    })
  }
}
