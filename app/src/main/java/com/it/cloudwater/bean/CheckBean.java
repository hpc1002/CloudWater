package com.it.cloudwater.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by hpc on 2017/7/3.
 */

public class CheckBean implements Serializable{

    public ArrayList<AddressListBean.Result.DataList> addressListBean;
    public boolean isSingle;

    public CheckBean(ArrayList<AddressListBean.Result.DataList> addressListBean, boolean isSingle) {
        this.addressListBean = addressListBean;
        this.isSingle=isSingle;
    }
}
