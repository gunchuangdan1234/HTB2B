package com.huatuo_b2b.htb2b.ui.mine;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.DrugLibrary;

import java.io.Serializable;

/**
 * Created by jinguo on 2016/4/18.
 */
public class DrugLibraryActivity extends Activity implements View.OnClickListener {
    private DrugLibrary drugLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_drug_library);
        drugLibrary = (DrugLibrary) getIntent().getSerializableExtra("drug_library");

        initViewID(drugLibrary);
    }

    private void initViewID(DrugLibrary drugLibrary) {

        findViewById(R.id.imageBack).setOnClickListener(this);

        if (drugLibrary != null) {

            TextView tv_drug_name = (TextView) findViewById(R.id.tv_drug_name);
            tv_drug_name.setText(drugLibrary.getDrug_name() == null ? "" : drugLibrary.getDrug_name());

            TextView tv_drug_common_name = (TextView) findViewById(R.id.tv_drug_common_name);
            tv_drug_common_name.setText(drugLibrary.getDrug_common_name() == null ? "" : drugLibrary.getDrug_common_name());

            TextView tv_drug_spec = (TextView) findViewById(R.id.tv_drug_spec);
            tv_drug_spec.setText(drugLibrary.getDrug_spec() == null ? "" : drugLibrary.getDrug_spec());

            TextView tv_drug_dosage = (TextView) findViewById(R.id.tv_drug_dosage);
            tv_drug_dosage.setText(drugLibrary.getDrug_dosage() == null ? "" : drugLibrary.getDrug_dosage());


            TextView tv_approval_number = (TextView) findViewById(R.id.tv_approval_number);
            tv_approval_number.setText(drugLibrary.getApproval_number() == null ? "" : drugLibrary.getApproval_number());

            TextView tv_product_company = (TextView) findViewById(R.id.tv_product_company);
            tv_product_company.setText(drugLibrary.getProduct_company() == null ? "" : drugLibrary.getProduct_company());

            TextView tv_drug_usage = (TextView) findViewById(R.id.tv_drug_usage);
            tv_drug_usage.setText(drugLibrary.getDrug_usage() == null ? "" : drugLibrary.getDrug_usage());


            TextView tv_adaptation_disease = (TextView) findViewById(R.id.tv_adaptation_disease);
            tv_adaptation_disease.setText(drugLibrary.getAdaptation_disease() == null ? "" : drugLibrary.getAdaptation_disease());

            TextView tv_untoward_effect = (TextView) findViewById(R.id.tv_untoward_effect);
            tv_untoward_effect.setText(drugLibrary.getUntoward_effect() == null ? "" : drugLibrary.getUntoward_effect());

            TextView tv_attention = (TextView) findViewById(R.id.tv_attention);
            tv_attention.setText(drugLibrary.getAttention() == null ? "" : drugLibrary.getAttention());

        }
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId())

        {
            case R.id.imageBack:
                finish();
                break;
        }
    }
}
