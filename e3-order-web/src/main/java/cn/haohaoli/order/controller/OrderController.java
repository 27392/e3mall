package cn.haohaoli.order.controller;

import cn.haohaoli.cart.service.CartService;
import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.model.TbItem;
import cn.haohaoli.model.TbUser;
import cn.haohaoli.order.service.OrderService;
import cn.haohaoli.order.vo.OrderVO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Liwenhao
 * @date 2018/8/25 22:20
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Resource
    private CartService cartService;

    @Resource
    private OrderService orderService;

    @GetMapping("/order-cart")
    public String order(HttpServletRequest request) {
        TbUser user = (TbUser) request.getAttribute("user");
        List<TbItem> itemList = cartService.selectList(user.getId());
        request.setAttribute("cartList",itemList);
        return "order-cart";
    }

    @PostMapping("/create")
    public String createOrder(HttpServletRequest request ,OrderVO orderVO) {
        //取用户信息
        TbUser user = (TbUser) request.getAttribute("user");
        //补全用户信息
        orderVO.setUserId(user.getId());
        orderVO.setBuyerNick(user.getUsername());
        //插入订单
        E3Result e3Result = orderService.insertOrder(orderVO);
        //如果订单生成成功，需要删除购物车
        if (e3Result.getStatus() == 200) {
            cartService.clearCart(user.getId());
        }
        //订单号传递给页面
        request.setAttribute("orderId",e3Result.getData());
        request.setAttribute("payment",orderVO.getPayment());
        return "success";
    }


}
