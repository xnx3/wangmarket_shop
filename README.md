网市场云商城

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

## 登录
登录地址：
http://localhost:9090/shop/store/login/login.do
登录账号跟密码都是 store
登录进去的便是商家后台，商家后台可以管理自己的店铺。而我们平台是多商家的，可以有多个商家存在，每个商家都有一个自己的账号，管理自己的店铺