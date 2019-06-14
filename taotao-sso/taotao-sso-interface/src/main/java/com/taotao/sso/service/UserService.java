package com.taotao.sso.service;

import com.taotao.pojo.TbUser;
import com.taotao.result.TaotaoResult;

public interface UserService {
    /**
     * 检查用户信息
     * @param param 用户名，手机号，邮箱
     * @param type 1，2，3分别代表用户名，手机号，邮箱
     * @return 返回TaotaoResult status：200为成功，400位失败；msg：返回信息；data：返回数据
     */
    TaotaoResult checkUser(String param,int type);

    /**
     * 注册用户
     * @param tbUser  用户信息包括：用户名，密码，手机号，邮箱
     * @return TaotaoResult status：200为成功，400位失败；msg：返回信息；data：返回数据
     */
    TaotaoResult creatUser(TbUser tbUser);

    /**
     * 用户登录
     * @param userName 用户名
     * @param passWords 密码要进行MD5加密处理
     * @return TaotaoResult status：200为成功，400位失败；msg：返回信息；data：返回数据
     */
    TaotaoResult login(String userName,String passWords);

    /**
     * 根据token获取用户信息
     * @param token token
     * @return TaotaoResult status：200为成功，400位失败；msg：返回信息；data：返回数据
     */
    TaotaoResult getUserByToken(String token);

    /**
     * 根据token删除redis中指定的数据
     * @param token 用户token
     * @return 删除指定token下的用户信息，或者该token没有存在
     */
    TaotaoResult logoutUser(String token);
}
