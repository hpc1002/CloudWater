//package com.it.cloudwater.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.bumptech.glide.Glide;
//import com.it.cloudwater.R;
//
///**
// * Created by cuboo on 2016/12/1.
// */
//
//public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyHolder> {
//    private ArrayList<DownLoadCourseBean> list;
//    private Context context;
//    private boolean checkState;
//
//    public recyclerAdapter(Context context, boolean checkState, ArrayList<DownLoadCourseBean> list) {
//
//        this.context = context;
//        this.list = list;
//        this.checkState = checkState;
//    }
//
//
//    public static class MyHolder extends RecyclerView.ViewHolder {
//        private TextView textView;
//        private ImageView course_img;
//        private CheckBox checkBox;
//
//
//        public CheckBox getCheckBox() {
//            return checkBox;
//        }
//
//
//        public TextView getTextView() {
//            return textView;
//        }
//
//        public ImageView getImageView() {
//            return course_img;
//        }
//
//        public MyHolder(View itemView) {
//            super(itemView);
//            textView = (TextView) itemView.findViewById(R.id.tv_name);
//            course_img = (ImageView) itemView.findViewById(R.id.course_img);
//            checkBox = (CheckBox) itemView.findViewById(R.id.checkbox0);
//
//        }
//    }
//
//    @Override
//    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shopcart, null);
//        MyHolder holder = new MyHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(final MyHolder holder, final int position) {
//
//
//
//        holder.getTextView().setText(list.get(position).courseName);
//        Glide.with(context)
//                .load(list.get(position).courseImg)
//                .centerCrop()
//                .into(holder.getImageView());
//        holder.getCheckBox().setChecked(list.get(position).isChecked);
//        holder.getCheckBox().setVisibility(View.VISIBLE);
//        if (checkState) {
//            holder.getCheckBox().setVisibility(View.VISIBLE);
//        } else {
//            holder.getCheckBox().setVisibility(View.GONE);
//
//        }
//        Log.i("wuxiao", list.get(position).isChecked + "recyclerAdapter");
//        holder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                //将课程的checkbox的点击变化事件进行回调
//                if (mCallBack != null) {
//                    mCallBack.OnCheckListener(isChecked, position);
//                }
//            }
//        });
//
//
//        holder.itemView.setTag(list.get(position));
//    }
//
//    @Override
//    public int getItemCount() {
//        return list.size();
//    }
//
//    private allCheck mCallBack;
//
//    public void setCallBack(allCheck callBack) {
//        mCallBack = callBack;
//    }
//
//    public interface allCheck {
//        //回调函数 将课程的checkbox的点击变化事件进行回调
//        public void OnCheckListener(boolean isSelected, int position);
//
//        //回调函数 将课程章节的checkbox的点击变化事件进行回调
//        public void OnItemCheckListener(boolean isSelected, int parentposition, int chaildposition);
//
//        void OnItemClickListener(boolean checkState, String filePath, String videoName);
//    }
//}
