package com.taotao.mapper;


import com.taotao.pojo.TbUser;
import org.apache.ibatis.annotations.Select;

public interface TbUserMapper {
    @Select("select * from tbuser where userName=#{userName}")
    TbUser checkUserName(String param);
    @Select("select * from tbuser where phone=#{phone}")
    TbUser checkPhone(String param);
    @Select("select * from tbuser where email=#{email}")
    TbUser checkEmail(String param);


    void insert(TbUser tbUser);

    TbUser checkUserNameAndPwd(String userName, String passWord);
}