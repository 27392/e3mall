package cn.haohaoli.controller;

import cn.haohaoli.content.service.TbContentService;
import cn.haohaoli.model.TbContent;
import cn.haohaoli.pojo.E3Result;
import cn.haohaoli.pojo.GridResult;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Liwenhao
 * @date 2018/8/9 18:55
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    @Resource
    private TbContentService tbContentService;

    @PostMapping("/save")
    public E3Result insert(TbContent tbContent){
        tbContent.setCreated(new Date());
        tbContent.setUpdated(new Date());
        boolean b = tbContentService.insert(tbContent);
        if (!b) {
            return E3Result.error("添加失败！");
        }
        return E3Result.ok();
    }

    @GetMapping("/query/list")
    public GridResult<TbContent> insert(long categoryId, int page, int rows){
        Page<TbContent> tbContentPage = tbContentService.selectPage(new Page<>(page, rows),
                new EntityWrapper<TbContent>().eq("category_id", categoryId));
        return new GridResult<>(tbContentPage.getTotal(),tbContentPage.getRecords());
    }

    @PostMapping("/delete")
    public E3Result insert(long ids){
        boolean b = tbContentService.deleteById(ids);
        if (!b) {
            return E3Result.error("删除失败");
        }
        return E3Result.ok();
    }

}
