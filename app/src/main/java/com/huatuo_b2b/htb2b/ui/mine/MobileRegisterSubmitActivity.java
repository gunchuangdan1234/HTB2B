package com.huatuo_b2b.htb2b.ui.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.Login;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by jinguo on 2016/3/18.
 */
public class MobileRegisterSubmitActivity extends Activity implements View.OnClickListener {
    private MyShopApplication myApplication;


    private EditText editUserName;
    private EditText editPassword;
    private EditText editEmail;

    private String phone;
    private String captcha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_registered_submit);
        myApplication = (MyShopApplication) getApplicationContext();

        Intent intent = getIntent();

        phone = intent.getStringExtra("member_mobile");
        captcha = intent.getStringExtra("sms_captcha");
        initViewID();
    }


    private void initViewID() {

        findViewById(R.id.imageBack).setOnClickListener(this);
        findViewById(R.id.buttonSend).setOnClickListener(this);

        editUserName = (EditText) findViewById(R.id.editUserName);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editEmail = (EditText) findViewById(R.id.editEmail);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imageBack:
                finish();
                break;

            case R.id.buttonSend:

                String username = editUserName.getText().toString();
                String password = editPassword.getText().toString();
                String email = editEmail.getText().toString();

                SendData(username, password, email, phone, captcha, "1");

                break;
        }
    }


    /**
     * 注册用户
     */
    public void SendData(String username, String password, String email, String phone, String captcha, String type) {
        String url = Constants.URL_REGISTER_BY_CAPTCHA;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("member_name", username);
        params.put("member_passwd", password);
        params.put("member_email", email);
        params.put("member_mobile", phone);
        params.put("sms_captcha", captcha);
        params.put("type", type);
        params.put("client", "android");
        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject obj = new JSONObject(json);
                        String msg = obj.getString("msg");

                        boolean isOK = obj.getBoolean("isOK");
                        if (!isOK) {

                            if (!TextUtils.isEmpty(msg))
                                Toast.makeText(MobileRegisterSubmitActivity.this, msg, Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(MobileRegisterSubmitActivity.this, "注册失败", Toast.LENGTH_SHORT).show();

                            return;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Login login = Login.newInstanceList(json);
                    myApplication.setUserName(login.getUsername());
                    myApplication.setLoginKey(login.getKey());
                    myApplication.setMemberID(login.getUserid());

                    myApplication.loadingUserInfo(login.getKey(), login.getUserid());

                    myApplication.getmSocket().connect();
                    myApplication.UpDateUser();

                    Intent mIntent = new Intent(Constants.LOGIN_SUCCESS_URL);
                    MobileRegisterSubmitActivity.this.sendBroadcast(mIntent);
                    MobileRegisterSubmitActivity.this.finish();
                } else {
                    Toast.makeText(MobileRegisterSubmitActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });
    }
}
