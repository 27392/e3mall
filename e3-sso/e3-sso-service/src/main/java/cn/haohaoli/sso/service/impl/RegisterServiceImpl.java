package cn.haohaoli.sso.service.impl;

import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.mapper.TbUserMapper;
import cn.haohaoli.model.TbUser;
import cn.haohaoli.sso.service.RegisterService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * @author Liwenhao
 * @date 2018/8/24 10:40
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    @Value("${password.salt}")
    private String salt;

    @Autowired
    private TbUserMapper userMapper;

    @Override
    public E3Result checkData(String param, int type) {
        String column;
        switch (type){
            case 1:
                column = "username";
                break;
            case 2:
                column = "phone";
                break;
            case 3:
                column = "email";
                break;
            default :
                return E3Result.error("数据类型错误");
        }
        List<TbUser> userList = userMapper.selectList(new EntityWrapper<TbUser>().eq(column, param));
        if (userList != null && !userList.isEmpty()) {
            return E3Result.ok(false);
        }
        return E3Result.ok(true);
    }

    @Override
    public E3Result insert(TbUser tbUser) {
        if ("".equals(tbUser.getUsername()) || "".equals(tbUser.getPassword()) || "".equals(tbUser.getPassword())) {
            return E3Result.error("信息不完整");
        }
        if (!(boolean)checkData(tbUser.getUsername(),1).getData()){
            return E3Result.error("用户名重复");
        }
        if (!(boolean)checkData(tbUser.getPhone(),2).getData()){
            return E3Result.error("手机号重复");
        }
        tbUser.setCreated(new Date());
        tbUser.setUpdated(new Date());
        String md5password = DigestUtils.md5DigestAsHex((tbUser.getPassword() + salt).getBytes());
        tbUser.setPassword(md5password);
        Integer insert = userMapper.insert(tbUser);
        if (insert != null && insert!= 0) {
            return E3Result.ok();
        }
        return E3Result.error("注册失败！");
    }
}
