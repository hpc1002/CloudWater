package com.it.cloudwater.viewholder;

import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.bean.CouponListBean;
import com.it.cloudwater.bean.ShopCartListBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/7/28.
 */

public class ShopCartViewHolder extends BaseViewHolder<ShopCartListBean.Result.DataList> {

    private CheckBox checkbox0;
    private ImageView course_img,delete;
    private TextView tv_name,tv_volume,tv_reduce,tv_number,tv_increase,tv_price;
    private TextView price;


    public ShopCartViewHolder(ViewGroup itemView) {
        super(itemView, R.layout.item_shopcart);

        checkbox0 = $(R.id.checkbox0);
        course_img = $(R.id.course_img);
        tv_name = $(R.id.tv_name);
        tv_volume = $(R.id.tv_volume);
        tv_reduce = $(R.id.tv_reduce);
        tv_number = $(R.id.tv_number);
        tv_increase = $(R.id.tv_increase);
        tv_price = $(R.id.tv_price);
        delete = $(R.id.delete);

    }

    @Override
    public void setData(ShopCartListBean.Result.DataList data) {
        super.setData(data);
        tv_name.setText(data.strGoodsname);
        tv_volume.setText(data.strStandard+"");
        tv_price.setText("￥"+((double) data.nPrice/ 100) + "元");

    }
}
