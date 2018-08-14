package cn.haohaoli.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * @author Liwenhao
 * @date 2018/8/13 19:11
 */
public class ActiveMQTest {

    /**
     * 点到点形式发送消息
     * @throws Exception
     */
    @Test
    public void testQuqueProducer () throws Exception {
        //创建一个连接工厂对象，需要指定服务的ip以及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://118.89.26.70:61616");
        //使用工厂对象创建Connection对象
        Connection connection = connectionFactory.createConnection();
        //开启连接Connection对象的start方法
        connection.start();
        //创建一个Session对象
        //参数：
        //  是否开启事务,如果开启事务第二个参数无意义,一般不开启事务
        //  应答模式,一般是自动应答或者手动应答，一般是自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用Session对象创建一个Destination对象 两种形式queue、topic
        Queue queue = session.createQueue("test-queue");
        //使用Session创建一个Producer对象
        MessageProducer producer = session.createProducer(queue);
        //创建一个消息Message对象 可以使用TextMessage
        /*TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("hello ActiveMQ");*/
        TextMessage message = session.createTextMessage("hello ActiveMQ");
        //发送消息
        producer.send(message);
        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testQueueConsumer() throws Exception{
        //创建一个ConnectionFactory对象连接mq服务器
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://118.89.26.70:61616");
        //创建一个连接对象
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用Connection对象创建一个Session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个Destination对象queue对象
        Queue queue = session.createQueue("test-queue");
        //使用Session对象创建一个消费者对象
        MessageConsumer consumer = session.createConsumer(queue);
        //接收消息
        consumer.setMessageListener(new MessageListener(){

            @Override
            public void onMessage(Message message) {
                TextMessage textMessag = (TextMessage) message;
                try {
                    String text = textMessag.getText();
                    System.out.println(text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //打印消息
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    @Test
    public void testTopicProducer () throws Exception {
         //创建一个连接工厂对象，需要指定服务的ip以及端口
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://118.89.26.70:61616");
        //使用工厂对象创建Connection对象
        Connection connection = connectionFactory.createConnection();
        //开启连接Connection对象的start方法
        connection.start();
        //创建一个Session对象
        //参数：
        //  是否开启事务,如果开启事务第二个参数无意义,一般不开启事务
        //  应答模式,一般是自动应答或者手动应答，一般是自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用Session对象创建一个Destination对象 两种形式queue、topic
        Topic topic = session.createTopic("test-topic");
        //使用Session创建一个Producer对象
        MessageProducer producer = session.createProducer(topic);
        //创建一个消息Message对象 可以使用TextMessage
        /*TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("hello ActiveMQ");*/
        TextMessage message = session.createTextMessage("topic message");
        //发送消息
        producer.send(message);
        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }
}
