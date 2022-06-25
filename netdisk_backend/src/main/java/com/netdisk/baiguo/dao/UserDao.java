package com.netdisk.baiguo.dao;

import com.netdisk.baiguo.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    @Insert("insert into user(e_mail,password)value(#{eMail},#{password})")
    @Options(useGeneratedKeys = true,keyProperty = "id")
    int insertUser(User user);

    @Select("select * from user where id=#{id} and status=1")
    User selectUser(int id);

    @Update("update user set status=0 where id=#{id}")
    int updateTo0(int id);//删除用户

    @Select("select * from user where e_mail=#{email} and status=1")
    User selectByEmail(String email);
}
