package cn.haohaoli.content.service.impl;

import cn.haohaoli.content.service.TbContentCategoryService;
import cn.haohaoli.mapper.ContentCategoryMapper;
import cn.haohaoli.model.TbContentCategory;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Liwenhao
 * @date 2018/8/9 16:15
 */
@Service
public class TbContentCategoryServiceImpl extends ServiceImpl<ContentCategoryMapper,TbContentCategory> implements TbContentCategoryService {
}
