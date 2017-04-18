package com.huatuo_b2b.htb2b.ui.type;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.ManSongAdapter;
import com.huatuo_b2b.htb2b.bean.ManSongRules;

import java.util.ArrayList;

/**
 * Created by jinguo on 2016/4/27.
 */
public class ManSongActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_man_song);

        ArrayList<ManSongRules> mRules = getIntent().getParcelableArrayListExtra("mRules");

        initViewID(mRules);
    }

    private void initViewID(ArrayList<ManSongRules> mRules) {

        ListView lv_man_song = (ListView) findViewById(R.id.lv_man_song);

        ManSongAdapter manSongAdapter = new ManSongAdapter(this, mRules);
        lv_man_song.setAdapter(manSongAdapter);

        findViewById(R.id.imageBack).setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imageBack:
                finish();
                break;
        }
    }
}
