package com.huatuo_b2b.htb2b.ui.type;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.adapter.InvoiceAddSpinnerAdapter;
import com.huatuo_b2b.htb2b.adapter.InvoiceListViewAdapter;
import com.huatuo_b2b.htb2b.bean.CityList;
import com.huatuo_b2b.htb2b.bean.InvoiceList;
import com.huatuo_b2b.htb2b.common.Constants;
import com.huatuo_b2b.htb2b.common.LogUtil;
import com.huatuo_b2b.htb2b.common.MyShopApplication;
import com.huatuo_b2b.htb2b.custom.MyAddInvoice;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 发票列表
 *
 * @author KingKong-HE
 * @Time 2015-1-20
 * @Email KingKong@QQ.COM
 */
public class InvoiceListActivity extends Activity implements OnClickListener {

    private MyShopApplication myApplication;

    private InvoiceListViewAdapter aListViewAdapter;

    private InvoiceAddSpinnerAdapter invoiceAddSpinnerAdapter;

    private ListView listView;

    private MyAddInvoice addInvoiceDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invoice_list_view);

        myApplication = (MyShopApplication) getApplicationContext();

        initViewID();//初始化控件ID

    }

    /**
     * 初始化控件ID
     */
    public void initViewID() {

        ImageView imageBack = (ImageView) findViewById(R.id.imageBack);

        listView = (ListView) findViewById(R.id.listViewID);

        TextView textAddInvoiceID = (TextView) findViewById(R.id.textAddInvoiceID);

        aListViewAdapter = new InvoiceListViewAdapter(InvoiceListActivity.this);

        invoiceAddSpinnerAdapter = new InvoiceAddSpinnerAdapter(InvoiceListActivity.this);

        listView.setAdapter(aListViewAdapter);

        imageBack.setOnClickListener(this);

        textAddInvoiceID.setOnClickListener(this);

        loadingInvoiceListData();//初始化加载发票列表数据

        //listview添加点击事件
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                InvoiceList bean = (InvoiceList) listView.getItemAtPosition(arg2);

                if (bean != null) {
                    Intent intent = new Intent(InvoiceListActivity.this, BuyStep1Activity.class);
                    intent.putExtra("inv_id", bean.getInv_id());
                    intent.putExtra("inv_context", bean.getInv_title() + bean.getInv_content());
                    setResult(Constants.SELECT_INVOICE, intent);
                    finish();
                }
            }
        });

        //listview添加长按监听事件
        listView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           final int arg2, long arg3) {

                final InvoiceList bean = (InvoiceList) listView.getItemAtPosition(arg2);

                if (bean != null && !bean.getInv_id().equals("-1")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(InvoiceListActivity.this);
                    builder.setTitle("功能选择")
                            .setMessage("您确定移除？");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {

                            invoiceDelete(bean.getInv_id());//删除发票

                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();

                }

                return true;
            }
        });

    }

    /**
     * 初始化加载发票列表数据
     */
    public void loadingInvoiceListData() {
        String url = Constants.URL_INVOICE_LIST;
        ;//index.php?act=member_buy&op=test
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject obj = new JSONObject(json);
                        String invoice_list = obj.getString("invoice_list");
                        ArrayList<InvoiceList> list = InvoiceList.newInstanceList(invoice_list);
                        aListViewAdapter.setInvoiceList(list);
                        aListViewAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(InvoiceListActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });
    }

    /**
     * 发票内容列表请求地址
     */
    public void getInvoiceContextData() {
        String url = Constants.URL_INVOICE_CONTEXT_LIST;
        ;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject objJSON = new JSONObject(json);
                        String invoice_list = objJSON.getString("invoice_content_list");
                        JSONArray arr = new JSONArray(invoice_list);
                        int size = null == arr ? 0 : arr.length();
                        ArrayList<CityList> lists = new ArrayList<CityList>();
                        for (int i = 0; i < size; i++) {
                            lists.add(new CityList("-1", arr.get(i).toString()));
                        }
//						invoiceAddSpinnerAdapter.setDatas(lists);
//						spinner.setAdapter(invoiceAddSpinnerAdapter);
//						invoiceAddSpinnerAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(InvoiceListActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 删除发票
     *
     * @param :key   当前登录令牌
     * @param inv_id 发票编号
     */
    public void invoiceDelete(String inv_id) {
        String url = Constants.URL_INVOICE_DELETE;
        ;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("inv_id", inv_id);

        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();

                    if (json.equals("1")) {
                        Toast.makeText(InvoiceListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        ;
                        loadingInvoiceListData();//刷新列表
                    }

                    try {
                        JSONObject obj = new JSONObject(json);
                        String error = obj.getString("error");
                        if (error != null) {
                            Toast.makeText(InvoiceListActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(InvoiceListActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });
    }

    /**
     * 新增发票
     *
     * @param :key 当前登录令牌
     */
    public void loadingSaveInvoiceData(final MyAddInvoice addInvoiceDialog, String inv_title_select, String invCompanyName, String invNum,
                                       String invCompanyAddress, String invPhoneNum, String invBankName, String invBankNum,
                                       String userName, String userPhoneNum, String userProvince, String userAddress) {
        String url = Constants.URL_INVOICE_ADD;
        ;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", myApplication.getLoginKey());
        params.put("inv_state", inv_title_select);


        params.put("inv_content", "药品");
        params.put("inv_title", invCompanyName);
        params.put("inv_company", invCompanyName);
        params.put("inv_code", invNum);
        params.put("inv_reg_addr", invCompanyAddress);
        params.put("inv_reg_phone", invPhoneNum);
        params.put("inv_reg_bname", invBankName);
        params.put("inv_reg_baccount", invBankNum);


        params.put("inv_rec_name", userName);
        params.put("inv_rec_mobphone", userPhoneNum);
        params.put("inv_rec_province", userProvince);
        params.put("inv_goto_addr", userAddress);


        RemoteDataHandler.asyncLoginPostDataString(url, params, myApplication, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                addInvoiceDialog.dismiss();
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();

                    loadingInvoiceListData();//刷新列表

                    try {
                        JSONObject obj = new JSONObject(json);
                        String error = obj.getString("error");
                        if (error != null) {
                            Toast.makeText(InvoiceListActivity.this, error, Toast.LENGTH_SHORT).show();
                            ;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    loadingInvoiceListData();
                } else {
                    Toast.makeText(InvoiceListActivity.this, "数据加载失败，请稍后重试", Toast.LENGTH_SHORT).show();
                    ;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageBack:

                finish();

                break;
            case R.id.textAddInvoiceID:


                addInvoiceDialog = new MyAddInvoice(InvoiceListActivity.this);

//			getInvoiceContextData(addInvoiceDialog.add_invoice_spinner_context);//发票内容列表请求地址
                getInvoiceContextData();
                addInvoiceDialog.show();

                addInvoiceDialog.tv_invoice_province.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent addIntent = new Intent(InvoiceListActivity.this, AddressSetActivity.class);

                        InvoiceListActivity.this.startActivityForResult(addIntent, 1001);
                    }
                });

                addInvoiceDialog.btu_on.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String invCompanyName = addInvoiceDialog.add_invoice_edit_danwei_name.getText().toString();
                        String invNum = addInvoiceDialog.add_invoice_edit_inv_num.getText().toString();
                        String invCompanyAddress = addInvoiceDialog.add_invoice_edit_company_address.getText().toString();
                        String invPhoneNum = addInvoiceDialog.add_invoice_edit_phone_num.getText().toString();
                        String invBankName = addInvoiceDialog.add_invoice_edit_bank_name.getText().toString();
                        String invBankNum = addInvoiceDialog.add_invoice_edit_bank_num.getText().toString();

                        String userName = addInvoiceDialog.et_invoice_name.getText().toString();
                        String userPhoneNum = addInvoiceDialog.et_invoice_phone_num.getText().toString();
                        String userProvince = addInvoiceDialog.tv_invoice_province.getText().toString();
                        String userAddress = addInvoiceDialog.et_invoice_address.getText().toString();


                        if (isNullInv(invCompanyName, invNum, invCompanyAddress, invPhoneNum, invBankName, invBankNum, userName, userPhoneNum, userProvince, userAddress))
                            return;


                        //新增发票
                        loadingSaveInvoiceData(addInvoiceDialog, addInvoiceDialog.inv_title_select, invCompanyName, invNum, invCompanyAddress, invPhoneNum, invBankName, invBankNum, userName, userPhoneNum, userProvince, userAddress);
                    }
                });

                addInvoiceDialog.btu_off.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addInvoiceDialog.dismiss();
                    }
                });

                break;
            default:
                break;
        }
    }

    private boolean isNullInv(String invCompanyName, String invNum, String invCompanyAddress, String invPhoneNum, String invBankName, String invBankNum, String userName, String userPhoneNum, String userProvince, String userAddress) {
        if (TextUtils.isEmpty(invCompanyName)) {
            LogUtil.toast(InvoiceListActivity.this, "单位名称不能为空！");
            return true;
        }

        if (TextUtils.isEmpty(invNum)) {
            LogUtil.toast(InvoiceListActivity.this, "纳税人识别号不能为空！");
            return true;
        }

        if (TextUtils.isEmpty(invCompanyAddress)) {
            LogUtil.toast(InvoiceListActivity.this, "注册地址不能为空！");
            return true;
        }


        if (TextUtils.isEmpty(invPhoneNum)) {
            LogUtil.toast(InvoiceListActivity.this, "注册电话不能为空！");
            return true;
        }

        if (TextUtils.isEmpty(invBankName)) {
            LogUtil.toast(InvoiceListActivity.this, "开户行名称不能为空！");
            return true;
        }

        if (TextUtils.isEmpty(invBankNum)) {
            LogUtil.toast(InvoiceListActivity.this, "银行账户称不能为空！");
            return true;
        }


        if (TextUtils.isEmpty(userName)) {
            LogUtil.toast(InvoiceListActivity.this, "收票人姓名不能为空！");
            return true;
        }

        if (TextUtils.isEmpty(userPhoneNum)) {
            LogUtil.toast(InvoiceListActivity.this, "收票人手机号不能为空！");
            return true;
        }
        if ("点击选择收票人省份".equals(userProvince)) {
            LogUtil.toast(InvoiceListActivity.this, "收票人省份不能为空！");
            return true;
        }


        if (TextUtils.isEmpty(userAddress)) {
            LogUtil.toast(InvoiceListActivity.this, "收票人地址不能为空！");
            return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1001:

                if (resultCode == RESULT_OK) {
                    String address_set = data.getStringExtra("address_set");

                    addInvoiceDialog.tv_invoice_province.setText(address_set);
                }
                break;

        }
    }
}
