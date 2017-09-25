package com.it.cloudwater.user;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.CheckUtil;
import com.it.cloudwater.utils.CountDownTimerUtils;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

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
    @BindView(R.id.et_mobileNumber)
    EditText etMobileNumber;
    @BindView(R.id.get_sms_code)
    TextView getSmsCode;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_password_again)
    EditText etPasswordAgain;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private String phoneNumberInput;
    private String smscodeInput;
    private String password;
    private String rePassword;
    private boolean isPhone;
    private String resetPassword = "3";

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("忘记密码");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(this);
        getSmsCode.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_forget_password);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        phoneNumberInput = etMobileNumber.getText().toString();
        smscodeInput = etSmsCode.getText().toString();
        password = etPassword.getText().toString();
        rePassword = etPasswordAgain.getText().toString();
        isPhone = CheckUtil.isMobile(phoneNumberInput);
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.get_sms_code:
                if (phoneNumberInput.equals("")) {
                    ToastManager.show("手机号不能为空");
                    return;
                }
                if (!isPhone) {
                    ToastManager.show("手机号输入不对");
                    return;
                }
                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(getSmsCode, 60000, 1000);
                mCountDownTimerUtils.start();
//                SmepApi.getSmsCode(0x002, phoneNumberInput, resetPassword, callBack);
                CloudApi.getSmsCode(0x002, phoneNumberInput,1, myCallBack);
                break;
            case R.id.btn_submit:
                if (phoneNumberInput.equals("")) {
                    ToastManager.show("手机号不能为空");
                    return;
                }
                if (!isPhone) {
                    ToastManager.show("手机号输入不对");
                    return;
                }
                if (smscodeInput.equals("")) {
                    ToastManager.show("验证码不能为空");
                    return;
                }
                if (password.equals("")) {
                    ToastManager.show("密码不能为空");
                    return;
                }
                if (rePassword.equals("")) {
                    ToastManager.show("重复密码不能为空");
                    return;
                }
                if (!password.equals(rePassword)) {
                    ToastManager.show("两次输入密码不一致");
                    return;
                }
                CloudApi.forgetPassword(0x001, phoneNumberInput, password, smscodeInput, myCallBack);
                break;
        }
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    try {
                        JSONObject jsonObject = new JSONObject(result.body());
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            ToastManager.show("找回密码成功");
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0x002:
                    try {
                        JSONObject jsonObject = new JSONObject(result.body());
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            ToastManager.show("短信发送成功");
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
