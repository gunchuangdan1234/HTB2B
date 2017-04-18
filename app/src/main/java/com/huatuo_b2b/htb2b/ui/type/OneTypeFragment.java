package com.huatuo_b2b.htb2b.ui.type;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.OneTypeListViewAdapter;
import com.huatuo_b2b.htb2b.bean.OneType;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.custom.MyListView;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.pulltorefresh.library.PullToRefreshScrollView;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * 一级分类界面
 *
 * @author KingKong-HE
 * @Time 2015-1-4
 * @Email KingKong@QQ.COM
 */
public class OneTypeFragment extends Fragment {

    private PullToRefreshScrollView mPullRefreshScrollView;

    private OneTypeListViewAdapter oneTypeListViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewLayout = inflater.inflate(R.layout.main_one_type_view, container, false);

        initViewID(viewLayout);//注册控件ID

        return viewLayout;
    }

    /**
     * 初始化注册控件ID
     */
    public void initViewID(View view) {

        mPullRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.pull_refresh_scrollview);

        final MyListView listViewID = (MyListView) view.findViewById(R.id.typeListViewID);

        oneTypeListViewAdapter = new OneTypeListViewAdapter(getActivity());

        listViewID.setAdapter(oneTypeListViewAdapter);
        setListViewHeightBasedOnChildren(listViewID);

        oneTypeListViewAdapter.notifyDataSetChanged();

        loadingTypeData();//初始化加载数据

//		//下拉刷新监听
//		mPullRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {
//			@Override
//			public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
//				loadingTypeData();//初始化加载数据
//			}
//		});

        //listview 点击事件,点击一级分类跳转二级分类
        listViewID.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                OneType bean = (OneType) listViewID.getItemAtPosition(arg2);

                if (bean != null) {

                    ifNOData(bean.getGc_id(), bean.getGc_name());//断是否有下级
                }
            }
        });
    }

    //动态设置ListView的高度
    private void setListViewHeightBasedOnChildren(MyListView m_listView) {
        if (m_listView == null) return;
        ListAdapter listAdapter = m_listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, m_listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = m_listView.getLayoutParams();
        params.height = 1000;
    }

    /**
     * 初始化加载数据
     */
    public void loadingTypeData() {

        String url = Constants.URL_GOODSCLASS;

        RemoteDataHandler.asyncDataStringGet(url, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {

                mPullRefreshScrollView.onRefreshComplete();//加载完成下拉控件取消显示

                if (data.getCode() == HttpStatus.SC_OK) {

                    String json = data.getJson();

                    try {

                        JSONObject obj = new JSONObject(json);

                        String array = obj.getString("class_list");

                        ArrayList<OneType> typeList = OneType.newInstanceList(array);

                        oneTypeListViewAdapter.setTypeList(typeList);
                        oneTypeListViewAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(TypeAcitivity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }

    /**
     * 判断是否有下级
     */
    public void ifNOData(final String gc_id, final String gc_name) {

        String url = Constants.URL_GOODSCLASS + "&gc_id=" + gc_id;

        RemoteDataHandler.asyncDataStringGet(url, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject obj = new JSONObject(json);
                        String array = obj.getString("class_list");
                        if (array.toString().equals("0") || array.toString().equals("null")) {

                            Intent intent = new Intent(getActivity(), GoodsListFragmentManager.class);
                            intent.putExtra("gc_id", gc_id);
                            intent.putExtra("gc_name", gc_name);
                            startActivity(intent);

                        } else {

                            Intent intent = new Intent(getActivity(), TwoTypeActivity.class);
                            intent.putExtra("gc_id", gc_id);
                            intent.putExtra("gc_name", gc_name);
                            startActivity(intent);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
//					Toast.makeText(TypeNextActivity.this, getString(R.string.datas_loading_fail_prompt), Toast.LENGTH_SHORT).show();;
                }
            }
        });
    }
}