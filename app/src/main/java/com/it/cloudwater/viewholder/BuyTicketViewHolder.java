package com.it.cloudwater.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.bean.BuyTicketListBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/7/28.
 */

public abstract class BuyTicketViewHolder extends BaseViewHolder<BuyTicketListBean.Result.DataList> {


    public BuyTicketViewHolder(ViewGroup parent, int item_water_ticket) {
        super(parent, item_water_ticket);
    }

//    public BuyTicketViewHolder(ViewGroup itemView) {
//        super(itemView, R.layout.item_water_ticket);
//        water_name = $(R.id.water_name);
//        ticketImg = $(R.id.iv_ticket);
//        water_sale = $(R.id.water_sale);
//        water_discount = $(R.id.water_discount);
//        tv_price = $(R.id.tv_price);
//        ori_price = $(R.id.ori_price);
//        buy = $(R.id.buy);
//
//    }

    @Override
    public abstract void setData(BuyTicketListBean.Result.DataList data);


}
