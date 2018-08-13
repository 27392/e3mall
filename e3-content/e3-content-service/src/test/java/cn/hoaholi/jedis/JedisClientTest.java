package cn.hoaholi.jedis;

import cn.haohaoli.common.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Liwenhao
 * @date 2018/8/11 21:00
 */
public class JedisClientTest {

    @Test
    public void testJedisClient() throws Exception{
        //获取spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-redis.xml");
        //从容器中获取jedisClient对象
        JedisClient bean = applicationContext.getBean(JedisClient.class);
        bean.set("cluster12", "redis cluster");
        System.out.println(bean.get("cluster12"));
        bean.del("cluster12");
    }

}
