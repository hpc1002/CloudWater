package com.it.cloudwater.user;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
    @BindView(R.id.spinner_quxian)
    Spinner spQuxian;
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
        CloudApi.getAreaList(0x001, myCallBack);
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
//                CloudApi.addAddress(0x001, jsonObject, myCallBack);
            }
        });
    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
               /* {
                        "result": [
                        "东城区",
                        "西城区"
                        ],
                        "resCode": "0"
                }*/
                    String body = result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            JSONArray data = jsonObject.getJSONArray("result");
                            ArrayList<Object> list = new ArrayList<>();
                            for (int i = 0; i < data.length(); i++) {
                                list.add(data.get(i));
                            }
                            ArrayAdapter<Object> stringArrayAdapter = new ArrayAdapter<Object>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
                            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spQuxian.setAdapter(stringArrayAdapter);
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
    protected void loadViewLayout() {
        setContentView(R.layout.activity_add_address);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }
}
