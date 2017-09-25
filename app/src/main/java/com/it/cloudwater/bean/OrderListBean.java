package com.it.cloudwater.bean;

import java.util.ArrayList;

/**
 * Created by hpc on 2017/8/17.
 */

public class OrderListBean {
    public String resCode;
    public Result result;

    public class Result {
        public ArrayList<DataList> dataList;
        public int nTotal;

        public class DataList {
            public long lId;
            public long lDeliveryid;
            public String strOrdernum;
            public int nState;
            public int nSendState;
            public int lBuyerid;
            public String strBuyername;
            public String strReceiptusername;
            public String strMobile;
            public int nBucketnum;
            public int nBucketmoney;
            public int nCouponPrice;
            public int lMyCouponId;
            public int nFactPrice;
            public int nTotalprice;
            public String strRemarks;
            public String strInvoiceheader;
            public long dtCreatetime;
            public long dtFinishtime;
            public ArrayList<OrderGoods> orderGoods;

            public class OrderGoods {
                public int lGId;
                public int lOrderid;
                public int lGoodsid;
                public String strGoodsname;
                public int nGoodsType;
                public int nPrice;
                public String strGoodsimgurl;
                public int nCount;
                public int nGoodsFactPrice;
                public int nGoodsTotalPrice;
                public int nWatertickets;
            }
        }
    }
}

