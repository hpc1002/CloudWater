package com.it.cloudwater.user;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
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
    @BindView(R.id.tv_detail_address)
    TextView tvDetailAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    @BindView(R.id.save)
    Button save;
    private String userId;
    private String buyerName;
    private String buyerPhone;
    private String strLocation;
    private String buyerDetailAddress;
    private String area;
    private static final String TAG = "AddAddressActivity";
    private int defaultPosition;
    ArrayList<Object> list = new ArrayList<>();
    private String address_tag;
    private String lId;

    @Override
    protected void processLogic() {
        userId = StorageUtil.getUserId(this);

    }

    @Override
    protected void setListener() {

        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        CloudApi.getAreaList(0x001, myCallBack);

        address_tag = getIntent().getStringExtra("address");
        if (address_tag.equals("address_edit")) {
            toolbarTitle.setText("编辑收货地址");
            lId = getIntent().getStringExtra("lId");
            String strReceiptusername = getIntent().getStringExtra("strReceiptusername");
            String strDetailaddress = getIntent().getStringExtra("strDetailaddress");
            area = getIntent().getStringExtra("strLocation");
            String strReceiptmobile = getIntent().getStringExtra("strReceiptmobile");
            etName.setText(strReceiptusername);
            etPhone.setText(strReceiptmobile);
            etDetailAddress.setText(strDetailaddress);
        } else if (address_tag.equals("address_new")) {
            toolbarTitle.setText("新增收货地址");
        }


//        if (!lId.equals("")) {
//            CloudApi.updateAddress(0x002, Long.parseLong(lId), strReceiptusername, strLocation, strDetailaddress, strReceiptmobile, myCallBack);
//        }

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

                            for (int i = 0; i < data.length(); i++) {
                                list.add(data.get(i));
                            }

                            ArrayAdapter<Object> stringArrayAdapter = new ArrayAdapter<Object>(AddAddressActivity.this, android.R.layout.simple_spinner_dropdown_item, list);
                            stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spQuxian.setAdapter(stringArrayAdapter);
                            if (address_tag.equals("address_edit") && !area.isEmpty()) {
                                for (int i = 0; i < list.size(); i++) {
                                    if (list.get(i).toString().equals(area)) {
                                        defaultPosition = i;
                                        spQuxian.setSelection(defaultPosition);
                                    }
                                }
                            }
                            spQuxian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    strLocation = list.get(position).toString();
                                    spQuxian.setSelection(position);
                                    Log.i(TAG, "onItemSelected: strLocation" + strLocation);
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                            save.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    buyerName = etName.getText().toString();
                                    buyerPhone = etPhone.getText().toString();
                                    buyerDetailAddress = etDetailAddress.getText().toString();
                                    Map<String, String> params = new HashMap<>();
                                    params.put("strReceiptusername", buyerName);
                                    params.put("strReceiptmobile", buyerPhone);
                                    params.put("strLocation", strLocation);
                                    params.put("strDetailaddress", buyerDetailAddress);
                                    params.put("lUserid", userId);
                                    if (address_tag.equals("address_edit")) {
                                        params.put("lId", lId);
                                    }
                                    JSONObject jsonObject = new JSONObject(params);
                                    if (address_tag.equals("address_edit")) {
                                        CloudApi.updateAddress(0x003, jsonObject, myCallBack);
                                    } else if (address_tag.equals("address_new")) {
                                        CloudApi.addAddress(0x002, jsonObject, myCallBack);
                                    }

                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0x002:
                    String body2 = result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body2);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
                            ToastManager.show("新建地址成功");
                            String addressId = jsonObject.getString("result");
                            finish();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 0x003:
                    try {
                        JSONObject jsonObject = new JSONObject(result.body());
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("0")) {
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

    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_add_address);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }
}
