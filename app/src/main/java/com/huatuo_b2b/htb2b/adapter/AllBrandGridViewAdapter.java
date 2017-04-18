/**
 * ProjectName:AndroidShopNC2014Moblie
 * PackageName:net.shopnc.android.adapter
 * FileNmae:CommendGridViewAdapter.java
 */
package com.huatuo_b2b.htb2b.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.AllBrandList;
import com.huatuo_b2b.htb2b.bean.CommendList;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * @author KingKong·HE
 * @Time 2014-1-6 下午12:06:09
 * @E-mail hjgang@bizpoer.com
 */
public class AllBrandGridViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<AllBrandList> allBrandLists;

    protected ImageLoader imageLoader = ImageLoader.getInstance();
    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();
    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public AllBrandGridViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return allBrandLists == null ? 0 : allBrandLists.size();
    }

    @Override
    public Object getItem(int position) {
        return allBrandLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<AllBrandList> getAllBrandLists() {
        return allBrandLists;
    }

    public void setAllBrandLists(ArrayList<AllBrandList> allBrandLists) {
        this.allBrandLists = allBrandLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_all_brand, null);
            holder = new ViewHolder();
            holder.iv_item_brand = (ImageView) convertView.findViewById(R.id.iv_item_brand);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AllBrandList bean = allBrandLists.get(position);


        imageLoader.displayImage(bean.getBrand_pic(), holder.iv_item_brand, options, animateFirstListener);

        return convertView;
    }

    class ViewHolder {
        ImageView iv_item_brand;
    }
}
