package com.it.cloudwater.constant;

/**
 * Created by hpc on 2017/3/24.
 */

public class Constant {
    public static final String BASE_URL = "http://59.110.235.32/api/app/";//基本URL
    public static final String LOGIN_URL = BASE_URL + "buyer/login";//买家登录
    public static final String REGISTER_URL = BASE_URL + "buyer/register";//买家注册
    public static final String SENDSMS_URL = BASE_URL + "common/sendSms";//获取手机验证码
    public static final String CHANGE_PASSWORD_URL = BASE_URL + "buyer/modifyPassword";//修改密码
    public static final String GOODS_LIST_URL = BASE_URL + "goods/list";//首页商品列表
    public static final String GOODS_DETAIL_URL = BASE_URL + "goods/detail/";//商品详情
    public static final String ORDER_URL = BASE_URL + "order/add";//提交订单
    public static final String BUYER_DETAIL_URL = BASE_URL + "buyer/detail/";//获取买家详细信息
    public static final String MY_ORDER_LIST_URL = BASE_URL + "order/list";//我的订单列表
    public static final String ADD_ADDRESS_URL = BASE_URL + "address/add";//添加收获地址
}
