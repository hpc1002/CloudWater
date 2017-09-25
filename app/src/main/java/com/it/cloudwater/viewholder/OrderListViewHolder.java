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
    private TextView orderNumber;
    private TextView payState;
    private TextView actualPay;
    private TextView quickPay;
    private TextView barrelCount;
    private TextView barrel_deposit;
    private TextView order_delete;
    private EasyRecyclerView goods_recyclerView;
    private recyclerAdapter1 adapter;
    private Context context;
    private String orderState;

    public OrderListViewHolder(ViewGroup itemView, Context context, String orderState) {
        super(itemView, R.layout.item_order_uncom);
        this.context = context;
        this.orderState = orderState;
        orderCreateTime = $(R.id.tv_time_order_create);
        orderNumber = $(R.id.tv_order_number);
        payState = $(R.id.tv_payState);
        goods_recyclerView = $(R.id.goods_recyclerView);
        actualPay = $(R.id.actual_pay);
        quickPay = $(R.id.quick_pay);
        order_delete = $(R.id.order_delete);
        barrel_deposit = $(R.id.barrel_deposit);
        barrelCount = $(R.id.barrel_count);
    }

    @Override
    public void setData(final OrderListBean.Result.DataList data) {
        super.setData(data);
        orderCreateTime.setText("下单时间" + DateUtil.toDate(data.dtCreatetime));
//        sendTime.setText("配送时间" + "今天8：00-9：00");
        orderNumber.setText("订单号" + data.strOrdernum);
        barrel_deposit.setText("桶押金:￥" + ((double) data.nBucketmoney * data.nBucketnum / 100));
        barrelCount.setText("x" + data.nBucketnum);
        if (orderState.equals("orderState") && data.nState != 3) {
            if (data.nState == 0) {
                quickPay.setText("去结算");
            } else if (data.nState == 1) {
                quickPay.setText("立即支付");
            }
            payState.setText("未支付");

        } else if (orderState.equals("orderState") && data.nState == 3) {
            payState.setText("已支付");
            quickPay.setVisibility(View.GONE);
            order_delete.setVisibility(View.GONE);
        }
        if (orderState.equals("distributionState") && data.nSendState == 0) {
            payState.setVisibility(View.GONE);
            quickPay.setVisibility(View.VISIBLE);
            quickPay.setText("立即配送");
            order_delete.setVisibility(View.GONE);
        } else if (orderState.equals("distributionState") && data.nSendState == 1) {
            payState.setVisibility(View.GONE);
            quickPay.setVisibility(View.GONE);
            order_delete.setVisibility(View.GONE);
        }
        actualPay.setText("实付款: ￥" + ((double) data.nTotalprice / 100));
        goods_recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        adapter = new recyclerAdapter1(data.orderGoods, context);
        goods_recyclerView.setAdapter(adapter);
        quickPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (orderState.equals("distributionState") && data.nSendState == 0) {
                    mCallBack.OnDistributionItemClickListener(data);
                } else if (orderState.equals("orderState") && data.nState != 3) {
                    if (data.nState == 0) {
                        //去结算
                        mCallBack.OnToSettleClickListener(data);
                    } else if (data.nState == 1) {
                        mCallBack.OnItemClickListener(data);
                    }

                }

            }
        });
        order_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallBack.OnItemDeleteClickListener(data);
            }
        });
    }

    private allCheck mCallBack;

    public void setCallBack(allCheck callBack) {
        mCallBack = callBack;
    }

    public interface allCheck {
        void OnItemClickListener(OrderListBean.Result.DataList data);

        void OnToSettleClickListener(OrderListBean.Result.DataList data);

        void OnDistributionItemClickListener(OrderListBean.Result.DataList data);

        void OnItemDeleteClickListener(OrderListBean.Result.DataList data);
    }
}
