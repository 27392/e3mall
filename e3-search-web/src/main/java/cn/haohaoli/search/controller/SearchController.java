package cn.haohaoli.search.controller;

import cn.haohaoli.common.pojo.SearchResult;
import cn.haohaoli.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Liwenhao
 * @date 2018/8/13 18:36
 */
@Controller
public class SearchController {

    @Value("${search.rows}")
    private Integer rows;

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public String selectList(String keyword,
                             @RequestParam(defaultValue = "1") Integer page,
                             Model model) throws Exception {
        keyword = new String(keyword.getBytes("iso-8859-1"),"utf-8");
        SearchResult search = searchService.search(keyword, page, rows);
        model.addAttribute("query",keyword);
        model.addAttribute("totalPages",search.getTotalPages());
        model.addAttribute("page",page);
        model.addAttribute("recordCount",search.getRecordCount());
        model.addAttribute("itemList",search.getItemList());
        return "search";
    }
}
