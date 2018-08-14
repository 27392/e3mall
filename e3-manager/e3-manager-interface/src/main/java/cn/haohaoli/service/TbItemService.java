package cn.haohaoli.service;

import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.model.TbItem;
import com.baomidou.mybatisplus.service.IService;

/**
 * @author Liwenhao
 * @date 2018/8/4 19:44
 */
public interface TbItemService extends IService<TbItem> {

    E3Result insert(TbItem tbItem, String desc);
}
