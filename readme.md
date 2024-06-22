CoinExchange 数字货币交易所
===============
[![](https://img.shields.io/badge/数字货币-交易所-orange.svg)](http://coin-pc.mj.ink/)
[![](https://img.shields.io/badge/BTC-交易所-blue.svg)](http://coin-pc.mj.ink/)
[![](https://img.shields.io/badge/ETH-交易所-brightgreen.svg)](http://coin-pc.mj.ink/)
[![](https://img.shields.io/badge/SpringCloud-2.2.9.RELEASE-orange.svg)](http://coin-pc.mj.ink/)
[![](https://img.shields.io/badge/MySql-8.0-blue.svg)](http://coin-pc.mj.ink/)
[![GitHub stars](https://img.shields.io/github/stars/CoinExPro/CoinExchange.svg?style=social&label=Stars)](https://github.com/CoinExPro/CoinExchange)

## 项目简介
&emsp;&emsp;**开源代码仅供参考学习，请勿用于非法用途** <br>

&emsp;&emsp;`一个专为数字货币爱好者打造的简洁而美观的交易平台。在这里，我们将为您呈现一个全新的数字货币交易体验，让您轻松掌握市场动态，把握投资机会。本git地址为交易所的开源代码地址。项目包括PC端和APP端以及后台管理端都有源码提供。欢迎使用!`

## 在线体验地址
```
客户端：
     PC访问地址：http://coin-pc.mj.ink   手机访问地址：http://coin-h5.mj.ink
     账号：coinexpro@gmail.com  
     密码：123456qq
     验证码：123456
管理端：
     访问地址：http://coin-ht.mj.ink 
     账号：test  
     密码：123456
```

## 客服电报号


客服Telegram:<a data-v-e7c4da1a="" href="https://t.me/CoinExPro1688" target="_blank" style="color: rgb(0, 0, 255);">@CoinExPro1688</a>（点击@跳转 唯一渠道，谨防被骗）

## 整体架构

## 微服务模块介绍

## 客户端-核心功能

| 模块     | 功能 |
| ---------------- | ------------------------------------------------------------ |
| 现货交易     | 限价委托、市价委托、止盈止损、当前委托、历史委托、划转等 |
| 杠杆交易     | 限价委托、市价委托、逐仓、全仓、借币、还币、风险率、借币记录、划转等 |
| 合约交易     | 开多、开空、逐仓、全仓、分仓、并仓、仓位、反向开仓、闪电平仓、追加保证金等 |
| OTC买币     | 自选买币、快捷买币等 |
| 行情来源 | 币安交易所、撮合机器人等 |
| 市值管理 | 创建市值维护交易对、参与维护、自动续期、历史维护记录等 |
| 理财管理 | 创建存币计划、参与存币，到期发放利息、单笔额度限制等 |
| 商家管理 | 商家管理、商家审核、缴纳保证金设置、发布广告等 |
| 安全设置 | 资金密码、谷歌验证器、防钓鱼码等 |
| 个人中心 | 账户信息，设备管理、登录记录、身份认证等 |
| 委托管理 | 币币交易、杠杆交易、U本位永续合约、币本位永续合约等 |
## 管理端-核心功能

| 模块     | 功能                                                         |
| -------- | ------------------------------------------------------------ |
| 币种管理 | 添加币种，修改币种等                                         |
| 用户管理 | 用户列表，用户钱包、身份认证列表、待审核认证等               |
| 推广管理 | 直推列表、直推关系、返佣列表等                               |
| 法币管理 | 保证金设置、商家列表、商家待审核列表、交易记录、申诉列表等   |
| 现货管理 | 交易对管理、交易明细、控盘机器人等                           |
| 合约管理 | 交易对管理、仓位明细、交易明细、持仓明细、限价参数，强平，强爆等 |
| 杠杆管理 | 交易记录，借币记录，还款记录、利息记录等                     |
| 商家管理 | 商家管理、商家审核、缴纳保证金设置、发布广告等               |
| 理财管理 | 存币记录，存储订单等                                         |
| 市值管理 | 市值列表、维护中的订单列表、历史维护记录，维护周期等         |
| 财务管理 | 充币记录，提币记录，提币申请，链路配置等                     |

## 技术架构

```
coin-cloud
    ├── coin-auth  # 鉴权模块
    ├── coin-gateway #网关模块 
    ├── coin-ops  
    ├    ├── coin-log # 系统日志
    ├    ├── coin-xxljob #任务调度模块
    ├    ├── coin-xxljob-admin #任务调度界面
    ├── coin-plugin 
    ├    ├── coin-bot # 撮合机器人
    ├    ├── coin-client # 客户端模块，同步币安行情数据
    ├    ├── coin-market # 市场模块
    ├    ├── coin-match # 自定义撮合模块
    ├    ├── coin-message # 消息模块
    ├    ├── coin-mng # 后台管理模块
    ├    ├── coin-udun # U盾钱包模块
    ├    ├── coin-web # pc客户端模块
    ├── coin-service 
    ├    ├── coin-system # 系统模块
    ├    ├── coin-user # 系统用户模块
```

## 1.App前台

#### 1.1app前台介绍

**界面设计：简洁而优雅。** 我们的APP界面设计简洁优雅，以白色和灰色为主色调，营造出一种清新、舒适的氛围。布局合理，功能分区明确，让您可以轻松找到所需功能。同时，我们注重细节处理，图标、按钮等元素设计精美，视觉效果极佳。

**用户体验：简单易用。** 虚拟币交易APP致力于提供简单易用的用户体验。我们采用了先进的交易算法和智能合约技术，确保交易的准确性和高效性。您只需注册账号、绑定银行卡或微信支付等手段，即可轻松进行买卖操作。同时，我们提供详尽的用户指南和帮助文档，以应对各种可能遇到的问题。

**安全保障：多重防护。** 安全是我们APP的首要考量因素。我们采用多重安全防护措施，确保您的虚拟货币交易安全无忧。包括但不限于SSL加密、登录验证、交易记录备份等。

**特色功能：丰富多样。** 实时行情：提供全球虚拟货币实时行情，让您随时了解市场动态。资产托管：支持多种资产托管方式，满足您的不同需求。交易对选择：提供丰富的交易对选择，满足不同投资者的偏好。

**语言切换：** 支持中、英语言以及简体繁体等语言切换

#### 1.2app前台展示

<table>
    <tr>
        <td><img src="/images/app1.jpg"/></td>
       	<td><img src="/images/app2.jpg"/></td>
        <td><img src="/images/app3.jpg"/></td>
        <td><img src="/images/app4.jpg"/></td>
    </tr>
</table>

<table>
    <tr>
        <td><img src="/images/app6.jpg"/></td>
       	<td><img src="/images/app7.jpg"/></td>
        <td><img src="/images/app8.jpg"/></td>
        <td><img src="/images/app9.jpg"/></td>
    </tr>
</table>

本项目虚拟币交易APP，带给用户全新的数字货币交易体验。简洁美观的界面设计、简单易用的操作方式、多重安全保障以及丰富的特色功能，为用户带来前所未有的交易之旅。赶快加入我们，一起探索数字货币的世界吧！

## 2.PC端界面展示

### 2.1首页

白天黑夜双模式：首页、新闻推送、市场行情、CoinExPro学院等

<table>
    <tr>
        <td><img src="/images/index-bt.png"/></td>
    </tr>
</table>

<table>
    <tr>
        <td><img src="/images/index-hy.png"/></td>
    </tr>
</table>


### 2.2行情

 <div align=left>
    <td ><img height="" width="" src="/images/index-hq.png"/></td>
 </div>


### 2.3 K线详情

 <div align=left>
    <td ><img height="" width="" src="/images/index-xq.png"/></td>
 </div>


## 3.开源项目完整运行步骤

### 3.1开源项目后端代码运行步骤

#### 3.1.1环境配置

**运行环境**：JDK1.8

**开发工具：** Idea、Maven

**默认已启动中间件(点击可跳转页面)：**[Nacos2.0.0++](https://nacos.io/)、[Mysql8.0](http://mysql.p2hp.com/)、[Redis](https://redis.io/)、[Minio](http://www.minio.org.cn/)、[MongoDB]([MongoDB Community Downloads | MongoDB](https://www.mongodb.com/download-center/community/releases))

如果觉得以上麻烦，推荐使用phpStudy简化环境配置：[phpstudy](https://www.xp.cn/)

#### 3.1.2后端代码运行

通过git拉取代码到本地后，项目结构如图：

<table>
    <tr>
        <td ><img src="/images/hd1.jpg"/></td>
    </tr>
</table>

在项目正式运行之前，我们需要再检查一遍本项目的正确maven路径,并且成功编译。

我们还需要配置nacos文件，**打开nacos,新增CoinExPro命名空间，并且空间id为CoinExPro,而后分别新增配置blade.yaml、blade-dev.yaml、blade-xxljob-admin-dev.yaml文件，关于数据库以及各中间件的配置地址请在个人搭建好后进入nacos配置文件**中自行配置，详情请查看下方示例图。

如图：(在这里关于部分中间件的地址于yaml文件中已有示例，具体还需与个人本地的地址相对应进行配置)

<table>
    Step1:新增命名空间
    <tr>
        <td ><img src="/images/hd2.jpg"/></td>
    </tr>
</table>

<table>
       Step2:导入配置文件
    <tr>
        <td ><img src="/images/hd3.jpg"/></td>
    </tr>
</table>

<table>
       Step3:配置mongodb、rabbitmq、redis地址
    <tr>
        <td ><img src="/images/hd4.jpg"/></td>
    </tr>
</table>

<table>
       Step4:配置数据库Mysql地址
    <tr>
        <td ><img src="/images/hd5.jpg"/></td>
    </tr>
</table>

**关于blade.yaml文件、blade-dev.yaml文件以及相关工具包请阅读本项目中的doc文件夹。**

在配置完成后，根据下图提示配置本地项目中nacos地址，或配置nacos的命名空间地址,使项目加入nacos管理，如图：

<table>
    <tr>
        <td ><img src="/images/hd6.jpg"/></td>
    </tr>
        <tr>
        <td ><img src="/images/hd7.jpg"/></td>
    </tr>
</table>

配置好nacos启动以下服务，nacos正常访问，如图：

<table>
    <tr>
        <td ><img src="/images/hd8.jpg"/></td>
    </tr>
</table>

至此，后端代码运行成功！

### 3.2开源项目前端代码运行步骤

#### 3.2.1项目前端App代码运行

**前台运行环境：** 无需配置node.js

**开发工具：** HBuilder X

官方下载对应软件HBuilder X，安装步骤非常简单，如图:

官方地址：[Windows - HBuilderX 文档 (dcloud.net.cn)](https://hx.dcloud.net.cn/Tutorial/install/windows)

<table>
    <tr>
        <td ><img src="/images/qd2.png"/></td>
    </tr>
</table>

通过提供的git地址拉取代码到本地后，将下载好的项目文件夹拖曳到打开的HBuilder X下，项目结构如图：

<table>
    <div >
    <td ><img src="/images/qd1.jpg" height="85%"/></td>
</div>
</table>

其中，需要注意网关地址是否与nacos中对应网关服务显示地址一致，如图：

 <table>
     <div >
    <td ><img src="/images/qd3.jpg" width="85%"/></td>
 </div>
</table>

运行项目，选择使用chorm运行项目，如图：

<table>
    <tr>
        <td ><img src="/images/qd4.png"/></td>
    </tr>
</table>



#### 3.2.2项目前端后台代码运行

**后台运行环境：** node12.13.0

**开发工具：** vscode

通过git拉取代码到本地，在配置好node.js环境的前提下，使用vs打开文件夹，运行命令npm i, npm run serve启动项目，如图：

<table>
    Step1:管理员身份打开vs,进入项目文件夹配置网关
    <tr>
        <td ><img src="/images/qd6.jpg"/></td>
    </tr>
</table>

<table>
    Step2：管理员身份打开vs,新建终端分别运行npm i 和 npm run serve命令，运行成功如下
    <tr>
        <td ><img src="/images/qd7.jpg"/></td>
    </tr>
</table>

账号admin,密码admin。运行成功：

<table>
    <tr>
        <td ><img src="/images/pd5.png"/></td>
    </tr>
</table>

#### 3.2.3项目前端交易所pc界面

**后台运行环境：** node12.13.0

**开发工具：** vscode

重复以上步骤分别运行npm i 以及npm run serve

<table>
    <tr>
        <td ><img src="/images/qd8.jpg"/></td>
    </tr>
</table>

运行成功：

<table>
    <tr>
        <td ><img src="/images/qd9.jpg"/></td>
    </tr>
</table>
