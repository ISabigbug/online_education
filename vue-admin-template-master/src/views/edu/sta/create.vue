<template>
  <div class="app-container">
    <!--表单-->
    <el-form :inline="true" class="demo-form-inline">
      <el-form-item label="日期">
        <el-date-picker
          v-model="day"
          type="date"
          placeholder="选择要统计的日期"
          value-format="yyyy-MM-dd"
        />
      </el-form-item>

      <el-button :disabled="btnDisabled" type="primary" @click="create()"
        >生成</el-button
      >
    </el-form>
  </div>
</template>

<script>
import sta from "@/api/edu/sta";

export default {
  data() {
    return {
      day: "",
      btnDisabled: false,
    };
  },

  created() {},

  methods: {
    create() {
      sta.saveRegister(this.day).then((Response) => {
        if (Response.success) {
          this.btnDisabled = false;
          this.$message({
            type: "success",
            message: "生成成功 !",
          });
          this.$router.push({ path: "/sta/show" });
        } else {
          this.$message({
            type: "error",
            message: "生成失败，请重新尝试 !",
          });
        }
      });
    },
  },
};
</script>