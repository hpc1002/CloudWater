package com.it.cloudwater.bean;

import java.util.ArrayList;

/**
 * Created by hpc on 2017/9/19.
 */

public class MessageListBean {
    public Result result;
    public String resCode;

    public class Result {
        public Integer nTotal;
        public ArrayList<DataList> dataList;

        public class DataList {
            public long lId;
            public String strNoticeCon;
            public String strNoticeTypeName;
            public long dtCreateTime;
        }
    }
}
