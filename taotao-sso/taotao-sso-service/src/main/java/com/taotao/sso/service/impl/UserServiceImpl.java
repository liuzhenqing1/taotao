package com.taotao.sso.service.impl;

import com.sun.corba.se.impl.oa.toa.TOA;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.result.TaotaoResult;
import com.taotao.sso.jedis.JedisClient;
import com.taotao.sso.service.UserService;

import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import redis.clients.jedis.Jedis;


import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Value("${USER_INFO}")
    private String USER_INFO;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Autowired
  private TbUserMapper tbUserMapper;
  @Autowired
  private JedisClient  jedisClient;
    @Override
    public TaotaoResult checkUser(String param, int type) {
        if(type==1){
            TbUser tbUser = tbUserMapper.checkUserName(param);
            if (tbUser!=null){
                return TaotaoResult.build(400,"已存在此账号",false);
            }
        }else if (type==2){
            TbUser tbUser = tbUserMapper.checkPhone(param);
            if (tbUser!=null){
            return TaotaoResult.build(400,"已存在此手机号",false);
        }
        }else if(type==3){
            TbUser tbUser = tbUserMapper.checkEmail(param);
            if (tbUser!=null){
                return TaotaoResult.build(400,"已存在此邮箱",false);
            }
        }else{
            return TaotaoResult.build(400,"非法参数",false);
        }
        return TaotaoResult.ok(true);
    }

    @Override
    public TaotaoResult creatUser(TbUser tbUser) {
       if(StringUtils.isBlank(tbUser.getUserName())){
           return TaotaoResult.build(400,"账号为空",false);
       }
        if (StringUtils.isBlank(tbUser.getPassWord())){
           return TaotaoResult.build(400,"密码为空",false);
        }
        if (StringUtils.isBlank(tbUser.getPhone())){
            return TaotaoResult.build(400,"手机号为空",false);
        }
        if (StringUtils.isBlank(tbUser.getEmail())){
            return TaotaoResult.build(400,"邮箱为空",false);
        }
        TaotaoResult result = checkUser(tbUser.getUserName(), 1);
        if (!(boolean)result.getData()){
            return TaotaoResult.build(400,"账号已经被使用");
        }
        TaotaoResult result1 = checkUser(tbUser.getPhone(), 2);
        if (!(boolean)result1.getData()){
            return TaotaoResult.build(400,"手机号已经被使用");
        }
        TaotaoResult result2 = checkUser(tbUser.getEmail(), 3);
        if (!(boolean)result2.getData()){
            return TaotaoResult.build(400,"邮箱已经被使用");
        }
        Date date =new Date();
        tbUser.setCreated(date);
        tbUser.setUpdated(date);
        tbUser.setPassWord(DigestUtils.md5DigestAsHex(tbUser.getPassWord().getBytes()));
        tbUserMapper.insert(tbUser);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult login(String userName, String passWord) {
    if(StringUtils.isBlank(userName)){
        return TaotaoResult.build(400,"用户名不能为空");
    }
    if(StringUtils.isBlank(passWord)){
            return TaotaoResult.build(400,"密码不能为空");
        }
        TbUser user = tbUserMapper.checkUserNameAndPwd(userName,DigestUtils.md5DigestAsHex(passWord.getBytes()));
    if (user==null){
      return TaotaoResult.build(400,"账号密码有误,请重新输入");

    }
        user.setPassWord(null);
        String token= UUID.randomUUID().toString().replace("-","");
    jedisClient.set(USER_INFO+":"+token, JsonUtils.objectToJson(user));
    jedisClient.expire(USER_INFO+":"+token,SESSION_EXPIRE);
        return TaotaoResult.ok(token);
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String json = jedisClient.get(USER_INFO+":"+token);
        if (StringUtils.isBlank(json)){
            return TaotaoResult.build(400,"用户没有登录",false);
        }
        jedisClient.expire(USER_INFO+":"+token,SESSION_EXPIRE);
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        return TaotaoResult.ok(tbUser);
    }

    @Override
    public TaotaoResult logoutUser(String token) {
        //rows：条数
        Long rows = jedisClient.del(USER_INFO + ":" + token);
        if (rows==0){
            return TaotaoResult.build(400,"没有找到该用户",false);
        }
        return TaotaoResult.ok();
    }


}

