package com.it.cloudwater.viewholder;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.it.cloudwater.R;
import com.it.cloudwater.bean.MyTicketListBean;
import com.it.cloudwater.constant.Constant;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/7/28.
 */

public class MyTicketListViewHolder extends BaseViewHolder<MyTicketListBean.Result.DataList> {

    private TextView name;
    private TextView time;
    private TextView number;
    private ImageView iv_ticket;
    private Context context;

    public MyTicketListViewHolder(ViewGroup itemView, Context context) {
        super(itemView, R.layout.item_my_ticket);
        this.context = context;
        name = $(R.id.tv_name);
        time = $(R.id.tv_time);
        number = $(R.id.ticket_number);
        iv_ticket = $(R.id.iv_ticket);

    }

    @Override
    public void setData(MyTicketListBean.Result.DataList data) {
        super.setData(data);
        name.setText(data.strTicketName);
        time.setText("有效期至:" + data.strExpire);
        number.setText(data.nRemainingCount + "张");
        Glide.with(context)
                .load(Constant.IMAGE_URL + "0/" + data.lGoodsid)
                .crossFade()
                .placeholder(R.mipmap.home_load_error)
                .into(iv_ticket);
    }
}
