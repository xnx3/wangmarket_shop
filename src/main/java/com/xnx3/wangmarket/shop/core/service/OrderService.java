package com.xnx3.wangmarket.shop.core.service;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Address;
import com.xnx3.wangmarket.shop.core.entity.Order;
import com.xnx3.wangmarket.shop.core.vo.OrderVO;

/**
 * 订单相关
 * @author 管雷鸣
 */
public interface OrderService {
	
	/**
	 * 取消订单，或者支付后退款，会导致商品数量进行改变，比如商品的已售数量会减少，商品库存会增加回来
	 * @param order 取消的订单，或者支付成功后退款的订单
	 * @return {@link BaseVO} result = BaseVO.SUCCESS 为成功
	 */
	public BaseVO orderCancelGoodsNumberChange(Order order);
	
	/**
	 * 确认收货
	 * @param order 要确认收货的订单。需要传入之前判断用户是否有进行确认订单的权限
	 * @return {@link OrderVO} 除了result、info外，只有order是有信息的
	 */
	public OrderVO receiveGoods(Order order);
	
	/**
	 * 获取已完成的订单数。
	 * <p>根据 userid、storeid 获取此用户在这个店铺下有几个已完成订单。</p>
	 * <p>这里已完成的订单，是指用户无法再退的订单，比如已确认收货( {@link Order#STATE_RECEIVE_GOODS} )、已完成( {@link Order#STATE_FINISH} ) 都是</p>
	 * <p>注意，这里是直接从数据库中查询获取，并没有走缓存，注意使用频率</p>
	 * @param userid 要查的是哪个用户，对应 user.id
	 * @param storeid 这个用户是在哪个店铺的下单，对应 store.id
	 * @return 已完成订单的数，有几个已完成的订单
	 */
	public int getFinishOrderCount(int userid, int storeid);
}
