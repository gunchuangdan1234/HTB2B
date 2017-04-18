package com.huatuo_b2b.htb2b.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.FavStoreList;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * 收藏商品列表适配器
 *
 * @author KingKong·HE
 * @Time 2014-1-6 下午12:06:09
 * @E-mail hjgang@bizpoer.com
 */
public class FavoritesStoreListViewAdapter extends BaseAdapter {
    private Context context;

    private LayoutInflater inflater;

    private ArrayList<FavStoreList> fList;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public FavoritesStoreListViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fList == null ? 0 : fList.size();
    }

    @Override
    public Object getItem(int position) {
        return fList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<FavStoreList> getfList() {
        return fList;
    }

    public void setfList(ArrayList<FavStoreList> fList) {
        this.fList = fList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.listivew_favorites_store_item, null);
            holder = new ViewHolder();
            holder.imageGoodsPic = (ImageView) convertView.findViewById(R.id.imageGoodsPic);
            holder.nameID = (TextView) convertView.findViewById(R.id.nameID);
            holder.goodsNumID = (TextView) convertView.findViewById(R.id.goodsNumID);
            holder.favNumID = (TextView) convertView.findViewById(R.id.favNumID);
            holder.favTimeID = (TextView) convertView.findViewById(R.id.favTimeID);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final FavStoreList bean = fList.get(position);

        holder.nameID.setText(bean.getStore_name() == null ? "" : bean.getStore_name());
        ;
        holder.goodsNumID.setText("商品数：" + (bean.getGoods_count() == null ? "0" : bean.getGoods_count()));
        ;
        holder.favNumID.setText("收藏数：" + (bean.getStore_collect() == null ? "0" : bean.getStore_collect()));
        ;
        holder.favTimeID.setText("收藏时间：" + (bean.getFav_time_text() == null ? "0" : bean.getFav_time_text()));
        ;
        imageLoader.displayImage(bean.getStore_avatar_url(), holder.imageGoodsPic, options, animateFirstListener);

        return convertView;
    }

    public class ViewHolder {
        ImageView imageGoodsPic;
        TextView nameID, goodsNumID, favNumID, favTimeID;
    }
}
