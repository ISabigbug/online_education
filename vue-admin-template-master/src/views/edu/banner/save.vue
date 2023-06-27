<template>
  <div class="app-container">
    <el-form ref="form" :model="form" label-width="120px">
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="幻灯片标题" prop="title">
            <el-input
              v-model="form.title"
              placeholder="请输入幻灯片标题"
              maxlength="30"
            />
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="放映顺序">
            <el-select v-model="form.sort" placeholder="请选择放映顺序">
              <el-option
                v-for="sort in sortList"
                :key="sort.value"
                :label="sort.label"
                :value="sort.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="幻灯片">
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
              bId === 1 ? "修改" : "发布"
            }}</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import upload from "@/api/edu/upload";
import banner from "@/api/edu/banner";
export default {
  data() {
    return {
      sortList: [
        {
          value: 0,
          label: "第一页",
        },
        {
          value: 1,
          label: "第二页",
        },
        {
          value: 2,
          label: "第三页",
        },
      ],
      value: "",
      form: {},
      bId: -1,
      fileList: [],
    };
  },
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
        this.bId = 1;
        const id = this.$route.params.id;
        this.getBanner(id);
      } else {
        this.bId = -1;
        //清空Banner
        this.form = {};
        this.fileRemove();
      }
    },
    getBanner(id) {
      banner.getBanner(id).then((response) => {
        this.form = response.data.crmBanner;
        if (response.data.crmBanner.imageUrl != "") {
          this.fileList.push({
            name: "幻灯片",
            url: response.data.crmBanner.imageUrl,
          });
        }
      });
    },
    handleSubmit() {
      if (this.bId != 1) {
        banner.addBanner(this.form).then((response) => {
          this.$message({
            type: "success",
            message: "幻灯片发布成功 !",
          });
          this.$router.push({ path: "/banner/list" });
        });
      } else {
        // 修改幻灯片信息
        banner.updateBanner(this.form).then((response) => {
          this.$message({
            type: "success",
            message: "幻灯片修改成功 !",
          });
          this.$router.push({ path: "/banner/list" });
        });
      }
    },
    onExceed() {
      this.$message.error("只能上传一个图片");
    },
    handleUpload(img) {
      upload
        .uploadImg(img.file)
        .then((response) => {
          this.form.imageUrl = response.data.url;
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
