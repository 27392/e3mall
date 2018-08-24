package cn.haohaoli.sso.service.impl;

import cn.haohaoli.common.jedis.JedisClient;
import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.model.TbUser;
import cn.haohaoli.sso.service.TokenService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author Liwenhao
 * @date 2018/8/24 14:06
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${session.expire}")
    private int sessionExpireTime;

    @Override
    public E3Result getUserByToken(String token) {
        String s = jedisClient.get("SESSION:" + token);
        if (StringUtils.isEmpty(s)) {
            return E3Result.error("用户登录已经过期");
        }
        TbUser tbUser = JSONObject.parseObject(s, TbUser.class);
        jedisClient.expire("SESSION:" + token, sessionExpireTime);
        return E3Result.ok(tbUser);
    }
}
