<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24" :xs="24">
        <el-form
          v-show="showSearch"
          ref="queryForm"
          :model="queryParams"
          :inline="true"
          label-width="68px"
        >
          <el-form-item label="课程名">
            <el-select v-model="queryParams.courseId" placeholder="请选择">
              <el-option
                v-for="course in courseList"
                :key="course.id"
                :label="course.title"
                :value="course.id"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="用户名称" prop="nickname">
            <el-input
              v-model="queryParams.nickname"
              placeholder="请输入用户名称"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="评论内容" prop="content">
            <el-input
              v-model="queryParams.content"
              placeholder="请输入评论内容"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="getList()"
              >搜索</el-button
            >
            <el-button type="primary" icon="el-icon-search" @click="reset()"
              >清空</el-button
            >
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              @click="deleteBannerList"
              >删除</el-button
            >
          </el-col>
        </el-row>

        <el-table
          :data="commentList"
          style="width: 100%"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" />
          <el-table-column prop="id" label="序号" align="center">
            <template slot-scope="scope">
              {{ (page - 1) * limit + scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column prop="courseTitle" label="课程名称" align="center" />
          <el-table-column prop="nickname" label="用户名称" align="center" />
          <el-table-column prop="content" label="评论内容" align="center" />
          <el-table-column prop="gmtCreate" label="创建时间" align="center" />

          <el-table-column
            label="操作"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                >删除</el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
    />
  </div>
</template>

<script>
import comment from "@/api/edu/comment";

export default {
  // name: "Article",
  data() {
    return {
      page: 1,
      // total: 0, //总页数
      limit: 10, //每页记录数
      // 查询参数
      queryParams: {
        courseId: "",
        nickname: "",
        content: "",
      },
      //   title: "",
      // 是否显示弹出层
      open: false,
      // 总条数
      total: 0,
      commentList: null,
      showSearch: true,
      courseList: null,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 搜索按钮操作 */
    reset() {
      this.queryParams.courseId = "";
      this.queryParams.nickname = "";
      this.queryParams.content = "";
      this.getList();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 查询用户列表 */
    getList(page = 1) {
      this.page = page;
      // this.loading = true;
      comment
        .listComment(this.page, this.limit, this.queryParams)
        .then((response) => {
          this.commentList = response.data.records;
          this.courseList = response.data.courseList;
          this.total = response.data.total;
          // this.loading = false;
        });
    },
    /** 修改按钮操作 */
    // handleUpdate(row) {
    //   this.$router.push("/banner/edit/" + row.id);
    // },
    /** 新增用户 */
    // handleAdd() {
    //   if (this.total >= 3) {
    //     this.$message({
    //       type: "error",
    //       message: "幻灯片最多放映三张",
    //     });
    //   } else {
    //     this.$router.push("/banner/save");
    //   }
    // },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        comment.delComment(ids).then((response) => {
          this.$message({
            type: "success",
            message: "删除成功 !",
          });
          //回到列表页面
          this.getList();
        });
      });
    },
    deleteBannerList(row) {
      const ids = row.id || this.ids;
      this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        comment.delCommentList(ids).then((response) => {
          this.$message({
            type: "success",
            message: "删除成功 !",
          });
          //回到列表页面
          this.getList();
        });
      });
    },
  },
};
</script>
