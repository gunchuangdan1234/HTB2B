package com.huatuo_b2b.htb2b.ui.store;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.StoreGoodsClassExpandableListAdapter;
import com.huatuo_b2b.htb2b.bean.StoreGoodsClassList;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StoreSearchActivity extends Activity implements OnClickListener {
    private EditText editSearchID;
    private StoreGoodsClassExpandableListAdapter adapter;
    private ExpandableListView elistViewID;
    private String store_goods_class, store_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_search_view);

        store_id = getIntent().getStringExtra("store_id");

        String store_name = getIntent().getStringExtra("store_name");

        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);
        TextView textSearchButton = (TextView) findViewById(R.id.textSearchButton);
        TextView textTitleName = (TextView) findViewById(R.id.textTitleName);
        editSearchID = (EditText) findViewById(R.id.editSearchID);
        elistViewID = (ExpandableListView) findViewById(R.id.elistViewID);

        adapter = new StoreGoodsClassExpandableListAdapter(this);

        textTitleName.setText(store_name == null ? "" : store_name);

        elistViewID.setAdapter(adapter);

        imageBack.setOnClickListener(this);
        textSearchButton.setOnClickListener(this);

        getData(store_id);

        elistViewID.setOnGroupExpandListener(new OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < StoreSearchActivity.this.adapter.getGroupCount(); i++) {
                    if (groupPosition != i) {
                        StoreSearchActivity.this.elistViewID.collapseGroup(i);
                    }
                }

                StoreGoodsClassList bean = (StoreGoodsClassList) elistViewID.getItemAtPosition(groupPosition);
                if (bean != null) {
                    ArrayList<StoreGoodsClassList> clist = StoreGoodsClassList.newInstanceCList(store_goods_class, Integer.parseInt(bean.getId()));
                    adapter.setTypeNextCList(clist);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        elistViewID.setOnChildClickListener(new OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                StoreGoodsClassList bean = (StoreGoodsClassList) adapter.getChild(groupPosition, childPosition);
                if (bean != null) {
                    Intent intent = new Intent(StoreSearchActivity.this, StoreGoodsListFragmentManager.class);
                    intent.putExtra("stc_id", bean.getId());
                    intent.putExtra("store_name", bean.getName());
                    intent.putExtra("store_id", store_id);
                    StoreSearchActivity.this.startActivity(intent);
                }

                return false;
            }
        });
    }

    /**
     * 店铺商品分类
     *
     * @param store_id 店铺ID
     */
    public void getData(String store_id) {
        String url = Constants.URL_STORE_GOODS_CLASS + "&store_id=" + store_id;
        RemoteDataHandler.asyncDataStringGet(url, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();

                    try {
                        JSONObject jsonObject = new JSONObject(json);
                        store_goods_class = jsonObject.getString("store_goods_class");
                        String store_info = jsonObject.getString("store_info");
                        ArrayList<StoreGoodsClassList> glist = StoreGoodsClassList.newInstanceGList(store_goods_class);

                        adapter.setTypeNextGList(glist);
                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {

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
            case R.id.textSearchButton:
                String keyword = editSearchID.getText().toString();

                if (keyword.trim() == null || keyword.trim().equals("")) {
                    Toast.makeText(StoreSearchActivity.this, "关键字不能为空", Toast.LENGTH_SHORT).show();
                    ;
                    return;
                }

                Intent intent = new Intent(StoreSearchActivity.this, StoreGoodsListFragmentManager.class);
                intent.putExtra("keyword", keyword);
                intent.putExtra("store_name", keyword);
                intent.putExtra("store_id", store_id);
                StoreSearchActivity.this.startActivity(intent);

                break;
            default:
                break;
        }
    }
}
