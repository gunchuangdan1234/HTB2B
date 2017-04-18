package com.huatuo_b2b.htb2b.ui.type;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View.OnClickListener;
import android.view.View;
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
public class GoodsListFragmentManager extends FragmentActivity implements OnClickListener {

    /**
     * 定义新品、价格、销量、人气Fragment
     */
    private GoodsListFragment goodsListFragment;

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

    private String gc_id;

    private String gc_name;

    private String barcode;//商品条形码

    private String keyword;//搜索关键字

    private String order;// 排序方式 1-升序 2-降序

    private boolean orderFlag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_fragmentmanager_view);

        fragmentManager = getSupportFragmentManager();

        gc_id = GoodsListFragmentManager.this.getIntent().getStringExtra("gc_id");//分类ID

        gc_name = GoodsListFragmentManager.this.getIntent().getStringExtra("gc_name");//分类Name

        barcode = GoodsListFragmentManager.this.getIntent().getStringExtra("barcode");//条码扫描

        keyword = GoodsListFragmentManager.this.getIntent().getStringExtra("keyword");//关键字搜索

        initViews();//初始化界面，并设置tab的监听
    }

    /**
     * 隐藏所有的fragment
     *
     * @param transaction 用于对fragment进行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (goodsListFragment != null) {
            transaction.hide(goodsListFragment);
        }
    }

    /**
     * 设置开启的tab页面
     */
    public void In(String key) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideFragments(transaction);

        if (goodsListFragment == null) {
            goodsListFragment = new GoodsListFragment();
            goodsListFragment.key = key;
            goodsListFragment.gc_id = gc_id;
            goodsListFragment.gc_name = gc_name;
            goodsListFragment.keyword = keyword;
            goodsListFragment.pageno = 1;
            if (key.equals("3")) {
                goodsListFragment.order = order;
            } else {
                goodsListFragment.order = null;
            }
            goodsListFragment.barcode = barcode;
            transaction.add(R.id.content, goodsListFragment);
        } else {
            transaction.show(goodsListFragment);
            goodsListFragment.key = key;
            goodsListFragment.gc_id = gc_id;
            goodsListFragment.gc_name = gc_name;
            goodsListFragment.keyword = keyword;
            goodsListFragment.pageno = 1;
            if (key.equals("3")) {
                goodsListFragment.order = order;
            } else {
                goodsListFragment.order = null;
            }
            goodsListFragment.barcode = barcode;
            goodsListFragment.loadingGoodsListData();
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

        textGoodsTabTitleName.setText(gc_name == null ? "" : gc_name);

        imageBack.setOnClickListener(this);

        MyRadioButtonClickListener listener = new MyRadioButtonClickListener();
        btnNewID.setOnClickListener(listener);
        btnPriceID.setOnClickListener(listener);
        btnNumID.setOnClickListener(listener);
        btnManID.setOnClickListener(listener);

        In("");// 首次进入显示界面 key  1-销量 2-浏览量 3-价格 空-按最新发布排序
    }

    class MyRadioButtonClickListener implements OnClickListener {
        public void onClick(View v) {
            RadioButton btn = (RadioButton) v;
            switch (btn.getId()) {
                case R.id.btnNewID:

                    In("");// 首次进入显示界面 key  1-销量 2-浏览量 3-价格 空-按最新发布排序

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

                    In("3");// 首次进入显示界面 key  1-销量 2-浏览量 3-价格 空-按最新发布排序

                    break;
                case R.id.btnNumID:

                    In("1");// 首次进入显示界面 key  1-销量 2-浏览量 3-价格 空-按最新发布排序

                    btnPriceID.setCompoundDrawables(null, null, null, null);
                    orderFlag = true;

                    break;
                case R.id.btnManID:

                    In("2");// 首次进入显示界面 key  1-销量 2-浏览量 3-价格 空-按最新发布排序

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
