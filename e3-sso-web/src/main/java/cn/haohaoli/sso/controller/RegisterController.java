package cn.haohaoli.sso.controller;

import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.model.TbUser;
import cn.haohaoli.sso.service.LoginService;
import cn.haohaoli.sso.service.RegisterService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Liwenhao
 * @date 2018/8/24 11:03
 */
@RestController
public class RegisterController {

    @Resource
    private RegisterService registerService;

    @GetMapping("/user/check/{param}/{type}")
    public E3Result checkData(@PathVariable String param ,@PathVariable int type){
        return registerService.checkData(param, type);
    }

    @PostMapping("/user/register")
    public E3Result register(TbUser tbUser){
        return registerService.insert(tbUser);
    }
}
