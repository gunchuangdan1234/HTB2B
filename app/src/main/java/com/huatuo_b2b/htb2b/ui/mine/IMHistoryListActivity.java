package com.huatuo_b2b.htb2b.ui.mine;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.ImHistoryListViewAdapter;
import com.huatuo_b2b.htb2b.bean.IMHistoryList;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.common.SmiliesData;
import com.huatuo_b2b.htb2b.custom.XListView;
import com.huatuo_b2b.htb2b.custom.XListView.IXListViewListener;
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
 * 聊天记录列表界面
 *
 * @author KingKong-HE
 * @Time 2015-2-6
 * @Email KingKong@QQ.COM
 */
public class IMHistoryListActivity extends Activity implements OnClickListener, IXListViewListener {

    private MyShopApplication myApplication;

    private Handler mXLHandler;

    public int pageno = 1;

    private ArrayList<IMHistoryList> historyLists;

    private ImHistoryListViewAdapter adapter;

    private XListView listViewID;

    private String t_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_history_view);

        t_id = getIntent().getStringExtra("t_id");

        myApplication = (MyShopApplication) getApplicationContext();

        mXLHandler = new Handler();

        initViewID();
    }

    /**
     * 初始化注册控件ID
     */
    public void initViewID() {

        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);

        listViewID = (XListView) findViewById(R.id.listViewID);

        historyLists = new ArrayList<IMHistoryList>();

        adapter = new ImHistoryListViewAdapter(IMHistoryListActivity.this);

        adapter.setSmiliesLists(SmiliesData.smiliesLists);

        listViewID.setAdapter(adapter);

        imageBack.setOnClickListener(this);

        listViewID.setXListViewListener(this);

        loadingListData();//加载列表数据
    }


    /**
     * 加载列表数据
     */
    public void loadingListData() {
        String url = Constants.URL_MEMBER_CHAT_GET_LOG_INFO + "&curpage=" + pageno + "&page=" + Constants.PAGESIZE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("t_id", t_id);
        params.put("t", "30");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String updataTime = sdf.format(new Date(System.currentTimeMillis()));
        listViewID.setRefreshTime(updataTime);

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {

                listViewID.stopLoadMore();
                listViewID.stopRefresh();

                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();

                    if (!data.isHasMore()) {
                        listViewID.setPullLoadEnable(false);
                    } else {
                        listViewID.setPullLoadEnable(true);
                    }
                    if (pageno == 1) {
                        historyLists.clear();
                    }

                    try {

                        JSONObject obj = new JSONObject(json);

                        String objJson = obj.getString("list");

                        ArrayList<IMHistoryList> fList = IMHistoryList.newInstanceList(objJson);

                        historyLists.addAll(fList);
                        adapter.setHistoryLists(historyLists);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject obj2 = new JSONObject(json);
                        String error = obj2.getString("error");
                        if (error != null) {
                            Toast.makeText(IMHistoryListActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(FavoritesListActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();;
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
                pageno = 1;
                listViewID.setPullLoadEnable(true);
                loadingListData();
            }
        }, 1000);
    }

    @Override
    public void onLoadMore() {
        //上拉加载
        mXLHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageno = pageno + 1;
                loadingListData();
            }
        }, 1000);
    }

}
