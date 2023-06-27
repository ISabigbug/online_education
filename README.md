## 项目说明

**包教会在线教育平台**是一个基于SpringBoot + SpringClound + Vue + Nuxt开发的**前后端分离项目**，采用**B2C 模式**。前台用户系统包含讲师、课程和文章等模块。后台管理系统包含权限管理、讲师管理、课程分类、课程管理、统计分析、幻灯片管理、文章管理、评论管理等模块。**功能持续更新中...**

## 开发环境

- IntelliJ IDEA 2022.2(可选)
- Visual Studio Code 2022(可选)
- MySQL 8.0
- Redis 6.0
- JDK 8
- Maven 3.8
- Node 16.18
- Nacos 1.1.4

## 开发技术

前端技术 : Vue + Nuxt + Element-UI + Nodejs + Axios + Echarts + ES6 + Babel

后端技术 : SpringBoot + SpringClound + MybatisPlus + SpringSecurity + Jwt + OAuth2 + Redis + Nginx + Maven + EasyExcel + OpenFeign

其它技术 : 阿里云OSS + 阿里云VOD + 阿里云 SMS + 微信支付和登录 + Git

## 前端启动注意事项

### 一、后台管理系统

**dev环境**

执行**npm install**后，需将**config**包下的**index.js**文件里面的assetsPublicPath: '**./**'都改为assetsPublicPath: '**/**'，然后执行npm run dev即可。账号 : admin 密码 : 123456

### 二、前台用户系统

**dev环境**

执行npm install后，需到**pages**下的**login.vue**文件和**layouts**下的**default.vue**文件中把**cookie**的作用域（**domain**）改为自己的主机号，然后执行npm run dev即可。账号 : 11111111111 密码 : 123456

![image](https://github.com/ISabigbug/online_education/assets/121659902/73a040e0-2225-45f3-ab02-85e736b7135e)

![image](https://github.com/ISabigbug/online_education/assets/121659902/14e1e8ac-dd23-4bc4-80d8-ede9354ec2af)

## 部署打包注意事项

**如果仅有一台服务器，则服务器最低要求为 : 2核4G。服务器环境如需快速安装，推荐可使用宝塔，可按照官网方法根据章节的系统进行安装https://www.bt.cn/new/download.html**

### 一、后台管理系统（前端）

**prod环境**

1、将**config**包下的**index.js**文件里面的assetsPublicPath: '**/**'都改为assetsPublicPath: '**./**'，否则控制台会报如下错误：

![image](https://github.com/ISabigbug/online_education/assets/121659902/9571d30a-1b16-4c1a-99d5-1e5ad2ac7b82)

2、将**config**包下的**prod.env.js**文件里面的**BASE_API**的IP地址改为自己服务器的主机号，然后终端执行**npm run build**生成dist文件即可。

![image](https://github.com/ISabigbug/online_education/assets/121659902/35a20e14-3f84-4770-b0cc-01535817737f)

### 二、用户前台系统（前端）

**prod环境**

1、将**utils**包下的**request.js**文件里面的**BASE_API**的IP地址改为自己服务器的主机号

![image](https://github.com/ISabigbug/online_education/assets/121659902/5479a2f2-2285-4d86-a557-b6339c4af87f)

2、将到**pages**下的**login.vue**文件和**layouts**下的**default.vue**文件中把**cookie**的作用域（**domain**）改为自己的主机号

![image](https://github.com/ISabigbug/online_education/assets/121659902/73a040e0-2225-45f3-ab02-85e736b7135e)
![image](https://github.com/ISabigbug/online_education/assets/121659902/14e1e8ac-dd23-4bc4-80d8-ede9354ec2af)

3、终端执行**npm run build**，**Nuxt**后续部署(服务器需下载pm2)可参考博客https://blog.csdn.net/qq_33323469/article/details/122080623?spm=1001.2014.3001.5506

### 三、微服务代码打包（后端）

1、填充完所有#########后先在总工程下执行**clean install**（顺序不可互调）

![image](https://github.com/ISabigbug/online_education/assets/121659902/408d236e-4f0a-4ee0-badd-fb15e9d8f0af)

2、然后在各个父工程（**common、infrastructure、service**）下执行**install package**（顺序不可互调），各个jar包即可打包成功

![image](https://github.com/ISabigbug/online_education/assets/121659902/46bee9a6-ce41-4af1-9f98-e8883027c446)

## 效果图

**首页参考自https://github.com/woziji2200/ProfilePage**

![image](https://github.com/ISabigbug/online_education/assets/121659902/116fbc0c-f724-493c-b0d5-ddd54bec1d43)

**以下为前台用户系统部分页面效果图**

![image](https://github.com/ISabigbug/online_education/assets/121659902/edc77daa-9f46-4dcf-907f-120b3e4ec50b)

![image](https://github.com/ISabigbug/online_education/assets/121659902/e8b4d164-e489-4a55-b500-d7abda2a722e)

**以下为后端管理系统部分页面效果图**

![image](https://github.com/ISabigbug/online_education/assets/121659902/0af812b6-7e11-4ca3-8bf5-909bcefdf5a8)

![image](https://github.com/ISabigbug/online_education/assets/121659902/fd681661-8880-4a7b-a5cf-8bf835fda148)
