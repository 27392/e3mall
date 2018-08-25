package cn.haohaoli.cart.service;

import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.model.TbItem;

import java.util.List;

/**
 * @author Liwenhao
 * @date 2018/8/25 19:04
 */
public interface CartService {

    E3Result insertCart(long userId, long itemId, int num);

    E3Result mergeCart(long userId,List<TbItem> tbItemList);

    List<TbItem> selectList(long userId);

    E3Result updateNum(long userId,long itemId, int num);

    E3Result deleteItem(long userId,long itemId);

    E3Result clearCart(long userId);

}
