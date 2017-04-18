package com.huatuo_b2b.htb2b.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.Login;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.fragment.widget.BaseFragmentActivity;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.ui.mine.FindPasActivity;
import com.huatuo_b2b.htb2b.ui.mine.RegisteredActivity;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by jinguo on 2016/3/17.
 */
public class UserNameLoginFragment extends Fragment implements View.OnClickListener {

    private EditText loginNameID, loginPassWordID;

    private MyShopApplication myApplication;
    private BaseFragmentActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user_name_login, container,
                false);
        activity = (BaseFragmentActivity) getActivity();
        myApplication = (MyShopApplication) activity.getApplicationContext();

        initViewID(view); //初始化注册控件ID

        return view;
    }


    /**
     * 初始化注册控件ID
     */
    public void initViewID(View view) {

//        ImageView imageBack = (ImageView) view.findViewById(R.id.imageBack);
        LinearLayout submitID = (LinearLayout) view.findViewById(R.id.submitID);
        TextView registeredID = (TextView) view.findViewById(R.id.registeredID);
        TextView tv_rem_pw = (TextView) view.findViewById(R.id.tv_rem_pw);

        loginNameID = (EditText) view.findViewById(R.id.loginNameID);
        loginPassWordID = (EditText) view.findViewById(R.id.loginPassWordID);

//        imageBack.setOnClickListener(this);
        submitID.setOnClickListener(this);
        registeredID.setOnClickListener(this);
        tv_rem_pw.setOnClickListener(this);
    }

    /**
     * 用户登录
     *
     * @param loginName     用户名
     * @param loginPassword 密码
     */
    public void infoLoginCheck(String loginName, String loginPassword) {
        String url = Constants.URL_LOGIN;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("username", loginName);
        params.put("password", loginPassword);
        params.put("client", "android");
        RemoteDataHandler.asyncPostDataString(url, params, new RemoteDataHandler.Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();

                    System.out.println("infoLoginCheck  ############# json： " + json);
                    //json： {"member_role":"1","userid":"51","key":"8b3ae2a52ec86bfb25a789dd206c0960","username":"liubingwei"}


                    try {
                        JSONObject objDate = new JSONObject(json);
//                        String error = objDate.getString("error");

                        boolean isOK = objDate.getBoolean("isOK");

                        if (!isOK) {
                            Toast.makeText(activity, "登录失败", Toast.LENGTH_SHORT).show();

                            return;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Login login = Login.newInstanceList(json);
                    //System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@login"+  login);

                    myApplication.setLoginKey(login.getKey());
                    myApplication.setUserName(login.getUsername());
                    myApplication.setMemberID(login.getUserid());
                    myApplication.setMember_role(login.getMember_role());

                    myApplication.loadingUserInfo(login.getKey(), login.getUserid());
                    myApplication.getmSocket().connect();
                    myApplication.UpDateUser();


                    Intent mIntent = new Intent(Constants.LOGIN_SUCCESS_URL);
                    activity.sendBroadcast(mIntent);
                    activity.finish();
                } else {
//					Toast.makeText(activity, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.imageBack:
//                activity.finish();
//
//                break;
            case R.id.registeredID://注册新帐号
                startActivity(new Intent(activity, RegisteredActivity.class));
                activity.finish();

                break;

            case R.id.tv_rem_pw://忘记密码按钮

//                ShowDialog("请拨打客服电话（4006964266）重置密码!");
                startActivity(new Intent(activity, FindPasActivity.class));
                activity.finish();
                break;

            case R.id.submitID:

                String loginName = loginNameID.getText().toString();
                String loginPassword = loginPassWordID.getText().toString();

                if (loginName == null || loginName.trim().equals("")) {
                    Toast.makeText(activity, "用户名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (loginPassword == null || loginPassword.trim().equals("")) {
                    Toast.makeText(activity, "密码不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }

                infoLoginCheck(loginName, loginPassword);//用户登录

                break;

            default:
                break;
        }
    }

    //弹出提示框
    private void ShowDialog(String string) {
        new AlertDialog.Builder(activity)
                .setTitle("提示信息")//弹出窗口的最上头文字
                        //.setIcon(R.mipmap.ic_dialog_icon)// 设置弹出窗口的图式
                .setMessage(string)//设置弹出窗口的信息

                .setPositiveButton("确定",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {

                            }
                        })/*

						//设置弹出窗口的返回事件
				.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface,
									int i) {
								finish();
							}
						})*/.show();
    }

}
