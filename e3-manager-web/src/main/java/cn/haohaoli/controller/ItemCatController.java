package cn.haohaoli.controller;

import cn.haohaoli.model.TbItemCat;
import cn.haohaoli.common.pojo.TreeNode;
import cn.haohaoli.service.TbItemCatService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类控制器
 * @author Liwenhao
 * @date 2018/8/6 12:59
 */
@RestController
@RequestMapping("/item/cat")
public class ItemCatController {

    @Resource
    private TbItemCatService tbItemCatService;

    @PostMapping("/list")
    public List<TreeNode> selectCatList(@RequestParam(name="id", required = false, defaultValue = "0") long parentId){
        List<TbItemCat> tbItemCatList = tbItemCatService.selectList(new EntityWrapper<TbItemCat>().eq("parent_id", parentId));
        List<TreeNode> tree = new ArrayList<>();
        for (TbItemCat itemCat : tbItemCatList) {
            TreeNode treeNode = new TreeNode();
            treeNode.setId(itemCat.getId());
            treeNode.setText(itemCat.getName());
            treeNode.setState(itemCat.getIsParent() == 1 ? "closed" : "open");
            tree.add(treeNode);
        }
        return tree;
    }
}
