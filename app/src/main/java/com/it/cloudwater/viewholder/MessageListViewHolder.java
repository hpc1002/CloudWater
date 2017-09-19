package com.it.cloudwater.viewholder;

import android.view.ViewGroup;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.bean.MessageListBean;
import com.it.cloudwater.utils.DateUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/7/28.
 */

public class MessageListViewHolder extends BaseViewHolder<MessageListBean.Result.DataList> {

    private TextView message_name;
    private TextView message_time;
    private TextView message_content;


    public MessageListViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_message_list);
        message_name = $(R.id.message_name);
        message_time = $(R.id.message_time);
        message_content = $(R.id.message_content);

    }

    @Override
    public void setData(MessageListBean.Result.DataList data) {
        super.setData(data);
        message_name.setText(data.strNoticeTypeName);
        message_time.setText(DateUtil.toDate(data.dtCreateTime));
        message_content.setText(data.strNoticeCon);

    }
}
