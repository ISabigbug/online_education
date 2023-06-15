<template>
  <div class="app-container">
    <!--查询表单-->
    <el-form :inline="true" class="demo-form-inline">
      <!-- 所属分类：级联下拉列表 -->
      <!-- 一级分类 -->
      <el-form-item label="课程类别">
        <el-select
          v-model="courseQuery.subjectParentId"
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
        <el-select v-model="courseQuery.subjectId" placeholder="请选择">
          <el-option
            v-for="subject in subSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          />
        </el-select>
      </el-form-item>
      <!-- 标题 -->
      <el-form-item>
        <el-input v-model="courseQuery.title" placeholder="课程标题" />
      </el-form-item>
      <el-button
        type="primary"
        icon="el-icon-search"
        @click="pageCourseCondition()"
        >查 询</el-button
      >
      <el-button type="default" @click="resetData()">清 空</el-button>
    </el-form>

    <!-- 表格 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="数据加载中"
      border
      fit
      highlight-current-row
      row-class-name="myClassList"
    >
      <el-table-column label="序号" width="70" align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="课程信息" width="470" align="center">
        <template slot-scope="scope">
          <div class="info">
            <div class="pic">
              <img :src="scope.row.cover" alt="scope.row.title" width="150px" />
            </div>
            <div class="title">
              <a href="#">{{ scope.row.title }}</a>
              <p>总课时：{{ scope.row.lessonNum }}</p>
            </div>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtCreate.substr(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center">
        <template slot-scope="scope">
          {{ scope.row.gmtModified.substr(0, 10) }}
        </template>
      </el-table-column>
      <el-table-column label="价格" width="100" align="center">
        <template slot-scope="scope">
          {{
            Number(scope.row.price) === 0
              ? "免费"
              : "¥" + scope.row.price.toFixed(2)
          }}
        </template>
      </el-table-column>
      <el-table-column
        prop="buyCount"
        label="付费学员"
        width="100"
        align="center"
      >
        <template slot-scope="scope">{{ scope.row.buyCount }}人</template>
      </el-table-column>
      <el-table-column label="操作" width="150" align="center">
        <template slot-scope="scope">
          <router-link :to="'/course/info/' + scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit"
              >编辑课程信息</el-button
            >
          </router-link>
          <router-link :to="'/course/chapter/' + scope.row.id">
            <el-button type="text" size="mini" icon="el-icon-edit"
              >编辑课程大纲</el-button
            >
          </router-link>
          <el-button
            type="text"
            size="mini"
            icon="el-icon-delete"
            @click="removeCourseByCid(scope.row.id)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center"
      layout="total, prev, pager, next, jumper"
      @current-change="pageCourseCondition"
    />
  </div>
</template>

<script>
import course from "@/api/edu/course";
import subject from "@/api/edu/subject";

export default {
  data() {
    return {
      listLoading: true, // 是否显示loading信息
      list: null, // 数据列表
      total: 0, // 总记录数
      page: 1, // 页码
      limit: 10, // 每页记录数
      courseQuery: {
        subjectParentId: "",
        subjectId: "",
        title: "",
      }, // 查询条件
      subjectNestedList: [], // 一级分类列表
      subSubjectList: [], // 二级分类列表,
    };
  },

  created() {
    this.findOneSubject();
    this.pageCourseCondition();
  },

  methods: {
    // 查询课程列表
    pageCourseCondition(page = 1) {
      this.page = page;
      course
        .pageCourseCondition(this.page, this.limit, this.courseQuery)
        .then((response) => {
          this.list = response.data.records;
          this.total = response.data.total;
          this.listLoading = false;
        });
    },

    //二级联动
    subjectLevelOneChanged(value) {
      for (var i = 0; i < this.subjectNestedList.length; i++) {
        if (value === this.subjectNestedList[i].id) {
          this.subSubjectList = this.subjectNestedList[i].children;
          this.CourseQuery.subjectId = "";
        }
      }
    },

    // 获取一级分类
    findOneSubject() {
      subject.findAllSubject().then((Response) => {
        this.subjectNestedList = Response.data.subjectList;
      });
    },

    // 清空功能
    resetData() {
      this.courseQuery = {};
      this.pageCourseCondition();
    },

    //根据课程ID删除
    removeCourseByCid(cid) {
      this.$confirm("此操作将永久删除该课程全部数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        course.removeCourseByCid(cid).then((response) => {
          this.$message({
            type: "success",
            message: "删除成功 !",
          });
          //回到列表页面
          this.pageCourseCondition();
        });
      });
    },
  },
};
</script>
<style scoped>
.myClassList .info {
  width: 450px;
  overflow: hidden;
}
.myClassList .info .pic {
  width: 150px;
  height: 90px;
  overflow: hidden;
  float: left;
}
.myClassList .info .pic a {
  display: block;
  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;
}
.myClassList .info .pic img {
  display: block;
  width: 100%;
}
.myClassList td .info .title {
  width: 280px;
  float: right;
  height: 90px;
}
.myClassList td .info .title a {
  display: block;
  height: 48px;
  line-height: 24px;
  overflow: hidden;
  color: #00baf2;
  margin-bottom: 12px;
}
.myClassList td .info .title p {
  line-height: 20px;
  margin-top: 5px;
  color: #818181;
}
</style>