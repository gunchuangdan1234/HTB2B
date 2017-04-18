package com.huatuo_b2b.htb2b.ui.mine;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.VoucherListViewAdapter;
import com.huatuo_b2b.htb2b.bean.VoucherList;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.custom.XListView;
import com.huatuo_b2b.htb2b.custom.XListView.IXListViewListener;
import com.huatuo_b2b.htb2b.fragment.widget.SegmentView;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * 我的代金券列表
 *
 * @author KingKong-HE
 * @Time 2015-1-26
 * @Email KingKong@QQ.COM
 */
public class VoucherListActivity extends Activity implements OnClickListener, IXListViewListener {


    private MyShopApplication myApplication;

    private Handler mXLHandler;

    private VoucherListViewAdapter adapter;

    private XListView listViewID;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voucher_list_view);

        myApplication = (MyShopApplication) getApplicationContext();

        initViewID();// 初始化注册控件ID

    }

    /**
     * 初始化注册控件ID
     */
    public void initViewID() {
        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);

        listViewID = (XListView) findViewById(R.id.listViewID);

        adapter = new VoucherListViewAdapter(VoucherListActivity.this);

        mXLHandler = new Handler();

        listViewID.setAdapter(adapter);

        imageBack.setOnClickListener(this);

        listViewID.setXListViewListener(this);

        listViewID.setPullLoadEnable(false);

        loadingListData("1");


        SegmentView segment_id = (SegmentView) findViewById(R.id.segment_id);

        segment_id.setOnSegmentViewClickListener(new SegmentView.onSegmentViewClickListener() {
            @Override
            public void onSegmentViewClick(View v, int position) {

                if (position == 0) {
                    VoucherListActivity.this.position = 0;
                    loadingListData("1");
                } else if (position == 1) {
                    VoucherListActivity.this.position = 1;
                    loadingListData("2");
                } else if (position == 2) {
                    VoucherListActivity.this.position = 2;
                    loadingListData("3");
                }

            }
        });
    }

    /**
     * 加载列表数据
     */
    public void loadingListData(String voucher_state) {
        String url = Constants.URL_VOUCHER;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("voucher_state", voucher_state);//(1-未使用 2-已使用 3-已过期)

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String updataTime = sdf.format(new Date(System.currentTimeMillis()));
        listViewID.setRefreshTime(updataTime);

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {

                listViewID.stopRefresh();

                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject obj = new JSONObject(json);
                        String objJson = obj.getString("voucher_list");
                        ArrayList<VoucherList> voucherList = VoucherList.newInstanceList(objJson);
                        adapter.setVoucherLists(voucherList);
                        ;
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(VoucherListActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });
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

    @Override
    public void onRefresh() {
        //下拉刷新
        mXLHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (VoucherListActivity.this.position == 0) {
                    loadingListData("1");
                } else if (VoucherListActivity.this.position == 1) {
                    loadingListData("2");
                } else if (VoucherListActivity.this.position == 2) {

                    loadingListData("3");
                }
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        //上拉加载
    }

}
