package com.huang.service.Imp;

import com.huang.Dao.SysMapper;
import com.huang.pojo.SysSetting;
import com.huang.service.SysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SysServiceImp  implements SysService {
    @Autowired
    SysMapper sysMapper;
    @Override
    public SysSetting search(int user_id) {
        return sysMapper.search(user_id);
    }

    @Override
    public void UpdateSetting(int user_id, int otherblog) {
        sysMapper.UpdateSetting(user_id, otherblog);
    }

    @Override
    public void insert(int user_id, int otherblog) {
        sysMapper.insert(user_id, otherblog);
    }
}
