package com.huatuo_b2b.htb2b.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.HomeGoodsList;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.huatuo_b2b.htb2b.ui.home.SubjectWebActivity;
import com.huatuo_b2b.htb2b.ui.type.GoodsDetailsActivity;
import com.huatuo_b2b.htb2b.ui.type.GoodsListFragmentManager;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by admin on 15/12/30.  liubw   商品详情展示
 */
public class HomeGoodsMyGridViewListAdapter extends BaseAdapter {
    private Context context;
    private static String member_role;
    private static String getLoginKey;
    private LayoutInflater inflater;
    private ArrayList<HomeGoodsList> goodsDatas;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private int type;//1.专题里调用


    public HomeGoodsMyGridViewListAdapter(Context context, String member_role, String getLoginKey) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.member_role = member_role;
        this.getLoginKey = getLoginKey;
    }

    @Override
    public int getCount() {
        return goodsDatas == null ? 0 : goodsDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return goodsDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<HomeGoodsList> getHomeGoodsList() {
        return goodsDatas;
    }

    public void setHomeGoodsList(ArrayList<HomeGoodsList> goodsDatas) {
        this.goodsDatas = goodsDatas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.tab_home_item_goods_gridview_item, null);
            holder = new ViewHolder();
            holder.ImageViewImagePic01 = (ImageView) convertView.findViewById(R.id.ImageViewImagePic01);
            holder.TextViewTitle = (TextView) convertView.findViewById(R.id.TextViewTitle);
            holder.TextViewPrice = (TextView) convertView.findViewById(R.id.TextViewPrice);
            holder.TextViewGuige = (TextView) convertView.findViewById(R.id.TextViewGuige);
            holder.TextViewFactory = (TextView) convertView.findViewById(R.id.TextViewFactory);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HomeGoodsList bean = goodsDatas.get(position);

        holder.TextViewTitle.setText(bean.getGoods_name());
        holder.TextViewGuige.setText(bean.getGoods_guige());
        holder.TextViewFactory.setText(bean.getGoods_factory());

        if (this.getLoginKey == null || this.getLoginKey.equals("")) {//价格登录后可见
            holder.TextViewPrice.setText("价格登录后可见");
        } else {
            if (this.member_role.equals("0")) {//显示正常价格   liubw
                holder.TextViewPrice.setText("价格认证后可见");
            } else {//价格认证后可见
                holder.TextViewPrice.setText("￥" + bean.getGoods_promotion_price());
            }
        }

        if (getType() == 1) {
            holder.TextViewPrice.setBackgroundColor(context.getResources().getColor(R.color.goods_price_bg_color));
            holder.TextViewPrice.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            holder.TextViewPrice.setBackgroundColor(context.getResources().getColor(R.color.goods_price_bg_color_normal));
            holder.TextViewPrice.setTextColor(context.getResources().getColor(R.color.goods_price_text_color_normal));

        }

        imageLoader.displayImage(bean.getGoods_image(), holder.ImageViewImagePic01, options, animateFirstListener);
        OnImageViewClick(holder.ImageViewImagePic01, "goods", bean.getGoods_id());

        return convertView;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    class ViewHolder {
        ImageView ImageViewImagePic01;  //药品缩略图
        TextView TextViewTitle;         //药品名字
        TextView TextViewPrice;         //药品价钱
        TextView TextViewGuige;         //药品规格
        TextView TextViewFactory;         //药品生产厂家
    }

    public void OnImageViewClick(View view, final String type, final String data) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("keyword")) {//搜索关键字
                    Intent intent = new Intent(context, GoodsListFragmentManager.class);
                    intent.putExtra("keyword", data);
                    intent.putExtra("gc_name", data);
                    context.startActivity(intent);
                } else if (type.equals("special")) {//专题编号
                    Intent intent = new Intent(context, SubjectWebActivity.class);
                    intent.putExtra("data", Constants.URL_SPECIAL + "&special_id=" + data + "&type=html");
                    context.startActivity(intent);
                } else if (type.equals("goods")) {//商品编号
                    Intent intent = new Intent(context, GoodsDetailsActivity.class);
                    intent.putExtra("goods_id", data);
                    context.startActivity(intent);
                } else if (type.equals("url")) {//地址
                    Intent intent = new Intent(context, SubjectWebActivity.class);
                    intent.putExtra("data", data);
                    context.startActivity(intent);
                }
            }
        });
    }
}
