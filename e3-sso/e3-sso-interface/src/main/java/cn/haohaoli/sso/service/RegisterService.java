package cn.haohaoli.sso.service;

import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.model.TbUser;

/**
 * @author Liwenhao
 * @date 2018/8/24 10:39
 */
public interface RegisterService {

    E3Result checkData (String param, int type);

    E3Result insert(TbUser tbUser);
}
