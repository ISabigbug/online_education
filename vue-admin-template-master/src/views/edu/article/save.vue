<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="90px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="文章标题" prop="title">
            <el-input
              v-model="form.title"
              placeholder="请输入文章标题"
              maxlength="30"
            />
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="分类">
            <el-input
              v-model="form.categoryName"
              placeholder="请输入分类标题"
              maxlength="30"
            />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="文章摘要">
            <el-input v-model="form.summary" type="textarea" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="允许评论">
            <el-radio-group v-model="form.isComment">
              <el-radio :label="'0'">正常</el-radio>
              <el-radio :label="'1'">停用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="是否置顶">
            <el-radio-group v-model="form.isTop">
              <el-radio :label="'0'">是</el-radio>
              <el-radio :label="'1'">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20" />

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="缩略图">
            <el-upload
              :file-list="fileList"
              class="upload-demo"
              list-type="picture"
              drag
              name="img"
              :action="BASE_API + '/eduoss/file/articleImg'"
              :on-remove="fileRemove"
              :before-upload="beforeAvatarUpload"
              :limit="1"
              :http-request="handleUpload"
              :on-exceed="onExceed"
            >
              <i class="el-icon-upload" />
              <div class="el-upload__text">
                将文件拖到此处，或<em>点击上传</em>
              </div>
              <div slot="tip" class="el-upload__tip">
                只能上传jpg/png文件，且不超过2MB
              </div>
            </el-upload>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item>
            <el-button type="primary" size="medium" @click="handleSubmit">{{
              aId === 1 ? "更新" : "发布"
            }}</el-button>
          </el-form-item>
          <el-form-item>
            <el-button v-if="aId !== 1" type="info" @click="handleSave"
              >保存到草稿箱</el-button
            >
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <mavon-editor ref="md" v-model="form.content" @imgAdd="addImg" />
      </el-row>
    </el-form>
  </div>
</template>

<script>
// import { listAllCategory } from '@/api/content/category'
import upload from "@/api/edu/upload";
import article from "@/api/edu/article";
// import { listAllTag } from '@/api/content/tag'
export default {
  name: "Write",
  data() {
    return {
      form: {
        summary: "",
        categoryName: "",
        title: "",
        thumbnail: "",
        isTop: "1",
        isComment: "0",
        // content: "",
      },
      aId: -1,
      fileList: [],
    };
  },
  // watch: {
  //   $route: {
  //     handler: function (route) {
  //       this.aId = route.query && route.query.id;
  //     },
  //     immediate: true,
  //   },
  // },
  created() {
    this.init();
  },
  watch: {
    $route(to, from) {
      this.init();
    },
  },
  methods: {
    init() {
      //根据路径是否有id值，判断是否回显
      if (this.$route.params && this.$route.params.id) {
        this.aId = 1;
        const id = this.$route.params.id;
        this.getArticle(id);
      } else {
        this.aId = -1;
        //清空Article
        // this.form = {};
        this.form.id = "";
        this.form.title = "";
        this.form.thumbnail = "";
        this.form.categoryName = "";
        this.form.summary = "";
        this.form.isTop = "1";
        this.form.isComment = "0";
        this.form.content = "";
        this.form.summary = "";
        this.form.categoryName = "";
        this.fileRemove();
      }
    },
    getArticle(id) {
      article.getArticle(id).then((response) => {
        this.form = response.data.article;
        if (response.data.article.thumbnail != "") {
          this.fileList.push({
            name: "缩略图",
            url: response.data.article.thumbnail,
          });
        }
      });
    },
    handleSave() {
      this.form.status = "1";
      article.addArticle(this.form).then((response) => {
        this.$message({
          type: "success",
          message: "保存草稿成功 !",
        });
        this.$router.push({ path: "/article/list" });
      });
    },
    handleSubmit() {
      if (this.aId != 1) {
        this.form.status = "0";
        article.addArticle(this.form).then((response) => {
          this.$message({
            type: "success",
            message: "文章发布成功 !",
          });
          this.$router.push({ path: "/article/list" });
        });
      } else {
        // 更新文章信息
        article.updateArticle(this.form).then((response) => {
          this.$message({
            type: "success",
            message: "文章更新成功 !",
          });
          this.$router.push({ path: "/article/list" });
        });
      }
    },
    onExceed() {
      this.$message.error("只能上传一个图片 !");
    },
    handleUpload(img) {
      upload
        .uploadImg(img.file)
        .then((response) => {
          this.form.thumbnail = response.data.url;
          this.fileList.push({ name: img.file.name, url: response.data.url });
        })
        .catch((error) => {
          this.$message.error(error.msg);
        });
    },
    fileRemove(file, fileList) {
      this.fileList.pop();
    },
    // 绑定@imgAdd event
    addImg(pos, file) {
      // 第一步.将图片上传到服务器.
      upload
        .uploadImg(file)
        .then((response) => {
          this.$refs.md.$img2Url(pos, response.data.url);
        })
        .catch((error) => {
          this.$message.error(error.msg);
        });
    },
    //设置上传图片的格式
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isPNG = file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG && !isPNG) {
        this.$message.error("上传头像图片只能是 JPG或PNG 格式 !");
      }
      // if (!isPNG) {
      //   this.$message.error("上传头像图片只能是 JPG或PNG 格式 !");
      // }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB !");
      }
      return (isJPG || isPNG) && isLt2M;
    },
  },
};
</script>
<style scoped>
div .upload-demo {
  /* padding-left: 80px; */
}
.el-col .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;

  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
