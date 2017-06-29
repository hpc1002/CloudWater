package com.it.cloudwater.user.more;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackActivity extends BaseActivity {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.feed_content)
    TextView feedContent;
    @BindView(R.id.feedback_submit)
    Button feedbackSubmit;

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("意见反馈");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_feedback);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

}
