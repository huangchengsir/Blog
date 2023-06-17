package com.huang.service.Imp;

import com.huang.service.AfterSaleSearch;
import org.springframework.stereotype.Service;

@Service
public class AfterSaleSearchImp implements AfterSaleSearch {
    @Override
    public String status(int type) {
        String status="";
        if(type==600){
            status="同意维修";
        } else if (type==602) {
            status="602同意维修";
        }
        else if (type==603) {
            status="603拒接维修";
        }
        else if (type==612) {
            status="拒接维修";
        }else {
            status="直接退款";
        }
        return status;
    }
}
