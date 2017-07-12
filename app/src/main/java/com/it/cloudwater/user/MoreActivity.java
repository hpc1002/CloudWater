package com.it.cloudwater.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.user.more.AboutUsActivity;
import com.it.cloudwater.user.more.FeedbackActivity;
import com.it.cloudwater.user.more.FindPasswordActivity;

import butterknife.BindView;

/**
 * 更多...
 */
public class MoreActivity extends BaseActivity implements View.OnClickListener {

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
    @BindView(R.id.feedback)
    TextView feedback;
    @BindView(R.id.terms_of_service)
    TextView termsOfService;
    @BindView(R.id.about_us)
    TextView aboutUs;
    @BindView(R.id.find_password)
    TextView findPassword;
    @BindView(R.id.logout)
    TextView logout;

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("更多");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        feedback.setOnClickListener(this);
        aboutUs.setOnClickListener(this);
        termsOfService.setOnClickListener(this);
        findPassword.setOnClickListener(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_more);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.feedback:
                startActivity(new Intent(MoreActivity.this, FeedbackActivity.class));
                break;
            case R.id.about_us:
                startActivity(new Intent(MoreActivity.this, AboutUsActivity.class));
                break;
            case R.id.find_password:
                startActivity(new Intent(MoreActivity.this, FindPasswordActivity.class));
                break;
            default:
                break;
        }
    }
}
