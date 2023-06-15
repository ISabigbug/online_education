<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>
    <el-steps
      :active="1"
      process-status="wait"
      align-center
      style="margin-bottom: 40px"
    >
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="提交审核" />
    </el-steps>

    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input
          v-model="courseInfo.title"
          placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"
        />
      </el-form-item>

      <!-- 所属分类 TODO -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="请选择"
          @change="subjectLevelOneChanged"
        >
          <el-option
            v-for="subject in subjectNestedList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="请选择">
          <el-option
            v-for="subject in subSubjectList"
            :key="subject.value"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
      </el-form-item>

      <!-- 课程讲师 TODO -->
      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number
          :min="0"
          v-model="courseInfo.lessonNum"
          controls-position="right"
          placeholder="请填写课程的总课时数"
        />
      </el-form-item>

      <!-- 课程简介 TODO -->
      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description" />
      </el-form-item>

      <!-- 课程封面 TODO -->
      <!-- 课程封面-->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="BASE_API + '/eduoss/file'"
          class="avatar-uploader"
        >
          <img :src="courseInfo.cover" />
        </el-upload>
      </el-form-item>

      <el-form-item label="课程价格">
        <el-input-number
          :min="0"
          v-model="courseInfo.price"
          controls-position="right"
          placeholder="免费课程请设置为0元"
        ></el-input-number
      ></el-form-item>
      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate"
          >保存并下一步</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import course from "@/api/edu/course";
import subject from "@/api/edu/subject";
import Tinymce from "@/components/Tinymce";

export default {
  components: { Tinymce }, //声明组件
  data() {
    return {
      saveBtnDisabled: false, //保存按钮是否禁用
      courseInfo: {
        title: "",
        subjectId: "",
        subjectParentId: "",
        teacherId: "",
        lessonNum: 0,
        description: "",
        cover:
          "https://online-education-test1010.oss-cn-fuzhou.aliyuncs.com/2023/03/14e63cd250e4044a36b5a47ba6a1a64f5afile.png",
        price: 0,
      },
      cid: "",
      teacherList: [],
      subjectNestedList: [], // 一级分类
      subSubjectList: [], // 二级分类
      BASE_API: process.env.BASE_API, // 接口API地址
    };
  },

  created() {
    this.init();
  },

  //监听器
  watch: {
    $route(to, from) {
      this.init();
    },
  },

  methods: {
    //数据回显
    init() {
      if (this.$route.params && this.$route.params.id) {
        this.cid = this.$route.params.id;
        course.findCourseInfo(this.cid).then((Response) => {
          this.courseInfo = Response.data.courseVo;
          //解决二级分类回显bug
          subject.findAllSubject().then((Response) => {
            this.subjectNestedList = Response.data.subjectList;
            //遍历所有一级分类并与courseInfo里的一级分类ID进行判断
            for (var i = 0; i < this.subjectNestedList.length; i++) {
              var oneSubject = this.subjectNestedList[i];
              if (this.courseInfo.subjectParentId === oneSubject.id) {
                this.subSubjectList = oneSubject.children;
              }
            }
          });
          //初始化讲师
          this.findAllTeacher();
        });
      } else {
        this.subSubjectList = {};
        this.courseInfo.title = "";
        this.courseInfo.subjectId = "";
        this.courseInfo.subjectParentId = "";
        this.courseInfo.teacherId = "";
        this.courseInfo.lessonNum = 0;
        this.courseInfo.description = "";
        this.courseInfo.cover =
          "https://online-education-test1010.oss-cn-fuzhou.aliyuncs.com/2023/03/14e63cd250e4044a36b5a47ba6a1a64f5afile.png";
        this.findAllTeacher();
        this.courseInfo.price = "";
        this.findOneSubject();
      }
    },

    //封面上传成功方法
    handleAvatarSuccess(res, file) {
      this.courseInfo.cover = res.data.url;
    },

    //封面上传之前方法
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },

    //二级联动
    subjectLevelOneChanged(value) {
      for (var i = 0; i < this.subjectNestedList.length; i++) {
        if (value === this.subjectNestedList[i].id) {
          this.subSubjectList = this.subjectNestedList[i].children;
          this.courseInfo.subjectId = "";
        }
      }
    },

    // 获取一级分类
    findOneSubject() {
      subject.findAllSubject().then((Response) => {
        this.subjectNestedList = Response.data.subjectList;
      });
    },

    findAllTeacher() {
      course.findAllTeacher().then((Response) => {
        this.teacherList = Response.data.teachers;
      });
    },

    //添加课程信息
    saveCourse() {
      course.saveCourseInfo(this.courseInfo).then((Response) => {
        this.$message({
          type: "success",
          message: "添加课程基本信息成功 !",
        });
        this.saveBtnDisabled = true;
        this.$router.push({ path: "/course/chapter/" + Response.data.cid });
      });
    },

    //修改课程信息
    updateCourse() {
      course.updateConseInfo(this.courseInfo).then((Response) => {
        this.$message({
          type: "success",
          message: "修改课程基本信息成功 !",
        });
        this.saveBtnDisabled = true;
        this.$router.push({ path: "/course/chapter/" + this.cid });
      });
    },

    saveOrUpdate() {
      if (!this.courseInfo.id) {
        this.saveCourse();
      } else {
        this.updateCourse();
      }
    },
  },
};
</script>
<style scoped>
.tinymce-container {
  line-height: 29px;
}
</style>