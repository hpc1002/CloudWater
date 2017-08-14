package com.it.cloudwater.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseQuickAdapter;
import com.it.cloudwater.base.BaseViewHolder;
import com.it.cloudwater.bean.GoodsListBean;

import java.util.List;

/**
 * Created by hpc on 2017/7/11.
 */

public class GoodListAdapter extends BaseQuickAdapter<GoodsListBean.Result.DataList> {


    public GoodListAdapter(int layoutResId, List<GoodsListBean.Result.DataList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsListBean.Result.DataList item) {
        Glide.with(mContext)
                .load(item.strGoodsimgurl)
                .crossFade()
                .placeholder(R.mipmap.home_load_error)
                .into((ImageView) helper.getView(R.id.bucketImg));
        helper.setText(R.id.bucket_name, item.strGoodsname);
        helper.setText(R.id.bucket_specifications, item.strStandard);
        helper.setText(R.id.bucket_price, ((double)item.nPrice/100) + "元");
        helper.setText(R.id.bucket_amount_sold, "已售" + item.nMothnumber);
    }
}
