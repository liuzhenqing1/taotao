package test.test.com.taotao.service.impl; 

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import org.springframework.jms.connection.SingleConnectionFactory;


import javax.jms.*;

/** 
* MyTeste Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 2, 2019</pre> 
* @version 1.0 
*/ 
public class MyTesteTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
}
    @Test
    public void show() throws JMSException {
        ConnectionFactory factory=new ActiveMQConnectionFactory("tcp://47.107.84.1:61616");
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue("queue test");

        MessageProducer producer = session.createProducer(queue);
        TextMessage message=new ActiveMQTextMessage();
        message.setText("point to point");
        producer.send(message);
        producer.close();
        session.close();;
        connection.close();
        SingleConnectionFactory connectionFactory=new SingleConnectionFactory();

    }
}


