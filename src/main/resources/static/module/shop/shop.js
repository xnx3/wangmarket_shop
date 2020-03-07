var shop = {
	//当前已登陆的用户信息。如果未登录，则是null
	user:null,
	//session token
	token:null,
	//购物车信息，缓存
	cart:null,
	//请求host，域名，格式如 http://weixin.shop.leimingyun.com/ 最后以 / 结尾
	host:'http://api.shop.leimingyun.com/',
	//当前使用的店铺编号，store.id
	storeid:1,
	
	/**
	 * 获取当前已缓存的登录用户信息，获取到的值如 {id: 1, username: "admin", head: "default.png", …}
	 * 注意，这里能获取到数据，并不代表用户已经登录了！
	 */
	getUser:function(){
		if(this.user == null){
			var u = localStorage.getItem('user');
			if(u != null && u.length > 1){
				this.user = JSON.parse(u);
			}
		}
		return this.user;
	},
	/**
	 * 设置缓存用户信息
	 * u 缓存的用户信息，格式如 {id: 1, username: "admin", head: "default.png", …}
	 */
	setUser:function(u){
		if(u == null){
			this.user = null;
			localStorage.setItem('user',null);
		}else{
			this.user = u;
			localStorage.setItem('user',JSON.stringify(this.user));
		}
	},
	/**
	 * 获取token，也就是 session id。获取的字符串如 f26e7b71-90e2-4913-8eb4-b32a92e43c00
	 */
	getToken:function(){
		if(this.token == null){
			this.token = localStorage.getItem('token');
		}
		return this.token;
	},
	/**
	 * 设置token，也就是session id
	 * 格式如 f26e7b71-90e2-4913-8eb4-b32a92e43c00
	 */
	setToken:function(t){
		this.token = t;
		localStorage.setItem('token',this.token);
	},
	/**
	 * 获取购物车信息，获取到的格式如 {result: 1, info: "成功", goodsCartMap: {…}, number: 0, money: 0}
	 */
	getCart:function(){
		if(this.cart == null){
			var c = localStorage.getItem('cart');
			if(c != null && c.length > 1){
				this.cart = JSON.parse(c);
			}
		}
		if(this.cart == null){
			//如果还未空，那可能第一次用，还没缓存过，那么模拟一个空的返回
			this.cart = {
				result:1,
				info:'success',
				goodsCartMap:{},
				number:0,
				money:0
			};
		}
		return this.cart;
	},
	/**
	 * 设置购物车数据缓存
	 * 设置的购物车数据，格式如 {result: 1, info: "成功", goodsCartMap: {…}, number: 0, money: 0}
	 */
	setCart:function(c){
		this.cart = c;
		if(c == null){
			localStorage.setItem('cart',null);
		}else{
			localStorage.setItem('cart',JSON.stringify(this.cart));
		}
	},
	/**
	 * 当前是否是已登陆，如果已登陆，返回true，未登录，返回false
	 * 注意，此方法只是判断一下 getUser() 中是否有信息，有可能服务端session已过期的情况，只是证明这个用户之前是登录过的
	 */
	isLogin:function(){
		return !(this.getUser() == null);
	},
	
	api:{
		//登录、注册等
		login:{
			token:"shop/api/login/getToken.json",		//获取token，也就是获取sessionid
			captcha:"shop/api/login/captcha.jpg",	//验证码图片，在 img 中显示的验证码图片url
			login:"shop/api/login/login.json",	//通过用户名+密码进行登录
			reg:"shop/api/login/reg.json",		//通过用户名+密码进行注册
			logout:"shop/api/login/logout.json",		//退出登录
		},
		//用户相关
		user:{
			getUser:"shop/api/user/getUser.json",		//获取当前登录的用户信息
			updatePassword:"shop/api/user/updatePassword.json",		//修改密码，如果使用的是账号、密码方式注册、登录的话。
			updateNickname:"shop/api/user/updateNickname.json",		//修改用户昵称
		},
		//轮播图
		carouselImage:{
			list:"shop/api/carouselImage/list.json",	//首页banner轮播图
		},
		//商品
		goods:{
			list:"shop/api/goods/list.json",			//获取该店铺下的商品列表
			details:"shop/api/goods/detail.json",		//获取某个商品的信息
		},
		//商品分类
		goodsType:{
			list:"shop/api/goodsType/list.json",	//商品分类列表
		},
		//购物车
		cart:{
			getStoreCart:"shop/api/cart/getStoreCart.json",	//获取当前在此店铺的购车信息
			getGoodsCart:"shop/api/cart/getGoodsCart.json",	//获取某个商品在当前店铺的购物车信息
			change:"shop/api/cart/change.json",	//获取某个商品在当前店铺的购物车信息
			goodsCartSelected:"shop/api/cart/goodsCartSelected.json",	//设置某个商品在购物车中是否选中
		},
		//订单规则
		orderRule:{
			getRule:"shop/api/orderRule/getRule.json",	//获取当前店铺的订单规则
		},
		//订单
		order:{
			create:"shop/api/order/create.json",	//创建订单
			list:"shop/api/order/list.json",	//我的订单列表
			statistics:"shop/api/order/statistics.json",	//订单状态统计，统计当前登录用户在某个店铺下，各个状态的订单分别有多少
			detail:"shop/api/order/detail.json",	//订单详情
			refund:"shop/api/order/refund.json",	//申请退款
			cancelRefund:"shop/api/order/cancelRefund.json",	//取消退单申请
			receiveGoods:"shop/api/order/receiveGoods.json",	//收到商品，确认收货
			cancel:"shop/api/order/cancel.json",	//取消订单。当订单未支付时，可以取消订单。当然，估计很少有用户这样做
			getStateChangeLog:"shop/api/order/getStateChangeLog.json",	//查看订单状态的变动日志记录
		},
		//地址
		address:{
			getDefault:"shop/api/address/getDefault.json",	//获取当前用户的默认收货地址
			save:"shop/api/address/save.json",	//收货地址新增、修改
			list:"shop/api/address/list.json",	//获取当前用户的收货地址列表
			setDefault:"shop/api/address/setDefault.json",	//将当前用户的某个地址设为默认收货地址
			delete:"shop/api/address/delete.json",	//删除用户的某个地址
			getAddress:"shop/api/address/getAddress.json",	//获取用户的某个地址信息，以便进行编辑等
		},
		//支付
		pay:{
			getPaySet:"shop/api/pay/getPaySet.json",	//获取当前商铺的支付列表，列出哪个支付使用，哪个支付不使用
			privatePay:"shop/api/pay/privatePay.json",	//订单线下支付，私下支付
			alipay:"shop/api/pay/alipay.json",	//支付宝支付
		},
		//商家店铺
		store:{
			getStore:"shop/api/store/getStore.json",	//获取店铺信息
		}
	},
	order:{
		/**
		 * 订单状态，传入订单的状态 order.state ，返回状态文字描述，如 已付款、配送中
		 */
		state:function(state){
			switch (state) {
			case 'generate_but_no_pay':
				//已成功下单，但未支付
				return '待付款';
			case 'pay_timeout_cancel':
				//下单后超过指定时间未支付，系统自动将订单取消
				return '已取消';
			case 'my_cancel':
				//成功下订单，但未支付，用户自己主动点击取消订单按钮取消的
				return '已取消';
			case 'pay':
				//已经通过支付宝、微信支付等在线支付款项
				return '已支付';
			case 'private_pay':
				//成功下单后，点击的是线下支付，将订单转为线下支付状态。此状态跟 STATE_PAY 已付款 状态是并列的级别
				return '线下支付';
			case 'refund_ing':
				//退款中，用户点击申请退款，就会变成退款中的状态
				return '退款中';
			case 'refund_finish':
				return '已退款';
			case 'receive_goods':
				//已收到货物，已确定收货
				return '已收货';
			case 'distribution_ing':
				return '配送中';
			case 'finish':
				return '已完成';
			}
		}
	}
}