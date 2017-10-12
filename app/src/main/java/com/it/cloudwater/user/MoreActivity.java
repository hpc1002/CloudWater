package com.it.cloudwater.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.home.HomeActivity;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.user.more.AboutUsActivity;
import com.it.cloudwater.user.more.ChangePasswordActivity;
import com.it.cloudwater.user.more.FeedbackActivity;
import com.it.cloudwater.user.more.TermsActivity;
import com.it.cloudwater.utils.PushUtil;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

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
    @BindView(R.id.rl_feedback)
    RelativeLayout rlFeedback;
    @BindView(R.id.terms_of_service)
    TextView termsOfService;
    @BindView(R.id.rl_terms_of_service)
    RelativeLayout rlTermsOfService;
    @BindView(R.id.about_us)
    TextView aboutUs;
    @BindView(R.id.rl_about_us)
    RelativeLayout rlAboutUs;
    @BindView(R.id.change_password)
    TextView changePassword;
    @BindView(R.id.rl_change_pwd)
    RelativeLayout rlChangePwd;
    @BindView(R.id.logout)
    TextView logout;
    private String userId;

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
        rlFeedback.setOnClickListener(this);
        rlAboutUs.setOnClickListener(this);
        rlTermsOfService.setOnClickListener(this);
        rlChangePwd.setOnClickListener(this);
        logout.setOnClickListener(this);
        userId = StorageUtil.getUserId(this);
        if (userId.equals("")) {
            logout.setVisibility(View.GONE);
            rlChangePwd.setVisibility(View.GONE);
        } else {
            logout.setVisibility(View.VISIBLE);
            rlChangePwd.setVisibility(View.VISIBLE);
        }
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
            case R.id.rl_feedback:
                startActivity(new Intent(MoreActivity.this, FeedbackActivity.class));
                break;
            case R.id.rl_about_us:
                startActivity(new Intent(MoreActivity.this, AboutUsActivity.class));
                break;
            case R.id.rl_change_pwd:
                startActivity(new Intent(MoreActivity.this, ChangePasswordActivity.class));
                break;
            case R.id.rl_terms_of_service:
                startActivity(new Intent(MoreActivity.this, TermsActivity.class));
                break;
            case R.id.logout:
                if (!userId.equals("")) {
                    CloudApi.Logout(0x001, Long.parseLong(userId), myCallBack);
                }
                break;
            default:
                break;
        }
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    String body = result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            ToastManager.show("退出登录成功");
                            PushUtil.unBindAccount();
                            StorageUtil.deteteUserId(MoreActivity.this);
                            StorageUtil.deteteUserPhone(MoreActivity.this);
                            Intent intent = new Intent(MoreActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

        @Override
        public void onFail(int what, Response<String> result) {

        }
    };
}
