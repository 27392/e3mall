package cn.haohaoli.cart.interceptor;

import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.common.utils.CookieUtils;
import cn.haohaoli.model.TbUser;
import cn.haohaoli.sso.service.TokenService;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Liwenhao
 * @date 2018/8/25 18:45
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //前处理执行handler之前执行此方法
        //返回true 放行,false 拦截
        //1.从cookie中获取token
        String token = CookieUtils.getCookieValue(request, "token");
        //2.如果没有token 未登录状态直接放行
        if (StringUtils.isEmpty(token)) {
            return true;
        }
        //3.取到token需要调用sso系统的服务，根据token取用户信息
        E3Result userInfo = tokenService.getUserByToken(token);
        //4.没有取到用户信息，登录过期，未登录 直接放行
        if (userInfo.getStatus() != 200){
            return true;
        }
        //5.取到用户信息，登录状态
        TbUser tbUser = (TbUser) userInfo.getData();
        request.setAttribute("user",tbUser);
        return true;
    }
}
