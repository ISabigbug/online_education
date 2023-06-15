<template>
  <el-row>
    <el-col :span="8" style="padding-right: 10px">
      <el-card class="box-card">
        <div class="user">
          <img src="@/assets/images/user.gif" alt="" />
          <div class="userinfo">
            <p class="name">{{ name }}</p>
            <p class="access" v-for="role in roles" :key="role">{{ role }}</p>
          </div>
        </div>
        <div class="login-info">
          <p>上次登录时间：<span>2023-6-30</span></p>
          <p>上次登录地点：<span>福建</span></p>
        </div>
      </el-card>
      <el-card style="margin-top: 20px; height: 460px">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="name" label="课程"> </el-table-column>
          <el-table-column prop="todayBuy" label="今日购买"> </el-table-column>
          <el-table-column prop="monthBuy" label="本月购买"> </el-table-column>
          <el-table-column prop="totalBuy" label="总购买"> </el-table-column>
        </el-table>
      </el-card>
    </el-col>
    <el-col :span="16" style="padding-left: 10px">
      <div class="num">
        <el-card
          v-for="item in countData"
          :key="item.name"
          :body-style="{ display: 'flex', padding: 0 }"
        >
          <i
            class="icon"
            :class="`el-icon-${item.icon}`"
            :style="{ background: item.color }"
          ></i>
          <div class="detail">
            <p class="price">￥{{ item.value }}</p>
            <p class="desc">{{ item.name }}</p>
          </div>
        </el-card>
      </div>
      <el-card style="height: 280px">
        <!-- 折线图 -->
        <div id="echarts1" style="height: 280px"></div>
      </el-card>
      <div class="graph">
        <el-card style="height: 260px">
          <div id="echarts2" style="height: 260px"></div>
        </el-card>
        <el-card style="height: 260px">
          <div id="echarts3" style="height: 240px"></div>
        </el-card>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import { mapGetters } from "vuex";
import * as echarts from "echarts";

export default {
  name: "Dashboard",
  data() {
    return {
      chart1: null,
      chart2: null,
      chart3: null,
      tableData: [
        {
          name: "Docker",
          todayBuy: "35",
          monthBuy: "185",
          totalBuy: "410",
        },
        {
          name: "SpringCloud",
          todayBuy: "12",
          monthBuy: "150",
          totalBuy: "320",
        },
        {
          name: "Jfinal",
          todayBuy: "18",
          monthBuy: "196",
          totalBuy: "310",
        },
        {
          name: "Kafka",
          todayBuy: "21",
          monthBuy: "161",
          totalBuy: "270",
        },
        {
          name: "Nginx",
          todayBuy: "27",
          monthBuy: "123",
          totalBuy: "210",
        },
        {
          name: "Java基础",
          todayBuy: "12",
          monthBuy: "75",
          totalBuy: "200",
        },
        {
          name: "Nacos",
          todayBuy: "6",
          monthBuy: "95",
          totalBuy: "191",
        },
      ],
      tableLabel: {
        name: "课程",
        todayBuy: "今日购买",
        monthBuy: "本月购买",
        totalBuy: "总购买",
      },
      countData: [
        {
          name: "今日支付订单",
          value: 85,
          icon: "success",
          color: "#2ec7c9",
        },
        {
          name: "今日收藏订单",
          value: 6,
          icon: "star-on",
          color: "#ffb980",
        },
        {
          name: "今日未支付订单",
          value: 32,
          icon: "s-goods",
          color: "#5ab1ef",
        },
        {
          name: "本月支付订单",
          value: 870,
          icon: "success",
          color: "#2ec7c9",
        },
        {
          name: "本月收藏订单",
          value: 32,
          icon: "star-on",
          color: "#ffb980",
        },
        {
          name: "本月未支付订单",
          value: 128,
          icon: "s-goods",
          color: "#5ab1ef",
        },
      ],
    };
  },
  computed: {
    ...mapGetters(["name", "roles"]),
  },

  mounted() {
    this.setChart1();
    this.setChart2();
    this.setChart3();
  },

  methods: {
    // 折线图
    setChart1() {
      //折线图
      this.chart1 = echarts.init(document.getElementById("echarts1"));
      var option = {
        tooltip: {
          trigger: "axis",
        },
        legend: {
          data: ["Nginx", "Jfinal", "Docker", "SpringCloud"],
        },
        grid: {
          left: "5%",
          right: "4%",
          bottom: "14%",
          top: "8%",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          boundaryGap: false,
          data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
        },
        yAxis: {
          axisLine: {
            show: false,
          },
          axisTick: {
            show: false,
          },
          type: "value",
        },
        series: [
          {
            name: "Nginx",
            type: "line",
            stack: "Total",
            data: [120, 132, 101, 134, 90, 230, 210],
          },
          {
            name: "Jfinal",
            type: "line",
            stack: "Total",
            data: [220, 182, 191, 234, 290, 330, 310],
          },
          {
            name: "Docker",
            type: "line",
            stack: "Total",
            data: [150, 232, 201, 154, 190, 330, 410],
          },
          {
            name: "SpringCloud",
            type: "line",
            stack: "Total",
            data: [320, 332, 301, 334, 390, 330, 320],
          },
        ],
      };
      this.chart1.setOption(option);
    },
    //柱状图
    setChart2() {
      this.chart2 = echarts.init(document.getElementById("echarts2"));
      var option = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "shadow",
          },
        },
        grid: {
          top: "3%",
          left: "3%",
          right: "4%",
          bottom: "10%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            data: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
            axisTick: {
              alignWithLabel: true,
            },
          },
        ],
        yAxis: [
          {
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            type: "value",
          },
        ],
        series: [
          {
            name: "Register",
            type: "bar",
            barWidth: "60%",
            color: "#5470c6",
            data: [19, 12, 32, 40, 22, 32, 28],
          },
        ],
      };
      this.chart2.setOption(option);
    },
    //柱饼图
    setChart3() {
      this.chart3 = echarts.init(document.getElementById("echarts3"));
      var option = {
        tooltip: {
          trigger: "item",
        },
        series: [
          {
            name: "Access From",
            type: "pie",
            radius: "70%",
            itemStyle: {
              color: function (params) {
                var colorList = [
                  "#5470c6",
                  "#91cc75",
                  "#fac858",
                  "#ee6666",
                  "#73c0de",
                ];
                return colorList[params.dataIndex];
              },
            },
            data: [
              { value: 18, name: "Java" },
              { value: 7, name: "HTML" },
              { value: 8, name: "Vue" },
              { value: 4, name: "JavaScript" },
              { value: 12, name: "Python" },
            ],
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      };
      this.chart3.setOption(option);
    },
  },
};
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
// .dashboard {
//   &-container {
//     margin: 30px;
//   }
//   &-text {
//     font-size: 30px;
//     line-height: 46px;
//   }
// }
.user {
  padding-bottom: 20px;
  margin-bottom: 20px;
  border-bottom: 1px solid #ccc;
  display: flex;
  align-items: center;
  img {
    margin-right: 40px;
    width: 150px;
    height: 150px;
    border-radius: 50%;
  }
  .userinfo {
    .name {
      font-size: 32px;
      margin-bottom: 10px;
    }
    .access {
      color: #999999;
    }
  }
}
.login-info {
  p {
    line-height: 28px;
    font-size: 14px;
    color: #999999;
    span {
      color: #666666;
      margin-left: 60px;
    }
  }
}
.num {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  .icon {
    width: 80px;
    height: 80px;
    font-size: 30px;
    text-align: center;
    line-height: 80px;
    color: #fff;
  }
  .detail {
    margin-left: 15px;
    display: flex;
    flex-direction: column;
    justify-content: center;
    .price {
      font-size: 30px;
      margin-bottom: 10px;
      line-height: 30px;
      height: 30px;
    }
    .desc {
      font-size: 14px;
      color: #999;
      text-align: center;
    }
  }
  .el-card {
    width: 32%;
    margin-bottom: 20px;
  }
}
.graph {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  .el-card {
    width: 48%;
  }
}
</style>
