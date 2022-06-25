package com.netdisk.baiguo.dao;

import com.netdisk.baiguo.domain.RecycleBin;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecycleBinDao {
    @Insert("insert into recycle_bin(file_dir_id,user_id) value(#{fileDir.id},#{userId})")
    int insert(RecycleBin recycleBin);

    @Update("update recycle_bin set status=0 where id==#{id}")
    int updateStatus(int id);

    @Select("select * from recycle_bin where user_id==#{userId}")
    @Results({
            @Result(column = "id", id = true, property = "id"),
            @Result(column = "file_dir_id", property = "fileDir",
                    one = @One(select = "com.netdisk.baiguo.dao.FileDirDao.selectSimpleById"))
    })
    List<RecycleBin> selectByUser(int userId);
}
