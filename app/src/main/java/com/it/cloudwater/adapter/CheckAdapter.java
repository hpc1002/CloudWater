package com.it.cloudwater.adapter;

import com.it.cloudwater.R;
import com.it.cloudwater.bean.CheckBean;
import com.lhalcyon.adapter.BasicAdapter;
import com.lhalcyon.adapter.base.BaseViewHolder;
import com.lhalcyon.adapter.helper.BasicController.BasicParams;

import java.util.List;

/**
 * ©2016-2017 kmhealthcloud.All Rights Reserved <p/>
 * Created by: L  <br/>
 * Description:
 */

public class CheckAdapter extends BasicAdapter<CheckBean> {

    public CheckAdapter(BasicParams params, List<CheckBean> data) {
        super(params, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, int position, CheckBean man) {
        holder.setText(R.id.tv_name, man.name);
        holder.setText(R.id.tv_address, man.address);
        holder.setText(R.id.tv_phone, man.phoneNumber+"");
    }
}
