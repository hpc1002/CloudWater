package com.it.cloudwater.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.bean.MyTicketListBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/7/28.
 */

public class MyTicketListViewHolder extends BaseViewHolder<MyTicketListBean.Result.DataList> {

    private TextView name;
    private TextView time;
    private TextView number;


    public MyTicketListViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_my_ticket);
        name = $(R.id.tv_name);
        time = $(R.id.tv_time);
        number = $(R.id.ticket_number);

    }

    @Override
    public void setData(MyTicketListBean.Result.DataList data) {
        super.setData(data);
        name.setText(data.strTicketName);
        time.setText(data.strExpire);
        number.setText(data.nRemainingCount + "张");

    }
}
