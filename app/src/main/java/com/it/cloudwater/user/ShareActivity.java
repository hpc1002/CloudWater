package com.it.cloudwater.user;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.StrictMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

import com.it.cloudwater.R;
import com.it.cloudwater.base.BaseActivity;
import com.it.cloudwater.constant.Constant;
import com.it.cloudwater.utils.BitmapUtil;
import com.it.cloudwater.utils.UIThread;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;

public class ShareActivity extends BaseActivity {


    //    @BindView(R.id.toolbar_title)
//    TextView toolbarTitle;
//    @BindView(R.id.tv_right)
//    TextView tvRight;
//    @BindView(R.id.iv_right)
//    ImageView ivRight;
//    @BindView(R.id.iv_search)
//    ImageView ivSearch;
//    @BindView(R.id.iv_left)
//    ImageView ivLeft;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webView)
    WebView webView;
    private Bitmap scaledBitmap;
    //应用和程序id
    private IWXAPI api;
    private String strId;
    private String strActivityName;
    private String strRemarks;
    private String strUrl;
    private long lId;
    private Bitmap bitmap;

    @Override
    protected void processLogic() {

    }


    @Override
    protected void setListener() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("分享");
        toolbar.setNavigationIcon(R.mipmap.back);
//        toolbar.setOverflowIcon();
//        toolbarTitle.setText("分享");
//        ivLeft.setVisibility(View.VISIBLE);
//        tvRight.setVisibility(View.VISIBLE);
//        tvRight.setText("分享");

        strUrl = getIntent().getStringExtra("strUrl");
        strId = getIntent().getStringExtra("strId");

        lId = Long.parseLong(strId);

        bitmap = BitmapUtil.GetLocalOrNetBitmap(Constant.IMAGE_URL + "1/" + lId);
        strActivityName = getIntent().getStringExtra("strActivityName");
        strRemarks = getIntent().getStringExtra("strRemarks");
        webView.loadUrl(strUrl);
//        ivLeft.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        tvRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                WXWebpageObject webpage = new WXWebpageObject();
//                webpage.webpageUrl =strUrl;
//                //创建一个对象
//                WXMediaMessage msg = new WXMediaMessage(webpage);
//                msg.title = strActivityName;
//                msg.description = strRemarks;
//
//                scaledBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//
//                msg.thumbData = bmpToByteArray(scaledBitmap, true);
//                //创建 SendMessageToWX.Req
//                SendMessageToWX.Req req = new SendMessageToWX.Req();
//                req.transaction = buildTransaction("webpage");
//                req.message = msg;
//                req.scene = SendMessageToWX.Req.WXSceneSession;
//                api.sendReq(req);
//            }
//        });
    }

    @Override
    protected void loadViewLayout() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        setContentView(R.layout.activity_share);
        api = WXAPIFactory.createWXAPI(this, Constant.WX_APP_ID, false);
        api.registerApp(Constant.WX_APP_ID);
    }

    @Override
    protected Context getActivityContext() {
        return this;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = strUrl;
        //创建一个对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = strActivityName;
        msg.description = strRemarks;

//        scaledBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        UIThread.getInstance().post(new Runnable() {
            @Override
            public void run() {

            }
        });
        scaledBitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
        msg.thumbData = bmpToByteArray(scaledBitmap, true);
        //创建 SendMessageToWX.Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        switch (item.getItemId()) {
            case R.id.action_share_friend:

                req.scene = SendMessageToWX.Req.WXSceneSession;
                api.sendReq(req);
                break;
            case R.id.action_share_circle:
                req.scene = SendMessageToWX.Req.WXSceneTimeline;
                api.sendReq(req);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    //将bitmap转换为byte[]格式
    private byte[] bmpToByteArray(final Bitmap bitmap, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bitmap.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
