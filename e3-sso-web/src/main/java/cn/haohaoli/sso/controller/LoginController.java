package cn.haohaoli.sso.controller;

import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.common.utils.CookieUtils;
import cn.haohaoli.sso.service.LoginService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Liwenhao
 * @date 2018/8/24 12:30
 */
@Controller
public class LoginController {

    @Resource
    private LoginService loginService;

    @Value("${cookie.key}")
    private String cookieKey;

    @GetMapping("/page/login")
    public String loginPage(String redirect, Model model){
        model.addAttribute("redirect", redirect);
        return "login";
    }

    @PostMapping("/user/login")
    @ResponseBody
    public E3Result userLogin(HttpServletRequest request, HttpServletResponse response,
                              String username, String password){
        E3Result e3Result = loginService.userLogin(username, password);
        if (e3Result.getStatus() == 200) {
            String token = e3Result.getData().toString();
            CookieUtils.setCookie(request, response, cookieKey, token);
        }
        return e3Result;
    }
}
