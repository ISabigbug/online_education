<template>
  <div class="error-page">
    <div class="error-code">开<span>发</span>中</div>
    <div class="error-desc">啊哦~ 你所访问的页面不存在</div>
    <div class="error-handle">
      <router-link to="/">
        <el-button type="primary" size="large">返回首页</el-button>
      </router-link>
    </div>
  </div>
</template>

<style rel="stylesheet/scss" lang="scss" scoped>
.error-page {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 100%;
  height: 100%;
  background: #f3f3f3;
  box-sizing: border-box;
}
.error-code {
  margin-top: 80px;
  line-height: 1;
  font-size: 160px;
  font-weight: bolder;
  color: #2d8cf0;
  animation: myfirst 1s infinite;
}
.error-code span {
  color: #00a854;
}
.error-desc {
  margin-top: 20px;
  font-size: 30px;
  color: #777;
}
.error-handle {
  margin-top: 30px;
  padding-bottom: 200px;
}
.error-btn {
  margin-left: 100px;
}
@keyframes myfirst {
  0% {
    letter-spacing: 8px;
    transform: translate(0px, 0px);
  }
  50% {
    letter-spacing: 10px;
    transform: translate(0px, -5px);
  }
  100% {
    letter-spacing: 8px;
    transform: translate(0px, 0px);
  }
}
</style>