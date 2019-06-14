package com.taotao.controller;


import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class MyTest {

    public static void main(String[] args) throws IOException {
        //创建FTP客户端对象， 用客户端对象来上传图片
        FTPClient client=new FTPClient();
        client.connect("192.168.237.128");
        //登录
        client.login("ftpuser","ftpuser");
        //需要上传的类型
        // 需要一个流对象
        //

        //1.创建FTP客户端对象 用客户端对象来上传图片
        //2.使用FTP客户端对象去连接FTP服务器
        //3使用FTp客户端对像登录
//解决上传图片出现0kb
         client.enterLocalPassiveMode();
        client.setFileType(FTP.BINARY_FILE_TYPE);
        FileInputStream fis=new FileInputStream(new File("E:/1.jpg"));
        client.storeFile("/home/ftpuser/www/images/abc.jpg",fis);
        client.logout();
    }

}
