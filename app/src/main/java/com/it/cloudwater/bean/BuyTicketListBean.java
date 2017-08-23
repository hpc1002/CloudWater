package com.it.cloudwater.bean;

import java.util.ArrayList;

/**
 * Created by hpc on 2017/8/15.
 */

public class BuyTicketListBean {
    public String resCode;
    public Result result;

    public class Result {
        public ArrayList<DataList> dataList;
        public int nTotal;

        public class DataList {
            public int lId;
            public String strGoodsName;
            public String strGoodsimgurl;
            public String strRemarks;
            public long dExpire;
            public int nMonthCount;
            public int nOldPrice;
            public int nPrice;
            public ArrayList<Ticketcontents> ticketcontents;

            public class Ticketcontents {

            }
        }
    }
}
