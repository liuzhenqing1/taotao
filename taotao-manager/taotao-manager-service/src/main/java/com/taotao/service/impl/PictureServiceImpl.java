package com.taotao.service.impl;

import com.taotao.result.PictureResult;
import com.taotao.service.PictureService;
import com.taotao.utils.FtpUtil;
import com.taotao.utils.IDUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
@Service
public class PictureServiceImpl implements PictureService{
    @Value("${FTP_ADDRESS}")
    private String FTP_ADDRESS;
    @Value("${FTP_PORT}")
    private Integer FTP_PORT;
    @Value("${FTP_USERNAME}")
    private String FTP_USERNAME;
    @Value("${FTP_PASSWORD}")
    private String FTP_PASSWORD;
    @Value("${FILI_UPLOAD_PATH}")
    private String FILI_UPLOAD_PATH;
    @Value("${IMAGE_BASE_URL}")
    private String IMAGE_BASE_URL;
    @Override
    public PictureResult uploadFile(byte[] bytes, String name) {
    PictureResult result=new PictureResult();
    try{
        ByteArrayInputStream bis=new ByteArrayInputStream(bytes);
        String newName= IDUtils.genImageName()+name.substring(name.lastIndexOf("."));
        String filepath = new DateTime().toString("yyyy/MM/dd");
        FtpUtil.uploadFile(FTP_ADDRESS,FTP_PORT,FTP_USERNAME,FTP_PASSWORD,FILI_UPLOAD_PATH,filepath,newName,bis);
        result.setError(0);
        result.setUrl(IMAGE_BASE_URL+"/"+filepath+"/"+newName);
        result.setMessage("上传成功");
    }catch (Exception ex){
        result.setError(1);

        result.setMessage("上传失败");
    }

        return result;
    }
}
