package com.huatuo_b2b.htb2b.ui.mine;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.FavoritesStoreListViewAdapter;
import com.huatuo_b2b.htb2b.bean.FavStoreList;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
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
 * 收藏店铺列表
 *
 * @author KingKong-HE
 * @Time 2015-2-3
 * @Email KingKong@QQ.COM
 */
public class FavStoreListActivity extends Activity implements OnClickListener, IXListViewListener {

    private MyShopApplication myApplication;

    private Handler mXLHandler;

    public int pageno = 1;

    private ArrayList<FavStoreList> favoritesLists;

    private FavoritesStoreListViewAdapter adapter;

    private XListView listViewID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favstore_list_view);

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

        favoritesLists = new ArrayList<FavStoreList>();

        adapter = new FavoritesStoreListViewAdapter(FavStoreListActivity.this);

        listViewID.setAdapter(adapter);

        imageBack.setOnClickListener(this);

        listViewID.setXListViewListener(this);

        loadingfavoritesListData();//加载列表数据

        listViewID.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {

                final FavStoreList bean = (FavStoreList) listViewID.getItemAtPosition(arg2);

                if (bean != null) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(FavStoreListActivity.this);
                    builder.setTitle("功能选择")
                            .setMessage("您确定操作的功能？");
                    builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            deleteFav(bean.getStore_id());
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();

                }

                return false;
            }
        });
    }


    /**
     * 加载列表数据
     */
    public void loadingfavoritesListData() {
        String url = Constants.URL_STORE_FAV_LIST + "&curpage=" + pageno + "&page=" + Constants.PAGESIZE;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());

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
                        favoritesLists.clear();
                    }

                    try {

                        JSONObject obj = new JSONObject(json);

                        String objJson = obj.getString("favorites_list");

                        ArrayList<FavStoreList> fList = FavStoreList.newInstanceList(objJson);

                        favoritesLists.addAll(fList);
                        adapter.setfList(favoritesLists);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        JSONObject obj2 = new JSONObject(json);
                        String error = obj2.getString("error");
                        if (error != null) {
                            Toast.makeText(FavStoreListActivity.this, error, Toast.LENGTH_SHORT).show();
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

    /**
     * 店铺收藏
     *
     * @param store_id 店铺ID
     * @param :key     登录标识
     */
    public void deleteFav(String store_id) {
        String url = Constants.URL_STORE_DELETE;

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("store_id", store_id);
        params.put("key", myApplication.getLoginKey());

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    if (json.equals("1")) {
                        Toast.makeText(FavStoreListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        ;
                        loadingfavoritesListData();//初始化加载数据
                    }
                    try {
                        JSONObject obj = new JSONObject(json);
                        String error = obj.getString("error");
                        if (error != null) {
                            Toast.makeText(FavStoreListActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(GoodsDetailsActivity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
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
                loadingfavoritesListData();
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
                loadingfavoritesListData();
            }
        }, 1000);
    }

}
