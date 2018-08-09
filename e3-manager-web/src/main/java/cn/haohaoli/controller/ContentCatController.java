package cn.haohaoli.controller;

import cn.haohaoli.content.service.TbContentCategoryService;
import cn.haohaoli.model.TbContentCategory;
import cn.haohaoli.pojo.TreeNode;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
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
}
