package com.it.cloudwater.user;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.adapter.CheckAdapter;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.bean.AddressListBean;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.lhalcyon.adapter.helper.BasicController;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 我的地址
 */
public class AddressActivity extends BaseActivity {

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
    @BindView(R.id.address_recycler)
    RecyclerView addressRecycler;
    ArrayList<AddressListBean.Result.DataList> addressList;
    private CheckAdapter mAdapter;
    private static final String TAG = "AddressActivity";
    private String userId;
    private String address_tag = "";

    @Override
    protected void processLogic() {
        userId = StorageUtil.getUserId(this);
        CloudApi.getMyAddressList(0x001, 1, 8, Integer.parseInt(userId), myCallBack);
    }

    @Override
    protected void setListener() {
        toolbarTitle.setText("我的地址");
        tvRight.setVisibility(View.VISIBLE);
        tvRight.setText("新增");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddressActivity.this, AddAddressActivity.class));
                ToastManager.show("新增地址");

            }
        });

        address_tag = getIntent().getStringExtra("address_tag");
        addressRecycler.setLayoutManager(new LinearLayoutManager(this));


    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    addressList = new ArrayList<>();
                    String body = result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("1")) {
                            String result1 = jsonObject.getString("result");
                            ToastManager.show(result1);
                            addressList.clear();
                        } else if (resCode.equals("0")) {
                            AddressListBean addressListBean = new Gson().fromJson(body, AddressListBean.class);
                            int size = addressListBean.result.dataList.size();
                            for (int i = 0; i < size; i++) {
                                addressList.add(addressListBean.result.dataList.get(i));
                            }
                            BasicController.BasicParams params = new BasicController.Builder()
                                    .checkId(R.id.checkbox)
                                    .choiceMode(BasicController.CHOICE_MODE_SINGLE)
                                    .layoutRes(R.layout.item_check)
                                    .build();
                            addressRecycler.setAdapter(mAdapter = new CheckAdapter(params, addressList) {
                                @Override
                                public boolean isItemChecked(AddressListBean.Result.DataList checkBean, int position) {
                                    return checkBean.isSingle;
                                }
                            });
                            mAdapter.setCallBack(new CheckAdapter.OnMyClickListener() {
                                @Override
                                public void OnItemEditClickListener(long lId, String strNeighbourhood, String strReceiptmobile) {
                                    Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                                    intent.putExtra("lId", lId);
                                    startActivity(intent);
//                                    CloudApi.updateAddress(0x002, lId, strNeighbourhood, strReceiptmobile, myCallBack);
                                }

                                @Override
                                public void OnItemDeleteClickListener(long lId) {
                                    CloudApi.deleteAddress(0x002, lId, myCallBack);

                                }

                                @Override
                                public void onItemClickListener(AddressListBean.Result.DataList data) {
                                    if (data != null && address_tag != null) {
                                        Intent intent = new Intent();
                                        //把返回数据存入Intent
                                        intent.putExtra("addressName", data.strReceiptusername);
                                        intent.putExtra("addressId", data.lId + "");
                                        intent.putExtra("addressPhone", data.strReceiptmobile + "");
                                        intent.putExtra("addressDetail", data.strDetailaddress);
                                        intent.putExtra("addressLocation", data.strLocation);
                                        AddressActivity.this.setResult(RESULT_OK, intent);
                                        //关闭Activity
                                        AddressActivity.this.finish();
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
                            CloudApi.getMyAddressList(0x001, 1, 8, Integer.parseInt(userId), myCallBack);
                            mAdapter.notifyDataSetChanged();
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
        setContentView(R.layout.activity_address);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }
}
