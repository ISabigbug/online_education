<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>
    <el-steps
      :active="2"
      process-status="wait"
      align-center
      style="margin-bottom: 40px"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="提交审核" />
    </el-steps>

    <el-button type="text" @click="openChapterDialog">添加章节</el-button>
    <!-- 章节 -->
    <ul class="chanpterList">
      <li v-for="chapter in chapterVideoList" :key="chapter.id">
        <p>
          {{ chapter.title }}

          <span class="acts">
            <el-button type="text" @click="openVideo(chapter.id)"
              >添加课时</el-button
            >
            <el-button
              style=""
              type="text"
              @click="openUpdateChapter(chapter.id)"
              >编辑</el-button
            >
            <el-button type="text" @click="openRemoveChapter(chapter.id)"
              >删除</el-button
            >
          </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li v-for="video in chapter.children" :key="video.id">
            <p>
              {{ video.title }}
              <span class="acts">
                <el-button type="text" @click="openUpdateVideo(video.id)"
                  >编辑</el-button
                >
                <el-button type="text" @click="removeVideo(video.id)"
                  >删除</el-button
                >
              </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>

    <div>
      <el-button @click="previous">上一步</el-button>
      <el-button :disabled="saveBtnDisabled" type="primary" @click="next"
        >下一步</el-button
      >
    </div>
    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" title="添加章节">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title" />
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number
            v-model="chapter.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" title="添加课时">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title" />
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number
            v-model="video.sort"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="1">免费</el-radio>
            <el-radio :label="0">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <el-upload
            :on-success="handleVodUploadSuccess"
            :on-remove="handleVodRemove"
            :before-remove="beforeVodRemove"
            :on-exceed="handleUploadExceed"
            :file-list="fileList"
            :action="BASE_API + '/eduvod/vod/uploadVod'"
            :limit="1"
            class="upload-demo"
          >
            <el-button size="small" type="primary">上传视频</el-button>
            <el-tooltip placement="right-end">
              <div slot="content">
                最大支持1G，<br />
                支持3GP、ASF、AVI、DAT、DV、FLV、F4V、<br />
                GIF、M2T、M4V、MJ2、MJPEG、MKV、MOV、MP4、<br />
                MPE、MPG、MPEG、MTS、OGG、QT、RM、RMVB、<br />
                SWF、TS、VOB、WMV、WEBM 等视频格式上传
              </div>
              <i class="el-icon-question" />
            </el-tooltip>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button
          :disabled="saveVideoBtnDisabled"
          type="primary"
          @click="saveOrUpdateVideo"
          >确 定</el-button
        >
      </div>
    </el-dialog>
  </div>
</template>

<script>
import chapter from "@/api/edu/chapter";
import video from "@/api/edu/video";

export default {
  data() {
    return {
      saveBtnDisabled: false, //保存按钮是否禁用
      cid: "", //课程ID
      chapterVideoList: [],
      dialogChapterFormVisible: false, //是否显示章节表单
      dialogVideoFormVisible: false, //小节表单
      chapter: {
        //封装章节数据
        title: "",
        sort: 0,
      },
      video: {
        title: "",
        sort: 0,
        isFree: 0,
        videoSourceId: "", //视频资源id
        videoOriginalName: "", //原始文件名称
      },
      fileList: [], //上传文件列表
      BASE_API: process.env.BASE_API, // 接口API地址
    };
  },

  created() {
    if (this.$route.params && this.$route.params.id) {
      this.cid = this.$route.params.id;
      this.findChapterVideo();
    }
  },

  methods: {
    //点击删除按键
    beforeVodRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}?`);
    },

    //点击确认删除视频按键
    handleVodRemove() {
      video.removeVod(this.video.videoSourceId).then((response) => {
        //删除成功信息提示
        this.$message({
          type: "success",
          message: "删除视频成功 !",
        });
      });
      this.fileList = [];
      //防止数据库添加ID和文件名
      this.video.videoSourceId = "";
      this.video.videoOriginalName = "";
    },
    //成功回调
    handleVodUploadSuccess(response, file, fileList) {
      this.video.videoSourceId = response.data.vodId;
      this.video.videoOriginalName = file.name;
    },
    //视图上传多于一个视频
    handleUploadExceed(files, fileList) {
      this.$message.warning("想要重新上传视频，请先删除已上传的视频");
    },
    //==========================================小节操作==========================================//
    //开启小节弹框
    openVideo(chapterId) {
      //开启弹窗
      this.dialogVideoFormVisible = true;
      //设置章节ID
      this.video.chapterId = chapterId;
      //清楚第二次添加的数据回显
      this.video.title = "";
      this.video.videoSourceId = "";
      this.video.sort = 0;
      this.video.isFree = 0;
      this.video.id = "";
      this.fileList = [];
    },

    //添加小节
    saveVideo() {
      //设置课程ID
      this.video.courseId = this.cid;
      video.saveVideo(this.video).then((Response) => {
        //关闭弹窗
        this.dialogVideoFormVisible = false;

        //添加成功信息提示
        this.$message({
          type: "success",
          message: "添加小节信息成功 !",
        });

        //刷新页面
        this.findChapterVideo();
      });
    },

    //修改小节时的数据回显方法
    openUpdateVideo(videoId) {
      //获取回显数据
      video.findVideoById(videoId).then((response) => {
        this.video = response.data.eduVideo;
      });
      //开启弹窗
      this.dialogVideoFormVisible = true;
    },

    //修改小节
    updateVideo() {
      video.updateVideo(this.video).then((Response) => {
        //关闭弹窗
        this.dialogVideoFormVisible = false;

        //添加成功信息提示
        this.$message({
          type: "success",
          message: "修改小节信息成功 !",
        });

        //刷新页面
        this.findChapterVideo();
      });
    },

    //添加或修改小节
    saveOrUpdateVideo() {
      if (!this.video.id) {
        this.saveVideo();
      } else {
        this.updateVideo();
      }
    },

    //根据小节ID删除小节
    removeVideo(videoId) {
      this.$confirm("此操作将永久删除该小节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        video.removeVideo(videoId).then((response) => {
          this.$message({
            type: "success",
            message: "删除成功 !",
          });
          //刷新页面
          this.findChapterVideo();
        });
      });
    },

    //==========================================章节操作==========================================//
    //删除章节
    openRemoveChapter(chapterId) {
      this.$confirm("此操作将永久删除该章节, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        chapter.deleteChapterById(chapterId).then((response) => {
          this.$message({
            type: "success",
            message: "删除成功 !",
          });
          //刷新页面
          this.findChapterVideo();
        });
      });
    },

    //弹出添加章节页面
    openChapterDialog() {
      this.dialogChapterFormVisible = true;
      //清除添加章节后第二次添加的数据回显
      this.chapter.title = "";
      this.chapter.sort = 0;
    },

    //添加章节信息
    saveChapter() {
      this.chapter.courseId = this.cid;
      chapter.saveChapter(this.chapter).then((Response) => {
        //关闭弹窗
        this.dialogChapterFormVisible = false;

        //添加成功信息提示
        this.$message({
          type: "success",
          message: "添加章节信息成功 !",
        });

        //刷新页面
        this.findChapterVideo();
      });
    },

    //回显数据并弹出弹框
    openUpdateChapter(chapterId) {
      //查询回显数据
      chapter.findChapterById(chapterId).then((Response) => {
        this.chapter = Response.data.eduChapter;
      });
      //弹出弹框
      this.dialogChapterFormVisible = true;
    },

    //修改章节信息
    updateChapter() {
      this.chapter.courseId = this.cid;
      chapter.updateChapter(this.chapter).then((Response) => {
        //关闭弹窗
        this.dialogChapterFormVisible = false;

        //修改成功信息提示
        this.$message({
          type: "success",
          message: "修改章节信息成功 !",
        });

        //刷新页面
        this.findChapterVideo();
      });
    },

    //添加或修改章节信息
    saveOrUpdate() {
      if (!this.chapter.id) {
        this.saveChapter();
      } else {
        this.updateChapter();
      }
    },

    //根据课程ID查询章节和小节
    findChapterVideo() {
      chapter.findChapterVideo(this.cid).then((Response) => {
        this.chapterVideoList = Response.data.chapterVideoList;
      });
    },

    //上一步
    previous() {
      this.$router.push({ path: "/course/info/" + this.cid });
    },

    //下一步
    next() {
      this.$router.push({ path: "/course/publish/" + this.cid });
    },
  },
};
</script>
<style scoped>
.chanpterList {
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}
.chanpterList li {
  position: relative;
}
.chanpterList p {
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #ddd;
}
.chanpterList .acts {
  float: right;
  font-size: 14px;
}

.videoList {
  padding-left: 50px;
}
.videoList p {
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #ddd;
}
</style>