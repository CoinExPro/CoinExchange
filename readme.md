## 项目简介
&emsp;&emsp;`一个专为数字货币爱好者打造的简洁而美观的交易平台。在这里，我们将为您呈现一个全新的数字货币交易体验，让您轻松掌握市场动态，把握投资机会。本git地址为交易所的开源代码地址。项目包括PC端和APP端以及后台管理端都有源码提供。欢迎使用!`

## 在线体验地址

前台：http://45.32.156.166:1888/

后台：http://45.32.156.166:1889/

## 整体架构

## 微服务模块介绍

## 核心业务介绍

| 数字货币交易     | 币币交易：允许用户直接用一种数字货币（如 ETH）购买另一种数字货币（如 BTC），并通过这种方式进行交易。 |
| ---------------- | ------------------------------------------------------------ |
| 法币交易         | 允许用户使用法定货币（如 USD，CNY）直接购买或出售数字货币。  |
| 杠杆交易         | 杠杆交易允许用户进行保证金交易，通过借用资金来增大交易头寸，从而可能获得更高的收益，同时也面临更高的风险 |
| 期货合约交易     | 币交易所提供标的物为数字货币的期货合约交易服务，用户可以通过购买和出售期货合约来对冲风险和获取利润。 |
| DeFi 服务        | 交易所提供的 Decentralized Finance（去中心化金融）服务包括流动性挖矿，借贷服务，预测市场等。 |
| 存取款服务       | 提供用户将数字货币从个人钱包中存入交易所，并且支持将数字货币从交易所提款至个人钱包或其他交易所。 |
| 信息服务         | 提供包括公告、帮助中心、新手教学等在内的信息服务，并实时呈现全球数字货币投资者感兴趣的深度、实时、专业的全方位资讯。 |
| Customer Support | 提供24/7的在线客户支持，解答用户对于交易，存提款，合约等问题的疑问。 |

## 技术特色

- 前后端分离，基于Token的Api授权机制！
- 交易所设计者提供技术支持，部署二开无忧~
- MySQL、MongoDB、Redis多种数据存储方式，只为更快！
- rabbitmq发布订阅消息队列，让订单更快流转！
- 主流币种对接区块链接口齐全，开箱即用！
- 同步行情，维护深度，防止搬砖！
- 集齐APP应用端、PC应用端、以及后台管理端，三端齐下有完整的系统组成部分！
- 基于SpringCloud微服务架构，扩展更容易，搭建和二次开发数字货币交易所！

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
        <td><img src="/images/ct1.jpeg"/></td>
    </tr>
</table>

<table>
    <tr>
        <td><img src="/images/ct2.jpeg"/></td>
    </tr>
</table>

### 2.4现货交易(白天黑夜)

 <div align=left>
    <td ><img height="" width="" src="/images/pc6.png"/></td>
    <td ><img height="" width="" src="/images/pc5.png"/></td>
 </div>

### 2.5U本位永续合约(白天黑夜)

 <div align=left>
    <td ><img height="" width="" src="/images/pc7.png"/></td>
     <td ><img height="" width="" src="/images/pc8.png"/></td>
 </div>

### 2.6成为商家(白天黑夜)

 <div align=left>
    <td ><img height="" width="" src="/images/pc9.png"/></td>
         <td ><img height="" width="" src="/images/pc10.png"/></td>
 </div>

### 2.7账户资产

 <div align=left>
    <td ><img height="" width="" src="/images/pc8.jpg"/></td>
 </div>

### 2.8币币交易

 <div align=left>
    <td ><img height="" width="" src="/images/pc9.jpg"/></td>
 </div>

### 2.9新手指南

 <div align=left>
    <td ><img height="" width="" src="/images/pc10.jpg"/></td>
 </div>

## 3.CoinExPro管理端界面展示

### 3.1首页

 <div align=left>
    <td ><img height="" width="" src="/images/gl1.jpg"/></td>
 </div>

### 3.2用户列表

 <div align=left>
    <td ><img height="" width="" src="/images/gl2.jpg"/></td>
 </div>

### 3.3币种管理

 <div align=left>
    <td ><img height="" width="" src="/images/gl4.jpg"/></td>
 </div>

### 3.4财务管理-系统提现记录

 <div align=left>
    <td ><img height="" width="" src="/images/gl5.jpg"/></td>
 </div>

### 3.5财务管理-运营总览

 <div align=left>
    <td ><img height="" width="" src="/images/gl6.jpg"/></td>
 </div>



## 4.低代码开发平台

**使用本系统的朋友们，在这里需要重点提及一下，在后台里的研发模块CoinExPro提供的一个低代码开发平台，旨在简化表单开发、表单设计，开发人员可专注于业务逻辑实现、提高开发效率。**

### 4.1 online在线开发

**表单开发，其作用为在线生成表单，项目中所有涉及到的业务表的新增或修改需要在此菜单中统一进行**。并且代码中有提供专门的工具类操作数据库，参考以下步骤熟悉后可自由进行开发。

<table>
    <tr>
        <td ><img src="/images/kf1.jpg"/></td>
    </tr>
    <tr>
        <td ><img src="/images/kf2.jpg"/></td>
    </tr>
    <tr>
        <td ><img src="/images/kf3.jpg"/></td>
    </tr>
    <tr>
        <td ><img src="/images/kf4.jpg"/></td>
</table>

### 4.2 App在线开发

研发模块中的表单设计：拖曳式快速生成App。

示例图：

<table>
    <tr>
        <td ><img src="/images/kf5.jpg"/></td>
    </tr>
</table>

## 5.开源项目完整运行步骤

### 5.1开源项目后端代码运行步骤

#### 5.1.1环境配置

**运行环境**：JDK1.8

**开发工具：** Idea、Maven

**默认已启动中间件(点击可跳转页面)：**[Nacos2.0.0++](https://nacos.io/)、[Mysql8.0](http://mysql.p2hp.com/)、[Redis](https://redis.io/)、[Minio](http://www.minio.org.cn/)、[MongoDB]([MongoDB Community Downloads | MongoDB](https://www.mongodb.com/download-center/community/releases))

如果觉得以上麻烦，推荐使用phpStudy简化环境配置：[phpstudy](https://www.xp.cn/)

#### 5.1.2后端代码运行

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

### 5.2开源项目前端代码运行步骤

#### 5.2.1项目前端App代码运行

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



#### 5.2.2项目前端后台代码运行

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

#### 5.2.3项目前端交易所pc界面

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

## 加入我们