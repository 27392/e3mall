package cn.haohaoli.content.service;

import cn.haohaoli.model.TbContentCategory;
import cn.haohaoli.common.pojo.E3Result;
import com.baomidou.mybatisplus.service.IService;

/**
 * @author Liwenhao
 * @date 2018/8/9 16:15
 */
public interface TbContentCategoryService extends IService<TbContentCategory> {

    E3Result insert(long parentId, String name);
}
