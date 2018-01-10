package com.it.cloudwater.bean;

import java.util.ArrayList;

/**
 * Created by hpc on 2017/8/17.
 */

public class OrderDetailBean {
    public String resCode;
    public Result result;
    public class Result{
        public int lId;
        public String strOrdernum;
        public int nState;
        public int lBuyerid;
        public int lAddressid;
        public int nBucketnum;
        public int nBucketmoney;
        public int nCouponPrice;
        public int lMyCouponId;
        public int nFactPrice;
        public int nTotalprice;
        public int nTotalWatertickets;
        public int nTotalWaterticketsPrice;
        public long dtCreatetime;
        public long dtPaytime;
        public long dtFinishtime;
        public String strReceiptusername;
        public String strDetailaddress;
        public String strLocation;
        public String strReceiptmobile;
        public String strInvoiceheader;
        public String strRemarks;
        public String strFieldName;
        public String strMobile;
        public String strBuyername;
        public ArrayList<OrderGoods> orderGoods;
        public class OrderGoods{
            public int lGId;
            public int lOrderid;
            public int lGoodsid;
            public int nPrice;
            public int nCount;
            public int nGoodsType;
            public int nGoodsFactPrice;
            public int nGoodsTotalPrice;
            public int nWatertickets;
            public String strGoodsname;
            public String strGoodsimgurl;
        }
    }
}
