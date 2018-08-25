package cn.haohaoli.order.interceptor;

import cn.haohaoli.cart.service.CartService;
import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.common.utils.CookieUtils;
import cn.haohaoli.model.TbItem;
import cn.haohaoli.model.TbUser;
import cn.haohaoli.sso.service.TokenService;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Liwenhao
 * @date 2018/8/25 22:30
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Value("${sso.address}")
    private String ssoAddress;

    @Resource
    private TokenService tokenService;

    @Resource
    private CartService cartService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从cookie中获取token
        String token = CookieUtils.getCookieValue(request, "token");
        //判断token是否存在
        if (StringUtils.isEmpty(token)) {
            //如果token不存在，未登录状态，条状到sso系统的登录页面。用户登录成功后，跳转到当前请求的url
            response.sendRedirect(ssoAddress + "/page/login?redirect=" + request.getRequestURL());
            return false;
        }
        //如果token存在，需要调用sso系统的服务，跟进token取用户信息
        E3Result userByToken = tokenService.getUserByToken(token);
        //如果取不到，用户登录已经过期，需要登录
        if (userByToken.getStatus() != 200) {
            response.sendRedirect(ssoAddress + "/page/login?redirect=" + request.getRequestURL());
            return false;
        }
        TbUser user = (TbUser) userByToken.getData();
        //如果取到用户信息，是登陆状态，需要把用户信息写入request
        request.setAttribute("user",user);
        //判断cookie中是否存在购物车数据，有就合并到服务端
        String cartCookie = CookieUtils.getCookieValue(request, "cart", true);
        if (!StringUtils.isEmpty(cartCookie)) {
            List<TbItem> cartList = JSONArray.parseArray(cartCookie, TbItem.class);
            cartService.mergeCart(user.getId(),cartList);
        }
        return true;
    }
}
