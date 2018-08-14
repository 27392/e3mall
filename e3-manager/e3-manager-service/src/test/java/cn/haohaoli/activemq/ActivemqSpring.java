package cn.haohaoli.activemq;

import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author Liwenhao
 * @date 2018/8/14 18:18
 */
public class ActivemqSpring {
    JmsTemplate bean;
    Destination destination;
    @Before
    public void before() throws Exception{
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-activemq.xml");
        bean = applicationContext.getBean(JmsTemplate.class);
        destination = (Destination) applicationContext.getBean("topicDestination");
    }


    @Test
    public void sendMessage() throws Exception {

        bean.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("send active message");
            }
        });
    }

    @Test
    public void sendItemAdd() throws Exception {

        bean.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("send active message");
            }
        });
    }
}
