package cn.haohaoli.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Liwenhao
 * @date 2018/8/15 17:13
 */
@Controller
public class HtmlGenController {

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    @GetMapping("/genhtml")
    public String genHtml() throws Exception {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Template template = configuration.getTemplate("hello.ftl");
        Map map = new HashMap<>();
        map.put("hello", "测试");
        Writer writer = new FileWriter("C:\\Users\\wenha\\Desktop\\hello.html");
        template.process(map,writer);
        writer.close();
        return "ok";
    }
}
