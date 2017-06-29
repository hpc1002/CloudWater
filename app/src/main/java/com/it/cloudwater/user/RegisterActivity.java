package com.it.cloudwater.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.constant.Constant;
import com.it.cloudwater.user.observer.SmsObserver;
import com.it.cloudwater.utils.CheckUtil;
import com.it.cloudwater.utils.CountDownTimerUtils;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;


import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Response;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {


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
    @BindView(R.id.get_sms_code)
    TextView getSmsCode;
    @BindView(R.id.tv_sms_code)
    TextView tvSmsCode;
    @BindView(R.id.et_sms_code)
    EditText etSmsCode;
    @BindView(R.id.btn_register)
    Button btnRegister;
    private String phoneNumberInput;
    private String smscodeInput;
    private boolean b;
    public static final int MSG_RECEIVED_CODE = 1;
    private SmsObserver smsObserver;
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == MSG_RECEIVED_CODE) {
                String code = (String) msg.obj;
                etSmsCode.setFocusable(true);
                etSmsCode.setText(code);
            }
        }
    };

    @Override
    protected void processLogic() {
        toolbarTitle.setText("注册");
        ivLeft.setVisibility(View.VISIBLE);
    }

    @Override
    protected void setListener() {
        ivLeft.setOnClickListener(this);
        getSmsCode.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
    }

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_register);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public void onClick(View view) {
        phoneNumberInput = etMobileNumber.getText().toString();
        smscodeInput = etSmsCode.getText().toString();
        b = CheckUtil.isMobile(phoneNumberInput);
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.get_sms_code:
                if (phoneNumberInput.equals("")) {
                    ToastManager.show("手机号不能为空");
                    return;
                }
                if (!b) {
                    ToastManager.show("手机号输入不对");
                    return;
                }

                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(getSmsCode, 60000, 1000);
                mCountDownTimerUtils.start();
//                OkGo.post(Constant.GETSMSCODE_URL)
//                        .tag(this)
//                        .params("phone", phoneNumberInput)
//                        .params("sms_template_code", "register_message")
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(String s, Call call, Response response) {
//                                try {
//                                    JSONObject jsonObject = new JSONObject(s);
//                                    String data = jsonObject.getString("data");
//                                    JSONObject dataObject = new JSONObject(data);
//                                    int code = dataObject.getInt("code");
//                                    String msg = dataObject.getString("msg");
//                                    if (code == 1) {
//                                        ToastManager.show(msg);
//                                        smsObserver = new SmsObserver(RegisterActivity.this, mHandler);
//                                        Uri uri = Uri.parse("content://sms");
//                                        getContentResolver().registerContentObserver(uri, true, smsObserver);
//                                    }
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                            @Override
//                            public void onError(Call call, Response response, Exception e) {
//                                super.onError(call, response, e);
//                                ToastManager.show("发送失败");
//                            }
//                        });
                break;
            case R.id.btn_register:

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

//                OkGo.post(Constant.REGISTER_URL)
//                        .tag(this)
//                        .params("phone", phoneNumberInput)
//                        .params("sms_code", smscodeInput)
//                        .params("sms_template_code", "register_message")
//                        .execute(new StringCallback() {
//                            @Override
//                            public void onSuccess(String s, Call call, Response response) {
//                                try {
//                                    JSONObject jsonObject = new JSONObject(s);
//                                    JSONObject data = jsonObject.getJSONObject("data");
//                                    int code = data.getInt("code");
//                                    if (code == 102) {
//                                        String msg = data.getString("msg");
//                                        ToastManager.show(msg);
//                                    } else if (code == 1) {
//                                        String token = data.getString("token");
//                                        String userId = data.getString("id");
//                                        StorageUtil.setKeyValue(RegisterActivity.this, "token", token);
//                                        StorageUtil.setKeyValue(RegisterActivity.this, "userId", userId);
//                                        startActivity(new Intent(RegisterActivity.this, HomeTabActivity.class));
//                                        ToastManager.show("注册成功");
//                                    } else if (code == 103) {
//                                        String msg = data.getString("msg");
//                                        ToastManager.show(msg);
//                                    }
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
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (smsObserver != null) {
            getContentResolver().unregisterContentObserver(smsObserver);
        }
    }
}
