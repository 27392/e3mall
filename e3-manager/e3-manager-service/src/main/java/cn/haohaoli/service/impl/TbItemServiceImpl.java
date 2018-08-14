package cn.haohaoli.service.impl;

import cn.haohaoli.common.jedis.JedisClient;
import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.mapper.TbItemDescMapper;
import cn.haohaoli.mapper.TbItemMapper;
import cn.haohaoli.model.TbItem;
import cn.haohaoli.model.TbItemDesc;
import cn.haohaoli.service.TbItemService;
import cn.haohaoli.common.utils.IDUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.jms.Destination;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Liwenhao
 * @date 2018/8/4 19:46
 */
@Service
public class TbItemServiceImpl extends ServiceImpl<TbItemMapper,TbItem> implements TbItemService {

    @Value("${item.key.prefix}")
    private String prefix;

    @Value("${item.key.expire}")
    private int expire;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private JedisClient jedisClient;

    @Resource
    private Destination topicDestination;

    @Resource
    private TbItemMapper tbItemMapper;

    @Resource
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public TbItem selectById(Serializable id) {
        String key = prefix + ":" + id + ":BASE";
        try {
            String s = jedisClient.get(key);
            if (!StringUtils.isEmpty(s)) {
                return JSON.parseObject(s, TbItem.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        TbItem tbItem = super.selectById(id);
        try {
            if (tbItem != null) {
                jedisClient.set(key, JSON.toJSONString(tbItem));
                jedisClient.expire(key, expire);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbItem;
    }

    @Override
    public E3Result insert(TbItem tbItem, String desc) {
        long itemId = IDUtils.genItemId();
        //设置ID
        tbItem.setId(itemId);
        //创建时间
        tbItem.setCreated(new Date());
        //修改时间
        tbItem.setUpdated(new Date());
        // 1-正常,2-下架,3-删除
        tbItem.setStatus((byte) 1);
        //插入商品
        Integer insert = tbItemMapper.insert(tbItem);
        //插入商品描述
        TbItemDesc tbItemDesc = new TbItemDesc();
        tbItemDesc.setItemId(itemId);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        Integer rows = tbItemDescMapper.insert(tbItemDesc);
        if (null != rows && rows >= 1) {
            jmsTemplate.send(topicDestination, session -> session.createTextMessage(itemId + ""));
            return E3Result.ok();
        }
        //发送一个
        return E3Result.error("添加失败");
    }
}
