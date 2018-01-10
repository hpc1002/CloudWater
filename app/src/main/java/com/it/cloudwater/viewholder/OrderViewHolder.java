package com.it.cloudwater.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.it.cloudwater.R;
import com.it.cloudwater.bean.OrderDetailBean;
import com.it.cloudwater.constant.Constant;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/7/28.
 */

public class OrderViewHolder extends BaseViewHolder<OrderDetailBean.Result.OrderGoods> {

    private TextView tv_wanter_name;
    private TextView unit_price;
    private TextView count;
    private ImageView img_water;


    public OrderViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_order_list);
        tv_wanter_name = $(R.id.tv_wanter_name);
        unit_price = $(R.id.unit_price);
        count = $(R.id.count);
        img_water = $(R.id.img_water);

    }

    @Override
    public void setData(OrderDetailBean.Result.OrderGoods data) {
        super.setData(data);
        tv_wanter_name.setText(data.strGoodsname);
        unit_price.setText("￥" + ((double) data.nPrice / 100));
        count.setText("x" + data.nCount);
        Glide.with(getContext())
                .load(Constant.IMAGE_URL + "0/" + data.lGoodsid+"?date="+System.currentTimeMillis())
                .placeholder(R.mipmap.home_load_error)
                .bitmapTransform(new CenterCrop(getContext()))
                .into(img_water);
    }
}
