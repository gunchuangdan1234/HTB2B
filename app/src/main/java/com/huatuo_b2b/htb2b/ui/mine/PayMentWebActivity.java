package com.huatuo_b2b.htb2b.ui.mine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.custom.RoundProgressBar;

/**
 * 在线支付页面
 */
public class PayMentWebActivity extends Activity implements OnClickListener {
    private RoundProgressBar roundProgressBar;
    private WebView webviewID;
    private MyShopApplication myApplication;
    private Handler mHandler = new Handler();

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_web_view);

        String pay_sn = getIntent().getStringExtra("pay_sn");//"430507745359431059";
        String order_sn = getIntent().getExtras().getString("order_sn");//"8000000000020801";

        myApplication = (MyShopApplication) getApplicationContext();

        webviewID = (WebView) findViewById(R.id.webviewID);
        roundProgressBar = (RoundProgressBar) findViewById(R.id.roundProgressBarID);
        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);

        WebSettings settings = webviewID.getSettings();
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//根据屏幕缩放
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);//根据屏幕缩放
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        webviewID.setWebChromeClient(new MyWebChromeClient());

//		if(order_sn!= null && !order_sn.equals("") && !order_sn.equals("null")){
//			System.out.println("URL1:"+Constants.URL_VIRTUAL_ORDER_PAYMENT+"&key="+myApplication.getLoginKey()+"&pay_sn="+order_sn);
//			//虚拟订单使用
//			webviewID.loadUrl(Constants.URL_VIRTUAL_ORDER_PAYMENT+"&key="+myApplication.getLoginKey()+"&pay_sn="+order_sn);
//		}else{
        System.out.println("URL2:" + Constants.URL_ORDER_PAYMENT + "&key=" + myApplication.getLoginKey() + "&pay_sn=" + pay_sn);
//			//实物订单使用
        webviewID.loadUrl(Constants.URL_ORDER_PAYMENT + "&key=" + myApplication.getLoginKey() + "&pay_sn=" + pay_sn);
//		}

        //URL1:http://b2b.huatuoyf.com/mobile/index.php?act=member_payment&op=vr_pay&key=24f93f0a13bc81313f5978ea5ae5c38d&pay_sn=null
        //URL2:http://b2b.huatuoyf.com/mobile/index.php?act=member_payment&op=pay&key=24f93f0a13bc81313f5978ea5ae5c38d&pay_sn=470507288650616112

        webviewID.addJavascriptInterface(new PayMentJavaScriptInterface(), "demo");
        webviewID.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                //System.out.println("########################url1: "+ url);

                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                //liubw System.out.println("url2: " + url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                //liubw System.out.println("url3: " + url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                view.loadUrl("file:///android_asset/error.html");

                //liubw System.out.println("url4: ");
            }
        });
        imageBack.setOnClickListener(this);
    }

    private class MyWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            roundProgressBar.setProgress(newProgress);
            if (newProgress == 100) {
                roundProgressBar.setVisibility(View.GONE);
            }
            super.onProgressChanged(view, newProgress);
        }
    }

    final class PayMentJavaScriptInterface {
        PayMentJavaScriptInterface() {
        }

        public void checkPaymentAndroid(final String flag) {
            mHandler.post(new Runnable() {
                public void run() {
                    // 此处调用 HTML 中的javaScript 函数
                    if (flag.equals("success")) {
                        Toast.makeText(PayMentWebActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        Intent mIntent = new Intent(Constants.PAYMENT_SUCCESS);
                        sendBroadcast(mIntent);
                    } else if (flag.equals("fail")) {
                        Toast.makeText(PayMentWebActivity.this, "支付失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
            default:
                break;
        }
    }
}
