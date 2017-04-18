package com.huatuo_b2b.htb2b.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.FavoritesList;
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
public class FavoritesListViewAdapter extends BaseAdapter {
    private Context context;

    private LayoutInflater inflater;

    private ArrayList<FavoritesList> fList;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public FavoritesListViewAdapter(Context context) {
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

    public ArrayList<FavoritesList> getfList() {
        return fList;
    }

    public void setfList(ArrayList<FavoritesList> fList) {
        this.fList = fList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.listivew_favorites_item, null);
            holder = new ViewHolder();
            holder.imageGoodsPic = (ImageView) convertView.findViewById(R.id.imageGoodsPic);
            holder.textGoodsName = (TextView) convertView.findViewById(R.id.textGoodsName);
            holder.textGoodsPrice = (TextView) convertView.findViewById(R.id.textGoodsPrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final FavoritesList bean = fList.get(position);

        holder.textGoodsName.setText(bean.getGoods_name() == null ? "" : bean.getGoods_name());
        ;
        holder.textGoodsPrice.setText("￥" + (bean.getGoods_price() == null ? "0.00" : bean.getGoods_price()));
        ;
        imageLoader.displayImage(bean.getGoods_image_url(), holder.imageGoodsPic, options, animateFirstListener);

        return convertView;
    }

    public class ViewHolder {
        ImageView imageGoodsPic;
        TextView textGoodsName;
        TextView textGoodsPrice;
        ImageView imageFacoritesDeleteButton;
    }
}
