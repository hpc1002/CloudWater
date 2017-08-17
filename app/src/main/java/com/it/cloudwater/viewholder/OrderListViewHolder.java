package com.it.cloudwater.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.it.cloudwater.R;
import com.it.cloudwater.bean.OrderListBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/8/17.
 */

public class OrderListViewHolder extends BaseViewHolder<OrderListBean.Result.DataList> {

    private TextView orderCreateTime;
    private TextView sendTime;
    private TextView orderNumber;
    private TextView payState;
    private TextView goodName;
    private TextView goodeCapccity;
    private TextView goodPrice;
    private TextView barrelDeposit;
    private TextView barrelCount;
    private TextView actualPay;
    private TextView deleteOrder;
    private TextView quickPay;
    private ImageView goodImg;

    public OrderListViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_order_uncom);
        orderCreateTime = $(R.id.tv_time_order_create);
        sendTime = $(R.id.tv_time_send);
        orderNumber = $(R.id.tv_order_number);
        payState = $(R.id.tv_payState);
        goodName = $(R.id.good_name);
        goodeCapccity = $(R.id.good_capacity);
        goodPrice = $(R.id.good_price);
        barrelDeposit = $(R.id.barrel_deposit);
        barrelCount = $(R.id.barrel_count);
        actualPay = $(R.id.actual_pay);
        deleteOrder = $(R.id.delete_order);
        quickPay = $(R.id.quick_pay);
        goodImg = $(R.id.good_img);
    }

    @Override
    public void setData(OrderListBean.Result.DataList data) {
        super.setData(data);
        orderCreateTime.setText(data.dtCreatetime + "");
        sendTime.setText("配送时间" + "今天8：00-9：00");
        orderNumber.setText("订单号" + data.strOrdernum);
        if (data.nState == 0) {
            payState.setText("未支付");
        }
        goodName.setText(data.orderGoods.get(0).strGoodsname);
        goodPrice.setText("￥"+((double)data.orderGoods.get(0).nGoodsFactPrice / 100));
        barrelDeposit.setText("桶押金" + ((double)data.nBucketmoney / 100));
        barrelCount.setText("×" + data.nBucketnum);
        actualPay.setText("实付款: ￥" + ((double)data.nTotalprice / 100));
        Glide.with(getContext())
                .load(data.orderGoods.get(0).strGoodsimgurl + "?x-oss-process=image/resize,m_fixed,w_350,h_200")
                .placeholder(R.mipmap.home_load_error)
                .bitmapTransform(new CenterCrop(getContext()))
                .into(goodImg);
    }
}
