package cn.haohaoli.cart.service.impl;

import cn.haohaoli.cart.service.CartService;
import cn.haohaoli.common.jedis.JedisClient;
import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.mapper.TbItemMapper;
import cn.haohaoli.model.TbItem;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liwenhao
 * @date 2018/8/25 19:08
 */
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${redis.cart.key.prefix}")
    private String cartPrefix;

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public E3Result insertCart(long userId, long itemId, int num) {
        String key = cartPrefix + ":" + userId;
        //向redis中添加购物车
        //数据类型是hash key:用户id,field:商品id,value:商品信息
        //判断商品是否存在 使用 hexists
        Boolean hexists = jedisClient.hexists(key, itemId + "");
        //如果存在数量数量相加
        if (hexists) {
            String tbItemJson = jedisClient.hget(key, itemId + "");
            TbItem tbItem = JSON.parseObject(tbItemJson, TbItem.class);
            tbItem.setNum(tbItem.getNum() + num);
            //写回redis
            jedisClient.hset(key, itemId + "",JSON.toJSONString(tbItem));
            return E3Result.ok();
        }
        //如果不存在根据商品ID取商品信息
        TbItem tbItem = tbItemMapper.selectById(itemId);
        tbItem.setNum(num);
        String image = tbItem.getImage();
        if (!StringUtils.isEmpty(image)) {
            tbItem.setImage(image.split(",")[0]);
        }
        //添加到购物车列表
        jedisClient.hset(key,itemId + "", JSON.toJSONString(tbItem));
        //返回成功
        return E3Result.ok();
    }

    @Override
    public E3Result mergeCart(long userId, List<TbItem> tbItemList) {
        //遍历商品列表
        for (TbItem tbItem : tbItemList) {
            insertCart(userId, tbItem.getId(), tbItem.getNum());
        }
        //把列表添加到购物车
        //判断购物车总是否有该商品
        //如果有，数量相加
        //如果没有添加新的商品
        return E3Result.ok();
    }

    @Override
    public List<TbItem> selectList(long userId) {
        //根据用户Id查询购物车列表
        List<String> hvals = jedisClient.hvals(cartPrefix + ":" + userId);
        List<TbItem> tbItemList = new ArrayList<>();
        for (String hval : hvals) {
            tbItemList.add(JSON.parseObject(hval, TbItem.class));
        }
        return tbItemList;
    }

    @Override
    public E3Result updateNum(long userId, long itemId, int num) {
        String hget = jedisClient.hget(cartPrefix + ":" + userId, itemId + "");
        TbItem tbItem = JSON.parseObject(hget, TbItem.class);
        tbItem.setNum(num);
        jedisClient.hset(cartPrefix + ":" + userId, itemId + "", JSON.toJSONString(tbItem));
        return E3Result.ok();
    }

    @Override
    public E3Result deleteItem(long userId, long itemId) {
        jedisClient.hdel(cartPrefix + ":" + userId,itemId + "");
        return E3Result.ok();
    }
}
