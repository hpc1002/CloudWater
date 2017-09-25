package com.it.cloudwater.user.more;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.home.HomeActivity;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.CheckUtil;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class ChangePasswordActivity extends BaseActivity implements View.OnClickListener {


    private static final String TAG = "FindPasswordActivity";
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
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_mobileNumber)
    EditText etMobileNumber;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.et_new_password)
    EditText etNewPassword;
    @BindView(R.id.tv_invent_code)
    TextView tvInventCode;
    @BindView(R.id.et_pwd_two)
    EditText etPwdTwo;
    @BindView(R.id.btn_preservation)
    Button btnPreservation;
    @BindView(R.id.tv_old_password)
    TextView tvOldPassword;
    @BindView(R.id.et_old_password)
    EditText etOldPassword;
    private String userId;

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("修改密码");
        userId = StorageUtil.getUserId(this);
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnPreservation.setOnClickListener(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_change_password);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> data) {
            switch (what) {
                case 0x001:
                    Log.i(TAG, "onSuccess: ------" + data);
                    String body = data.body();
                    try {
                        JSONObject pwdData = new JSONObject(body);
                        String resCode = pwdData.getString("resCode");
                        String result = pwdData.getString("result");
                        if (resCode.equals("1")) {
                            ToastManager.show(result);
                        } else if (resCode.equals("0")) {
                            ToastManager.show(result);
                            startActivity(new Intent(ChangePasswordActivity.this, HomeActivity.class));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onFail(int what, Response<String> result) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_preservation:
                String phone = etMobileNumber.getText().toString();
//                String smsCode = etSmsCode.getText().toString();
                String password = etNewPassword.getText().toString();
                String pwdTwo = etPwdTwo.getText().toString();
                String oldPassword = etOldPassword.getText().toString();
                boolean isPhone = CheckUtil.isMobile(phone);
                boolean isPwd = CheckUtil.isPassword(password);
                boolean isPwdTwo = CheckUtil.isPassword(pwdTwo);
                boolean isPwdOld = CheckUtil.isPassword(oldPassword);
                if (phone.equals("")) {
                    ToastManager.show("手机号不能为空");
                    return;
                }
                if (!isPhone) {
                    ToastManager.show("手机号输入不对");
                    return;
                }
                if (oldPassword.equals("")) {
                    ToastManager.show("旧密码不能为空");
                }
                if (!isPwdOld) {
                    ToastManager.show("旧密码输入格式输入不对");
                    return;
                }
                if (password.equals("")) {
                    ToastManager.show("密码不能为空");
                }
                if (!isPwd) {
                    ToastManager.show("密码输入格式输入不对");
                    return;
                }
                if (oldPassword.equals(password)) {
                    ToastManager.show("新旧密码不能一样");
                    return;
                }
                if (pwdTwo.equals("")) {
                    ToastManager.show("重复密码不能为空");
                }
                if (!isPwdTwo) {
                    ToastManager.show("重复密码输入格式不对");
                    return;
                }
                if (!password.equals(pwdTwo)) {
                    ToastManager.show("两次输入密码不一致,请重新输入");
                    return;
                }
                CloudApi.ChangePassword(0x001, phone, password, Long.parseLong(userId), oldPassword, myCallBack);
                break;
        }
    }
}
