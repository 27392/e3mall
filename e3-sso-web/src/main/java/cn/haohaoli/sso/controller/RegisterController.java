package cn.haohaoli.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Liwenhao
 * @date 2018/8/16 17:09
 */
@Controller
public class RegisterController {

    @GetMapping("/page/register")
    public String showRegister(){
        return "register";
    }
}
