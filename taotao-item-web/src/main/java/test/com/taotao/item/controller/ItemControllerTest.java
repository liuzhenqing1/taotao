package test.com.taotao.item.controller; 

import com.sun.javafx.collections.MappingChange;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.io.*;
import java.util.*;


/**
* ItemController Tester. 
* 
* @author <Authors name> 
* @since <pre>���� 12, 2019</pre> 
* @version 1.0 
*/ 
public class ItemControllerTest { 
@Test
public  void show() throws Exception {
/**
 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
 第二步：设置模板文件所在的路径。
 第三步：设置模板文件使用的字符集。一般就是utf-8.
 第四步：加载一个模板，创建一个模板对象。
 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
 第七步：调用模板对象的process方法输出文件。
 第八步：关闭流。

 */
    Configuration configuration=new Configuration(Configuration.getVersion());
    configuration.setDirectoryForTemplateLoading(new File("E:\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
    configuration.setDefaultEncoding("UTF-8");
    Template template=configuration.getTemplate("demo2.ftl");
   Student student=new Student(1,"zhangsan",100);

    Writer writer=new FileWriter("E:/static/demo2.html");
    template.process(student,writer);
    writer.close();
}
    @Test
    public  void show3() throws Exception {
/**
 */
        Configuration configuration=new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File("E:\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        configuration.setDefaultEncoding("UTF-8");
        Template template=configuration.getTemplate("demo3.ftl");
        List<Student> studentList=new ArrayList<Student>();
        Student student1=new Student(1,"zhangsan",100);
        Student student2=new Student(2,"lisi",99);
        Student student3=new Student(3,"wangwu",98);
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        Map map=new HashMap();
        map.put("studentList",studentList);
        Writer writer=new FileWriter(new File("E:/static/demo3.html"));
        template.process(map,writer);
        writer.close();
    }


    @Test
    public  void show4() throws Exception {
/**
 */
        Configuration configuration=new Configuration(Configuration.getVersion());
        configuration.setDirectoryForTemplateLoading(new File("E:\\taotao\\taotao-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        configuration.setDefaultEncoding("UTF-8");
        Template template=configuration.getTemplate("demo5.ftl");
        Date date=new Date();
        Map map=new HashMap();
        map.put("test",null);
        Writer writer=new FileWriter(new File("E:/static/demo5.html"));
        template.process(map,writer);
        writer.close();
    }





    @Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
} 

/** 
* 
* Method: showItem(@PathVariable Long itemId, Model model) 
* 
*/ 
@Test
public void testShowItem() throws Exception { 
//TODO: Test goes here... 
}



/**
* 
* Method: showItemDesc(@PathVariable Long itemId) 
* 
*/ 
@Test
public void testShowItemDesc() throws Exception { 
//TODO: Test goes here... 
} 


} 
