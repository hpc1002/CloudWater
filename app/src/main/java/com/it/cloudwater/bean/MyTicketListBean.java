package com.it.cloudwater.bean;

import java.util.ArrayList;

/**
 * Created by hpc on 2017/8/15.
 */

public class MyTicketListBean {
    public String resCode;
    public Result result;

    public class Result {
        public ArrayList<DataList> dataList;
        public int nTotal;

        public class DataList {
            public long lId;
            public long lGoodsid;
            public int nRemainingCount;
            public String strTicketName;
            public String strExpire;
        }
    }
}
