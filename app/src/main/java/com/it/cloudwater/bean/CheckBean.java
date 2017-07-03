package com.it.cloudwater.bean;

import java.io.Serializable;

/**
 * Created by hpc on 2017/7/3.
 */

public class CheckBean implements Serializable{
    public String name;
    public String address;
    public int phoneNumber;
    public boolean isSingle;

    public CheckBean(String name, String address, int phoneNumber,boolean isSingle) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.isSingle=isSingle;
    }
}
