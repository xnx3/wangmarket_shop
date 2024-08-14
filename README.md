## 简介

本产品是一款saas云商城系统，无需懂服务器、后端技能，在线开通商城账户、添加商品、快速上线发布，极大降低商城开发成本!  
一台1核2G服务器，可做过千独立商城

## 功能

#### 整体介绍
* 通过超级管理后台在线开通商城账户，开通后可直接登录自己商家管理后台使用，不需要任何服务器及后端知识
* 商家独立支付通道、独立短信通道、自定义自己商城订单规则、二级分销等
* 用户端、商家管理端全部开放api，可自由定制开发app、小程序、h5、以及做自己OEM定制的商家后台等等。

#### 商家管理后台功能

* **商城基本设置**  可设置商城的一些基本信息，除此之外，还有：   
  * **店铺状态**   商家可设置自己店铺是否开启，如设置为经营中、已打样。已打样情况下用户将无法从商城下单   
  * **商家电话**   可设置小程序商城-我的中，服务电话，用户可以通过服务电话一点直接给商家打电话沟通  
  * **在线客服**   可给商家开通一个坐席客服，当小程序商城中用户点咨询客服时，即可打开跟商家的咨询对话，商家给用户答疑解惑   
* **首页轮播图管理**  通过后台可自由设置小程序商城首页的顶部轮播图   
* **商品分类管理**  通过后台可自由设置小程序商城的商品分类   
* **商品管理**  可自由管理商城的商品。除最基本信息外，还有比如：   
  * **商品库存**  卖出一个就减一，库存为0后商品便不可再被买  
  * **告警数量**   库存量低于这个数，会通知商家告警，提醒商家该加库存了   
  * **产品上下架**   下架的商品将不会再商城中显示   
  * **已售数量造假**   店家可自由增加某个商品的已售数量，让用户看到某个商品销量很高，以促进用户下单   
  * **自定计量单位**   可设置商品的单位是个、斤、箱等   
* **订单管理**   可查看、管理自己商城所产生的订单   
  * **订单搜索**   根据订单号、订单状态、时间等，进行订单搜索，筛选   
  * **数据统计**  可自由统计指定时间段（从几月几号到几月几号）的订单数量、订单状态数、以及金额等   
  * **订单管理**  订单的基本管理，比如发货、确认收货、处理退款等   
  * **小票打印**  订单打印，可用于打印小票（57mm热敏打印机，打印类似于叫外卖的小票）   
  * **主动通知**  当订单有最新状态变换时，可以实时给指定商家的手机号发送一条短信提醒。比如：当用户下单支付成功后，主动发短信通知商家发货；当用户提交退单申请后，主动通知商家尽快处理   
  * **支付超时**   设置自己商城中，当订单创建后，超过多长时间未支付，就自动变为支付超时已取消。  
  * **自动确认收货**   设置自己商城中，当顾客多少天未确认收货，系统自动将这个订单变为已确认收货   
  * **退单设置**   商家可设置自己商城中，用户是否有退单功能，如果设置为允许，那么用户端会有退款按钮，用户可以点击退单按钮发起退单申请，有商家后台进行审核。如果设置为用户不可退单，那么用户端不会有退单按钮，用户是不可以发起退单申请的   
  * **配送中设置**   有的商家在商城上的精力比较少，每次去送还要将那个订单点开，点一下配送，对商家来说造成了负担。如果没有配送中这个状态，那么用户下单-付款后，是已支付，商家可以直接去送货，送完后可以将已支付的订单状态直接变为已完成。如果有配送中这个状态，用户支付完成后，商家去送货时还要找到用户订单，点击订单的配送按钮，将订单变为配送中，再去送货，送货完成后再点击完成。中间会多一步，去送货时要先将用户订单变为配送中的操作。   
* **自定义支付通道**  用户在商城中支付费用，直接支付到商家自己支付通道中，而并非支付到平台总账户。不压钱，不需要定期结算   
  * **支付宝支付**   配置自己独立的支付宝支付通道。  
  * **微信支付**   配置自己独立的微信支付通道。  
  * **线下支付**   用户线上下单后不用在线支付，而是私下通过线下转账付款。商家可通过后台设置决定是否启用线下支付方式。   
* **二级分销**   二级分销功能，用户A推荐B，B推荐C，用户C下单成交，用户A、B都有分佣奖励   
  * **一级分佣设置**   可设置是否开启一级分佣、以及一级分佣所分的商品金额的百分比 
  * **二级分佣设置**   可设置是否开启二级分佣、以及二级分佣所分的商品金额的百分比 
  * **用户提现设置**   可设置用户满多少金额才可以申请提现、以及用户申请提现后提示的几个工作日内到账提醒  
* **员工子账号**   店铺管理员可给自己员工开通子账号，可以给子账号指定拥有哪些权限。比如只允许管理商品、或者只允许管理订单等。  
* **商家多终端管理**   商家使用自己的账号密码登录自己的商家后台进行管理操作  
  * **PC浏览器**   商家可以使用Chrome浏览器登录自己的后台网址进行登录操作   
  * **PC客户端**   PC客户端软件，可以下载安装到自己电脑上。打开软件使用自己的商家账号密码登录进去，可查看订单列表、及订单信息。当有新订单时，会自动播放声音提醒有新订单   
  * **微信端**   商家可以通过微信来方便的简单管理自己的商品、订单   


## 效果
#### 微信小程序商城
![输入图片说明](https://video.zvo.cn/aiseo/wangmarket-shop/wangmarket_shop_weixinapplet_demo.mov)  

API接口开放,你可以任意用它来做H5商城、PC商城、小程序商城、APP商城。。。

#### 电脑网站商城
![输入图片说明](https://video.zvo.cn/aiseo/wangmarket-shop/wangmarket_shop_weixinapplet_demo.mov) 
预览体验网址：[http://shop-pc-demo.wang.market/index.html](http://shop-pc-demo.wang.market)

#### 商家管理后台-电脑网页端（全功能）
![输入图片说明](https://video.zvo.cn/wangmarket-shop/wangmarket_shop_pc_store_admin_demo.mp4) 

另外您可在线开通一个商城，更方便了解商家管理后台。开通url： [http://api.shop.zvo.cn/plugin/phoneCreateStore/reg.do](http://api.shop.zvo.cn/plugin/phoneCreateStore/reg.do)  

#### 商家管理后台-手机网页端（简单功能）
![](https://video.zvo.cn/wangmarket-shop/wangmarket_shop_mobile_store_admin_demo.mp4)
手机网页版功能很简单，就是基本的商品、订单、以及订单号搜索等操作。当然你也可以根据商城管理后台的开放API来丰富商家手机端的更多功能互动。

## 相关文档  
* [二次开发,本地运行、二次开发相关说明](https://gitee.com/leimingyun/dashboard/wikis/leimingyun/bed2ecca-8e2e-4b20-8099-10f09101b097/preview?doc_id=1532896&sort_id=4255124)
* [安装部署,将本项目部署到线上服务器](https://gitee.com/leimingyun/dashboard/wikis/leimingyun/bed2ecca-8e2e-4b20-8099-10f09101b097/preview?doc_id=1532896&sort_id=4255147)
* [后台账号,登录管理后台、商城的默认账号密码](https://gitee.com/leimingyun/dashboard/wikis/leimingyun/serverinstall/preview?sort_id=4110640&doc_id=1473420)
* [商家后台API,可以自由定制商家管理后台](https://gitee.com/leimingyun/dashboard/wikis/leimingyun/889cb0c9-be33-4a47-aec6-20cd27ea52be/preview?doc_id=1525567&sort_id=4298491)
* [用户商城API,可用来定做小程序商城、APP商城、PC端商城等](http://shop.wang.market)


## 开发语言及环境  
Java 1.8、Maven 3.5、Mysql 5.7  
如果想进行二次开发，可以参考此开发文档 http://wm.zvo.cn


## 开源项目

致力于开源基础化信息建设，如有需要，可直接拿去使用。这里列出了我部分开源项目：

| 项目| star数量 | 简介 |  
| --- | --- | --- |
|[wangmarket CMS](https://gitee.com/mail_osc/wangmarket) | ![](https://gitee.com/mail_osc/wangmarket/badge/star.svg?theme=white) | [私有部署自己的SAAS建站系统](https://gitee.com/mail_osc/wangmarket)  |
|[obs-datax-plugins](https://gitee.com/HuaweiCloudDeveloper/obs-datax-plugins) | ![](https://gitee.com/HuaweiCloudDeveloper/obs-datax-plugins/badge/star.svg?theme=white) | [Datax 的 华为云OBS 插件](https://gitee.com/HuaweiCloudDeveloper/obs-datax-plugins) |
| [templatespider](https://gitee.com/mail_osc/templatespider) | ![](https://gitee.com/mail_osc/templatespider/badge/star.svg?theme=white) | [扒网站工具，所见网站皆可为我所用](https://gitee.com/mail_osc/templatespider) |
|[FileUpload](https://gitee.com/mail_osc/FileUpload)| ![](https://gitee.com/mail_osc/FileUpload/badge/star.svg?theme=white ) | [文件上传，各种存储任意切换](https://gitee.com/mail_osc/FileUpload) |
| [cms client](https://gitee.com/HuaweiCloudDeveloper/huaweicloud-obs-website-wangmarket-cms) | ![](https://gitee.com/HuaweiCloudDeveloper/huaweicloud-obs-website-wangmarket-cms/badge/star.svg?theme=white) | [云服务深度结合无服务器建站](https://gitee.com/HuaweiCloudDeveloper/huaweicloud-obs-website-wangmarket-cms)  |
| [kefu.js](https://gitee.com/mail_osc/kefu.js) | ![](https://gitee.com/mail_osc/kefu.js/badge/star.svg?theme=white ) | https://gitee.com/mail_osc/kefu.js | [在线聊天的前端框架](https://gitee.com/mail_osc/kefu.js)  | 
| [msg.js](https://gitee.com/mail_osc) | ![](https://gitee.com/mail_osc/msg/badge/star.svg?theme=white ) | [轻量级js消息提醒组件](https://gitee.com/mail_osc)  | 
| [translate.js](https://gitee.com/mail_osc/translate) | ![](https://gitee.com/mail_osc/translate/badge/star.svg?theme=white )  | [三行js实现 html 全自动翻译](https://gitee.com/mail_osc/translate)  | 
| [WriteCode](https://gitee.com/mail_osc/writecode) | ![](https://gitee.com/mail_osc/writecode/badge/star.svg?theme=white ) | [代码生成器，自动写代码](https://gitee.com/mail_osc/writecode)  | 
| [log](https://gitee.com/mail_osc/log) | ![](https://gitee.com/mail_osc/log/badge/star.svg?theme=white ) | [Java日志存储及读取](https://gitee.com/mail_osc/log) | 
| [layui translate](https://gitee.com/mail_osc/translate_layui) |  ![](https://gitee.com/mail_osc/translate_layui/badge/star.svg?theme=white ) | [Layui的国际化支持组件](https://gitee.com/mail_osc/translate_layui) |
| [http.java](https://gitee.com/mail_osc/http.java) |  ![](https://gitee.com/mail_osc/http.java/badge/star.svg?theme=white ) | [Java8轻量级http请求类](https://gitee.com/mail_osc/http.java) |
| [xnx3](https://gitee.com/mail_osc/xnx3) |  ![](https://gitee.com/mail_osc/xnx3/badge/star.svg?theme=white ) | [Java版按键精灵，游戏辅助开发](https://gitee.com/mail_osc/xnx3) |  
| [websocket.js](https://gitee.com/mail_osc/websocket.js)  | ![](https://gitee.com/mail_osc/websocket.js/badge/star.svg?theme=white ) | [js的WebSocket框架封装](https://gitee.com/mail_osc/websocket.js) |
| [email.java](https://gitee.com/mail_osc/email.java) | ![](https://gitee.com/mail_osc/email.java/badge/star.svg?theme=white ) | [邮件发送](https://gitee.com/mail_osc/email.java) | 
| [notification.js](https://gitee.com/mail_osc/notification.js) | ![](https://gitee.com/mail_osc/notification.js/badge/star.svg?theme=white ) | [浏览器通知提醒工具类](https://gitee.com/mail_osc/notification.js) | 
| [pinyin.js](https://gitee.com/mail_osc/pinyin.js) | ![](https://gitee.com/mail_osc/pinyin.js/badge/star.svg?theme=white ) | [JS中文转拼音工具类](https://gitee.com/mail_osc/pinyin.js) |
| [xnx3_weixin](https://gitee.com/mail_osc/xnx3_weixin) | ![](https://gitee.com/mail_osc/xnx3_weixin/badge/star.svg?theme=white ) | [Java 微信常用工具类](https://gitee.com/mail_osc/xnx3_weixin) |
| [xunxian](https://gitee.com/mail_osc/xunxian) | ![](https://gitee.com/mail_osc/xunxian/badge/star.svg?theme=white ) | [QQ寻仙的游戏辅助软件](https://gitee.com/mail_osc/xunxian) | 
| [wangmarket_shop](https://gitee.com/leimingyun/wangmarket_shop) | ![](https://gitee.com/leimingyun/wangmarket_shop/badge/star.svg?theme=white ) | [私有化部署自己的 SAAS 商城](https://gitee.com/leimingyun/wangmarket_shop) |
| [wm](https://gitee.com/leimingyun/wm) | ![](https://gitee.com/leimingyun/wm/badge/star.svg?theme=white ) | [Java开发框架及规章约束](https://gitee.com/leimingyun/wm) |
| [yunkefu](https://gitee.com/leimingyun/yunkefu) | ![](https://gitee.com/leimingyun/yunkefu/badge/star.svg?theme=white ) | [私有化部署自己的SAAS客服系统](https://gitee.com/leimingyun/yunkefu) |
| [javadoc](https://gitee.com/leimingyun/javadoc) | ![](https://gitee.com/leimingyun/javadoc/badge/star.svg?theme=white) | [根据标准的 JavaDoc 生成接口文档 ](https://gitee.com/leimingyun/javadoc) |
| [elasticsearch util](https://gitee.com/leimingyun/elasticsearch) | ![](https://gitee.com/leimingyun/elasticsearch/badge/star.svg?theme=white ) | [用sql方式使用Elasticsearch](https://gitee.com/leimingyun/elasticsearch) |
| [AutoPublish](https://gitee.com/leimingyun/sftp-ssh-autopublish) | ![](https://gitee.com/leimingyun/sftp-ssh-autopublish/badge/star.svg?theme=white ) | [Java应用全自动部署及更新](https://gitee.com/leimingyun/sftp-ssh-autopublish) |
| [aichat](https://gitee.com/leimingyun/aichat) | ![](https://gitee.com/leimingyun/aichat/badge/star.svg?theme=white ) | [智能聊天机器人](https://gitee.com/leimingyun/aichat) | 
| [yunbackups](https://gitee.com/leimingyun/yunbackups) | ![](https://gitee.com/leimingyun/yunbackups/badge/star.svg?theme=white ) | [自动备份文件到云存储及FTP等](https://gitee.com/leimingyun/yunbackups) |
| [chatbot](https://gitee.com/leimingyun/chatbot) | ![](https://gitee.com/leimingyun/chatbot/badge/star.svg?theme=white) | [智能客服机器人](https://gitee.com/leimingyun/chatbot) |
| [java print](https://gitee.com/leimingyun/printJframe) | ![](https://gitee.com/leimingyun/printJframe/badge/star.svg?theme=white ) | [Java打印及预览的工具类](https://gitee.com/leimingyun/printJframe) |
…………

