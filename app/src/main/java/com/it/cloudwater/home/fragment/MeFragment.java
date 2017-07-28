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
import com.it.cloudwater.user.InvitationActivity;
import com.it.cloudwater.user.MoreActivity;
import com.it.cloudwater.widget.RoundedCornerImageView;

import butterknife.BindView;

/**
 * Created by hpc on 2017/6/16.
 */

public class MeFragment extends BaseFragment implements View.OnClickListener {
    @BindView(R.id.iv_avatar)
    RoundedCornerImageView ivAvatar;
    @BindView(R.id.rl_tag)
    RelativeLayout rlTag;
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

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_me, container, false);
    }

    @Override
    protected void initListener() {
        rlTicketMy.setOnClickListener(this);
        rlBucketMy.setOnClickListener(this);
        rlInventMy.setOnClickListener(this);
        rlMore.setOnClickListener(this);
        rlAddressMy.setOnClickListener(this);
        rlCouponMy.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_ticket_my:

                break;
            case R.id.rl_bucket_my:
                intent = new Intent(getActivity(), BucketActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_invent_my:
                intent = new Intent(getActivity(), InvitationActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_more:
                intent = new Intent(getActivity(), MoreActivity.class);
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

            default:
                break;
        }

    }
}
