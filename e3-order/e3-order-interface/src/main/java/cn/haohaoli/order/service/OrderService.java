package cn.haohaoli.order.service;

import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.order.vo.OrderVO;

/**
 * @author Liwenhao
 * @date 2018/8/25 23:30
 */
public interface OrderService {

    E3Result insertOrder(OrderVO orderVO);
}
