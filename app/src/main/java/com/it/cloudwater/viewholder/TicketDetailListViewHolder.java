package com.it.cloudwater.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.bean.TicketDetailBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.util.ArrayList;

/**
 * Created by hpc on 2017/8/30.
 */

public class TicketDetailListViewHolder extends BaseViewHolder<TicketDetailBean.Result.TicketContents> {
    private TextView tv_bucket_name, tv_discount, tv_price, tv_gift;
    private CheckBox checkBox;
    private RadioGroup radioGroup;

    public TicketDetailListViewHolder(ViewGroup itemView,ArrayList<CheckBox> ticketContentsesCheckBox ) {
        super(itemView, R.layout.item_ticket_list);
        tv_bucket_name = $(R.id.tv_bucket_name);
        tv_discount = $(R.id.tv_discount);
        tv_price = $(R.id.tv_price);
        checkBox = $(R.id.checkBox);
        radioGroup = $(R.id.radioGroup);
        tv_gift = $(R.id.tv_gift);
        ticketContentsesCheckBox.add(checkBox);//每次初始化存放checkbox
    }

    @Override
    public void setData(final TicketDetailBean.Result.TicketContents data) {
        super.setData(data);
        tv_bucket_name.setText(data.strTicketname);
        tv_price.setText("￥" + ((double) data.nPrice / 100));
        tv_discount.setText(data.strRemarks);

//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
////                if (isChecked) {
////                    mCallBack.OnItemClickListener(data,isChecked);
////                }
//                mCallBack.OnItemClickListener(data,isChecked);
//            }
//        });
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean checked = checkBox.isChecked();
                mCallBack.OnItemClickListener(data,checked);

            }
        });
    }

    private allCheck mCallBack;

    public void setCallBack(allCheck callBack) {
        mCallBack = callBack;
    }

    public interface allCheck {
        void OnItemClickListener(TicketDetailBean.Result.TicketContents data,boolean isChecked);
    }
}
