package com.netdisk.baiguo.dao;

import com.netdisk.baiguo.domain.FileDir;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDirDao {
    @Insert("insert into file_dir(name,parent_id,level,user_id,path,size,type,gen_path)value(#{name},#{parentId},#{level},#{user.id},#{path},#{size},#{type},#{genPath})")
    int insertFileDir(FileDir fileDir);

    @Insert("insert into file_dir(name,user_id,type)value(#{name},#{user.id},0)")
    int insertRootFileDir(FileDir fileDir);

    @Update("update file_dir set name=#{name} where id=#{id} and status=1 and user_id=#{user.id}")
    int updateName(FileDir fileDir);

    @Update("update file_dir set parent_id=#{parentDir.id},path=#{path},level=#{level} where id=#{id} and status=1")
    int updateParent(FileDir fileDir);

    @Update("update file_dir set status=#{status} where id=#{id}")
    int updateStatus(int id, int status);

    @Select("select id,name,type,level,path,parent_id,size,create_time from file_dir where user_id=#{userId} and parent_id=#{parentId} and status=#{status}")
    List<FileDir> selectByParent(int userId, int status, int parentId);

    @Select("select id,name,type,level,path,parent_id,size,gen_path,user_id,create_time from file_dir where id=#{id} and status>0")
    @Results({
            @Result(column = "id", id = true, property = "id"),
            @Result(column = "user_id", property = "user",
                    one = @One(select = "com.netdisk.baiguo.dao.UserDao.selectUser"))
    })
    FileDir selectById(int id);

    @Select("select id,name,type,path,size from file_dir where id=#{id}")
    FileDir selectSimpleById(int id);

    @Select("select id,level from file_dir where user_id=#{userId} and level=1 and status=1")
//level为2表示第二级也就是根目录下的文件
    FileDir selectRoot(int userId);
}
