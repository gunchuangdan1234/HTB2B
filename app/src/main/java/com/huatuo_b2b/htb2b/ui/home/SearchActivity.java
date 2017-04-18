package com.huatuo_b2b.htb2b.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.SearchListViewAdapter;
import com.huatuo_b2b.htb2b.bean.Search;
import com.huatuo_b2b.htb2b.common.DatabaseHelper;
import com.huatuo_b2b.htb2b.ui.type.GoodsListFragmentManager;
import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.List;

/**
 * 搜索商品界面
 *
 * @author KingKong·HE
 * @Time 2014年1月27日 下午4:00:02
 * @E-mail hjgang@bizpoer.com
 */
public class SearchActivity extends OrmLiteBaseActivity<DatabaseHelper> {

    private EditText editSearch;

    private TextView textSearchButton;

    private ImageView imageBack;

    private ListView searchListView;

    private SearchListViewAdapter adapter;

    private RuntimeExceptionDao<Search, Integer> searchDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_view);

        searchDAO = getHelper().getSearchDataDao();

        editSearch = (EditText) findViewById(R.id.editSearch);

        textSearchButton = (TextView) findViewById(R.id.textSearchButton);

        imageBack = (ImageView) findViewById(R.id.imageBack);

        searchListView = (ListView) findViewById(R.id.searchListView);

        adapter = new SearchListViewAdapter(SearchActivity.this);

        searchListView.setAdapter(adapter);

        List<Search> sList = queryAll();

        adapter.setSearchLists(sList);

        adapter.notifyDataSetChanged();

        textSearchButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = editSearch.getText().toString();
                if (keyword.equals("") || keyword.equals("") || keyword == null) {
                    Toast.makeText(SearchActivity.this, "内容不能为空",
                            Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(SearchActivity.this, GoodsListFragmentManager.class);
                    intent.putExtra("keyword", keyword);
                    intent.putExtra("gc_name", keyword);
                    SearchActivity.this.startActivity(intent);
                    SearchActivity.this.finish();
                    searchDAO.createIfNotExists(new Search(keyword));
                }
            }
        });

        imageBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivity.this.finish();
            }
        });

        searchListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                Search bean = (Search) searchListView.getItemAtPosition(arg2);
                Intent intent = new Intent(SearchActivity.this,
                        GoodsListFragmentManager.class);
                intent.putExtra("keyword", bean.getSearchKeyWord());
                intent.putExtra("gc_name", bean.getSearchKeyWord());
                SearchActivity.this.startActivity(intent);
                SearchActivity.this.finish();
            }
        });
    }

    /**
     * 查询所有的
     */
    private List<Search> queryAll() {
        List<Search> searchList = searchDAO.queryForAll();
        return searchList;
    }
}
