<template>
  <div id="video">
    <!-- 阿里云视频播放器样式 -->
    <link
      rel="stylesheet"
      href="https://g.alicdn.com/de/prismplayer/2.8.1/skins/default/aliplayer-min.css"
    />
    <!-- 阿里云视频播放器脚本 -->
    <script
      charset="utf-8"
      type="text/javascript"
      src="https://g.alicdn.com/de/prismplayer/2.8.1/aliplayer-min.js"
    />

    <!-- 定义播放器dom -->
    <div id="J_prismPlayer" class="prism-player" />
  </div>
</template>
<script>
import vod from "@/api/vod";
export default {
  // layout: "video", //应用video布局

  asyncData({ params, error }) {
    return vod.getPlayAuth(params.vid).then((response) => {
      return {
        vid: params.vid,
        playAuth: response.data.data.playAuth,
      };
    });
  },

  mounted() {
    new Aliplayer(
      {
        id: "J_prismPlayer",
        vid: this.vid, // 视频id
        playauth: this.playAuth, // 播放凭证
        encryptType: "1", // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
        width: "100%",
        height: "500px",
        // 以下可选设置
        // cover: "https://online-education-test1010.oss-cn-fuzhou.aliyuncs.com/2023/04/03cb32b79fc4f64ed0bce913be766fb2c6qxlarge-dsc-99AD75021339FC0595E1DBBA15973133%282%29.jpg", // 封面
        qualitySort: "asc", // 清晰度排序
        mediaType: "video", // 返回音频还是视频
        autoplay: false, // 自动播放
        isLive: false, // 直播
        rePlay: false, // 循环播放
        preload: true,
        controlBarVisibility: "hover", // 控制条的显示方式：鼠标悬停
        useH5Prism: true, // 播放器类型：html5
      },
      function (player) {
        console.log("播放器创建成功");
      }
    );
  },
};
</script>
<style>
#video {
  margin-top: 20px;
  margin-bottom: 20px;
  margin-left: 320px;
  width: 900px;
}
</style>