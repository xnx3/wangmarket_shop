package com.xnx3.wangmarket.shop.core.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xnx3.j2ee.service.SqlService;
import com.xnx3.wangmarket.shop.core.entity.Goods;
import com.xnx3.wangmarket.shop.core.entity.GoodsData;
import com.xnx3.wangmarket.shop.core.entity.GoodsImage;
import com.xnx3.wangmarket.shop.core.entity.GoodsType;
import com.xnx3.wangmarket.shop.core.service.GoodsService;
import com.xnx3.wangmarket.shop.core.vo.GoodsDetailsVO;
import com.xnx3.wangmarket.shop.core.vo.GoodsListVO;
import com.xnx3.wangmarket.shop.core.vo.GoodsTypeListVO;

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {
	@Resource
	private SqlService sqlService;
	
	Map<Integer, Integer> goodsidAndStoreidCache = new HashMap<Integer, Integer>();
	
	@Override
	public GoodsTypeListVO getStoreGoodsType(int storeid) {
		GoodsTypeListVO vo = new GoodsTypeListVO();
		
		if(storeid == 0){
			vo.setBaseVO(GoodsTypeListVO.FAILURE, "请传入要获取的店铺编号 storeid");
			return vo;
		}
		
		List<GoodsType> list = sqlService.findBySqlQuery("SELECT * FROM shop_goods_type WHERE storeid = "+storeid+" AND isdelete = "+GoodsType.ISDELETE_NORMAL +" ORDER BY rank ASC", GoodsType.class);
		vo.setList(list);
		return vo;
	}

	@Override
	public GoodsListVO getGoodsList(int storeid, int typeid, int orderBy) {
		GoodsListVO vo = new GoodsListVO();
		if(storeid == 0 && typeid > 0){
			vo.setBaseVO(GoodsListVO.FAILURE, "storeid未设值，查询所有商铺的商品，那么typeid还传值干什么？typeid应该也要传0才是");
			return vo;
		}
		
		String orderBySql = "";
		switch (orderBy) {
		case 1:
			orderBySql = "sale DESC";
			break;
		case 2:
			orderBySql = "id ASC";
			break;
		default:
			orderBySql = "sale DESC";
			break;
		}
		
		String sql = "SELECT * FROM shop_goods WHERE "+
						(storeid > 0? "storeid = "+storeid+" AND ":"")+
						(typeid > 0? "typeid = "+typeid+" AND ":"")+
						"isdelete = "+Goods.ISDELETE_NORMAL+" "+
					"ORDER BY "+orderBySql;
		List<Goods> list = sqlService.findBySqlQuery(sql, Goods.class);
		vo.setList(list);
		return vo;
	}

	@Override
	public GoodsDetailsVO getGoodsDetail(int goodsid) {
		GoodsDetailsVO vo = new GoodsDetailsVO();
		if(goodsid < 1){
			vo.setBaseVO(GoodsDetailsVO.FAILURE, "请传入要查看的商品id");
			return vo;
		}
		
		/*** 查出goods表的信息 ***/
		Goods goods = sqlService.findById(Goods.class, goodsid);
		if(goods == null){
			vo.setBaseVO(GoodsDetailsVO.FAILURE, "商品不存在！");
			return vo;
		}
		if(goods.getIsdelete() - Goods.ISDELETE_DELETE == 0){
			vo.setBaseVO(GoodsDetailsVO.FAILURE, "商品已被删除！");
			return vo;
		}
		vo.setGoods(goods);
		
		/*** 查出goods_image表的信息 ***/
		List<GoodsImage> goodsImageList = sqlService.findBySqlQuery("SELECT * FROM shop_goods_image WHERE goodsid = "+goods.getId()+" ORDER BY rank ASC", GoodsImage.class);
		vo.setGoodsImageList(goodsImageList);
		
		/*** 查出goods_data表的信息 ***/
		GoodsData goodsData = sqlService.findById(GoodsData.class, goods.getId());
		if(goodsData == null){
			goodsData = new GoodsData();
		}
		vo.setGoodsData(goodsData);
		
		return vo;
	}
	
	
}
