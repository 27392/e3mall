package cn.haohaoli.content.service.impl;

import cn.haohaoli.content.service.TbContentCategoryService;
import cn.haohaoli.mapper.TbContentCategoryMapper;
import cn.haohaoli.model.TbContentCategory;
import cn.haohaoli.common.pojo.E3Result;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author Liwenhao
 * @date 2018/8/9 16:15
 */
@Service
public class TbContentCategoryServiceImpl extends ServiceImpl<TbContentCategoryMapper,TbContentCategory> implements TbContentCategoryService {

    @Autowired
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Transactional()
    @Override
    public E3Result insert(long parentId, String name) {
        TbContentCategory parent = tbContentCategoryMapper.selectById(parentId);
        if (parent == null) {
            return E3Result.error("找不到父类");
        }
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setParentId(parentId);
        //1正常,2删除
        contentCategory.setStatus(1);
        //默认排序1
        contentCategory.setSortOrder(1);
        //是否是父类节点 1是,0否
        contentCategory.setIsParent(0);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        Integer rows = tbContentCategoryMapper.insert(contentCategory);
        if (rows != 1) {
            return E3Result.error("插入失败");
        }
        //如果不是父类修改为父类
        if (parent.getIsParent() == 0) {
            parent.setIsParent(1);
            Integer j = tbContentCategoryMapper.updateById(parent);
            if(j != 1) {
                return E3Result.error("更新失败");
            }
        }
        return  E3Result.ok(contentCategory);
    }
}
