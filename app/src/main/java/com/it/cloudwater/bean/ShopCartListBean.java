package com.it.cloudwater.bean;

import java.util.ArrayList;

/**
 * Created by hpc on 2017/8/22.
 */

public class ShopCartListBean {
    public Result result;
    public String resCode;
    public class Result {
        public int nTotal;
        public ArrayList<DataList> dataList;
        public class DataList{
            public int lId;
            public int lGoodsId;
            public int nPrice;
            public String strGoodsname;
            public String strGoodsimgurl;
            public String strStandard;
            public long dtCreateTime;
        }
    }
}
