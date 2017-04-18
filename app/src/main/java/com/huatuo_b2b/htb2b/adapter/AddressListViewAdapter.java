package com.huatuo_b2b.htb2b.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.AddressList;

import java.util.ArrayList;

/**
 * 收货地址列表适配器
 *
 * @author KingKong·HE
 * @Time 2014-1-6 下午12:06:09
 */
public class AddressListViewAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<AddressList> addressLists;

    public AddressListViewAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return addressLists == null ? 0 : addressLists.size();
    }

    @Override
    public Object getItem(int position) {
        return addressLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<AddressList> getAddressLists() {
        return addressLists;
    }

    public void setAddressLists(ArrayList<AddressList> addressLists) {
        this.addressLists = addressLists;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (null == convertView) {
            convertView = inflater.inflate(R.layout.listview_address_item, null);
            holder = new ViewHolder();
            holder.textAddressName = (TextView) convertView.findViewById(R.id.textAddressName);
            holder.textAddressPhone = (TextView) convertView.findViewById(R.id.textAddressPhone);
            holder.textAddressAddress = (TextView) convertView.findViewById(R.id.textAddressAddress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AddressList bean = addressLists.get(position);
        holder.textAddressName.setText(bean.getTrue_name() == null ? "" : bean.getTrue_name());
        holder.textAddressPhone.setText(bean.getMob_phone() == null ? "" : bean.getMob_phone());
        holder.textAddressAddress.setText(bean.getArea_info() + "\t" + bean.getAddress());

        return convertView;
    }

    class ViewHolder {
        TextView textAddressName;
        TextView textAddressPhone;
        TextView textAddressAddress;
    }
}
