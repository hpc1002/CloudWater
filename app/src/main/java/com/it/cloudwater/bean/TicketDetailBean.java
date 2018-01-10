package com.it.cloudwater.bean;

import java.util.ArrayList;

/**
 * Created by hpc on 2017/8/30.
 */

public class TicketDetailBean {
    public Result result;
    public String resCode;

    public class Result {
        public long lId;
        public long lGoodsid;
        public long dExpire;
        public int nPrice;
        public String strGoodsName;
        public String strGoodsimgurl;
        public ArrayList<TicketContents> ticketcontents;

        public class TicketContents {
            public long lId;
            public int nCount;
            public int nPrice;
            public int indexNum;
            public String strRemarks;
            public String strTicketname;

            public void setIndexNum(int indexNum) {
                this.indexNum = indexNum;
            }

            public int getIndexNum() {
                return indexNum;
            }
        }
    }
}
