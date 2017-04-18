/**
 * ProjectName:AndroidShopNC2014Moblie
 * PackageName:net.shopnc.android.model
 * FileNmae:Login.java
 */
package com.huatuo_b2b.htb2b.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 我的商城Bean
 *
 * @author KingKong·HE
 * @Time 2014年1月17日 下午4:44:35
 * @E-mail hjgang@bizpoer.com
 */
public class Mine {
    public static class Attr {
        public static final String USERNAME = "user_name";
        public static final String AVATOR = "avator";
        public static final String POINT = "point";
        public static final String PREDEPOIT = "predepoit";
        public static final String AVAILABLE_RC_BALANCE = "available_rc_balance";
    }

    private String username;
    private String avator;
    private String point;
    private String predepoit;
    private String available_rc_balance;

    public Mine() {
    }

    public Mine(String username, String avator, String point,
                String predepoit, String available_rc_balance) {
        super();
        this.username = username;
        this.avator = avator;
        this.point = point;
        this.predepoit = predepoit;
        this.available_rc_balance = available_rc_balance;
    }

    public static Mine newInstanceList(String json) {
        Mine bean = null;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.length() > 0) {
                String username = obj.optString(Attr.USERNAME);
                String avator = obj.optString(Attr.AVATOR);
                String point = obj.optString(Attr.POINT);
                String predepoit = obj.optString(Attr.PREDEPOIT);
                String available_rc_balance = obj.optString(Attr.AVAILABLE_RC_BALANCE);
                bean = new Mine(username, avator, point, predepoit, available_rc_balance);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public String getAvailable_rc_balance() {
        return available_rc_balance;
    }

    public void setAvailable_rc_balance(String available_rc_balance) {
        this.available_rc_balance = available_rc_balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvator() {
        return avator;
    }

    public void setAvator(String avator) {
        this.avator = avator;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPredepoit() {
        return predepoit;
    }

    public void setPredepoit(String predepoit) {
        this.predepoit = predepoit;
    }

    @Override
    public String toString() {
        return "MyStore [username=" + username + ", avator=" + avator
                + ", point=" + point + ", predepoit=" + predepoit
                + ", available_rc_balance=" + available_rc_balance + "]";
    }
}
