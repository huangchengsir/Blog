package com.huang.Dao;


import com.huang.pojo.returnList.OrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderMapper {
    List<OrderInfo> searchAll();
}
