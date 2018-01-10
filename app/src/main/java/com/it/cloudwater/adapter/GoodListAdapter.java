package com.it.cloudwater.adapter;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.StringSignature;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseQuickAdapter;
import com.it.cloudwater.base.BaseViewHolder;
import com.it.cloudwater.bean.GoodsListBean;
import com.it.cloudwater.constant.Constant;

import java.util.List;

/**
 * Created by hpc on 2017/7/11.
 */

public class GoodListAdapter extends BaseQuickAdapter<GoodsListBean.Result.DataList> {


    public GoodListAdapter(int layoutResId, List<GoodsListBean.Result.DataList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final GoodsListBean.Result.DataList item) {
        Glide.with(mContext)
                .load(Constant.IMAGE_URL + "0/" + item.lId+"?date="+System.currentTimeMillis())
                .crossFade()
                .signature(new StringSignature("02"))
                .skipMemoryCache(true)
                .placeholder(R.mipmap.home_load_error)
                .into((ImageView) helper.getView(R.id.bucketImg));
        helper.setText(R.id.bucket_name, item.strGoodsname);
        helper.setText(R.id.bucket_specifications, item.strStandard);
        helper.setText(R.id.bucket_price, ((double) item.nPrice / 100) + "元");
        helper.setText(R.id.bucket_amount_sold, "已售" + item.nMothnumber);
        helper.getView(R.id.add_shop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.OnItemClickListener(item.nPrice, item.lId, item.strGoodsname, item.strGoodsimgurl, item.strStandard, item.lId);
            }
        });
    }

    private OnMyClickListener mCallBack;

    public void setCallBack(OnMyClickListener callBack) {
        mCallBack = callBack;
    }

    public interface OnMyClickListener {
        void OnItemClickListener(Integer price, long id, String strGoodsname, String strGoodsimgurl, String strStandard, long goodId);
    }
}
