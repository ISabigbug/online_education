<template>
  <div>
    <!-- 幻灯片 开始 -->
    <div v-swiper:mySwiper="swiperOption">
      <div class="swiper-wrapper">
        <div
          v-for="banner in bannerList"
          :key="banner.id"
          class="swiper-slide"
          style="background: #040b1b"
        >
          <a target="_blank">
            <img
              id="backgroundDiv"
              :src="banner.imageUrl"
              :alt="banner.title"
            />
          </a>
        </div>
      </div>
      <div class="swiper-pagination swiper-pagination-white"></div>
      <div
        class="swiper-button-prev swiper-button-white"
        slot="button-prev"
      ></div>
      <div
        class="swiper-button-next swiper-button-white"
        slot="button-next"
      ></div>
    </div>
    <!-- 幻灯片 结束 -->

    <div id="aCoursesList">
      <!-- 网校课程 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">热门课程</span>
            </h2>
          </header>
          <div>
            <article class="comm-course-list">
              <ul class="of" id="bna">
                <li v-for="(course, index) in courseList" v-bind:key="index">
                  <div class="cc-l-wrap">
                    <section class="course-img">
                      <!-- ~/assets/photo/course/01.jpg -->
                      <img
                        :src="course.cover"
                        class="img-responsive"
                        :alt="course.title"
                      />
                      <div class="cc-mask" @click="isLogin(course.id)">
                        <a title="开始学习" class="comm-btn c-btn- 1"
                          >开始学习</a
                        >
                      </div>
                    </section>
                    <h3 @click="isLogin(course.id)" class="hLh30 txtOf mt10">
                      <a
                        :title="course.title"
                        class="course-title fsize18 c- 333"
                        >{{ course.title }}</a
                      >
                    </h3>
                    <section class="mt10 hLh20 of">
                      <span
                        class="fr jgTag bg-green"
                        v-if="Number(course.price) === 0"
                      >
                        <i class="c-fff fsize12 f-fA">免费</i>
                      </span>
                      <span class="fr jgTag bg-green" v-else>
                        <i class="c-fff fsize12 f-fA"> ￥{{ course.price }}</i>
                      </span>
                      <span class="fl jgAttr c-ccc f-fA">
                        <i class="c-999 f-fA">{{ course.buyCount }} 人学习</i>
                        |
                        <i class="c-999 f-fA">{{ course.viewCount }} 人浏览</i>
                      </span>
                    </section>
                  </div>
                </li>
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a href="/course" title="全部课程" class="comm-btn c-btn-2"
                >全部课程</a
              >
            </section>
          </div>
        </section>
      </div>
      <!-- /网校课程 结束 -->
      <!-- 网校名师 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">
            <h2 class="tac">
              <span class="c-333">名师大咖</span>
            </h2>
          </header>
          <div>
            <article class="i-teacher-list">
              <ul class="of">
                <li v-for="(teacher, index) in teacherList" v-bind:key="index">
                  <section class="i-teach-wrap">
                    <div class="i-teach-pic">
                      <a :href="'/teacher/' + teacher.id" :title="teacher.name">
                        <img :alt="teacher.name" :src="teacher.avatar" />
                      </a>
                    </div>
                    <div class="mt10 hLh30 txtOf tac">
                      <a
                        :href="'/teacher/' + teacher.id"
                        :title="teacher.name"
                        class="fsize18 c-666"
                        >{{ teacher.name }}</a
                      >
                    </div>
                    <div class="hLh30 txtOf tac">
                      <span class="fsize14 c-999">{{ teacher.intro }}</span>
                    </div>
                    <div class="mt15 i-q-txt">
                      <p class="c-999 f-fA">{{ teacher.career }}</p>
                    </div>
                  </section>
                </li>
              </ul>
              <div class="clear"></div>
            </article>
            <section class="tac pt20">
              <a href="/teacher" title="全部讲师" class="comm-btn c-btn-2"
                >全部讲师</a
              >
            </section>
          </div>
        </section>
      </div>
      <!-- /网校名师 结束 -->
    </div>
    <div v-if="dialogVisible">
      <el-drawer
        title="包教会在线教育提示"
        :visible.sync="drawer"
        :direction="direction"
        :size="150"
      >
        <span id="font">
          短信服务目前使用阿里云短信测试实现，暂不提供注册，测试账号:11111111111
          测试密码:123456
        </span>
        <img
          id="img"
          src="https://online-education-test1010.oss-cn-fuzhou.aliyuncs.com/2023/04/%E6%96%B0%E5%BB%BA%E6%96%87%E4%BB%B6%E5%A4%B9/mmexport1683430598949.jpg"
        />
        <br />
        <span id="font">
          网站所使用到的图片文字素材均来自网上，此网站仅做个人学习之用，请勿支付！如有侵权请联系chasel_wql@163.com
        </span>
      </el-drawer>
    </div>
  </div>
</template>

 <script>
import banner from "@/api/banner";
import index from "@/api/index";
import course from "@/api/course";

export default {
  data() {
    return {
      drawer: true,
      direction: "ttb",
      dialogVisible: false,
      swiperOption: {
        //开启自动轮播
        autoplay: true,
        speed: 500,
        //配置分页
        pagination: {
          el: ".swiper-pagination", //分页的dom节点
        },
        //配置导航
        navigation: {
          nextEl: ".swiper-button-next", //下一页dom节点
          prevEl: ".swiper-button-prev", //前一页dom节点
        },
      },
      bannerList: [],
      teacherList: [],
      courseList: [],
    };
  },
  created() {
    this.findAllBanner();
    this.index();
  },
  mounted() {
    this.dialogVisible = true;
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

    findAllBanner() {
      banner.findAllBanner().then((Response) => {
        this.bannerList = Response.data.data.bannerList;
      });
    },
    index() {
      index.index().then((Response) => {
        this.teacherList = Response.data.data.teacherList;
        this.courseList = Response.data.data.courseList;
      });
    },
  },
};
</script>
<style>
#backgroundDiv {
  width: 100%;
  height: 100%;
  background-size: 100% 100%;
}
#font {
  padding-left: 30px;
  font-weight: bold;
}
#img {
  float: right;
  height: 70px;
  width: 70px;
  margin-right: 29px;
}
</style>