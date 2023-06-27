<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>

    <div class="sign-up-container">
      <el-form ref="userForm" :model="user">
        <el-form-item
          class="input-prepend restyle"
          prop="mobile"
          :rules="[
            {
              required: true,
              message: '请输入手机号码',
              trigger: 'blur',
            },
            { validator: checkPhone, trigger: 'blur' },
          ]"
        >
          <div>
            <el-input type="text" placeholder="手机号" v-model="user.mobile" />
            <i class="iconfont icon-phone" />
          </div>
        </el-form-item>

        <el-form-item
          class="input-prepend"
          prop="password"
          :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]"
        >
          <div>
            <el-input
              type="password"
              placeholder="密码"
              v-model="user.password"
            />
            <i class="iconfont icon-password" />
          </div>
        </el-form-item>

        <div class="btn">
          <input
            type="button"
            class="sign-in-button"
            value="登录"
            @click="submitLogin()"
          />
        </div>
      </el-form>
      <!-- 更多登录方式 -->
      <div class="more-sign">
        <h6>社交帐号登录</h6>
        <ul>
          <li>
            <a id="weixin" class="weixin" target="_blank" @click="warn"
              ><i class="iconfont icon-weixin"
            /></a>
          </li>
          <li>
            <a id="qq" class="qq" target="_blank" @click="warn"
              ><i class="iconfont icon-qq"
            /></a>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

 <script>
import "~/assets/css/sign.css";
import "~/assets/css/iconfont.css";
import cookie from "js-cookie";
import login from "@/api/login";

export default {
  layout: "sign",

  data() {
    return {
      user: {
        mobile: "",
        password: "",
      },
      loginInfo: {},
    };
  },

  methods: {
    warn() {
      const h = this.$createElement;
      this.$notify({
        title: "包教会在线教育提示",
        message: h("i", { style: "color: teal" }, "该功能敬请期待！"),
      });
    },

    submitLogin() {
      login.loginUser(this.user).then((Response) => {
        if (Response.data.success) {
          //登录成功信息提示
          this.$message({
            type: "success",
            message: " 登录成功！",
          });
          //把token存在cookie中,domain为cookie的作用范围
          cookie.set("token", Response.data.data.token, {
            domain: "ibaojiaohui.cn",
            // domain: "192.168.220.1",
          });
          //登录成功根据token获取用户信息
          login.findUserInfo().then((Response) => {
            this.loginInfo = Response.data.data.userInfo;
            //将用户信息记录cookie
            cookie.set("ucenter", JSON.stringify(this.loginInfo), {
              domain: "ibaojiaohui.cn",
              // domain: "192.168.220.1",
            });
            //跳转页面
            // window.open("/");
            window.location.href = "/";
          });
        } else {
          //登录失败信息提示
          this.$message({
            type: "error",
            message: Response.data.message,
          });
        }
      });
    },

    checkPhone(rule, value, callback) {
      //debugger
      if (!/^1[12345789]\d{9}$/.test(value)) {
        return callback(new Error("手机号码格式不正确"));
      }
      return callback();
    },
  },
};
</script>
 <style>
.el-form-item__error {
  z-index: 9999999;
}
</style>