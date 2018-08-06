package cn.haohaoli.controller;

import cn.haohaoli.model.TbItem;
import cn.haohaoli.pojo.GridResult;
import cn.haohaoli.service.TbItemService;
import cn.haohaoli.utils.IDUtils;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author Liwenhao
 * @date 2018/8/4 19:36
 */
@RestController
@RequestMapping("/item")
public class TbItemController {

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
    public void insertItem(TbItem tbItem,String desc){
        boolean insert = tbItemService.insert(tbItem, desc);
        System.out.println(tbItem);
    }
}

