package com.huatuo_b2b.htb2b.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.Login;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.fragment.widget.BaseFragmentActivity;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by jinguo on 2016/3/18.
 */
public class UserNameRegisterFragment extends Fragment implements View.OnClickListener {

    private EditText editUserName;

    private EditText editPassword;

    private EditText editPasswordConfirm;

    private EditText editEmail;

    private MyShopApplication myApplication;
    private BaseFragmentActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.registered_view, container,
                false);
        activity = (BaseFragmentActivity) getActivity();
        myApplication = (MyShopApplication) activity.getApplicationContext();

        initViewID(view); //初始化注册控件ID

        return view;
    }


    //初始化注册控件ID
    public void initViewID(View view) {

        editUserName = (EditText) view.findViewById(R.id.editUserName);

        editPassword = (EditText) view.findViewById(R.id.editPassword);

        editPasswordConfirm = (EditText) view.findViewById(R.id.editPasswordConfirm);

        editEmail = (EditText) view.findViewById(R.id.editEmail);

        TextView buttonSend = (TextView) view.findViewById(R.id.buttonSend);

//        ImageView imageBack  = (ImageView)view. findViewById(R.id.imageBack);

        buttonSend.setOnClickListener(this);

//        imageBack.setOnClickListener(this);
    }

    /**
     * 注册用户
     */
    public void SendData(String username, String password, String password_confirm, String email) {
        String url = Constants.URL_REGISTER;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("password", password);
        params.put("password_confirm", password_confirm);
        params.put("email", email);
        params.put("client", "android");
        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject obj = new JSONObject(json);
                        String error = obj.getString("error");
                        if (error != null) {
                            Toast.makeText(activity, error, Toast.LENGTH_SHORT).show();
                            ;
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
                    activity.sendBroadcast(mIntent);
                    activity.finish();
                } else {
                    Toast.makeText(activity, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.imageBack:
//
//                activity.finish();
//
//                break;

            case R.id.buttonSend:

                String username = editUserName.getText().toString();
                String password = editPassword.getText().toString();
                String password_confirm = editPasswordConfirm.getText().toString();
                String email = editEmail.getText().toString();
                if (username.equals("") || username == null) {
                    Toast.makeText(activity, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }
                if (password.equals("") || password == null) {
                    Toast.makeText(activity, "密码不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }
                if (password_confirm.equals("") || password_confirm == null) {
                    Toast.makeText(activity, "确认密码不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }
                if (!password.equals(password_confirm)) {
                    Toast.makeText(activity, "两次密码不同", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }
                if (email.equals("") || email == null) {
                    Toast.makeText(activity, "邮箱不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }
                SendData(username, password, password_confirm, email);
                ;

                break;

            default:
                break;
        }
    }

}

