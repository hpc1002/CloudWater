package com.it.cloudwater.bean;

import java.util.ArrayList;

/**
 * Created by hpc on 2017/9/16.
 */

public class BannerBean {
    public Result result;
    public String resCode;
    public class Result {
        public int nTotal;
        public ArrayList<DataList> dataList;
        public class DataList {
            public long lId;
            public String strActivityName;
            public String strImgUrl;
        }

    }
}
