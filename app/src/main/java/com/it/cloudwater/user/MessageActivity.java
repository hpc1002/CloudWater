package com.it.cloudwater.user;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.bean.MessageListBean;
import com.it.cloudwater.http.CloudApi;
import com.it.cloudwater.http.MyCallBack;
import com.it.cloudwater.push.MyMessageReceiver;
import com.it.cloudwater.utils.StorageUtil;
import com.it.cloudwater.utils.ToastManager;
import com.it.cloudwater.viewholder.MessageListViewHolder;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.lzy.okgo.model.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * 消息页面
 */
public class MessageActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener {

    private static final String TAG = "MessageActivity";
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
    @BindView(R.id.message_recycler)
    EasyRecyclerView messageRecycler;
    private String userId;
    private RecyclerArrayAdapter<MessageListBean.Result.DataList> messageAdapter;
    private int nTotal;
    private static final int REFRESH_COMPLETE = 0X110;
    private static final int SWIPE_REFRESH_COMPLETE = 0X111;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    messageRecycler.setRefreshing(false);
                    break;
                case SWIPE_REFRESH_COMPLETE:
//                    swipeRefresh.setRefreshing(false);
                    break;
            }
        }
    };

    @Override
    protected void processLogic() {
        if (!userId.equals("")) {
            CloudApi.getMessageList(0x001, 1, 10, Long.parseLong(userId), myCallBack);
        }

    }

    private MyCallBack myCallBack = new MyCallBack() {
        @Override
        public void onSuccess(int what, Response<String> result) {
            switch (what) {
                case 0x001:
                    String body = result.body();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String resCode = jsonObject.getString("resCode");
                        if (resCode.equals("1")) {
                            String emptyResult = jsonObject.getString("result");
                            ToastManager.show(emptyResult);
                        } else if (resCode.equals("0")) {
                            //解析数据并展示
                            MessageListBean messageListBean = new Gson().fromJson(body, MessageListBean.class);
                            nTotal=messageListBean.result.nTotal;
                            ArrayList<MessageListBean.Result.DataList> dataLists = new ArrayList<>();
                            for (int i = 0; i < messageListBean.result.dataList.size(); i++) {
                                dataLists.add(messageListBean.result.dataList.get(i));
                            }
                            initUi(dataLists);
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

    private void initUi(ArrayList<MessageListBean.Result.DataList> dataLists) {
        messageRecycler.setAdapterWithProgress(messageAdapter = new RecyclerArrayAdapter<MessageListBean.Result.DataList>(this) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new MessageListViewHolder(parent);
            }
        });
        messageAdapter.addAll(dataLists);
        mHandler.sendEmptyMessage(REFRESH_COMPLETE);
        messageAdapter.setNoMore(R.layout.view_nomore);
        messageAdapter.setMore(R.layout.view_more, this);
    }

    @Override
    protected void setListener() {
        userId = StorageUtil.getUserId(this);
        toolbarTitle.setText("我的消息");
        ivLeft.setVisibility(View.VISIBLE);
        ivLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        messageRecycler.setLayoutManager(new LinearLayoutManager(this));
        regMessageReceiver();
    }
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            for (BaseFragment fragment : fragments) {
//                if (fragment instanceof MessageFragment) {
//                    fragment.reload();
//                }
//            }
            if (!userId.equals("")) {
                CloudApi.getMessageList(0x001, 1, 10, Long.parseLong(userId), myCallBack);
            }
            Log.i(TAG, "onReceive: "+intent);
        }
    };

    private void regMessageReceiver() {
        IntentFilter filter = new IntentFilter(MyMessageReceiver.REC_TAG);
        registerReceiver(receiver, filter);
    }
    @Override
    protected void loadViewLayout() {
        setContentView(R.layout.activity_message);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    int page = 1;

    @Override
    public void onLoadMore() {
        if (!userId.equals("")) {

            if (page < (nTotal / 10 + 1)) {
                page++;
                CloudApi.getMessageList(0x001, 1, 10 * page, Long.parseLong(userId), myCallBack);
            } else {
                messageAdapter.stopMore();
            }
        }
    }

}
