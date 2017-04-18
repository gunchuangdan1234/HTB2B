package com.huatuo_b2b.htb2b.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.OneType;
import com.huatuo_b2b.htb2b.common.AnimateFirstDisplayListener;
import com.huatuo_b2b.htb2b.common.SystemHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * 一级分类适配器
 *
 * @author KingKong·HE
 * @Time 2014-1-6 下午12:06:09
 * @E-mail hjgang@bizpoer.com
 */
public class OneTypeListViewAdapter extends BaseAdapter {

    private Context context;

    private LayoutInflater inflater;

    private ArrayList<OneType> typeList;

    protected ImageLoader imageLoader = ImageLoader.getInstance();

    private DisplayImageOptions options = SystemHelper.getDisplayImageOptions();

    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    public OneTypeListViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return typeList == null ? 0 : typeList.size();
    }

    @Override
    public Object getItem(int position) {
        return typeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<OneType> getTypeList() {
        return typeList;
    }

    public void setTypeList(ArrayList<OneType> typeList) {
        this.typeList = typeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.listivew_type_item, null);
            holder = new ViewHolder();
            holder.imagePic = (ImageView) convertView.findViewById(R.id.imagePic);
            holder.typeTitleName = (TextView) convertView.findViewById(R.id.typeTitleName);
            holder.typeDesc = (TextView) convertView.findViewById(R.id.typeDesc);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        OneType bean = typeList.get(position);
        holder.typeTitleName.setText(bean.getGc_name());
        holder.typeDesc.setText(bean.getText());

        imageLoader.displayImage(bean.getImage(), holder.imagePic, options, animateFirstListener);
        return convertView;
    }

    class ViewHolder {
        ImageView imagePic;
        TextView typeTitleName;
        TextView typeDesc;
    }
}
