package com.it.cloudwater.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.constant.Constant;
import com.it.cloudwater.utils.CheckUtil;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

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
    private String phoneNumberInput;
    private String smscodeInput;
    private boolean b;

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
        smscodeInput = etPassword.getText().toString();
        b = CheckUtil.isMobile(phoneNumberInput);
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_right:
                intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_left:
                finish();
                break;

            case R.id.btn_login:
                if (phoneNumberInput.equals("")) {
                    ToastManager.show("手机号不能为空");
                    return;
                }
                if (!b) {
                    ToastManager.show("手机号输入不对");
                    return;
                }
                if (smscodeInput.equals("")) {
                    ToastManager.show("验证码不能为空");
                    return;
                }

//                OkGo.post(Constant.LOGIN_URL)
//                        .tag(this)
//                        .params("phone", phoneNumberInput)
//                        .params("sms_code", smscodeInput)
//                        .params("sms_template_code", "login_message")
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(String s, Call call, Response response) {
//                                System.out.println("数据记录------------" + s);
//                                try {
//                                    JSONObject jsonObject = new JSONObject(s);
//                                    JSONObject data = jsonObject.getJSONObject("data");
//                                    int code = data.getInt("code");
//                                    if (code == 103) {
//                                        String msg = data.getString("msg");
//                                        ToastManager.show(msg);
//
//                                    } else if (code == 1) {
//                                        String token = data.getString("token");
//                                        String userId = data.getString("id");
//                                        StorageUtil.setKeyValue(LoginActivity.this, "token", token);
//                                        StorageUtil.setKeyValue(LoginActivity.this, "userId", userId);
//                                        startActivity(new Intent(LoginActivity.this, HomeTabActivity.class));
//                                        ToastManager.show("登录成功");
//                                    } else if (code == 104) {
//                                        String msg = data.getString("msg");
//                                        ToastManager.show(msg);
//                                    }
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onError(Call call, Response response, Exception e) {
//                                super.onError(call, response, e);
//                            }
//
//                        });
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
