package com.it.cloudwater.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.bean.OrderListBean;

import java.util.ArrayList;


/**
 * Created by cuboo on 2016/12/1.
 */

public class recyclerAdapter1 extends RecyclerView.Adapter<recyclerAdapter1.MyHolder> {
    private ArrayList<OrderListBean.Result.DataList.OrderGoods> cbeanList;
    private boolean checkState;

    public recyclerAdapter1(ArrayList<OrderListBean.Result.DataList.OrderGoods> cbeanList) {
        this.cbeanList = cbeanList;
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
        public TextView getBarrelDeposit() {
            return barrel_deposit;
        }
        public TextView getBarrelCount() {
            return barrel_count;
        }

        public MyHolder(View itemView) {
            super(itemView);
            good_img = (ImageView) itemView.findViewById(R.id.good_img);
            good_name = (TextView) itemView.findViewById(R.id.good_name);
            good_capacity = (TextView) itemView.findViewById(R.id.good_capacity);
            good_price = (TextView) itemView.findViewById(R.id.good_price);
            barrel_deposit = (TextView) itemView.findViewById(R.id.barrel_deposit);
            barrel_count = (TextView) itemView.findViewById(R.id.barrel_count);
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
        holder.getGoodCapacity().setText(1.8+"");
        holder.getGoodPrice().setText("￥" + ((double) cbeanList.get(position).nGoodsFactPrice / 100));
//        holder.getBarrelCount().setText(3+"");
//        holder.getBarrelDeposit().setText(2+"L");


        holder.itemView.setId(position);
    }

    @Override
    public int getItemCount() {
        return cbeanList.size();
    }


}
