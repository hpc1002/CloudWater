package com.it.cloudwater.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.it.cloudwater.R;
import com.it.cloudwater.bean.CourseRecommendBean;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by hpc on 2017/7/6.
 */

public class RecommendViewholder extends BaseViewHolder<CourseRecommendBean.RecommendData.Course.CourseData> {

    private TextView mCourseName;
    private TextView mCourse_learn_number;
    private ImageView mCourseImg;

    public RecommendViewholder(ViewGroup parent) {
        super(parent, R.layout.item_pub_course);
        mCourseName = $(R.id.course_name);
        mCourse_learn_number = $(R.id.course_learn_number);
        mCourseImg = $(R.id.courseImg);
    }

    @Override
    public void setData(CourseRecommendBean.RecommendData.Course.CourseData data) {
        mCourseName.setText(data.name);
        mCourse_learn_number.setText(data.show_number + "");
        Glide.with(getContext())
                .load(data.image.data.path + "?x-oss-process=image/resize,m_fixed,w_350,h_200")
                .placeholder(R.mipmap.home_load_error)
                .bitmapTransform(new CenterCrop(getContext()))
                .into(mCourseImg);
    }
}
