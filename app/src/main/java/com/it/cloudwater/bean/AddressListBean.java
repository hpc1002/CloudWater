package com.it.cloudwater.bean;

import java.util.ArrayList;

/**
 * Created by hpc on 2017/8/16.
 */

public class AddressListBean {
    public Result result;
    public String resCode;

    public class Result {
        public int nTotal;
        public ArrayList<DataList> dataList;
        public class DataList{
            public long lId;
            public int lShopId;
            public int lUserid;
            public String strReceiptusername;
            public String strReceiptmobile;
            public String strLocation;
            public String strNeighbourhood;
            public String strShopname;
            public int nIsdefault;
            public String strDetailaddress;
            public long dtCreatetime;
            public boolean isSingle;
        }
    }

}
