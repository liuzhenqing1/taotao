package com.taotao.controller;

import com.taotao.result.PictureResult;
import com.taotao.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/pic")
public class PictureController {
    @Autowired
    private PictureService pictureService;
@RequestMapping("/upload")
@ResponseBody
    public PictureResult upload(MultipartFile uploadFile) throws IOException {
        byte[] bytes = uploadFile.getBytes();
        String filename = uploadFile.getOriginalFilename();
        PictureResult result = pictureService.uploadFile(bytes, filename);

    return result;
    }

}
