package com.huatuo_b2b.htb2b.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.fragment.widget.BaseFragmentActivity;
import com.huatuo_b2b.htb2b.ui.mine.MobileRegisterSubmitActivity;

/**
 * Created by jinguo on 2016/3/18.
 */
public class FindByEmailFragment extends Fragment implements View.OnClickListener {

    private MyShopApplication myApplication;
    private BaseFragmentActivity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find_pas_email, container,
                false);
        activity = (BaseFragmentActivity) getActivity();
        initViewID(view);
        return view;
    }

    private void initViewID(View view) {

        view.findViewById(R.id.btn_find).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_find:
                break;
        }
    }
}
