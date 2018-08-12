package cn.haohaoli.content.service;

import cn.haohaoli.model.TbContent;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * @author Liwenhao
 * @date 2018/8/9 16:01
 */
public interface TbContentService extends IService<TbContent> {

    List<TbContent> selectListByCategoryId(long categoryId);
}
