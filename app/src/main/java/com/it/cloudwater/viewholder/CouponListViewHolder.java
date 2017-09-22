package com.it.cloudwater.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.bean.CouponListBean;
import com.it.cloudwater.utils.DateUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/7/28.
 */

public class CouponListViewHolder extends BaseViewHolder<CouponListBean.Result.DataList> {

    private TextView coupon_name;
    private TextView dExpire;
    private TextView price;
    private View fl_coupon;


    public CouponListViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_coupon);
        coupon_name = $(R.id.coupon_name);
        dExpire = $(R.id.dExpire);
        price = $(R.id.price);
        fl_coupon = $(R.id.fl_coupon);

    }

    @Override
    public void setData(final CouponListBean.Result.DataList data) {
        super.setData(data);
        coupon_name.setText(data.strUserName);
        dExpire.setText("有效期至: " + DateUtil.toDate(data.dExpire));
        price.setText("￥" + ((double) data.nPrice / 100) + "元");
        int nDataFlag = data.nDataFlag;
        fl_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.OnItemClickListener(data);
            }
        });
        if (nDataFlag == 0) {
            fl_coupon.setBackgroundResource(R.color.gray);
        }
    }

    private allCheck mCallBack;

    public void setCallBack(allCheck callBack) {
        mCallBack = callBack;
    }

    public interface allCheck {
        void OnItemClickListener(CouponListBean.Result.DataList data);
    }
}
