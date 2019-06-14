package test;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class MyTestTest {

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

@Test
    public void show() throws IOException, SolrServerException {
    SolrServer solrServer=new HttpSolrServer("http://47.107.84.1:8080/solr");
    SolrInputDocument document=new SolrInputDocument();
    document.addField("id","test001");
    document.addField("item_title","华为运动手表");
    document.addField("item_price","1000");
    document.addField("item_sell_point","防水，可上网，使用app");
    solrServer.add(document);
    solrServer.commit();
}

    @Test
    public void show1() throws IOException, SolrServerException {
        SolrServer solrServer=new HttpSolrServer("http://47.107.84.1:8080/solr");
        SolrQuery query=new SolrQuery();
       //设置查询条件
        query.setQuery("华为");
        //设置默认搜索域
        query.set("df","item_keywords");
        query.setHighlight(true);
        query.addHighlightField("item_title");
        query.setHighlightSimplePre("<em style='color:red'>");
        query.setHighlightSimplePost("</em>");
        //执行查询得到 一个response对象
        QueryResponse response = solrServer.query(query);
        //取高亮显示 对象
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        SolrDocumentList documents = response.getResults();
        long numFound = documents.getNumFound();
        System.out.println("商品总数"+numFound);
        //遍历结果并打印
        for (SolrDocument document:documents) {
            System.out.println(document.get("id"));
            List<String> list = highlighting.get(document.get("id")).get("item_title");
            //判断list集合是否为空
            String itemTitle=null;
            if (list!=null&list.size()>0){
                itemTitle=list.get(0);
            }else {
                itemTitle=(String)document.get("item_title");
            }
            System.out.println("商品名称为："+itemTitle);
        }
    }

    @Test
    public void show4() throws JMSException {
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


    }
} 
