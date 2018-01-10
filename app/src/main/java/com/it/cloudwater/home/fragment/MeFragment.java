package com.it.cloudwater.home.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseFragment;
import com.it.cloudwater.user.AddressActivity;
import com.it.cloudwater.user.BucketActivity;
import com.it.cloudwater.user.CouponActivity;
import com.it.cloudwater.user.Distribution2Activity;
import com.it.cloudwater.user.DistributionActivity;
import com.it.cloudwater.user.InvitationActivity;
import com.it.cloudwater.user.LoginActivity;
import com.it.cloudwater.user.MoreActivity;
import com.it.cloudwater.user.OrderActivity;
import com.it.cloudwater.user.ReceiveCenterActivity;
import com.it.cloudwater.user.TicketActivity;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.it.cloudwater.widget.RoundedCornerImageView;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by hpc on 2017/6/16.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.iv_avatar)
    RoundedCornerImageView ivAvatar;
    @BindView(R.id.tv_phoneNumber)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_login)
    TextView tvLogin;
    @BindView(R.id.rl_avatar)
    RelativeLayout rlAvatar;
    @BindView(R.id.iv_coupon_my)
    ImageView ivCouponMy;
    @BindView(R.id.tv_coupon_my)
    TextView tvCouponMy;
    @BindView(R.id.rl_coupon_my)
    RelativeLayout rlCouponMy;
    @BindView(R.id.iv_invent_my)
    ImageView ivInventMy;
    @BindView(R.id.tv_invent_my)
    TextView tvInventMy;
    @BindView(R.id.rl_invent_my)
    RelativeLayout rlInventMy;
    @BindView(R.id.iv_address_my)
    ImageView ivAddressMy;
    @BindView(R.id.tv_address_my)
    TextView tvAddressMy;
    @BindView(R.id.rl_address_my)
    RelativeLayout rlAddressMy;
    @BindView(R.id.iv_bucket_my)
    ImageView ivBucketMy;
    @BindView(R.id.tv_bucket_my)
    TextView tvBucketMy;
    @BindView(R.id.rl_bucket_my)
    RelativeLayout rlBucketMy;
    @BindView(R.id.iv_ticket_my)
    ImageView ivTicketMy;
    @BindView(R.id.tv_ticket_my)
    TextView tvTicketMy;
    @BindView(R.id.rl_ticket_my)
    RelativeLayout rlTicketMy;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.tv_more)
    TextView tvMore;
    @BindView(R.id.rl_more)
    RelativeLayout rlMore;
    @BindView(R.id.iv_order_my)
    ImageView ivOrderMy;
    @BindView(R.id.tv_order_my)
    TextView tvOrderMy;
    @BindView(R.id.rl_order_my)
    RelativeLayout rlOrderMy;
    @BindView(R.id.rl_receive_center_my)
    RelativeLayout rlReceiveCenterMy;
    @BindView(R.id.iv_distribution_my)
    ImageView ivDistributionMy;
    @BindView(R.id.tv_distribution_my)
    TextView tvDistributionMy;
    @BindView(R.id.rl_distribution_my)
    RelativeLayout rlDistributionMy;
    Unbinder unbinder;
    private String userId;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    protected void initListener() {
        rlDistributionMy.setVisibility(View.GONE);
        String userType = StorageUtil.getUserType(getActivity());
        String userPhone = StorageUtil.getValue(getActivity(), "userPhone");
        if (!userPhone.equals("")) {
            tvPhoneNumber.setText(userPhone);
        } else {
            tvPhoneNumber.setText("登录");
            tvPhoneNumber.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
        }

        userId = StorageUtil.getUserId(getActivity());
        if (!userType.equals("")) {
            if (Integer.parseInt(userType) == 0) {
                //员工
                rlDistributionMy.setVisibility(View.VISIBLE);
            } else if (Integer.parseInt(userType) == 1) {
                //客户
                rlDistributionMy.setVisibility(View.GONE);
            }
        }

        rlTicketMy.setOnClickListener(this);
        rlBucketMy.setOnClickListener(this);
        rlInventMy.setOnClickListener(this);

        rlAddressMy.setOnClickListener(this);
        rlCouponMy.setOnClickListener(this);
        rlOrderMy.setOnClickListener(this);
        rlTicketMy.setOnClickListener(this);
        rlDistributionMy.setOnClickListener(this);
        rlReceiveCenterMy.setOnClickListener(this);
        rlMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MoreActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        if (userId.equals("")) {
            ToastManager.show("请先登录");
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        switch (v.getId()) {
            case R.id.rl_ticket_my:
                intent = new Intent(getActivity(), TicketActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_bucket_my:
                intent = new Intent(getActivity(), BucketActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_invent_my:
                intent = new Intent(getActivity(), InvitationActivity.class);
                startActivity(intent);
                break;

            case R.id.rl_address_my:
                intent = new Intent(getActivity(), AddressActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_coupon_my:
                intent = new Intent(getActivity(), CouponActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_order_my:
                intent = new Intent(getActivity(), OrderActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_distribution_my:
                intent = new Intent(getActivity(), Distribution2Activity.class);
                startActivity(intent);
                break;
                case R.id.rl_receive_center_my:
                intent = new Intent(getActivity(), ReceiveCenterActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

    }

}
