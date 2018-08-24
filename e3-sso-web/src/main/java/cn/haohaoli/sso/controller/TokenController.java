package cn.haohaoli.sso.controller;

import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.sso.service.TokenService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author Liwenhao
 * @date 2018/8/24 14:13
 */
@Controller
public class TokenController {

    @Resource
    private TokenService tokenService;

    @GetMapping("/user/token/{token}")
    @ResponseBody
    public E3Result getUserByToken(@PathVariable String token){
        E3Result userByToken = tokenService.getUserByToken(token);
        return userByToken;
    }
}
