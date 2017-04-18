package com.huatuo_b2b.htb2b.adapter;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.Area;
import com.huatuo_b2b.htb2b.bean.ManSongRules;

import java.util.ArrayList;

/**
 * Created by jinguo on 2016/4/27.
 */
public class ManSongAdapter extends BaseAdapter {

    ArrayList<ManSongRules> mRules = new ArrayList<ManSongRules>();

    private Activity activity;
    private LayoutInflater inflater;

    public ManSongAdapter(Activity activity, ArrayList<ManSongRules> mRules) {
        this.activity = activity;
        this.mRules = mRules;
        this.inflater = LayoutInflater.from(activity);

    }

    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        return mRules.size();
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        return mRules.get(position);
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.item_man_song, null);
            holder = new ViewHolder();
            holder.manSongID = (TextView) convertView.findViewById(R.id.manSongID);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ManSongRules bean = mRules.get(position);

        if (bean.getGoods_id().equals("0")) {
            holder.manSongID.setText(Html.fromHtml("单笔订单满<font color='#FF3300'>" + bean.getPrice() + "元</font>，立减现金<font color='#339900'>" + bean.getDiscount() + "元</font>"));
        } else {
            holder.manSongID.setText(Html.fromHtml("单笔订单满<font color='#FF3300'>" + bean.getPrice() + "元</font> ， 立减现金<font color='#339900'>" + bean.getDiscount() + "</font>， 送礼品"));
        }


        return convertView;
    }


    class ViewHolder {
        TextView manSongID;
    }
}
