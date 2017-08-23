package com.it.cloudwater.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.it.cloudwater.R;
import com.it.cloudwater.bean.BuyTicketListBean;
import com.it.cloudwater.bean.MyTicketListBean;
import com.it.cloudwater.bean.TicketBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/7/28.
 */

public class BuyTicketViewHolder extends BaseViewHolder<BuyTicketListBean.Result.DataList> {

    private TextView water_name;
    private TextView water_sale;
    private TextView water_discount;
    private TextView tv_price;
    private TextView ori_price;
    private TextView buy;
    private ImageView ticketImg;

    public BuyTicketViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_water_ticket);
        water_name = $(R.id.water_name);
        ticketImg = $(R.id.iv_ticket);
        water_sale = $(R.id.water_sale);
        water_discount = $(R.id.water_discount);
        tv_price = $(R.id.tv_price);
        ori_price = $(R.id.ori_price);
        buy = $(R.id.buy);

    }

    @Override
    public void setData(BuyTicketListBean.Result.DataList data) {
        super.setData(data);
        water_name.setText(data.strGoodsName);
        water_sale.setText(data.nMonthCount+"");
        water_discount.setText(data.strRemarks);
        tv_price.setText(data.nPrice+"");
        ori_price.setText(data.nOldPrice+"");
        Glide.with(getContext())
                .load(data.strGoodsimgurl)
                .placeholder(R.mipmap.home_load_error)
                .bitmapTransform(new CenterCrop(getContext()))
                .into(ticketImg);
    }
}
