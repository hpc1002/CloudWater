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
import com.lzy.okgo.model.Response;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class AddAddressActivity extends BaseActivity {


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
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_quxian)
    TextView tvQuxian;
    @BindView(R.id.et_quxian)
    EditText etQuxian;
    @BindView(R.id.tv_business_circle)
    TextView tvBusinessCircle;
    @BindView(R.id.et_business_circle)
    EditText etBusinessCircle;
    @BindView(R.id.tv_village)
    TextView tvVillage;
    @BindView(R.id.et_village)
    EditText etVillage;
    @BindView(R.id.tv_detail_address)
    TextView tvDetailAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    @BindView(R.id.save)
    Button save;

    @Override
    protected void processLogic() {

    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("新建收货地址");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> params = new HashMap<>();
                params.put("strReceiptusername", "strReceiptusername");
                params.put("strReceiptmobile", "strReceiptmobile");
                params.put("strLocation", "strLocation");
                params.put("strShopname", "strShopname");
                params.put("strNeighbourhood", "strNeighbourhood");
                params.put("lUserid", "lUserid");
                params.put("lShopId", "lShopId");
                JSONObject jsonObject = new JSONObject(params);
                CloudApi.addAddress(0x001, jsonObject, myCallBack);
            }
        });
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {

        }

        @Override
        public void onFail(int what, Response<String> result) {

        }
    };

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_add_address);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }
}
