package com.it.cloudwater.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseQuickAdapter;
import com.it.cloudwater.base.BaseViewHolder;
import com.it.cloudwater.bean.CourseRecommendBean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class CourseRecommendAdapter extends BaseQuickAdapter<CourseRecommendBean.RecommendData.Course.CourseData> {

    public CourseRecommendAdapter(int layoutResId, List<CourseRecommendBean.RecommendData.Course.CourseData> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CourseRecommendBean.RecommendData.Course.CourseData item) {
        Glide.with(mContext)
                .load(item.image.data.path)
                .crossFade()
                .placeholder(R.mipmap.home_load_error)
                .into((ImageView) helper.getView(R.id.courseImg));
        helper.setText(R.id.course_name, item.name);
        helper.setText(R.id.course_learn_number, item.show_number + "");
    }
}
