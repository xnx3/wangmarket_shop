网市场云商城
以网市场云建站系统为基础开发而来
      
Java 1.8
Mysql 5.7
（Maven 3.5）

## 数据库
mysql 数据库所在路径：  else/wangmarket_shop.sql

## 包说明
1. com.xnx3.wangmarket.shop.core 这个包是核心包
1. com.xnx3.wangmarket.shop.api 对外开放接口的包，也就是用户端的包,依赖 com.xnx3.wangmarket.code 这个包
1. com.xnx3.wangmarket.shop.store 这个包是商家管理后台的，依赖 com.xnx3.wangmarket.code 这个包。 

## controller 及 url方面
#### 用户端url统一为 /shop/api/ 开头，接口后缀以.json 结尾 如:
商品相关是 /shop/api/goods/
购物车相关是 /shop/api/cart/
获取店铺首页的轮播图列表为 /shop/api/carouselImage/list.json

#### 商家管理后台url统一为 /shop/store/ 开头，这个后缀还是跟之前一样.do结尾 如：
商品管理是 /shop/storeadmin/goods/
轮播图管理是 /shop/storeadmin/carouselImage/


 ## 安装部署
 https://gitee.com/leimingyun/dashboard/wikis/leimingyun/serverinstall/preview?sort_id=4110638&doc_id=1473420
 
 ## 后台登录
 https://gitee.com/leimingyun/dashboard/wikis/leimingyun/serverinstall/preview?sort_id=4110640&doc_id=1473420