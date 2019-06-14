package test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import javax.jms.*;
import java.io.IOException;


public class Teste {
    @Test
    public  void show() throws JMSException {
        // 创建一个config 工厂对象
        ConnectionFactory factory=new ActiveMQConnectionFactory("tcp://47.107.84.1:61616");
       //创建一个连接对象
        Connection connection = factory.createConnection();
        connection.start();
        //用连接对象创建一个sesssion对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个目的地对象 这里用目的地的子类点对点对象
        Queue queue=session.createQueue("queue");
        //用session创建一个 生产者对象
        MessageProducer producer = session.createProducer(queue);
        //创建一个message  是jason格式的发送数据
        TextMessage message=new ActiveMQTextMessage();
        message.setText("point to point");
        //发送消息
        producer.send(message);
        producer.close();
        session.close();
        connection.close();
    }

@Test
        public  void show2() throws Exception {
            // 创建一个config 工厂对象
            ConnectionFactory factory=new ActiveMQConnectionFactory("tcp://47.107.84.1:61616");
            //创建一个连接对象
            Connection connection = factory.createConnection();
            connection.start();
            //用连接对象创建一个sesssion对象
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            //创建目的地对象
            Queue queue=session.createQueue("queue");
            //创建消费者对象
            MessageConsumer consumer = session.createConsumer(queue);
           //创建消费者监听器对象 监听消息中间件 里面一个叫queue的消息
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    if (message instanceof TextMessage){
                        TextMessage textMessage= (TextMessage) message;
                        try {
                            System.out.println("接受到了数据，数据如下");
                            System.out.println(textMessage.getText());
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            //为了演示 设置一个睡眠 最粗浅的睡眠方式
            //等待接受数据
    System.out.println("等待接收数据");
            System.in.read();
            consumer.close();
            session.close();
            connection.close();
        }
    @Test
    public  void show3() throws JMSException {
        // 创建一个config 工厂对象
        ConnectionFactory factory=new ActiveMQConnectionFactory("tcp://47.107.84.1:61616");
        //创建一个连接对象
        Connection connection = factory.createConnection();
        connection.start();
        //用连接对象创建一个sesssion对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建一个目的地对象 这里用目的地的子类点对点对象

        Topic topic= session.createTopic("topic test");
        //用session创建一个 生产者对象
        MessageProducer producer = session.createProducer(topic);
        //创建一个message  是jason格式的发送数据
        TextMessage message=new ActiveMQTextMessage();
        message.setText("point to point");
        //发送消息
        producer.send(message);
        producer.close();
        session.close();
        connection.close();
    }
    @Test
    public  void show4() throws Exception {
        // 创建一个config 工厂对象
        ConnectionFactory factory=new ActiveMQConnectionFactory("tcp://47.107.84.1:61616");
        //创建一个连接对象
        Connection connection = factory.createConnection();
        connection.start();
        //用连接对象创建一个sesssion对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
       Topic topic=session.createTopic("topic test");
        MessageConsumer consumer = session.createConsumer(topic);
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message instanceof TextMessage){
                    TextMessage textMessage= (TextMessage) message;
                    try {
                        System.out.println("接受到了数据，数据如下1");
                        System.out.println(textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        System.out.println("等待接收数据1");
        System.in.read();
        consumer.close();
        session.close();
        connection.close();


    }
    }
