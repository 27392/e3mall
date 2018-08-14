package cn.haohaoli.item.controller;

import cn.haohaoli.item.pojo.Item;
import cn.haohaoli.model.TbItem;
import cn.haohaoli.model.TbItemDesc;
import cn.haohaoli.service.TbItemDescService;
import cn.haohaoli.service.TbItemService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @author Liwenhao
 * @date 2018/8/14 21:51
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Resource
    private TbItemService tbItemService;

    @Resource
    private TbItemDescService tbItemDescService;

    @GetMapping("/{itemId}")
    public String selectItem(@PathVariable Long itemId,Model model){
        TbItem tbItem = tbItemService.selectById(itemId);
        Item item = new Item(tbItem);
        TbItemDesc tbItemDesc = tbItemDescService.selectById(itemId);
        model.addAttribute("item", item);
        model.addAttribute("itemDesc", tbItemDesc);
        return "item";
    }
}
