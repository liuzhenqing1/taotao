package com.taotao.item.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;
@Controller
public class HtmlGenController {
    @Autowired
    private FreeMarkerConfig freeMarkerConfig;
    @RequestMapping("/genhtml")
    public String genHtml() throws Exception {
        Configuration configuration = freeMarkerConfig.getConfiguration();

        Template template = configuration.getTemplate("demo2.ftl");
        Map map=new HashMap();
        map.put("hello","1000");
        Writer writer=new FileWriter(new File("E:/static/demo2.html"));
        template.process(map,writer);
        writer.close();
        return "ok";
    }

}
