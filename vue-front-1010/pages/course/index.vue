<template>
  <div id="aCoursesList" class="bg-fa of">
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li>
                  <a
                    title="全部"
                    href="javascript:void(0);"
                    @click="searchOne('')"
                    >全 部</a
                  >
                </li>
                <li
                  v-for="(item, index) in subjectNestedList"
                  v-bind:key="index"
                  :class="{ active: oneIndex == index }"
                >
                  <a
                    :title="item.title"
                    href="javascript:void(0);"
                    @click="searchOne(item.id, index)"
                    >{{ item.title }}</a
                  >
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14" />
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li
                  v-for="(item, index) in subjectList"
                  v-bind:key="index"
                  :class="{ active: twoIndex == index }"
                >
                  <a
                    :title="item.title"
                    href="javascript:void(0);"
                    @click="searchTwo(item.id, index)"
                    >{{ item.title }}</a
                  >
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear" />
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <!-- 排序方式显示开始 -->
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{ 'current bg-orange': buyCountSort != '' }">
                <a
                  title="销量"
                  href="javascript:void(0);"
                  @click="searchBuyCount()"
                  >销量
                  <span :class="{ hide: buyCountSort == '' }">↓</span>
                </a>
              </li>
              <li :class="{ 'current bg-orange': gmtCreateSort != '' }">
                <a
                  title="最新"
                  href="javascript:void(0);"
                  @click="searchGmtCreate()"
                  >最新
                  <span :class="{ hide: gmtCreateSort == '' }">↓</span>
                </a>
              </li>
              <li :class="{ 'current bg-orange': priceSort != '' }">
                <a
                  title="价格"
                  href="javascript:void(0);"
                  @click="searchPrice()"
                  >价格&nbsp;
                  <span :class="{ hide: priceSort == '' }">↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section class="no-data-wrap" v-show="data.total === 0">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam"
              >没有相关数据，小编正在努力整理中...</span
            >
          </section>
          <!-- /无数据提示 结束-->
          <!-- 数据列表 开始-->
          <article v-show="data.total > 0" class="comm-course-list">
            <ul id="bna" class="of">
              <li v-for="item in data.items" :key="item.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="item.cover" class="img-responsive" alt />
                    <div @click="isLogin(item.id)" class="cc-mask">
                      <a title="开始学习" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 @click="isLogin(item.id)" class="hLh30 txtOf mt10">
                    <a :title="item.title" class="course-title fsize18 c-333">{{
                      item.title
                    }}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span
                      v-show="Number(item.price) === 0"
                      class="fr jgTag bg-green"
                    >
                      <i class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{ item.viewCount }}人学习</i>
                      |
                      <i class="c-999 f-fA">{{ item.commentNum }}评论</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear" />
          </article>
          <!-- /数据列表 结束-->
        </div>
        <!-- 公共分页 开始 -->
        <div>
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{ undisable: !data.hasPrevious }"
              href="#"
              title="首页"
              @click.prevent="gotoPage(1)"
              >首页</a
            >
            <a
              :class="{ undisable: !data.hasPrevious }"
              href="#"
              title="前一页"
              @click.prevent="gotoPage(data.current - 1)"
              >&lt;</a
            >
            <a
              v-for="page in data.pages"
              :key="page"
              :class="{
                current: data.current == page,
                undisable: data.current == page,
              }"
              :title="'第' + page + '页'"
              href="#"
              @click.prevent="gotoPage(page)"
              >{{ page }}</a
            >
            <a
              :class="{ undisable: !data.hasNext }"
              href="#"
              title="后一页"
              @click.prevent="gotoPage(data.current + 1)"
              >&gt;</a
            >
            <a
              :class="{ undisable: !data.hasNext }"
              href="#"
              title="末页"
              @click.prevent="gotoPage(data.pages)"
              >末页</a
            >
            <div class="clear" />
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
 <script>
import course from "@/api/course";
export default {
  data() {
    return {
      page: 1,
      data: {},
      subjectNestedList: [], // 一级分类列表
      subjectList: [], // 二级分类列表
      searchObj: {}, // 查询表单对象
      oneIndex: -1, //点击阴影
      twoIndex: -1, //点击阴影
      buyCountSort: "",
      gmtCreateSort: "",
      priceSort: "",
    };
  },

  created() {
    if (this.$route.query.courseName) {
      this.searchObj.title = this.$route.query.courseName;
    }
    this.initCourse();
    this.initSubject();
  },

  methods: {
    //判断用户是否登录
    isLogin(cid) {
      course.isLogin().then((Response) => {
        if (Response.data.success) {
          this.$router.push({ path: "/course/" + cid });
        } else {
          this.$router.push({ path: "/login" });
          this.$message({
            type: "success",
            message: "请先登录",
          });
        }
      });
    },

    //查询课程列表
    initCourse() {
      course.pageCourseFront(1, 8, this.searchObj).then((Response) => {
        this.searchObj = {};
        this.data = Response.data.data;
      });
    },

    //分页查询
    gotoPage(page) {
      if ((page >= 1 && page <= this.data.pages) || this.data.pages == 0) {
        course.pageCourseFront(page, 8, this.searchObj).then((Response) => {
          this.data = Response.data.data;
        });
      }
    },

    //查询一级标题
    initSubject() {
      course.findAllSubject().then((Response) => {
        this.subjectNestedList = Response.data.data.subjectList;
      });
    },

    //点击一级标题，查询相应数据，并显示二级标题
    searchOne(subjectParentId, index) {
      this.oneIndex = index;

      //清空二级标题数据
      this.twoIndex = -1;
      this.subjectList = [];
      this.searchObj.subjectId = "";

      //查询一级标题相应数据
      this.searchObj.subjectParentId = subjectParentId;
      this.gotoPage(1);

      //显示二级标题
      for (var i = 0; i < this.subjectNestedList.length; i++) {
        if (this.subjectNestedList[i].id === subjectParentId) {
          this.subjectList = this.subjectNestedList[i].children;
        }
      }
    },

    //点击二级标题查询相应的数据
    searchTwo(subjectId, index) {
      this.twoIndex = index;
      this.searchObj.subjectId = subjectId;
      this.gotoPage(1);
    },

    //购买量查询
    searchBuyCount() {
      //查询之前需要清除上一次点击的数据
      this.buyCountSort = "1";
      this.gmtCreateSort = "";
      this.priceSort = "";
      this.searchObj.buyCountSort = this.buyCountSort;
      this.searchObj.gmtCreateSort = this.gmtCreateSort;
      this.searchObj.priceSort = this.priceSort;
      this.gotoPage(1);
    },

    //更新时间查询
    searchGmtCreate() {
      //查询之前需要清除上一次点击的数据
      this.buyCountSort = "";
      this.gmtCreateSort = "1";
      this.priceSort = "";
      this.searchObj.buyCountSort = this.buyCountSort;
      this.searchObj.gmtCreateSort = this.gmtCreateSort;
      this.searchObj.priceSort = this.priceSort;
      this.gotoPage(1);
    },

    //价格查询
    searchPrice() {
      //查询之前需要清除上一次点击的数据
      this.buyCountSort = "";
      this.gmtCreateSort = "";
      this.priceSort = "1";
      this.searchObj.buyCountSort = this.buyCountSort;
      this.searchObj.gmtCreateSort = this.gmtCreateSort;
      this.searchObj.priceSort = this.priceSort;
      this.gotoPage(1);
    },
  },
};
</script>
<style scoped>
.active {
  background: #bdbdbd;
}
.hide {
  display: none;
}
.show {
  display: block;
}
</style>