package com.it.cloudwater.bean;

import java.util.ArrayList;

/**
 * Created by hpc on 2017/7/11.
 */

public class GoodsListBean {
    public Result result;
    public String resCode;

    public class Result {
        public Integer nTotal;
        public ArrayList<DataList> dataList;

        public class DataList {
            public Long lId;
            public Integer nMothnumber;
            public Integer nPrice;
            public Integer nOnline;
            public Integer nStock;
            public String strGoodsimgurl;
            public String strGoodsname;
            public String strIntroduce;
            public String strRemarks;
            public String strStandard;
        }
    }
}
