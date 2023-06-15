<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download" />
          <a
            :href="'https://online-education-test1010.oss-cn-fuzhou.aliyuncs.com/excel/testEasyExcel.xlsx'"
            >点击下载模版</a
          >
        </el-tag>
      </el-form-item>

      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API + '/eduservice/subject/addSubject'"
          name="file"
          accept="application/vnd.ms-excel.xls.xlsx"
        >
          <el-button slot="trigger" size="small" type="primary"
            >选取文件</el-button
          >
          <el-button
            :loading="loading"
            style="margin-left: 10px"
            size="small"
            type="success"
            @click="submitUpload"
            >{{ fileUploadBtnText }}</el-button
          >
        </el-upload>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      BASE_API: process.env.BASE_API, // 接口API地址
      fileUploadBtnText: "确认上传", // 按钮文字
      importBtnDisabled: false, // 按钮是否禁用,
      loading: false,
    };
  },

  created() {},

  methods: {
    submitUpload() {
      this.importBtnDisabled = true;
      this.loading = true;
      this.$refs.upload.submit();
    },

    //上传成功
    fileUploadSuccess(response) {
      if (response.success == true) {
        this.fileUploadBtnText = "上传成功 !";
        this.loading = false;
        this.$message({
          type: "success",
          message: "添加课程分类成功 !",
        });
        //页面跳转
        this.$router.push({ path: "/subject/list" });
      }
    },

    //上传失败
    fileUploadError(response) {
      if (response.success == false) {
        this.fileUploadBtnText = "上传失败 !";
        this.loading = false;
        this.$message({
          type: "success",
          message: "添加课程分类失败 !",
        });
      }
    },
  },
};
</script>