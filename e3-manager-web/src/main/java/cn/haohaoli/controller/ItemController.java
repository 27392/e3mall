package cn.haohaoli.controller;

import cn.haohaoli.model.TbItem;
import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.common.pojo.GridResult;
import cn.haohaoli.service.TbItemService;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 商品控制器
 * @author Liwenhao
 * @date 2018/8/4 19:36
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Resource
    private TbItemService tbItemService;

    @GetMapping("/{itemId}")
    private TbItem getItemById(@PathVariable Long itemId) {
        return tbItemService.selectById(itemId);
    }

    /**
     * 获取商品列表(分页)
     * @param page
     * @param rows
     * @return
     */
    @GetMapping("/list")
    private GridResult<TbItem> getItemList(int page, int rows) {
        Page<TbItem> tbItemPage = tbItemService.selectPage(new Page<>(page, rows));
        return new GridResult<>(tbItemPage.getTotal(),tbItemPage.getRecords());
    }

    /**
     * 插入商品
     */
    @PostMapping("/save")
    public E3Result insertItem(TbItem tbItem, String desc){
        return tbItemService.insert(tbItem, desc);
    }
}

