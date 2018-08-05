package cn.haohaoli.service.impl;

import cn.haohaoli.mapper.TbItemMapper;
import cn.haohaoli.model.TbItem;
import cn.haohaoli.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Liwenhao
 * @date 2018/8/4 19:46
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper tbItemMapper;

    @Override
    public TbItem getItemById(long itemId) {
        return tbItemMapper.selectById(itemId);
    }
}
