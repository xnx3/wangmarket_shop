## 简介

本产品是一款saas云商城系统，无需懂服务器、后端技能，在线开通商城账户、添加商品、快速上线发布，极大降低商城开发成本!  
一台1核2G服务器，可做过千独立商城

## 功能介绍

1. 通过超级管理后台在线开通商城账户，开通后可直接登录就能使用，不需要任何服务器及后端知识
2. 商家独立支付通道，填写自己的微信、支付宝支付参数，付款金额无需经过平台流转，支付成功直接进入商家自己的公户。
3. 商家独立短信通道，可根据自己商城的名字，定制自己独特的短信签名。可以将签名设置为跟自己店铺名字一样。
4. 商家自定义订单规则。店铺中，下订单后，多长时间未支付自动取消、用户是否可退款、订单是否有配送中的状态等，店铺管理员都可以自定义自己店铺的订单规则。每个店铺都可制定自己店铺的订单规则，与其他店铺互不影响。
5. 二级分销插件，店家可自由设置一级下线、二级下线的分佣比例、最低提现金额、提现结算周期等。
6. 独立商品、商品分类体系，可自由管理自己店铺的商品分类、以及商品。
7. 独立订单体系，可自由管理自己店铺中所产生的订单，以及对订单进行某些操作等。
8. 无限扩展！用户端、商家管理端全部开放api，可自由定制开发app、小程序、h5、以及做自己OEM定制的商家后台等等。

## 在线快速体验
#### 1. 在线开通一个商城  
开通url： http://api.imall.net.cn/plugin/phoneCreateStore/reg.do  
#### 2. 体验店铺管理后台  
记得添加个分类、添加个商品，试试功能，同时也能测试时看到效果  
#### 3. 体验效果  
这里我们开放了一个方便体验的h5页面（[这个H5页面也是开源的](https://gitee.com/leimingyun/dashboard/wikis/leimingyun/wangmarket_site_learn/preview?doc_id=1258300&sort_id=3912490)），访问url：   
http://demo.imall.net.cn/index.html?storeid=1&host=https://api.imall.net.cn/  
其中:  
storeid： 便是你开通的店铺的id，刚开通店铺后跳转的页面里就有。  
host： 商城的url，如果你是自己跑在本地，这里可以传入 http://localhost:8080/ ，如果你部署在服务器，可以传入 http://服务器ip/ ，格式一定是 http:// 或者 https:// 开头、  / 结尾  
  
这里只是给出了一个H5的一个演示，因为全部接口开放,你可以任意用它来做H5商城、PC商城、小程序商城、APP商城。。。


## 相关文档  
|   | 链接地址  | 说明 |
|---|---|---|
| 二次开发  | [点此查看文档](https://gitee.com/leimingyun/dashboard/wikis/leimingyun/bed2ecca-8e2e-4b20-8099-10f09101b097/preview?doc_id=1532896&sort_id=4255124)  | 本地运行、二次开发相关说明 |
| 安装部署  | [点此查看文档](https://gitee.com/leimingyun/dashboard/wikis/leimingyun/bed2ecca-8e2e-4b20-8099-10f09101b097/preview?doc_id=1532896&sort_id=4255147)  | 将本项目部署到线上服务器 |
| 后台账号  | [点此查看文档](https://gitee.com/leimingyun/dashboard/wikis/leimingyun/serverinstall/preview?sort_id=4110640&doc_id=1473420)  | 登录管理后台、商城的默认账号密码 |
| 商家后台API  | [点此查看文档](https://gitee.com/leimingyun/dashboard/wikis/leimingyun/889cb0c9-be33-4a47-aec6-20cd27ea52be/preview?doc_id=1525567&sort_id=4241705)  | 商家管理后台的API开放文档，可以自由定制管理后台 |
| 用户商城API  | [点此查看文档](http://shop.wang.market)  | 用户在使用商城的API功能，可以用来定做小程序商城、APP商城等 |


## 开发语言及环境  
Java 1.8、Maven 3.5、Mysql 5.7


## 商家管理后台效果图  
![商家管理后台](https://images.gitee.com/uploads/images/2021/0816/101520_63daccd0_429922.png)

