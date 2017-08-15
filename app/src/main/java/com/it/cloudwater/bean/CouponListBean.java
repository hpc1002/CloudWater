package com.it.cloudwater.bean;

import java.util.ArrayList;

/**
 * Created by hpc on 2017/8/15.
 */

public class CouponListBean {
    public Result result;
    public String resCode;

    public class Result {
        public int nTotal;
        public ArrayList<DataList> dataList;

        public class DataList {
            public int lLd;
            public int lCouponId;
            public String strCouponName;
            public int lUserid;
            public String strUserName;
            public long dExpire;
            public int nPrice;
        }
    }
}
