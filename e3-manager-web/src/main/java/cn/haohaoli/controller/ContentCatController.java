package cn.haohaoli.controller;

import cn.haohaoli.content.service.TbContentCategoryService;
import cn.haohaoli.model.TbContentCategory;
import cn.haohaoli.pojo.E3Result;
import cn.haohaoli.pojo.TreeNode;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容分类控制器
 * @author Liwenhao
 * @date 2018/8/9 16:35
 */
@RestController
@RequestMapping("/content/category")
public class ContentCatController {

    @Resource
    private TbContentCategoryService tbContentCategoryService;

    @GetMapping("/list")
    public List<TreeNode> selectContentCatList(@RequestParam(name="id", required = false, defaultValue = "0") long parentId){
        List<TbContentCategory> contentCategoryList = tbContentCategoryService.selectList(new EntityWrapper<TbContentCategory>().eq("parent_id", parentId));
        List<TreeNode> tree = new ArrayList<>();
        for (TbContentCategory tbContentCategory : contentCategoryList) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(tbContentCategory.getId());
            treeNode.setText(tbContentCategory.getName());
            treeNode.setState(tbContentCategory.getIsParent() == 1 ? "closed" : "open");
            tree.add(treeNode);
        }
        return tree;
    }

    @PostMapping("/create")
    public E3Result insert(long parentId,String name){
        return tbContentCategoryService.insert(parentId, name);
    }

    @PostMapping("/update")
    public E3Result updateByPrimaryId(long id,String name){
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategory.setUpdated(new Date());
        boolean b = tbContentCategoryService.updateById(contentCategory);
        if (!b) {
            return E3Result.error("更新失败！");
        }
        return E3Result.ok();
    }

    @PostMapping("/delete")
    public E3Result deleteByPrimaryId(long id){
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(id);
        boolean b = tbContentCategoryService.deleteById(contentCategory);
        if (b) {
            //删除成功之后查询是否有子级
            int childCount = tbContentCategoryService.selectCount(new EntityWrapper<TbContentCategory>().eq("parent_id", id));
            if (childCount != 0) {
                //或者子级列表
                List<TbContentCategory> childList = tbContentCategoryService.selectList(new EntityWrapper<TbContentCategory>().eq("parent_id", id));
                if (childList.size() != 0) {
                    for (TbContentCategory child : childList) {
                        deleteByPrimaryId(child.getId());
                    }
                }
            }
        }
        return E3Result.ok();
    }
}
