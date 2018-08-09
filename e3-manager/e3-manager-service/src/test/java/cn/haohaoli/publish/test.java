package cn.haohaoli.publish;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Liwenhao
 * @date 2018/8/9 15:30
 */
public class test {

    @Test
    public void publishServer() throws InterruptedException {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/spring-*.xml");

        while (true){
            Thread.sleep(1000);
        }
    }
}
