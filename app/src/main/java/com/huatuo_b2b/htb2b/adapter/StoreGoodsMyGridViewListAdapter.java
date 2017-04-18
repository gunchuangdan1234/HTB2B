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
import com.huatuo_b2b.htb2b.bean.GoodsList;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.huatuo_b2b.htb2b.ui.type.GoodsDetailsActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * 店铺GoodsGridView适配器
 *
 * @author KingKong-HE
 * @Time 2015年1月4日
 * @Email KingKong@QQ.COM
 */
public class StoreGoodsMyGridViewListAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<GoodsList> goodsDatas;
    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private String getLoginKey;
    private String member_role;

    public StoreGoodsMyGridViewListAdapter(Context context, final String getLoginKey, final String member_role) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.getLoginKey = getLoginKey;
        this.member_role = member_role;
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

    public ArrayList<GoodsList> getGoodsList() {
        return goodsDatas;
    }

    public void setGoodsList(ArrayList<GoodsList> goodsDatas) {
        this.goodsDatas = goodsDatas;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(
                    R.layout.tab_home_item_goods_gridview_item, null);
            holder = new ViewHolder();
            holder.ImageViewImagePic01 = (ImageView) convertView.findViewById(R.id.ImageViewImagePic01);
            holder.TextViewTitle = (TextView) convertView.findViewById(R.id.TextViewTitle);
            holder.TextViewPrice = (TextView) convertView.findViewById(R.id.TextViewPrice);

            holder.TextViewFactory = (TextView) convertView.findViewById(R.id.TextViewFactory);
            holder.TextViewGuige = (TextView) convertView.findViewById(R.id.TextViewGuige);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        GoodsList bean = goodsDatas.get(position);

        holder.TextViewTitle.setText(bean.getGoods_name());

        holder.TextViewFactory.setText(bean.getGoods_factory());
        holder.TextViewGuige.setText(bean.getGoods_guige());

        if (getLoginKey == null || getLoginKey.equals("")) {//价格登录后可见
            holder.TextViewPrice.setText("价格登录后可见");
        } else {
            if (member_role.equals("0")) {//显示正常价格   liubw
                holder.TextViewPrice.setText("价格认证后可见");
            } else {//价格认证后可见
                holder.TextViewPrice.setText("￥" + bean.getGoods_price());
            }
        }

        imageLoader.displayImage(bean.getGoods_image_url(), holder.ImageViewImagePic01, options, animateFirstListener);
        OnImageViewClick(holder.ImageViewImagePic01, "goods", bean.getGoods_id());

        return convertView;
    }

    class ViewHolder {
        ImageView ImageViewImagePic01;      //药品缩略图
        TextView TextViewTitle;             //药品名字
        TextView TextViewPrice;             //价钱
        TextView TextViewFactory;           //生产厂家
        TextView TextViewGuige;             //规格
    }

    public void OnImageViewClick(View view, final String type, final String data) {
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("goods")) {// 商品编号
                    Intent intent = new Intent(context, GoodsDetailsActivity.class);
                    intent.putExtra("goods_id", data);
                    context.startActivity(intent);
                }
            }
        });
    }
}
