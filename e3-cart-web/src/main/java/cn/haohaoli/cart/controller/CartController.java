package cn.haohaoli.cart.controller;

import cn.haohaoli.cart.service.CartService;
import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.common.utils.CookieUtils;
import cn.haohaoli.model.TbItem;
import cn.haohaoli.model.TbUser;
import cn.haohaoli.service.TbItemService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liwenhao
 * @date 2018/8/24 20:01
 */
@Controller
@RequestMapping("/cart")
public class CartController {

    @Resource
    private TbItemService tbItemService;

    @Value("${cookie.cart.expire}")
    private Integer cartExpire;

    @Value("${cookie.cart.key}")
    private String CartKey;

    @Resource
    private CartService cartService;

    @GetMapping("/add/{itemId}")
    public String addCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") int num,
                          HttpServletRequest request, HttpServletResponse response){
        //判断用户是否登录
        TbUser tbUser = (TbUser) request.getAttribute("user");
        //如果是登录状态,把购物车写入redis
        if (tbUser != null) {
            cartService.insertCart(tbUser.getId(),itemId,num);
            //返回添加成功页面
            return "cartSuccess";
        }

        //从cookie中取购物车列表
        List<TbItem> tbItemList = getCartListFromCookie(request);
        //判断商品在商品列表中是否存在
        boolean flag = false;
        for (TbItem tbItem : tbItemList) {
            if (tbItem.getId() == itemId.longValue()) {
                //找到商品数量相加
                tbItem.setNum(tbItem.getNum() + num);
                flag = true;
                break;
            }
        }
        //如果不存在
        if (!flag) {
            //根据商品id查询商品信息。得到一个TbItem对象
            TbItem tbItem = tbItemService.selectById(itemId);
            //设置商品数量
            tbItem.setNum(num);
            //取一张图片
            String image = tbItem.getImage();
            if (!StringUtils.isEmpty(image)) {
                tbItem.setImage(image.split(",")[0]);
            }
            //把商品添加到商品列表
            tbItemList.add(tbItem);
        }
        //写入cookie
        CookieUtils.setCookie(request,response,CartKey, JSON.toJSONString(tbItemList),cartExpire,true);
        //返回添加成功页面
        return "cartSuccess";
    }

    @GetMapping("/list")
    public String showCartList(HttpServletRequest request,HttpServletResponse response) {
        List<TbItem> cartList = getCartListFromCookie(request);
        //判断用户是否登录
        TbUser tbUser = (TbUser) request.getAttribute("user");
        //如果是登录状态,从redis中取购物车
        if (tbUser != null) {
            //如果不为空
            cartService.mergeCart(tbUser.getId(), cartList);
            //删除cookie
            CookieUtils.deleteCookie(request,response,CartKey);
            cartList = cartService.selectList(tbUser.getId());
        }
        //列表传递给页面
        request.setAttribute("cartList",cartList);
        return "cart";
    }

    @PostMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public E3Result updateCartNum(@PathVariable Long itemId, @PathVariable Integer num,
                                  HttpServletRequest request, HttpServletResponse response){
        //判断用户是否登录
        TbUser tbUser = (TbUser) request.getAttribute("user");
        //如果是登录状态,从redis中取购物车
        if (tbUser != null) {
            cartService.updateNum(tbUser.getId(), itemId, num);
            return E3Result.ok();
        }
        List<TbItem> tbItemList = getCartListFromCookie(request);
        //从cookie中获取购物车列表
        for (TbItem tbItem : tbItemList) {
            if (tbItem.getId() == itemId.longValue()) {
                tbItem.setNum(num);
                break;
            }
        }
        CookieUtils.setCookie(request, response, CartKey, JSON.toJSONString(tbItemList), cartExpire, true);
        return E3Result.ok();
    }

    @GetMapping("/delete/{itemId}")
    public String deleteCart(@PathVariable Long itemId,
                             HttpServletRequest request, HttpServletResponse response){
        //判断用户是否登录
        TbUser tbUser = (TbUser) request.getAttribute("user");
        //如果是登录状态,从redis中删除
        if (tbUser != null) {
            cartService.deleteItem(tbUser.getId(), itemId);
            return "redirect:/cart/list.html";
        }
        //从cookie中获取购物车列表
        List<TbItem> tbItemList = getCartListFromCookie(request);
        for (TbItem tbItem : tbItemList) {
            if (tbItem.getId() == itemId.longValue()) {
                tbItemList.remove(tbItem);
                break;
            }
        }
        CookieUtils.setCookie(request, response, CartKey, JSON.toJSONString(tbItemList), cartExpire, true);
        return "redirect:/cart/list.html";
    }


    private List<TbItem> getCartListFromCookie(HttpServletRequest request){
        String cart = CookieUtils.getCookieValue(request, CartKey, true);
        if (StringUtils.isEmpty(cart)) {
            return new ArrayList<>();
        }
        return JSONArray.parseArray(cart, TbItem.class);
    }
}
