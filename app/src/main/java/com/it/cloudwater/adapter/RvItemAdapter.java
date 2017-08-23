package com.it.cloudwater.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.it.cloudwater.R;
import com.it.cloudwater.bean.ShopCartListBean;
import com.it.cloudwater.callback.OnItemListener;
import com.it.cloudwater.widget.button.AnimShopButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Song on 2016/9/7.
 */
public class RvItemAdapter extends RecyclerView.Adapter<RvItemAdapter.MyViewHolder> {

    private Context mContext;
    private List<ShopCartListBean.Result.DataList> mDataList;
    private LayoutInflater mInflater;
    private OnItemListener mOnItemListener;

    public RvItemAdapter(Context context, List<ShopCartListBean.Result.DataList> dataList) {
        this.mContext = context;
        this.mDataList = dataList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mInflater.inflate(R.layout.layout_item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(rootView);
        return myViewHolder;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final ShopCartListBean.Result.DataList item = mDataList.get(position);
        holder.tv_name.setText(item.strGoodsname);
        holder.tv_volume.setText(item.strStandard+"");
        holder.tv_price.setText("￥"+((double) item.nPrice/ 100) + "元");
        holder.cbSelect.setChecked(item.isSelect);
        holder.cbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.isSelect = holder.cbSelect.isChecked();
                mOnItemListener.checkBoxClick(position);
            }
        });

        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemListener.onItemClick(view,position);
            }
        });

        holder.rlContent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mOnItemListener.onItemLongClick(view,position);
                return true;
            }
        });
    }

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    /**
     * ViewHolder
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_contnet)
        TextView tvContent;
        @BindView(R.id.course_img)
        ImageView course_img;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_volume)
        TextView tv_volume;
        @BindView(R.id.delete)
        ImageView delete;
        @BindView(R.id.btn_animShopButton)
        AnimShopButton btn_animShopButton;
        @BindView(R.id.tv_price)
        TextView tv_price;
        @BindView(R.id.cb_select)
        CheckBox cbSelect;
        @BindView(R.id.rl_content)
        RelativeLayout rlContent;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public void remove(ShopCartListBean.Result.DataList itemModel){
        mDataList.remove(itemModel);
    }

    public ShopCartListBean.Result.DataList getItem(int pos){
        return mDataList.get(pos);
    }
}
