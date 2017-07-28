package com.it.cloudwater.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.it.cloudwater.R;
import com.it.cloudwater.bean.TicketBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/7/28.
 */

public class MyTicketViewHolder extends BaseViewHolder<TicketBean> {

    private TextView name;
    private TextView time;
    private TextView number;
    private ImageView ticketImg;

    public MyTicketViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_my_ticket);
        name = $(R.id.tv_name);
        ticketImg = $(R.id.iv_ticket);
        time = $(R.id.tv_time);
        number = $(R.id.ticket_number);

    }

    @Override
    public void setData(TicketBean data) {
        super.setData(data);
        name.setText(data.ticketName);
        time.setText(data.time);
        number.setText(data.number+"");
        Glide.with(getContext())
                .load(data.ticketImg)
                .placeholder(R.mipmap.home_load_error)
                .bitmapTransform(new CenterCrop(getContext()))
                .into(ticketImg);
    }
}
