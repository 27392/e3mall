package cn.haohaoli.order.service.impl;

import cn.haohaoli.common.jedis.JedisClient;
import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.mapper.TbOrderItemMapper;
import cn.haohaoli.mapper.TbOrderMapper;
import cn.haohaoli.mapper.TbOrderShippingMapper;
import cn.haohaoli.model.TbOrderItem;
import cn.haohaoli.model.TbOrderShipping;
import cn.haohaoli.order.service.OrderService;
import cn.haohaoli.order.vo.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Liwenhao
 * @date 2018/8/25 23:31
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${redis.order.id}")
    private String orderIdKey;

    @Value("${redis.order.detail.id}")
    private String orderDetailIdKey;

    @Value("${redis.order.start}")
    private String orderStartId;

    @Override
    public E3Result insertOrder(OrderVO orderVO) {
        //生成订单号，使用redis的incr生成
        if (!jedisClient.exists(orderIdKey)) {
            jedisClient.set(orderIdKey, orderStartId);
        }
        String orderId = jedisClient.incr(orderIdKey).toString();
        //补全orderVo的属性
        orderVO.setOrderId(orderId);
        //1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭'
        orderVO.setStatus(1);
        orderVO.setCreateTime(new Date());
        orderVO.setUpdateTime(new Date());
        //插入订单表
        orderMapper.insert(orderVO);
        //插入订单明细表
        List<TbOrderItem> orderItems = orderVO.getOrderItems();
        for (TbOrderItem orderItem : orderItems) {
            //生成明细id
            String orderItemId = jedisClient.incr(orderDetailIdKey).toString();
            orderItem.setId(orderItemId);
            orderItem.setOrderId(orderId);
            orderItemMapper.insert(orderItem);
        }
        //插入订单物流表
        TbOrderShipping orderShipping = orderVO.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);
        //返回包含订单号
        return E3Result.ok(orderId);
    }
}
