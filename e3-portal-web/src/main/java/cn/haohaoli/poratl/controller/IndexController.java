package cn.haohaoli.poratl.controller;

import cn.haohaoli.content.service.TbContentService;
import cn.haohaoli.model.TbContent;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Liwenhao
 * @date 2018/8/9 19:20
 */
@Controller
public class IndexController {

    @Value("${index.silde.id}")
    private Long sildeId;

    @Resource
    private TbContentService tbContentService;

    @GetMapping("/index")
    public ModelAndView index () {
        List<TbContent> contentList = tbContentService.selectListByCategoryId(sildeId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sildeList", contentList);
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
