package com.huatuo_b2b.htb2b.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.Search;

import java.util.List;

/**
 * Created by admin on 15/12/30.
 */
public class SearchListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private List<Search> searchLists;

    public SearchListViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int size = searchLists == null ? 0 : searchLists.size();
        if (size >= 20) {
            size = 20;
        }
        return size;
    }

    @Override
    public Object getItem(int position) {
        return searchLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Search> getSearchLists() {
        return searchLists;
    }

    public void setSearchLists(List<Search> list) {
        this.searchLists = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.listivew_search_item, null);
            holder = new ViewHolder();
            holder.searchKeyWord = (TextView) convertView.findViewById(R.id.searchKeyWord);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Search bean = searchLists.get(position);
        holder.searchKeyWord.setText(bean.getSearchKeyWord());

        return convertView;
    }

    class ViewHolder {
        TextView searchKeyWord;
    }
}

