package cn.haohaoli.service.impl;

import cn.haohaoli.common.jedis.JedisClient;
import cn.haohaoli.mapper.TbItemDescMapper;
import cn.haohaoli.model.TbItemDesc;
import cn.haohaoli.service.TbItemDescService;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @author Liwenhao
 * @date 2018/8/14 21:47
 */
@Service
public class TbItemDescServiceImpl extends ServiceImpl<TbItemDescMapper, TbItemDesc> implements TbItemDescService {

    @Value("${item.key.prefix}")
    private String prefix;

    @Value("${item.key.expire}")
    private int expire;

    @Autowired
    private TbItemDescMapper tbItemDescMapper;

    @Autowired
    private JedisClient jedisClient;

    @Override
    public TbItemDesc selectById(Serializable id) {
        String key = prefix + ":" + id + ":DESC";
        try {
            String s = jedisClient.get(key);
            if (!StringUtils.isEmpty(s)) {
                return JSON.parseObject(s, TbItemDesc.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<TbItemDesc> tbItemDescList = tbItemDescMapper.selectList(new EntityWrapper<TbItemDesc>().eq("item_id", id));
        TbItemDesc tbItemDesc = null;
        if (tbItemDescList != null && tbItemDescList.size() > 0) {
            tbItemDesc = tbItemDescList.get(0);
        }
        try {
            jedisClient.set(key, JSON.toJSONString(tbItemDesc));
            jedisClient.expire(key, expire);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItemDesc;
    }
}
