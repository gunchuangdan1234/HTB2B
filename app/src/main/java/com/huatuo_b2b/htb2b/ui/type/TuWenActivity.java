package com.huatuo_b2b.htb2b.ui.type;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.custom.RoundProgressBar;

/**
 * 图文详情界面
 *
 * @author KingKong-HE
 * @Time 2015-1-6
 * @Email KingKong@QQ.COM
 */
public class TuWenActivity extends Activity implements OnClickListener {
    private RoundProgressBar roundProgressBar;
    private WebView webviewID;
    private String goods_id;
    private String mobile_body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tuwen_view);

        goods_id = getIntent().getStringExtra("goods_id");
        mobile_body = getIntent().getStringExtra("mobile_body");

        webviewID = (WebView) findViewById(R.id.webviewID);
        roundProgressBar = (RoundProgressBar) findViewById(R.id.roundProgressBarID);
        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);

        if (mobile_body != null && !mobile_body.equals("") && !mobile_body.equals("null")) {
            webviewID.loadDataWithBaseURL(null, mobile_body, "text/html", "utf-8", "");
        } else {
            webviewID.loadUrl(Constants.URL_GOODS_DETAILS_WEB + "&goods_id=" + goods_id);
        }

        WebSettings settings = webviewID.getSettings();
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);//根据屏幕缩放
        settings.setDefaultZoom(WebSettings.ZoomDensity.FAR);//根据屏幕缩放
        settings.setSupportZoom(false);
        settings.setBuiltInZoomControls(false);
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        webviewID.setWebChromeClient(new MyWebChromeClient());

        webviewID.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                view.loadUrl("file:///android_asset/error.html");
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:
                finish();
                break;
            case R.id.computerID:

                webviewID.loadUrl(Constants.URL_GOODS_DETAILS_WEB + "&goods_id=" + goods_id);

                break;
            case R.id.iphoneID:
                webviewID.loadDataWithBaseURL(null, mobile_body, "text/html", "utf-8", "");
                break;
            default:
                break;
        }
    }
}
