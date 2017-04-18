package com.huatuo_b2b.htb2b.ui.store;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;

/**
 * 商品列表菜单管理界面
 *
 * @author KingKong-HE
 * @Time 2015-1-4
 * @Email KingKong@QQ.COM
 */
public class StoreGoodsListFragmentManager extends FragmentActivity implements OnClickListener {

    /**
     * 定义新品、价格、销量、人气Fragment
     */
    private StoreGoodsListFragment storeGoodsListFragment;

    /**
     * 定义新品、价格、销量、人气tab的图标
     */
    private RadioButton btnNewID;
    private RadioButton btnPriceID;
    private RadioButton btnNumID;
    private RadioButton btnManID;

    /**
     * 对Fragment进行管理
     */
    private FragmentManager fragmentManager;

    private String store_name;

    private String keyword;//搜索关键字

    private String store_id;//店铺ID

    private String stc_id;//店铺分类ID

    private String order;// 排序方式 1-升序 2-降序

    private boolean orderFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_fragmentmanager_view);

        fragmentManager = getSupportFragmentManager();

        store_name = StoreGoodsListFragmentManager.this.getIntent().getStringExtra("store_name");//店铺Name

        keyword = StoreGoodsListFragmentManager.this.getIntent().getStringExtra("keyword");//关键字搜索

        store_id = StoreGoodsListFragmentManager.this.getIntent().getStringExtra("store_id");//店铺ID

        stc_id = StoreGoodsListFragmentManager.this.getIntent().getStringExtra("stc_id");//店铺分类ID

        initViews();//初始化界面，并设置tab的监听
    }

    /**
     * 隐藏所有的fragment
     *
     * @param transaction 用于对fragment进行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (storeGoodsListFragment != null) {
            transaction.hide(storeGoodsListFragment);
        }
    }

    /**
     * 设置开启的tab页面
     */
    public void In(String key) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);

        if (storeGoodsListFragment == null) {
            storeGoodsListFragment = new StoreGoodsListFragment();
            storeGoodsListFragment.key = key;
            storeGoodsListFragment.store_name = store_name;
            storeGoodsListFragment.keyword = keyword;
            storeGoodsListFragment.store_id = store_id;
            storeGoodsListFragment.stc_id = stc_id;
            storeGoodsListFragment.pageno = 1;
            if (key.equals("2")) {
                storeGoodsListFragment.order = order;
            } else {
                storeGoodsListFragment.order = null;
            }
            transaction.add(R.id.content, storeGoodsListFragment);
        } else {
            transaction.show(storeGoodsListFragment);
            storeGoodsListFragment.key = key;
            storeGoodsListFragment.store_name = store_name;
            storeGoodsListFragment.keyword = keyword;
            storeGoodsListFragment.store_id = store_id;
            storeGoodsListFragment.stc_id = stc_id;
            storeGoodsListFragment.pageno = 1;
            if (key.equals("2")) {
                storeGoodsListFragment.order = order;
            } else {
                storeGoodsListFragment.order = null;
            }
            storeGoodsListFragment.loadingGoodsListData();
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 初始化界面，并设置tab的监听
     */
    private void initViews() {
        // //////////////////// find View ////////////////////////////
        btnNewID = (RadioButton) this.findViewById(R.id.btnNewID);
        btnPriceID = (RadioButton) this.findViewById(R.id.btnPriceID);
        btnNumID = (RadioButton) this.findViewById(R.id.btnNumID);
        btnManID = (RadioButton) this.findViewById(R.id.btnManID);
        TextView textGoodsTabTitleName = (TextView) findViewById(R.id.textGoodsTabTitleName);
        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);

        textGoodsTabTitleName.setText(store_name == null ? "" : store_name);

        imageBack.setOnClickListener(this);

        MyRadioButtonClickListener listener = new MyRadioButtonClickListener();
        btnNewID.setOnClickListener(listener);
        btnPriceID.setOnClickListener(listener);
        btnNumID.setOnClickListener(listener);
        btnManID.setOnClickListener(listener);

        In("1");// 首次进入显示界面 key   1发布时间 2价格 3销量 4收藏数
    }

    class MyRadioButtonClickListener implements OnClickListener {
        public void onClick(View v) {
            RadioButton btn = (RadioButton) v;
            switch (btn.getId()) {
                case R.id.btnNewID:

                    In("1");// 首次进入显示界面 key   1发布时间 2价格 3销量 4收藏数

                    btnPriceID.setCompoundDrawables(null, null, null, null);
                    orderFlag = true;

                    break;
                case R.id.btnPriceID:

                    Drawable uparrow = getResources().getDrawable(R.mipmap.uparrow);
                    Drawable downarrow = getResources().getDrawable(R.mipmap.downarrow);

                    uparrow.setBounds(0, 0, uparrow.getMinimumWidth(), uparrow.getMinimumHeight());
                    downarrow.setBounds(0, 0, downarrow.getMinimumWidth(), downarrow.getMinimumHeight());

                    if (orderFlag) {
                        orderFlag = false;
                        order = "1";
                        btnPriceID.setCompoundDrawables(null, null, uparrow, null);
                    } else {
                        orderFlag = true;
                        order = "2";
                        btnPriceID.setCompoundDrawables(null, null, downarrow, null);
                    }

                    In("2");// 首次进入显示界面 key   1发布时间 2价格 3销量 4收藏数

                    break;
                case R.id.btnNumID:

                    In("3");// 首次进入显示界面 key   1发布时间 2价格 3销量 4收藏数

                    btnPriceID.setCompoundDrawables(null, null, null, null);
                    orderFlag = true;

                    break;
                case R.id.btnManID:

                    In("4");// 首次进入显示界面 key   1发布时间 2价格 3销量 4收藏数

                    btnPriceID.setCompoundDrawables(null, null, null, null);
                    orderFlag = true;

                    break;
            }
        }
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
}
