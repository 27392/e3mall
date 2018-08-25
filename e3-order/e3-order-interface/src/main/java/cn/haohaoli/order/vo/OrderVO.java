package cn.haohaoli.order.vo;

import cn.haohaoli.model.TbOrder;
import cn.haohaoli.model.TbOrderItem;
import cn.haohaoli.model.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * @author Liwenhao
 * @date 2018/8/25 23:25
 */
public class OrderVO extends TbOrder implements Serializable {

    private List<TbOrderItem> orderItems;

    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }

    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }
}
