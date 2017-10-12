package com.it.cloudwater.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
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
import com.it.cloudwater.utils.PushUtil;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_mobileNumber)
    EditText etMobileNumber;
    @BindView(R.id.tv_sms_code)
    TextView tvSmsCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    private String phoneNumberInput;
    private String passwordInput;
    private boolean isPhone;
    private boolean isPwd;

    @Override
    protected void processLogic() {

    }


    @Override
    protected void setListener() {
        toolbarTitle.setText("登录");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("注册");
        ivLeft.setVisibility(View.VISIBLE);
        tvRight.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        forgetPassword.setOnClickListener(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        phoneNumberInput = etMobileNumber.getText().toString();
        passwordInput = etPassword.getText().toString();
        isPhone = CheckUtil.isMobile(phoneNumberInput);
        isPwd = CheckUtil.isPassword(passwordInput);
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_right:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_left:
                finish();
                break;
            case R.id.forget_password:
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
                break;

            case R.id.btn_login:
                if (phoneNumberInput.equals("")) {
                    ToastManager.show("手机号不能为空");
                    return;
                }
                if (!isPhone) {
                    ToastManager.show("手机号输入不对");
                    return;
                }
                if (passwordInput.equals("")) {
                    ToastManager.show("密码不能为空");
                    return;
                }
                if (!isPwd) {
                    ToastManager.show("密码输入格式有误");
                    return;
                }
                CloudApi.Login(0x001, phoneNumberInput, passwordInput, 1, myCallBack);
            default:
                break;
        }
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> data) {
            switch (what) {
                case 0x001:
                    String body = data.body();
                    try {
                        JSONObject loginData = new JSONObject(body);
                        String resCode = loginData.getString("resCode");
                        if (resCode.equals("0")) {
                            ToastManager.show("登录成功");
                            PushUtil.bindAccount(LoginActivity.this);
                            JSONObject result = loginData.getJSONObject("result");
                            int lId = result.getInt("lId");
                            String strPassword = result.getString("strPassword");
                            int nUserType = result.getInt("nUserType");
                            if (nUserType == 0) {
                                String userName = result.getString("strUsername");
                                StorageUtil.setKeyValue(LoginActivity.this, "userName", userName);
                            }
                            String strMobile = result.getString("strMobile");

                            String strInvitecode = result.getString("strInvitecode");
                            int nState = result.getInt("nState");
                            int nBucketnum = result.getInt("nBucketnum");
                            StorageUtil.setKeyValue(LoginActivity.this, "userId", lId + "");
                            StorageUtil.setKeyValue(LoginActivity.this, "userType", nUserType + "");
                            StorageUtil.setKeyValue(LoginActivity.this, "userPhone", strMobile);

                            StorageUtil.setKeyValue(LoginActivity.this, "invitation_code", strInvitecode);
                            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        } else if (resCode.equals("1")) {
                            String result = loginData.getString("result");
                            ToastManager.show(result);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

}
