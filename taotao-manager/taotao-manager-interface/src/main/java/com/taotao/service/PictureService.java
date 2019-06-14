package com.taotao.service;

import com.taotao.result.PictureResult;

public interface PictureService {
    PictureResult uploadFile(byte[] bytes, String name);
}
