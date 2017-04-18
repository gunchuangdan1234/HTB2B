package com.huatuo_b2b.htb2b.ui.mine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;

import java.io.File;

/**
 * 设置页面
 *
 * @author KingKong-HE
 * @Time 2015-2-1
 * @Email KingKong@QQ.COM
 */
public class SettingActivity extends Activity implements OnClickListener {

    private MyShopApplication myApplication;

    private Button buttonOutID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_view);

        myApplication = (MyShopApplication) getApplicationContext();

        intiViewID();
    }

    /**
     * 初始化注册控件ID
     */
    public void intiViewID() {
        buttonOutID = (Button) findViewById(R.id.buttonOutID);
        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);
        TextView cleanID = (TextView) findViewById(R.id.cleanID);
        TextView helpID = (TextView) findViewById(R.id.helpID);
        TextView feekID = (TextView) findViewById(R.id.feekID);
        TextView aboutID = (TextView) findViewById(R.id.aboutID);

        String loginKey = myApplication.getLoginKey();
        if (loginKey != null && !loginKey.equals("")) {
            buttonOutID.setVisibility(View.VISIBLE);
        } else {
            buttonOutID.setVisibility(View.GONE);
        }

        imageBack.setOnClickListener(this);
        buttonOutID.setOnClickListener(this);
        cleanID.setOnClickListener(this);
        helpID.setOnClickListener(this);
        feekID.setOnClickListener(this);
        aboutID.setOnClickListener(this);
    }

    private class MyFileAsyncTask extends AsyncTask<String, Void, String> {
        ProgressDialog dialog = new ProgressDialog(SettingActivity.this);

        @Override
        protected String doInBackground(String... params) {
            delAllFile(Constants.CACHE_DIR_IMAGE);
//			Toast.makeText(MyStoreActivity.this, "删除缓存图片成功", Toast.LENGTH_SHORT).show();;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "1";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 设置进度条风格，风格为圆形，旋转的
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            // 设置ProgressDialog 标题
            dialog.setTitle("提示");
            // 设置ProgressDialog提示信息
            dialog.setMessage("正在删除...");
            dialog.show();
        }
    }

    /**
     * 删除文件夹里面的所有文件
     *
     * @param path String 文件夹路径 如 c:/fqf
     */
    public void delAllFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        if (!file.isDirectory()) {
            return;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                temp.delete();
            }
            if (temp.isDirectory()) {
                delAllFile(path + "/" + tempList[i]);//先删除文件夹里面的文件
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonOutID:

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setTitle("功能选择")
                        .setMessage("您确定注销当前帐号吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        myApplication.setLoginKey("");
                        myApplication.setMemberID("");
                        myApplication.setMemberAvatar("");
                        myApplication.setUserName("");
                        myApplication.getmSocket().disconnect();
                        myApplication.getmSocket().io().reconnection(false);
                        buttonOutID.setVisibility(View.GONE);

                        myApplication.setMember_role("0");

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create().show();

                break;

            case R.id.imageBack:
                finish();
                break;

            case R.id.cleanID:
                MyFileAsyncTask fileAsyncTask = new MyFileAsyncTask();
                fileAsyncTask.execute();
                break;

            case R.id.helpID:
                startActivity(new Intent(SettingActivity.this, HelpActivity.class));
                break;

            case R.id.feekID:
                startActivity(new Intent(SettingActivity.this, FeekBaskActivity.class));
                break;

            case R.id.aboutID:
                startActivity(new Intent(SettingActivity.this, AboutActivity.class));
                break;

            default:
                break;
        }
    }

}
