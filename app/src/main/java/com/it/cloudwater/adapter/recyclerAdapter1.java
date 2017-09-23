package com.it.cloudwater.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.it.cloudwater.R;
import com.it.cloudwater.bean.OrderListBean;
import com.it.cloudwater.constant.Constant;

import java.util.ArrayList;


/**
 * Created by cuboo on 2016/12/1.
 */

public class recyclerAdapter1 extends RecyclerView.Adapter<recyclerAdapter1.MyHolder> {
    private ArrayList<OrderListBean.Result.DataList.OrderGoods> cbeanList;
    private Context context;

    public recyclerAdapter1(ArrayList<OrderListBean.Result.DataList.OrderGoods> cbeanList, Context context) {
        this.cbeanList = cbeanList;
        this.context = context;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        private TextView good_name, good_capacity, good_price, barrel_deposit, barrel_count;
        private CheckBox checkBox;
        private ImageView good_img;


        public TextView getGoodName() {
            return good_name;
        }

        public TextView getGoodCapacity() {
            return good_capacity;
        }

        public TextView getGoodPrice() {
            return good_price;
        }

        public ImageView getGoodImg() {
            return good_img;
        }


        public MyHolder(View itemView) {
            super(itemView);
            good_img = (ImageView) itemView.findViewById(R.id.good_img);
            good_name = (TextView) itemView.findViewById(R.id.good_name);
            good_capacity = (TextView) itemView.findViewById(R.id.good_capacity);
            good_price = (TextView) itemView.findViewById(R.id.good_price);

        }
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_good, null);
        MyHolder holder = new MyHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyHolder holder, final int position) {
        holder.getGoodName().setText(cbeanList.get(position).strGoodsname);
        holder.getGoodCapacity().setText(1.8 + "");
        holder.getGoodPrice().setText("￥" + ((double) cbeanList.get(position).nGoodsFactPrice / 100));
        Glide.with(context)
                .load(Constant.IMAGE_URL + "0/" + cbeanList.get(position).lGoodsid)
                .centerCrop()
                .placeholder(R.mipmap.home_load_error)
                .into(holder.getGoodImg());

        holder.itemView.setId(position);
    }

    @Override
    public int getItemCount() {
        return cbeanList.size();
    }


}
