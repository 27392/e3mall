package cn.haohaoli.content.service.impl;

import cn.haohaoli.common.jedis.JedisClient;
import cn.haohaoli.content.service.TbContentService;
import cn.haohaoli.mapper.TbContentMapper;
import cn.haohaoli.model.TbContent;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.Condition;
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
 * @date 2018/8/9 16:03
 */
@Service
public class TbContentServiceImpl extends ServiceImpl<TbContentMapper,TbContent> implements TbContentService {

    @Autowired
    private JedisClient jedisClient;

    @Value("${redis.user.contentList.key}")
    private String contentListKey;

    @Autowired
    private TbContentMapper tbContentMapper;

    @Override
    public boolean insert(TbContent entity) {
        boolean b = super.insert(entity);
        //缓存同步
        try {
            if (b) {
                jedisClient.hdel(contentListKey, entity.getCategoryId() + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public boolean deleteById(Serializable id) {
        TbContent tbContent = super.selectById(id);
        boolean b = super.deleteById(id);
        //缓存同步
        try {
            if (b) {
                jedisClient.hdel(contentListKey, tbContent.getCategoryId() + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    public List<TbContent> selectListByCategoryId(long categoryId) {
        List<TbContent> contentList = null;
        //查询缓存
        try {
            String cacheContent = jedisClient.hget(contentListKey, categoryId + "");
            if (!StringUtils.isEmpty(cacheContent)) {
                contentList = JSON.parseArray(cacheContent, TbContent.class);
                return contentList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //不存在则查询数据库
        contentList = tbContentMapper.selectList((new EntityWrapper<TbContent>().eq("category_id", categoryId)));
        //添加到缓存
        try {
            jedisClient.hset(contentListKey,categoryId + "", JSON.toJSONString(contentList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return contentList;
    }
}
