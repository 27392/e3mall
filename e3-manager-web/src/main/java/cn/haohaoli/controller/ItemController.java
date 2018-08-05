package cn.haohaoli.controller;

import cn.haohaoli.model.TbItem;
import cn.haohaoli.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Liwenhao
 * @date 2018/8/4 19:36
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/{itemId}")
    private TbItem getItemById(@PathVariable Long itemId) {
        return  itemService.getItemById(itemId);
    }
}

