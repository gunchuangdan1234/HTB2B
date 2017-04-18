package com.huatuo_b2b.htb2b.fragment.widget;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;


public class BaseFragmentActivity extends FragmentActivity {

    private static final String TAG = "BaseActivity";
//	private BaseReceiver baseReceiver;

    public String loadStr = "正在加载数据...";
    private Activity activity;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    // private CustomProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        activity = this;
//		registBaseReceiver();

    }

//	private void registBaseReceiver() {
//		if (baseReceiver == null) {
//			baseReceiver = new BaseReceiver();
//			IntentFilter filter = new IntentFilter();
//			filter.addAction(this.getClass().getName());
//			registerReceiver(baseReceiver, filter);
//		}
//	}
//
//	private void unregistBaseReceiver() {
//		if (baseReceiver != null) {
//			unregisterReceiver(baseReceiver);
//		}
//	}
//
//	class BaseReceiver extends BroadcastReceiver {
//		@Override
//		public void onReceive(Context context, Intent intent) {
//			String action = intent.getAction();
//			if (BaseFragmentActivity.this.getClass().getName().equals(action)) {
//
//
//			}
//		}
//	}

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//		unregistBaseReceiver();
    }

    // /**
    // * showDialog
    // *
    // * @param resID
    // * 展示内容
    // */
    // protected void showProgress(String resID) {
    // if (progressDialog != null) {
    // progressDialog.cancel();
    // }
    // progressDialog = new CustomProgressDialog(this, resID);
    // progressDialog.setCancelable(false);
    // progressDialog.show();
    // }
    //
    // protected void dismissProgressDialog() {
    // if (progressDialog != null && progressDialog.isShowing()) {
    // progressDialog.dismiss();
    // progressDialog = null;
    // }
    // }


}


