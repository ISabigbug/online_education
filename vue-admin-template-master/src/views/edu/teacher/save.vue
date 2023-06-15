<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="讲师名称">
        <el-input v-model="eduTeacher.name" />
      </el-form-item>
      <el-form-item label="讲师排序">
        <el-input-number
          v-model="eduTeacher.sort"
          controls-position="right"
          min="0"
        />
      </el-form-item>
      <el-form-item label="讲师头衔">
        <el-select v-model="eduTeacher.level" clearable placeholder="请选择">
          <!--
 数据类型一定要和取出的json中的一致，否则没法回填
 因此，这里value使用动态绑定的值，保证其数据类型是number
 -->
          <el-option :value="1" label="高级讲师" />
          <el-option :value="2" label="首席讲师" />
        </el-select>
      </el-form-item>
      <el-form-item label="讲师资历">
        <el-input v-model="eduTeacher.career" />
      </el-form-item>
      <el-form-item label="讲师简介">
        <el-input v-model="eduTeacher.intro" :rows="10" type="textarea" />
      </el-form-item>
      <!-- 讲师头像 -->
      <el-form-item label="讲师头像">
        <!-- 头衔缩略图 -->
        <pan-thumb :image="eduTeacher.avatar" />
        <!-- 文件上传按钮 -->
        <el-button
          type="primary"
          icon="el-icon-upload"
          @click="imagecropperShow = true"
          >更换头像
        </el-button>
        <!--
 v-show：是否显示上传组件
 :key：类似于id，如果一个页面多个图片上传控件，可以做区分
 :url：后台上传的url地址
 @close：关闭上传组件
 @crop-upload-success：上传成功后的回调 -->
        <image-cropper
          v-show="imagecropperShow"
          :width="300"
          :height="300"
          :key="imagecropperKey"
          :url="BASE_API + '/eduoss/file'"
          field="file"
          @close="close"
          @crop-upload-success="cropSuccess"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          :disabled="saveBtnDisabled"
          type="primary"
          @click="saveOrUpdate"
          >保存</el-button
        >
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import teacher from "@/api/edu/teacher";
import ImageCropper from "@/components/ImageCropper";
import PanThumb from "@/components/PanThumb";

export default {
  components: { ImageCropper, PanThumb },
  data() {
    return {
      eduTeacher: {
        name: "",
        sort: 0,
        level: 1,
        career: "",
        intro: "",
        //默认头像，init方法也需设置
        avatar:
          "https://online-education-test1010.oss-cn-fuzhou.aliyuncs.com/2023/03/14e63cd250e4044a36b5a47ba6a1a64f5afile.png",
      },
      saveBtnDisabled: false,
      BASE_API: process.env.BASE_API, // 接口API地址
      imagecropperShow: false, // 是否显示上传组件
      imagecropperKey: 0, // 上传组件id
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
    //头像上传成功
    cropSuccess(data) {
      this.imagecropperShow = false;
      this.eduTeacher.avatar = data.url;
      // 上传成功后，重新打开上传组件时初始化组件，否则显示上一次的上传结果
      this.imagecropperKey = this.imagecropperKey + 1;
    },
    //头像上传点击关闭
    close() {
      this.imagecropperShow = false;
      // this.imagecropperKey = this.imagecropperKey + 1;
    },
    init() {
      //根据路径是否有id值，判断是否回显
      if (this.$route.params && this.$route.params.id) {
        const id = this.$route.params.id;
        this.getTeacher(id);
      } else {
        //清空eduTeacher
        this.eduTeacher = {};
        //默认头像
        this.eduTeacher.avatar =
          "https://online-education-test1010.oss-cn-fuzhou.aliyuncs.com/2023/03/14e63cd250e4044a36b5a47ba6a1a64f5afile.png";
      }
    },

    saveOrUpdate() {
      //根据id判断是修改还是添加
      if (this.eduTeacher.id) {
        //修改
        this.updateTeacher();
      } else {
        //添加
        this.saveTeather();
      }
    },

    //修改讲师
    updateTeacher() {
      teacher
        .updateTeacher(this.eduTeacher)
        .then((response) => {
          this.$message({
            type: "success",
            message: "修改成功 !",
          });
        })
        .then((response) => {
          //路由跳转
          this.$router.push({ path: "/teacher/table" });
        });
    },

    //添加讲师
    saveTeather() {
      teacher
        .addTeacher(this.eduTeacher)
        .then((response) => {
          this.$message({
            type: "success",
            message: "添加成功 !",
          });
        })
        .then((response) => {
          //路由跳转
          this.$router.push({ path: "/teacher/table" });
        });
    },

    //根据id查询讲师
    getTeacher(id) {
      teacher.getTeacher(id).then((response) => {
        this.eduTeacher = response.data.teacher;
      });
    },
  },
};
</script>