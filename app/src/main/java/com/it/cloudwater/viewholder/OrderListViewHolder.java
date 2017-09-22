package com.it.cloudwater.viewholder;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.adapter.recyclerAdapter1;
import com.it.cloudwater.bean.OrderListBean;
import com.it.cloudwater.utils.DateUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/8/17.
 */

public class OrderListViewHolder extends BaseViewHolder<OrderListBean.Result.DataList> {

    private TextView orderCreateTime;
    //    private TextView sendTime;
    private TextView orderNumber;
    private TextView payState;
    //    private TextView goodName;
//    private TextView goodeCapccity;
//    private TextView goodPrice;
//    private TextView barrelDeposit;
//    private TextView barrelCount;
    private TextView actualPay;
    //    private TextView deleteOrder;
    private TextView quickPay;
    private EasyRecyclerView goods_recyclerView;
    //    private ImageView goodImg;
    private recyclerAdapter1 adapter;
    private Context context;

    public OrderListViewHolder(ViewGroup itemView, Context context) {
        super(itemView, R.layout.item_order_uncom);
        this.context = context;
        orderCreateTime = $(R.id.tv_time_order_create);
        orderNumber = $(R.id.tv_order_number);
        payState = $(R.id.tv_payState);
        goods_recyclerView = $(R.id.goods_recyclerView);
        actualPay = $(R.id.actual_pay);
        quickPay = $(R.id.quick_pay);
    }

    @Override
    public void setData(final OrderListBean.Result.DataList data) {
        super.setData(data);
        orderCreateTime.setText("下单时间" + DateUtil.toDate(data.dtCreatetime));
//        sendTime.setText("配送时间" + "今天8：00-9：00");
        orderNumber.setText("订单号" + data.strOrdernum);
        if (data.nState == -1) {
            payState.setText("未支付");
        } else if (data.nState == 3) {
            payState.setText("已支付");
            quickPay.setVisibility(View.GONE);
        }
        actualPay.setText("实付款: ￥" + ((double) data.nTotalprice / 100));
        goods_recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        adapter = new recyclerAdapter1(data.orderGoods, context);
        goods_recyclerView.setAdapter(adapter);
        quickPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.OnItemClickListener(data);
            }
        });
    }

    private allCheck mCallBack;

    public void setCallBack(allCheck callBack) {
        mCallBack = callBack;
    }

    public interface allCheck {
        void OnItemClickListener(OrderListBean.Result.DataList data);
    }
}
