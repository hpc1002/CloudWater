package com.it.cloudwater.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.bean.CouponListBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/7/28.
 */

public class CouponListViewHolder extends BaseViewHolder<CouponListBean.Result.DataList> {

    private TextView coupon_name;
    private TextView dExpire;
    private TextView price;


    public CouponListViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_coupon);
        coupon_name = $(R.id.coupon_name);
        dExpire = $(R.id.dExpire);
        price = $(R.id.price);

    }

    @Override
    public void setData(CouponListBean.Result.DataList data) {
        super.setData(data);
        coupon_name.setText(data.strUserName);
        dExpire.setText(data.dExpire + "");
        price.setText("￥"+((double) data.nPrice / 100) + "元");

    }
}
