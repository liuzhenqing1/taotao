package test.com.taotao.contente.service.impl; 

import org.junit.Test; 
import org.junit.Before; 
import org.junit.After;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;


/** 
* MyTest Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 28, 2019</pre> 
* @version 1.0 
*/ 
public class MyTestTest { 

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception {

} 
@Test
    public void show(){
    Jedis jedis=new Jedis("47.107.84.1",6379);

    jedis.set("key3","zhaomin");
    String key1 = jedis.get("key3");
    System.out.println(key1);

}
    @Test
    public void show1(){
        Set<HostAndPort> sets= new HashSet<HostAndPort>();
        sets.add(new HostAndPort("192.168.237.102",7001));
        sets.add(new HostAndPort("192.168.237.102",7002));
        sets.add(new HostAndPort("192.168.237.102",7003));
        sets.add(new HostAndPort("192.168.237.102",7004));
        sets.add(new HostAndPort("192.168.237.102",7005));
        sets.add(new HostAndPort("192.168.237.102",7006));

        JedisCluster cluster=new JedisCluster(sets);
        cluster.set("key4","zhangsan");
        String key3 = cluster.get("key4");
        System.out.println(key3);
    }

} 
