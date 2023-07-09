package com.huang.service;

import com.huang.pojo.SysSetting;
import org.springframework.stereotype.Service;

@Service
public interface SysService {
    SysSetting search(int user_id);
    void UpdateSetting(int user_id,int otherblog);
    void insert(int user_id,int otherblog);
}
