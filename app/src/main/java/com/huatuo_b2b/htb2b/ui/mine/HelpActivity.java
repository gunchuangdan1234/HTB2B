package com.huatuo_b2b.htb2b.ui.mine;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.common.Constants;

/**
 * 在线帮助界面
 *
 * @author KingKong·HE
 * @Time 2014年2月14日 下午4:10:35
 * @E-mail hjgang@bizpoer.com
 */
public class HelpActivity extends Activity {
    private WebView webviewHelp;
    private ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_help);

        webviewHelp = (WebView) findViewById(R.id.webviewHelp);
        imageBack = (ImageView) findViewById(R.id.imageBack);
        webviewHelp.getSettings().setSupportZoom(true);
        webviewHelp.getSettings().setBuiltInZoomControls(true);
        WebSettings settings = webviewHelp.getSettings();
        settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        webviewHelp.loadUrl(Constants.URL_HELP);
        webviewHelp.setWebViewClient(new WebViewClient() {
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
        imageBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                HelpActivity.this.finish();
                ;
            }
        });
    }
}
