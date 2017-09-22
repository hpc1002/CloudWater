package com.it.cloudwater.constant;

/**
 * Created by hpc on 2017/3/24.
 */

public class Constant {
    public static final String BASE_URL = "http://59.110.235.32:8080/api/app/";//基本URL
//    public static final String BASE_URL = "http://www.fuzhents.com/api/app/";//基本URL
    public static final String LOGIN_URL = BASE_URL + "buyer/login";//买家登录
    public static final String REGISTER_URL = BASE_URL + "buyer/register";//买家注册
    public static final String SENDSMS_URL = BASE_URL + "common/sendSms";//获取手机验证码
    public static final String CHANGE_PASSWORD_URL = BASE_URL + "buyer/modifyPassword";//修改密码
    public static final String GOODS_LIST_URL = BASE_URL + "goods/list";//首页商品列表
    public static final String GOODS_DETAIL_URL = BASE_URL + "goods/detail/";//商品详情
    public static final String ORDER_SUBMIT_URL = BASE_URL + "order/add";//提交订单
    public static final String BUYER_DETAIL_URL = BASE_URL + "buyer/detail/";//获取买家详细信息
    public static final String MY_ORDER_LIST_URL = BASE_URL + "order/list";//我的订单列表
    public static final String ORDER_PAY_DETAIL_URL = BASE_URL + "order/payDetail/";//订单支付详情
    public static final String ADD_ADDRESS_URL = BASE_URL + "address/add";//添加收获地址

    public static final String AREA_LIST_URL = BASE_URL + "shop/allLocation";//获取区列表
    public static final String GET_SHOP_URL = BASE_URL + "shop/getShops";//根据区获取商铺

    public static final String MYTICKET_LIST_URL = BASE_URL + "ticket/myList";//获取买家水票
    public static final String BUYTICKET_LIST_URL = BASE_URL + "ticket/list";//购买水票,获取可用水票
    public static final String TICKET_DETAIL_URL = BASE_URL + "ticket/detail/";//水票详情
    public static final String TICKET_BUY_URL = BASE_URL + "ticket/buy";//购买水票
    public static final String MYCOUPON_LIST_URL = BASE_URL + "coupon/list";//我的优惠券列表

    public static final String ADDRESS_LIST_URL = BASE_URL + "address/list";//获取买家收货地址
    public static final String ADDRESS_UPDATE_URL = BASE_URL + "address/update";//修改收货地址
    public static final String ADDRESS_DELETE_URL = BASE_URL + "address/delete";//删除收货地址
    public static final String BUCKET_URL = BASE_URL + "buyer/myBucket/";//获取我的水桶
    public static final String RETREAT_BUCKET_URL = BASE_URL + "buyer/retreatBucket";//退桶
    public static final String ADD_SHOP_CART_URL = BASE_URL + "shopping/add";//添加购物车
    public static final String SHOP_CART_DELETE_URL = BASE_URL + "shopping/delete";//购物车删除
    public static final String SHOP_CART_LIST_URL = BASE_URL + "shopping/myList";//购物车商品列表
    public static final String SETTLEMENT_URL = BASE_URL + "order/settlement";//去结算
    public static final String ORDER_DETAIL_URL = BASE_URL + "order/detail/";//订单详情
    public static final String MESSAGE_LIST_URL = BASE_URL + "notice/myList";//消息列表
    public static final String MORE_URL = BASE_URL + "sysvalue/con/";//更多内容获取
    public static final String FEEDBACK_URL = BASE_URL + " feedback/add";//添加用户反馈
    public static final String IMAGE_URL = BASE_URL + "common/getImg/";//获取图片资源
    public static final String LUNBO_URL = BASE_URL + "activity/list";//获取轮播图片
    public static final String LOGOUT_URL = BASE_URL + "buyer/logout";//退出登录
    public static final String ALIPAY_URL = BASE_URL+"pay/alipay.do";//阿里支付


}
