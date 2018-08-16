package cn.haohaoli.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

/**
 * @author Liwenhao
 * @date 2018/8/15 11:22
 */
public class TestFreemarker {

    @Test
    public void testFreemarker() throws Exception{
        //创建一个模板文件
        //创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置模板文件保存的目录
        configuration.setDirectoryForTemplateLoading(new File("C:\\Users\\wenha\\Desktop\\分布式项目\\宜立方\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //模板文件的编码格式，一般就是utf-8
        configuration.setDefaultEncoding("utf-8");
        //加载一个模板文件，创建一个模板对象
//        Template template = configuration.getTemplate("hello.ftl");
        Template template = configuration.getTemplate("student.ftl");
        //创建一个数据集，可以是pojo也可以是map
        Map data = new HashMap();
        data.put("hello", "hello freemarker");
        //创建一个pojo对象
        Student student = new Student(1, "赵四", 23, "江西省");
        data.put("student", student);
        //添加一个list
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(1, "赵四", 23, "江西省"));
        studentList.add(new Student(2, "小明", 22, "江西省"));
        studentList.add(new Student(3, "王五", 23, "江西省"));
        studentList.add(new Student(4, "马奇", 24, "江西省"));
        studentList.add(new Student(5, "露丝", 25, "江西省"));
        studentList.add(new Student(6, "赵丽", 26, "江西省"));
        studentList.add(new Student(7, "丽萨", 27, "江西省"));
        studentList.add(new Student(8, "马丹", 28, "江西省"));
        studentList.add(new Student(9, "程栋", 29, "江西省"));
        data.put("studentList", studentList);
        //创建日期类型
        data.put("date", new Date());
        //添加null值
        data.put("null", null);
        //创建一个Writer对象，指定输出文件的路径和文件名
//        Writer writer = new FileWriter("C:\\Users\\wenha\\Desktop\\hello.txt");
        Writer writer = new FileWriter("C:\\Users\\wenha\\Desktop\\student.html");
        //生产静态页面
        template.process(data,writer);
        //关闭流
        writer.close();
    }
}
