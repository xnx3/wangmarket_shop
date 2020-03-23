package com.xnx3.wangmarket.shop.api.controller;

import java.util.HashMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xnx3.j2ee.pluginManage.controller.BasePluginController;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.j2ee.service.UserService;
import com.xnx3.j2ee.util.ActionLogUtil;
import com.xnx3.j2ee.vo.BaseVO;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.api.service.CartService;
import com.xnx3.wangmarket.shop.api.vo.CartVO;
import com.xnx3.wangmarket.shop.api.vo.GoodsCartVO;
import com.xnx3.wangmarket.shop.api.vo.StoreCartVO;
import com.xnx3.wangmarket.shop.api.vo.bean.GoodsCart;
import com.xnx3.wangmarket.shop.api.vo.bean.StoreCart;

/**
 * 商城相关功能
 * @author 管雷鸣
 */
@Controller(value="ShopCartController")
@RequestMapping("/shop/api/cart/")
public class CartController extends BasePluginController {
	@Resource
	private SqlService sqlService;
	@Resource
	private UserService userService;
	@Resource
	private CartService cartService;
	
	/**
	 * 操作购物车中的商品进行加减操作
	 * @param storeid 要操作哪个店铺的购物车信息，对应 store.id。
	 * @param goodsid 要修改的购物车中的商品编号，必传，对应 {@link Goods}.id
	 * @param changeNumber 要修改购物车中对应商品编号的商品，是增加，还是减少，这里便是增加或者减少的数值。
	 * 				<ul>
	 * 					<li>正数，增加
	 * 					<li>负数，减少。最多能减少到0，如果到0，也就是将这个商品从购物车中清除掉了。例如，想要清除掉某个商品，那么可以传入 -99999
	 * 				</ul>
	 * @return {@link CartVO} 当前的购物车数据
	 */
	@RequestMapping(value="change${api.suffix}",method= {RequestMethod.POST})
	@ResponseBody
	public StoreCartVO change(HttpServletRequest request,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid,
			@RequestParam(value = "goodsid", required = false, defaultValue="0") int goodsid,
			@RequestParam(value = "changeNumber", required = false, defaultValue="0") int changeNumber){
		//购物车的数据存在于Session中
		CartVO cartVO = cartService.change(goodsid, changeNumber);
		ActionLogUtil.insert(request, "操作购物车中的商品进行加减操作");
		return cartVoToStoreCartVo(cartVO, storeid);
	}
	
	
	/**
	 * 获整个购物车的数据，包含所有商家的购物车
	 * @return 
	 */
	@RequestMapping(value="getCart${api.suffix}",method= {RequestMethod.POST})
	@ResponseBody
	public CartVO getCart(HttpServletRequest request){
		ActionLogUtil.insert(request, "获取购物车数据");
		
		//刷新最新商品状态
		cartService.refresh(0);
		return cartService.getCart();
	}
	
	/**
	 * 清空用户在某个商家下的购物车数据。
	 * @param storeid 要清除哪个店铺的购物车信息，对应 store.id。 如果不传这个参数，则是清空所有的购物车信息
	 */
	@RequestMapping(value="clearStoreCart${api.suffix}",method= {RequestMethod.POST})
	@ResponseBody
	public BaseVO clearStoreCart(HttpServletRequest request,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid){
		ActionLogUtil.insert(request, "清空购物车数据");
		if(storeid == 0){
			return cartService.clearCart();
		}
		
		return cartService.clearStoreCart(storeid);
	}
	
	
	/**
	 * 购物车中的商品，选中或不选中购物车中的商品，以便进行下一步进行结算
	 * @param goodsid 要选中或者不选中的商品，对应 {@link Goods}.id 
	 * @param storeid 要操作的商品所在的店铺，对应 {@link Store}.id 
	 * @param selected 是否选中， 1选中， 0不选
	 * @return {@link CartVO}
	 */
	@RequestMapping(value="goodsCartSelected${api.suffix}",method= {RequestMethod.POST})
	@ResponseBody
	public StoreCartVO goodsCartSelected(HttpServletRequest request,
			@RequestParam(value = "goodsid", required = false, defaultValue="0") int goodsid,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid,
			@RequestParam(value = "selected", required = false, defaultValue="0") int selected){
		CartVO cartVO = cartService.selectedByGoodsId(goodsid, selected);
		return cartVoToStoreCartVo(cartVO, storeid);
	}
	
	/**
	 * 购物车中某个店铺下的商品，全选或全部不选所有购物车中的商品（只是操作的这个店铺下的所有购物车商品）
	 * @param storeid 是哪个店铺下，进行要全选中或者全不选中。传入店铺id，对应 Store.id
	 * @param selected 是否选中， 1选中， 0不选
	 * @return {@link CartVO}
	 */
	@RequestMapping(value="storeCartSelected${api.suffix}",method= {RequestMethod.POST})
	@ResponseBody
	public StoreCartVO storeCartSelected(HttpServletRequest request,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid,
			@RequestParam(value = "selected", required = false, defaultValue="0") int selected){
//		return cartService.selectedByStoreId(storeid, selected);
		return cartVoToStoreCartVo(cartService.selectedByStoreId(storeid, selected), storeid);
	}
	

	/**
	 * 获取某个商铺的购物车数据,这里获取到的数据仅仅只是某个商铺下的购物车信息
	 * @return StoreCartVO
	 */
	@RequestMapping(value="getStoreCart${api.suffix}",method= {RequestMethod.POST})
	@ResponseBody
	public StoreCartVO getStoreCart(HttpServletRequest request,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid){
		ActionLogUtil.insert(request, "获取购物车数据");
		
		//刷新最新商品状态
		cartService.refresh(0);
		
		//从购物车中取数据
		return cartVoToStoreCartVo(cartService.getCart(), storeid);
	}
	
	/**
	 * 获取某个商品的购物车信息，如这个商品在购物车中是否被选中，在购物车中的数量是几等
	 * @param storeid 商家的店铺id， store.id
	 * @param goodsid 要查看的商品的id， goods.id
	 */
	@RequestMapping(value="getGoodsCart${api.suffix}",method= {RequestMethod.POST})
	@ResponseBody
	public GoodsCartVO getGoodsCart(HttpServletRequest request,
			@RequestParam(value = "storeid", required = false, defaultValue="0") int storeid,
			@RequestParam(value = "goodsid", required = false, defaultValue="0") int goodsid){
		GoodsCartVO vo = null;
		
		StoreCartVO storeCartVO = getStoreCart(request, storeid);
		if(storeCartVO != null){
			GoodsCart goodsCart = storeCartVO.getGoodsCartMap().get(goodsid);
			vo = new GoodsCartVO(goodsCart);
		}
		if(vo == null){
			vo = new GoodsCartVO();
		}
		
		ActionLogUtil.insert(request, "获取购物车中某个商品的数据");
		return vo;
	}
	
	
	/**
	 * 将 {@link CartVO} 转为 {@link StoreCartVO}
	 * @param cartVO 原始的 {@link CartVO}
	 * @param storeid 要取哪个店铺
	 * @return 某个店铺的购物车数据
	 */
	private StoreCartVO cartVoToStoreCartVo(CartVO cartVO, int storeid){
		StoreCart storeCart = cartVO.getStoreCartMap().get(storeid);
		StoreCartVO vo = new StoreCartVO();
		if(storeCart == null){
			vo.setMoney(0);
			vo.setNumber(0);
			vo.setGoodsCartMap(new HashMap<Integer, GoodsCart>());
		}else{
			vo.setMoney(storeCart.getMoney());
			vo.setNumber(storeCart.getNumber());
			vo.setGoodsCartMap(storeCart.getGoodsCartMap());
			vo.setBaseVO(cartVO.getResult(), cartVO.getInfo());
		}
		return vo;
	}
}