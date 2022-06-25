package com.netdisk.baiguo.dao;

import com.netdisk.baiguo.domain.OperationLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationLogDao {
    @Insert("insert into operation_log(type,content,user_id)value(#{type},#{content},#{user.id})")
    int insertOperationLog(OperationLog operationLog);

    @Select("select id,type,content,date_time from operation_log where user_id = #{id} order by id desc limit 0,20")
    List<OperationLog> selectOperationLog(int id);
}
