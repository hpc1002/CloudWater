package com.it.cloudwater.viewholder;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.it.cloudwater.R;
import com.it.cloudwater.bean.OrderListBean;
import com.it.cloudwater.constant.Constant;
import com.it.cloudwater.utils.DateUtil;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/7/28.
 */

public class ShopperListViewHolder extends BaseViewHolder<OrderListBean.Result.DataList> {

    private TextView order_time;
    private TextView order_number;
    private TextView send_status;
    private TextView bucket_name;
    private TextView bucket_count;
    private TextView bucket_price;
    private TextView actual_pay;
    private Button btn_toSend;
    private ImageView bucket_image;


    public ShopperListViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_shopper);
        order_time = $(R.id.order_time);
        order_number = $(R.id.order_number);
        send_status = $(R.id.send_status);
        bucket_name = $(R.id.bucket_name);
        bucket_count = $(R.id.bucket_count);
        bucket_price = $(R.id.bucket_price);
        actual_pay = $(R.id.actual_pay);
        btn_toSend = $(R.id.btn_toSend);
        bucket_image = $(R.id.bucket_image);


    }

    @Override
    public void setData(OrderListBean.Result.DataList data) {
        super.setData(data);
        order_time.setText("下单时间:" + DateUtil.toDate(data.dtCreatetime));
        order_number.setText("订单号: " + data.strOrdernum);
        actual_pay.setText("桶押金:￥" + ((double) data.nBucketnum * data.nBucketmoney / 100));
        bucket_name.setText(data.orderGoods.get(0).strGoodsname);
        bucket_count.setText(data.orderGoods.get(0).nCount + "");
        bucket_price.setText("￥" + ((double) data.orderGoods.get(0).nPrice / 100));
        if (data.nSendState == 1) {
            send_status.setText("未配送");
        }
        Glide.with(getContext())
                .load(Constant.IMAGE_URL + "0/" + data.orderGoods.get(0).lGoodsid)
                .placeholder(R.mipmap.home_load_error)
                .bitmapTransform(new CenterCrop(getContext()))
                .into(bucket_image);

    }
}
