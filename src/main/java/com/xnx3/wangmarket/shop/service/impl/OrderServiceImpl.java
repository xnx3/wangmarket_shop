package com.xnx3.wangmarket.shop.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.xnx3.DateUtil;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.wangmarket.shop.entity.Address;
import com.xnx3.wangmarket.shop.entity.Order;
import com.xnx3.wangmarket.shop.entity.Store;
import com.xnx3.wangmarket.shop.service.OrderService;
import com.xnx3.wangmarket.shop.util.SessionUtil;
import com.xnx3.wangmarket.shop.vo.OrderVO;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
	@Resource
	private SqlService sqlService;

	@Override
	public OrderVO createOrder(Address address, Order o) {
		Order createOrder = new Order();
		createOrder.setAddtime(DateUtil.timeForUnix10());
		createOrder.setNo(o.getNo());
		createOrder.setPayMoney(o.getPayMoney());
		createOrder.setRemark(o.getRemark());
		createOrder.setState(Order.STATE_CREATE_BUT_NO_PAY);
		createOrder.setStoreid(0);
		createOrder.setTotalMoney(o.getTotalMoney());
		createOrder.setUserid(SessionUtil.getUserId());
		createOrder.setVersion(0);
		
		
		
		OrderVO orderVO = new OrderVO();
		Order order = new Order();
		
		if(address == null || address.getId() - 0 == 0){
			orderVO.setBaseVO(OrderVO.FAILURE, "收货地址不存在");
			return orderVO;
		}
		//判断手机号是否符合要求
		if(address.getPhone() == null || address.getPhone().equals("")){
			orderVO.setBaseVO(OrderVO.FAILURE, "请填写您的手机号，不然我们怎么联系您呢？");
			return orderVO; 
		}
		/**** 其他的详细地址了、这些后面可以再加入 ****/
		
//		//获取要创建的此订单对应的商家店铺的信息
//		Store store = sqlService.findById(Store.class, storeid);
//		if(store == null){
//			orderVO.setBaseVO(OrderVO.FAILURE, "店铺不存在，订单创建失败");
//			return orderVO; 
//		}
//		//判断当前店铺是否是营业中
//		BaseVO buVO = storeCurrentBusiness(store);
//		if(buVO.getResult() - BaseVO.SUCCESS != 0){
//			orderVO.setBaseVO(OrderVO.FAILURE, buVO.getInfo());
//			return orderVO;
//		}
//		
//		//判断自己的收货距离，是否在店铺的可配送范围之内
//		/**
//		 * 为方便调试，后续加入
//		 */
//		
//		//获取购物车所有信息
//		ShopCartVO shopCartVO = getCurrentShopCart(request,storeid);
//		//此店铺的购物车信息
//		ShopCart shopCart = shopCartVO.getStoreCartMap().get(storeid);
//		if(shopCart == null){
//			orderVO.setBaseVO(OrderVO.FAILURE, "购物车中无此店铺相关的记录");
//			return orderVO;
//		}
//		Map<Integer, CartProduct> cartProductMap = shopCart.getProductMap();
//		if(cartProductMap.size() == 0){
//			orderVO.setBaseVO(OrderVO.FAILURE, "购物车中无此店铺的商品记录");
//			return orderVO;
//		}
//		
//		//进行最新的商品验证，验证其库存是否还有，验证其价格是否有了变动
//		for (Entry<Integer, CartProduct> entry : cartProductMap.entrySet()) {
//			CartProduct cartProduct = entry.getValue();
//			Product cartP = cartProduct.getProduct();	//购物车的商品
//			Product product =  (Product) sqlService.findById(Product.class, cartP.getId());	//当前数据库的商品
//			//进行商品库存的判定
//			if(product.getRepertory() - cartProduct.getNumber() < 0){
//				orderVO.setBaseVO(OrderVO.FAILURE, cartP.getTitle()+"当前库存剩余"+product.getRepertory()+"件，您想购买"+cartProduct.getNumber()+"件，无法购买！");
//				return orderVO;
//			}
//			//进行商品单价的判断，判断单价是否有了修改，flaot会有误差，其在0.1元以内便认为无误差了
//			if(Math.abs(product.getPrice() - cartP.getPrice()) > 0.1){
//				//这里可以清空这个店铺的购物车，待调试完毕没问题后，可考虑增加
//				orderVO.setBaseVO(OrderVO.FAILURE, cartP.getTitle()+"单价有变动，购买失败！请重新下单");
//				return orderVO;
//			}
//		}
//		
//		/**
//		 * 配送费的计算
//		 */
//		if(DeliveryType.TONGCHENGHUZHU.getValue() - store.getDeliveryType() == 0){
//			
//			/*
//			 * 
//			 * 同城邦进行配送，进行api对接
//			 * 
//			 */
//			order.setDeliveryMoney(0);
//			
//		}else{
//			//如果不使用同城互助进行配送，则便是使用商家自己配送，或者物流配送，这两种配送会有配送限制，比如，低于30元免配送费，超过30元会有多少的配送费
//			if(shopCart.getAllMoney() - store.getDeliveryScopeOutAvoid() < 0){
//				//下单价格小于起送价，那么有配送费
//				order.setDeliveryMoney(store.getDeliveryPrice());
//			}else{
//				//下单价格超出，免除配送费
//				order.setDeliveryMoney(0);
//			}
//		}
//		order.setDeliveryType(store.getDeliveryType()); 	//配送方式使用店铺所设置的方式
//		
//		
//		/*
//		 * 创建订单
//		 */
//		//赋予订单收货人信息
//		order.setBuyRemark(buyRemark);
//		order.setAddress(address.getAddress());
//		order.setCommunityName(address.getCommunityName());
//		order.setLatitude(address.getLatitude());
//		order.setLongitude(address.getLongitude());
//		order.setPhone(address.getPhone());
//		order.setSex(address.getSex());
//		order.setUsername(address.getUsername());
//		
//		
//		//初始化订单信息
//		order.setAddtime(DateUtil.timeForUnix10());
//		order.setUserid(ShiroFunc.getUserId());
//		order.setPayMoney(0f); 	//已支付金额，只是下单，还未支付过。   其值 ＝ 订单的总金额totalMoney - 优惠金额preferentialMoney
//		order.setPayTime(0); 		//支付时间
//		order.setState(Order.STATE_ONLY_CREATE);
//		order.setStoreid(storeid);
//		order.setTotalMoney(Lang.floatRound(shopCart.getAllMoney()+order.getDeliveryMoney(), 2));		//订单的总金额（未优惠的金额，不算优惠的），其值 ＝ 商品总金额productMoney + 运费deliveryMoney 
//		order.setPreferentialMoney(0f); 	//此订单的优惠金额，预留字段,尚未用到
//		order.setProductMoney(shopCart.getAllMoney()); 		//商品的总金额，未优惠前的总金额，单纯商品，不含运费
//		
//		//进行订单应付金额的校验
//		if(order.getTotalMoney() - payMoney != 0){
//			orderVO.setBaseVO(OrderVO.FAILURE, "订单金额校验失败！");
//			logger.error("订单金额校验失败，order.getTotalMoney()："+order.getTotalMoney()+"，payMoney："+payMoney);
//			return orderVO;
//		}
//		
//		sqlService.save(order);
//		if(order.getId() == null || order.getId() == 0){
//			orderVO.setBaseVO(OrderVO.FAILURE, "订单创建失败！");
//			return orderVO;
//		}
//		
//		//创建订单对应的商品
//		for (Entry<Integer, CartProduct> entry : cartProductMap.entrySet()) {
//			CartProduct cartProduct = entry.getValue();
//			Product product = cartProduct.getProduct();	//购物车的商品
//			
//			OrderProduct orderProduct = new OrderProduct();
//			orderProduct.setNumber(cartProduct.getNumber());
//			orderProduct.setOrderid(order.getId());
//			orderProduct.setProductid(product.getId());
//			orderProduct.setProductPrice(product.getPrice());
//			orderProduct.setProductTitle(product.getTitle());
//			orderProduct.setProductUnits(product.getUnits());
//			orderProduct.setProductTitlepic(product.getTitlepic());
//			sqlService.save(orderProduct);
//			if(orderProduct.getId() == null || orderProduct.getId() == 0){
//				//既然商品保存失败了，直接将所属订单删除，不允许其进行支付
//				sqlService.delete(order);
//				logger.error("订单创建成功，但订单商品保存失败！");
//				orderVO.setBaseVO(OrderVO.FAILURE, "订单商品保存失败！");
//				return orderVO;
//			}
//			//商品减库存
//			sqlService.executeSql("UPDATE product SET repertory=repertory-"+orderProduct.getNumber()+" WHERE id = "+orderProduct.getProductid());
//		}
//		
//		//日志记录
//		logService.insert(order.getId(),"SHOP_CREATE_ORDER", order.getId()+"");
//		
//		//清空此店铺的购物车数据
//		clearShopCart(request, storeid);
//		
//		//加入消息队列
//		if(G.ORDER_PAY_TIMEOUT > 0){
//			Message message = new Message();
//			message.setMessageBody(order.getId()+"");
//			message.setDelaySeconds(G.ORDER_PAY_TIMEOUT);
//			Message m = MNSUtil.mns.putMessage(MNSUtil.ORDER_PAY_TIMEOUT_QUEUENAME, message);
//			System.out.println(m);
//		}
//		
//		orderVO.setOrder(order);
//		return orderVO;
		
		
		
		return null;
	}
	
	
	
}
