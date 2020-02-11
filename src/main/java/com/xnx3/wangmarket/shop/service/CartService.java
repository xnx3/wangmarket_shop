package com.xnx3.wangmarket.shop.service;

import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.entity.Goods;
import com.xnx3.wangmarket.shop.vo.CartVO;
import com.xnx3.wangmarket.shop.vo.bean.StoreCart;

/**
 * 购物车相关
 * @author 管雷鸣
 */
public interface CartService {
	
	/**
	 * 获取此用户当前的购物车详细信息，单店铺
	 * @param storeid 店铺id，对应 Store.id ,要获取的是哪个店铺的购物车数据
	 * @return {@link StoreCart} 若不存在，则会new一个返回过来，不会存在null的情况
	 */
	public StoreCart getStoreCart(int storeid);
	
	/**
	 * 获取此用户当前的购物车详细信息，将购物车中的所有信息都返回过来
	 * @return {@link CartVO}
	 */
	public CartVO getCart();
	
	/**
	 * 设置此用户当前的购物车详细信息
	 * 调用此接口设置后，一是会设置Session缓存，然后也会将cartVO变为json形式存到数据表 shop_cart
	 * 这里保存的cartVO，会默认都加上 cartVO.setBaseVO(CartVO.SUCCESS, "success"); 也就是都是成功状态的vo
	 * @param cartVO 购物车的信息
	 */
	public void setCart(CartVO cartVO);
	
	/**
	 * 操作购物车的商品，每个店铺都有一个自己的购物车，每个店铺的购物车不冲突，可并存，如去A店铺加入了几个商品进入购物车，又去B店铺加入了几个进购物车，这两个店铺的购物车信息是不冲突的，可用 {@link #getCart(int)}
	 * @param goodsid 要修改的购物车中的商品编号，对应 {@link Goods}.id
	 * @param changeNumber 要修改购物车中对应商品编号的商品，是增加，还是减少，这里便是增加或者减少的数值。
	 * 				<ul>
	 * 					<li>正数，增加
	 * 					<li>负数，减少。最多能减少到0，如果到0，也就是将这个商品从购物车中清除掉了。例如，想要清除掉某个商品，那么可以传入 -99999
	 * 				</ul>
	 * 				
	 * @return {@link CartVO} 操作之后的购物车对象
	 */
	public CartVO cart(int goodsid ,int changeNumber);
	
	/**
	 * 清空此用户当前的购物车所存储的某个店铺的购物车信息。只有这个店铺下的购物车信息会清空，其他店铺的购物车信息不变
	 * @param storeid 店铺id，对应 Store.id ,要清空的是哪个店铺的购物车数据
	 * @return {@link BaseVO}
	 */
	public BaseVO clearStoreCart(int storeid);
	
	/**
	 * 清空此用户当前的购物车。这是清理整个购物车，包含所有店铺的购物车都会被清理掉
	 * @return {@link BaseVO}
	 */
	public BaseVO clearCart();
	

	/**
	 * 刷新当前购物车中的商品信息。如商品下架了、或者商品无库存了等,会将最新的商品信息更新
	 * @param storeid 店铺id，对应 Store.id ,要刷新的是哪个店铺的购物车商品。如果传入0，则是刷新所有店铺的购物车商品
	 * @return {@link CartVO}
	 */
	public CartVO refresh(int storeid);
	
	/**
	 * 购物车中的商品，全选或全部不选所有购物车中的商品（包含所有店铺的商品）
	 * @param selected 是否选中， 1选中， 0不选
	 * @return {@link CartVO} 操作之后，统计数据跟随变化之后的购物车信息
	 */
	public CartVO selected(int selected);
	
	/**
	 * 购物车中某个店铺下的商品，全选或全部不选所有购物车中的商品（只是操作的这个店铺下的所有购物车商品）
	 * @param storeid 是哪个店铺下，进行要全选中或者全不选中。传入店铺id，对应 Store.id
	 * @param selected 是否选中， 1选中， 0不选
	 * @return {@link CartVO} 操作之后，统计数据跟随变化之后的购物车信息
	 */
	public CartVO selectedByStoreId(int storeid, int selected);
	
	/**
	 * 购物车中的商品，选或不选购物车中的商品，以便进行下一步结算
	 * @param goodsid 要选中或者不选中的商品，对应 {@link Goods}.id
	 * @param selected 是否选中， 1选中， 0不选
	 * @return {@link CartVO} 操作之后，统计数据跟随变化之后的购物车信息
	 */
	public CartVO selectedByGoodsId(int goodsid, int selected);
	
}
