package com.huang.Dao;


import com.huang.pojo.SysSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SysMapper {
    SysSetting search(int user_id);
    void UpdateSetting(int user_id,int otherblog);
    void insert(int user_id,int otherblog);
}
