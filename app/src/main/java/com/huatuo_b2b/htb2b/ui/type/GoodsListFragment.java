package com.huatuo_b2b.htb2b.ui.type;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.GoodsListViewAdapter;
import com.huatuo_b2b.htb2b.bean.GoodsList;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.custom.XListView;
import com.huatuo_b2b.htb2b.custom.XListView.IXListViewListener;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * 商品列表Fragment
 *
 * @author KingKong-HE
 * @Time 2015-1-4
 * @Email KingKong@QQ.COM
 */
public class GoodsListFragment extends Fragment implements IXListViewListener {

    public String key = "";//排序方式 1-销量 2-浏览量 3-价格 空-按最新发布排序

    public String gc_id;//gc_id和keyword二选一不能同时出现

    public String gc_name;

    public String order = "1";// 排序方式 1-升序 2-降序

    public String barcode;//商品条形码

    public String keyword;//搜索关键字

    public int pageno = 1;

    private GoodsListViewAdapter goodsListViewAdapter;

    private Handler mXLHandler;

    private XListView listViewID;

    private ArrayList<GoodsList> goodsLists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.goods_fragment_view, container, false);

        initViewID(viewLayout);//注册控件ID

        return viewLayout;
    }

    /**
     * 初始化注册控件ID
     */
    public void initViewID(View view) {

        listViewID = (XListView) view.findViewById(R.id.listViewID);

        goodsListViewAdapter = new GoodsListViewAdapter(getActivity());

        goodsLists = new ArrayList<GoodsList>();

        listViewID.setAdapter(goodsListViewAdapter);

        loadingGoodsListData();

        listViewID.setXListViewListener(this);
        listViewID.setPullRefreshEnable(false);//禁止下拉刷新
        mXLHandler = new Handler();

        listViewID.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                GoodsList bean = (GoodsList) listViewID.getItemAtPosition(arg2);
                if (bean != null) {
                    Intent intent = new Intent(getActivity(), GoodsDetailsActivity.class);
                    intent.putExtra("goods_id", bean.getGoods_id());
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    public void onRefresh() {
        //下拉刷新
    }

    @Override
    public void onLoadMore() {
        //上拉加载
        mXLHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                pageno = pageno + 1;
                loadingGoodsListData();
            }
        }, 1000);
    }

    /**
     * 初始化加载列表数据
     */
    public void loadingGoodsListData() {

        String url = Constants.URL_GOODSLIST + "&curpage=" + pageno + "&page=" + Constants.PAGESIZE;

        if (keyword != null && !keyword.equals("null")) {
            String keywordstr = URLEncoder.encode(keyword);
            url = url + "&keyword=" + keywordstr + "&key=" + key;
        }

        if (barcode != null && !barcode.equals("") && !barcode.equals("null")) {
            url = url + "&key=" + key + "&barcode=" + barcode;
        }

        if (gc_id != null && !gc_id.equals("") && !gc_id.equals("null")) {
            url = url + "&gc_id=" + gc_id + "&key=" + key;
        }

        if (order != null && !order.equals("null") && !order.equals("")) {
            url = url + "&order=" + order;
        }

        RemoteDataHandler.asyncDataStringGet(url, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {

                listViewID.stopLoadMore();

                if (data.getCode() == HttpStatus.SC_OK) {

                    String json = data.getJson();

                    if (!data.isHasMore()) {
                        listViewID.setPullLoadEnable(false);
                    } else {
                        listViewID.setPullLoadEnable(true);
                    }
                    if (pageno == 1) {
                        goodsLists.clear();
                    }

                    try {

                        JSONObject obj = new JSONObject(json);
                        String array = obj.getString("goods_list");
                        if (array != "" && !array.equals("array") && array != null && !array.equals("[]")) {
                            ArrayList<GoodsList> list = GoodsList.newInstanceList(array);
                            goodsLists.addAll(list);
                            goodsListViewAdapter.setGoodsLists(goodsLists);
                            goodsListViewAdapter.notifyDataSetChanged();
                        } else {
                            if (pageno == 1) {
//								textNoNoDatas.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(GoodsListViewActivity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }
}
