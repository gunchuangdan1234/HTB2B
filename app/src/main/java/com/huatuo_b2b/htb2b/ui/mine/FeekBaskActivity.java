package com.huatuo_b2b.htb2b.ui.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 意见反馈界面
 *
 * @author KingKong·HE
 * @Time 2014年2月14日 下午3:57:51
 * @E-mail hjgang@bizpoer.com
 */
public class FeekBaskActivity extends Activity {
    private ImageView imageBack;
    private TextView textFavritesEditButton;
    private EditText more_feedback_content;
    private MyShopApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more_feekbask);
        myApplication = (MyShopApplication) getApplication();
        imageBack = (ImageView) findViewById(R.id.imageBack);
        textFavritesEditButton = (TextView) findViewById(R.id.textFavritesEditButton);
        more_feedback_content = (EditText) findViewById(R.id.more_feedback_content);
        imageBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FeekBaskActivity.this.finish();
                ;
            }
        });
        textFavritesEditButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String feedback_content = more_feedback_content.getText().toString();
                if (feedback_content.equals("") || feedback_content == null) {
                    Toast.makeText(FeekBaskActivity.this, "反馈内容不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }
                sendFeekBask(feedback_content);
            }
        });
    }

    public void sendFeekBask(String feedback_content) {
        String url = Constants.URL_FEEDBACK_ADD;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("feedback", feedback_content);
        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    if (json.equals("1")) {
                        Toast.makeText(FeekBaskActivity.this, "反馈成功", Toast.LENGTH_SHORT).show();
                        ;
                        FeekBaskActivity.this.finish();
                    }
                    try {
                        JSONObject obj = new JSONObject(json);
                        String error = obj.getString("error");
                        if (error != null) {
                            Toast.makeText(FeekBaskActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(SettingActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }
}
