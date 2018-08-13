package cn.haohaoli.controller;

import cn.haohaoli.common.pojo.E3Result;
import cn.haohaoli.search.service.SearchItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Liwenhao
 * @date 2018/8/13 15:37
 */
@RestController
public class SearchItemController {

    @Resource
    private SearchItemService searchService;

    @PostMapping("/index/item/import")
    public E3Result importSearchItemList(){
        return searchService.importAllItems();
    }
}
