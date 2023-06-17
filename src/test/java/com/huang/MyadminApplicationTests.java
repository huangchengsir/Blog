package com.huang;

import com.huang.Dao.OrderMapper;
import com.huang.pojo.returnList.OrderInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private OrderMapper orderMapper;
    @Test
    void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
        List<OrderInfo> list = orderMapper.searchAll();
        for (OrderInfo info : list) {
            System.out.println(info);
        }
    }

}
