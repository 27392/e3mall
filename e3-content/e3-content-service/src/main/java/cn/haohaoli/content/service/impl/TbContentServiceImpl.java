package cn.haohaoli.content.service.impl;

import cn.haohaoli.content.service.TbContentService;
import cn.haohaoli.mapper.ContentMapper;
import cn.haohaoli.model.TbContent;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Liwenhao
 * @date 2018/8/9 16:03
 */
@Service
public class TbContentServiceImpl extends ServiceImpl<ContentMapper,TbContent> implements TbContentService {
}
