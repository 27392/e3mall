package cn.haohaoli.sso.service;

import cn.haohaoli.common.pojo.E3Result;

/**
 * @author Liwenhao
 * @date 2018/8/24 12:13
 */
public interface LoginService {

    E3Result userLogin(String username, String password);
}
