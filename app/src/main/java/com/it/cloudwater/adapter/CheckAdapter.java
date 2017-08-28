package com.it.cloudwater.adapter;

import android.view.View;

import com.it.cloudwater.R;
import com.it.cloudwater.bean.AddressListBean;
import com.lhalcyon.adapter.BasicAdapter;
import com.lhalcyon.adapter.base.BaseViewHolder;
import com.lhalcyon.adapter.helper.BasicController.BasicParams;

import java.util.List;

/**
 * ©2016-2017 kmhealthcloud.All Rights Reserved <p/>
 * Created by: L  <br/>
 * Description:
 */

public class CheckAdapter extends BasicAdapter<AddressListBean.Result.DataList> {

    public CheckAdapter(BasicParams params, List<AddressListBean.Result.DataList> data) {
        super(params, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, int position, final AddressListBean.Result.DataList man) {
        holder.setText(R.id.tv_name, man.strReceiptusername);
        holder.setText(R.id.tv_address, man.strDetailaddress);
        holder.setText(R.id.tv_phone, man.strReceiptmobile + "");
        holder.getView(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.OnItemEditClickListener(man.lId, man.strNeighbourhood, man.strReceiptmobile);
            }
        });
        holder.getView(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.OnItemDeleteClickListener(man.lId);
            }
        });
        holder.getView(R.id.checkbox).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCallBack.onItemClickListener(man);
            }
        });
    }

    private CheckAdapter.OnMyClickListener mCallBack;

    public void setCallBack(CheckAdapter.OnMyClickListener callBack) {
        mCallBack = callBack;
    }

    public interface OnMyClickListener {
        void OnItemEditClickListener(long lId, String strNeighbourhood, String strReceiptmobile);

        void OnItemDeleteClickListener(long lId);
        void onItemClickListener(AddressListBean.Result.DataList data);

    }
}
