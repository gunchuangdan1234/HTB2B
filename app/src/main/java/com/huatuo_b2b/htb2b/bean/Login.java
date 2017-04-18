package com.huatuo_b2b.htb2b.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 登录Bean
 *
 * @author KingKong·HE
 * @Time 2014年1月17日 下午4:44:35
 * @E-mail hjgang@bizpoer.com
 */
public class Login {
    public static class Attr {
        public static final String KEY = "key";
        public static final String USERNAME = "username";
        public static final String USERID = "userid";
        public static final String MEMBER_ROLE = "member_role";
    }

    private String key;
    private String username;
    private String userid;
    private String member_role;

    public Login() {
    }


    public Login(String key, String username, String userid, String member_role) {
        super();
        this.key = key;
        this.username = username;
        this.userid = userid;
        this.member_role = member_role;
    }


    public static Login newInstanceList(String json) {
        Login bean = null;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.length() > 0) {
                String key = obj.optString(Attr.KEY);
                String username = obj.optString(Attr.USERNAME);
                String userid = obj.optString(Attr.USERID);
                String member_role = obj.getString(Attr.MEMBER_ROLE);//member_role  0 普通   1、2 是买家或是卖家

                bean = new Login(key, username, userid, member_role);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMember_role() {
        return member_role;
    }

    public void setMember_role(String member_role) {
        this.member_role = member_role;
    }


    @Override
    public String toString() {
        return "Login [key=" + key + ", username=" + username + ", member_role=" + member_role + "]";
    }

}
