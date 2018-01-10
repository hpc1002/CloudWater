package com.it.cloudwater.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.user.observer.SmsObserver;
import com.it.cloudwater.utils.CheckUtil;
import com.it.cloudwater.utils.CountDownTimerUtils;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;

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
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.tv_password)
    TextView tvPassword;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.tv_invent_code)
    TextView tvInventCode;
    @BindView(R.id.et_invent_code)
    EditText etInventCode;
    private String phoneNumberInput;
    private String smscodeInput;
    private String password;
    private boolean isPhone;
    private boolean isPwd;
    private String resCode;
    private String inwenteCode;
    public static final int MSG_RECEIVED_CODE = 1;
    private SmsObserver smsObserver;
    private static final String TAG = "RegisterActivity";
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
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

        inwenteCode = etInventCode.getText().toString();
        password = etPassword.getText().toString();
        isPhone = CheckUtil.isMobile(phoneNumberInput);
        isPwd = CheckUtil.isPassword(password);
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
                if (!isPhone) {
                    ToastManager.show("手机号输入不对");
                    return;
                }


                CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(getSmsCode, 60000, 1000);
                mCountDownTimerUtils.start();
                CloudApi.getSmsCode(0x001, phoneNumberInput, 0, new MyCallBack() {
                    @Override
                    public void onSuccess(int what, Response<String> data) {
                        try {
                            String body = data.body();
                            JSONObject sendData = new JSONObject(body);


                            resCode = sendData.getString("resCode");
                            String result = sendData.getString("result");
                            if (resCode.equals("1")) {
                                ToastManager.show(result);
                            } else if (resCode.equals("0")) {
                                ToastManager.show(result);
                            }

                            Log.i(TAG, "SendCode: ---------" + "result" + result + "resCode" + resCode);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int what, Response<String> result) {

                    }
                });
//                smsCode=resCode;
                break;
            case R.id.btn_register:

                if (phoneNumberInput.equals("")) {
                    ToastManager.show("手机号不能为空");
                    return;
                }
                if (!isPhone) {
                    ToastManager.show("手机号输入不对");
                    return;
                }
                if (!isPwd) {
                    ToastManager.show("密码格式不对");
                    return;
                }
                if (smscodeInput.equals("")) {
                    ToastManager.show("验证码不能为空");
                    return;
                }

                CloudApi.Register(0x002, phoneNumberInput, password, smscodeInput, inwenteCode, myCallBack);
                break;
            default:
                break;
        }
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> data) {
            switch (what) {
                case 0x001:
                    Log.i(TAG, "onDataSuccess: success" + data);
                    String body = data.body();
                    try {
                        JSONObject sendData = new JSONObject(body);
                        String result = sendData.getString("result");
                        resCode = sendData.getString("resCode");
                        Log.i(TAG, "SendCode: ---------" + "result" + result + "resCode" + resCode);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0x002:
                    String body1 = data.body();
                    try {
                        JSONObject registerData = new JSONObject(body1);
                        String result = registerData.getString("result");
                        String resCode = registerData.getString("resCode");
                        StorageUtil.setKeyValue(RegisterActivity.this, "userId", result);
                        if (resCode.equals("1")) {
                            ToastManager.show(result);
                        } else if (resCode.equals("0")) {
                            ToastManager.show("注册成功");
                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        }
                        Log.i(TAG, "Register: ---------" + "result" + result + "resCode" + resCode);
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
        if (smsObserver != null) {
            getContentResolver().unregisterContentObserver(smsObserver);
        }
    }

}
