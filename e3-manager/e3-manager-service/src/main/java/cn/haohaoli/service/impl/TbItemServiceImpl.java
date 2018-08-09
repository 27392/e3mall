package cn.haohaoli.service.impl;

import cn.haohaoli.mapper.TbItemDescMapper;
import cn.haohaoli.mapper.TbItemMapper;
import cn.haohaoli.model.TbItem;
import cn.haohaoli.model.TbItemDesc;
import cn.haohaoli.service.TbItemService;
import cn.haohaoli.utils.IDUtils;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Liwenhao
 * @date 2018/8/4 19:46
 */
@Service
public class TbItemServiceImpl extends ServiceImpl<TbItemMapper,TbItem> implements TbItemService {

    @Resource
    private TbItemMapper tbItemMapper;

    @Resource
    private TbItemDescMapper tbItemDescMapper;

    @Override
    public boolean insert(TbItem tbItem, String desc) {
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
        Integer insert1 = tbItemDescMapper.insert(tbItemDesc);
        return false;
    }
}
