package com.huatuo_b2b.htb2b.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 更改收货地址Bean
 *
 * @author KingKong·HE
 * @Time 2014年1月17日 下午4:44:35
 * @E-mail hjgang@bizpoer.com
 */
public class UpdateAddress {
    public static class Attr {
        public static final String ALLOW_OFFPAY = "allow_offpay";
        public static final String CONTENT = "content";
        public static final String OFFPAY_HASH = "offpay_hash";
        public static final String OFFPAY_HASH_BATCH = "offpay_hash_batch";
    }

    private String allow_offpay;
    private String content;
    private String offpay_hash;
    private String offpay_hash_batch;

    public UpdateAddress() {
    }

    public UpdateAddress(String allow_offpay, String content,
                         String offpay_hash, String offpay_hash_batch) {
        super();
        this.allow_offpay = allow_offpay;
        this.content = content;
        this.offpay_hash = offpay_hash;
        this.offpay_hash_batch = offpay_hash_batch;
    }

    public static UpdateAddress newInstanceList(String json) {
        UpdateAddress bean = null;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.length() > 0) {
                String allow_offpay = obj.optString(Attr.ALLOW_OFFPAY);
                String content = obj.optString(Attr.CONTENT);
                String offpay_hash = obj.optString(Attr.OFFPAY_HASH);
                String offpay_hash_batch = obj.optString(Attr.OFFPAY_HASH_BATCH);
                bean = new UpdateAddress(allow_offpay, content, offpay_hash, offpay_hash_batch);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public String getAllow_offpay() {
        return allow_offpay;
    }

    public void setAllow_offpay(String allow_offpay) {
        this.allow_offpay = allow_offpay;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOffpay_hash() {
        return offpay_hash;
    }

    public void setOffpay_hash(String offpay_hash) {
        this.offpay_hash = offpay_hash;
    }

    public String getOffpay_hash_batch() {
        return offpay_hash_batch;
    }

    public void setOffpay_hash_batch(String offpay_hash_batch) {
        this.offpay_hash_batch = offpay_hash_batch;
    }

    @Override
    public String toString() {
        return "UpdateAddress [allow_offpay=" + allow_offpay + ", content="
                + content + ", offpay_hash=" + offpay_hash
                + ", offpay_hash_batch=" + offpay_hash_batch + "]";
    }


}
