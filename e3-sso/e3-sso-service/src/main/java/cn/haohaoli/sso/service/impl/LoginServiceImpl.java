package cn.haohaoli.sso.service.impl;

import cn.haohaoli.common.jedis.JedisClient;
import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.mapper.TbUserMapper;
import cn.haohaoli.model.TbUser;
import cn.haohaoli.sso.service.LoginService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;

/**
 * @author Liwenhao
 * @date 2018/8/24 12:14
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private TbUserMapper tbUserMapper;

    @Autowired
    private JedisClient jedisClient;

    @Value("${session.expire}")
    private int sessionExpireTime;

    @Value("${password.salt}")
    private String salt;

    @Override
    public E3Result userLogin(String username, String password) {
        List<TbUser> userList = tbUserMapper.selectList(new EntityWrapper<TbUser>()
                .eq("username", username)
                .eq("password", DigestUtils.md5DigestAsHex((password + salt).getBytes())));
        if (userList != null && !userList.isEmpty()) {
            TbUser tbUser = userList.get(0);
            String token = UUID.randomUUID().toString();
            tbUser.setPassword(null);
            jedisClient.set("SESSION:" + token, JSON.toJSONString(tbUser));
            jedisClient.expire("SESSION:" + token, sessionExpireTime);
            return E3Result.ok(token);
        } else {
            return E3Result.error("用户名密码错误");
        }

    }
}
