package cn.haohaoli.sso.service;

import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.model.TbUser;

/**
 * @author Liwenhao
 * @date 2018/8/24 14:05
 */
public interface TokenService {

    E3Result getUserByToken(String token);
}
