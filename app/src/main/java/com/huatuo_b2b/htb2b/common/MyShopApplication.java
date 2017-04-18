package com.huatuo_b2b.htb2b.common;


import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import com.github.nkzawa.emitter.Emitter.Listener;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.IMMemberInFo;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler;
import com.huatuo_b2b.htb2b.http.RemoteDataHandler.Callback;
import com.huatuo_b2b.htb2b.http.ResponseData;
import com.huatuo_b2b.htb2b.ui.mine.IMFriendsListActivity;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.net.URISyntaxException;
import java.util.HashMap;

/**
 * 全局变量
 *
 * @author KingKong-HE
 * @Time 2014-12-31
 * @Email KingKong@QQ.COM
 */
public class MyShopApplication extends Application {

    /**
     * 系统初始化配置文件操作器
     */
    private SharedPreferences sysInitSharedPreferences;

    /**
     * 记录用户登录后的密钥KEY
     */
    private String loginKey;

    /**
     * 记录用户登录后的MemberID
     */
    private String memberID;

    /**
     * 记录用户身份member_role
     */
    private String member_role;

    /**
     * 记录用户登录后的UserName
     */
    private String userName;

    /**
     * 记录用户登录后的memberAvatar
     */
    private String memberAvatar;

    /**
     * 记录是否自动登录
     */
    private boolean IsCheckLogin;

    /**
     * 记录IM是否在线
     */
    private boolean IMConnect = true;

    /**
     * 记录IM是否提示Notification
     */
    private boolean IMNotification = true;

    /**
     * 记录是否显示未读消息
     */
    private boolean showNum = true;

    /**
     * 消息通知
     */
    private Notification mNotification;
    private NotificationManager mNotificationManager;

    /**
     * Socket即时通讯实例
     */
    private Socket mSocket;

    @Override
    public void onCreate() {
        super.onCreate();

        sysInitSharedPreferences = getSharedPreferences(Constants.SYSTEM_INIT_FILE_NAME, MODE_PRIVATE);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotification = new Notification(R.mipmap.ic_launcher, getString(R.string.more_aboutus_appname), System.currentTimeMillis());

        loginKey = sysInitSharedPreferences.getString("loginKey", "");

        memberID = sysInitSharedPreferences.getString("memberID", "");

        userName = sysInitSharedPreferences.getString("userName", "");

        member_role = sysInitSharedPreferences.getString("member_role", "");

        memberAvatar = sysInitSharedPreferences.getString("memberAvatar", "");

        IsCheckLogin = sysInitSharedPreferences.getBoolean("IsCheckLogin", false);

        loadingUserInfo(loginKey, memberID);

        createCacheDir();

        initImageLoader(this);

        try {
            //连接Socket
            mSocket = IO.socket(Constants.IM_HOST);

            mSocket.io().reconnectionDelay(2000);

            mSocket.connect();

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        //通知已连接
        mSocket.on(Socket.EVENT_CONNECT, new Listener() {

            public void call(Object... args) {
//			   System.out.println("已连接");

                UpDateUser();

            }
        });

        //通知已断开
        mSocket.on(Socket.EVENT_DISCONNECT, new Listener() {

            public void call(Object... args) {

//			  System.out.println("已断开");

                IMConnect = false;////设置链接失败
//			  mNotification.tickerText = "您的IM帐号已离线";;
//			  Intent intent = new Intent(getApplicationContext(),IMFriendsListActivity.class);
//              PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
//              mNotification.setLatestEventInfo(getApplicationContext(), "", "",contentIntent);//ShopNC商城客户端
//              mNotificationManager.notify(-1, mNotification);// 通知一下才会生效哦
//              mNotificationManager.cancel(-1);

            }
        });
        //获取node消息
        mSocket.on("get_msg", new Listener() {

            public void call(Object... get_msg) {
                String message = get_msg[0].toString();

                IMConnect = true;//设置链接成功

                if (!message.equals("{}")) {
                    if (isIMNotification()) {
                        mNotification.tickerText = "新消息注意查收";
                        ;
                        Intent intent = new Intent(getApplicationContext(), IMFriendsListActivity.class);
                        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

//                        mNotification.setLatestEventInfo(getApplicationContext(), "消息提示", "有新消息注意查收", contentIntent);//ShopNC商城客户端
                        mNotificationManager.notify(13, mNotification);// 通知一下才会生效哦
                    } else {
                        Intent intent = new Intent(Constants.IM_UPDATA_UI);
                        intent.putExtra("message", message);
                        sendBroadcast(intent);
                    }
                }

            }
        });

        mSocket.on("get_state", new Listener() {
            public void call(Object... obj) {
                String get_state = obj[0].toString();

                Intent intent = new Intent(Constants.IM_FRIENDS_LIST_UPDATA_UI);
                intent.putExtra("get_state", get_state);
                sendBroadcast(intent);
            }
        });

    }

    /**
     * node 更新会员状态
     *
     * @param :u_id   会员编号
     * @param :u_name 会员名
     * @param :avatar 会员头像
     * @param :s_id   店铺编号
     * @param :s_name 店铺名
     */
    public void UpDateUser() {

        if (!TextUtils.isEmpty(memberID)) {
            try {
                String update_user = "{\"u_id\":\"" + memberID + "\",\"u_name\":\"" + userName + "\",\"avatar\":\"" + memberAvatar + "\"}";
                mSocket.emit("update_user", new JSONObject(update_user));
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
        }
    }

    /**
     * 获取个人资料
     *
     * @param key  用户标识
     * @param u_id 用户ID
     *             *
     */
    public void loadingUserInfo(String key, String u_id) {
        String url = Constants.URL_MEMBER_CHAT_GET_USER_INFO;
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("key", key);
        params.put("u_id", u_id);
        params.put("t", "member_id");

        RemoteDataHandler.asyncPostDataString(url, params, new Callback() {
            @Override
            public void dataLoaded(ResponseData data) {
                if (data.getCode() == HttpStatus.SC_OK) {
                    String json = data.getJson();
                    try {
                        JSONObject obj2 = new JSONObject(json);
                        String member_info = obj2.optString("member_info");
                        IMMemberInFo memberINFO = IMMemberInFo.newInstanceList(member_info);
                        if (memberINFO != null) {
                            setMemberID(memberINFO.getMember_id() == null ? "" : memberINFO.getMember_id());
                            setMemberAvatar(memberINFO.getMember_avatar() == null ? "" : memberINFO.getMember_avatar());
                            setUserName(memberINFO.getMember_name() == null ? "" : memberINFO.getMember_name());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    public boolean isShowNum() {
        return showNum;
    }

    public void setShowNum(boolean showNum) {
        this.showNum = showNum;
    }

    public SharedPreferences getSysInitSharedPreferences() {
        return sysInitSharedPreferences;
    }

    public void setSysInitSharedPreferences(
            SharedPreferences sysInitSharedPreferences) {
        this.sysInitSharedPreferences = sysInitSharedPreferences;
    }

    public void setLoginKey(String loginKey) {
        this.loginKey = loginKey;

        // System.out.println("@@@@@@@@@@@@@@@@setLoginKey      loginKey:"+ loginKey);
        sysInitSharedPreferences.edit().putString("loginKey", this.loginKey).commit();
    }

    public String getLoginKey() {
        String loginKey = sysInitSharedPreferences.getString("loginKey", "");

        //System.out.println("@@@@@@@@@@@@@@@@@@getLoginKey      loginKey:"+ loginKey);
        return loginKey;
    }

    public String getMemberAvatar() {
        String memberAvatar = sysInitSharedPreferences.getString("memberAvatar", "");
        return memberAvatar;
    }

    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar;
        sysInitSharedPreferences.edit().putString("memberAvatar", this.memberAvatar).commit();
    }

    public boolean isIsCheckLogin() {
        boolean IsCheckLogin = sysInitSharedPreferences.getBoolean("IsCheckLogin", false);
        return IsCheckLogin;
    }

    public void setIsCheckLogin(boolean isCheckLogin) {
        IsCheckLogin = isCheckLogin;
        sysInitSharedPreferences.edit().putBoolean("IsCheckLogin", this.IsCheckLogin).commit();
    }

    public boolean isIMConnect() {
        return IMConnect;
    }

    public void setIMConnect(boolean iMConnect) {
        IMConnect = iMConnect;
    }

    public String getMemberID() {
        String memberID = sysInitSharedPreferences.getString("memberID", "");
        return memberID;
    }

    public String getUserName() {
        String userName = sysInitSharedPreferences.getString("userName", "");
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        sysInitSharedPreferences.edit().putString("userName", this.userName).commit();
    }

    public void setMemberID(String memberID) {
        this.memberID = memberID;
        sysInitSharedPreferences.edit().putString("memberID", this.memberID).commit();
    }

    public String getMember_role() {
        String member_role = sysInitSharedPreferences.getString("member_role", "");
        return member_role;
    }

    public void setMember_role(String member_role) {
        this.member_role = member_role;
        sysInitSharedPreferences.edit().putString("member_role", this.member_role).commit();
    }

    public boolean isIMNotification() {
        return IMNotification;
    }

    public Socket getmSocket() {
        return mSocket;
    }

    public void setmSocket(Socket mSocket) {
        this.mSocket = mSocket;
    }

    public void setIMNotification(boolean iMNotification) {
        IMNotification = iMNotification;
    }

    public NotificationManager getmNotificationManager() {
        return mNotificationManager;
    }

    public void setmNotificationManager(NotificationManager mNotificationManager) {
        this.mNotificationManager = mNotificationManager;
    }

    public Notification getmNotification() {
        return mNotification;
    }

    public void setmNotification(Notification mNotification) {
        this.mNotification = mNotification;
    }

    /**
     * 设置ImageLoader初始化参数
     */
    public static void initImageLoader(Context context) {
        // This configuration tuning is custom. You can tune every option, you may tune some of them,
        // or you can create default configuration by
        //  ImageLoaderConfiguration.createDefault(this);
        // method.
        File cacheDir = new File(Constants.CACHE_DIR_IMAGE);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .writeDebugLogs() // Remove for release app
                .build();
        // Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(config);
    }

    /**
     * 创建SD卡缓存目录
     */
    private void createCacheDir() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            File f = new File(Constants.CACHE_DIR);
            if (f.exists()) {
                System.out.println("SD卡缓存目录:已存在!");
            } else {
                if (f.mkdirs()) {
                    System.out.println("SD卡缓存目录:" + f.getAbsolutePath()
                            + "已创建!");
                } else {
                    System.out.println("SD卡缓存目录:创建失败!");
                }
            }

            File ff = new File(Constants.CACHE_DIR_IMAGE);
            if (ff.exists()) {
                System.out.println("SD卡照片缓存目录:已存在!");
            } else {
                if (ff.mkdirs()) {
                    System.out.println("SD卡照片缓存目录:" + ff.getAbsolutePath()
                            + "已创建!");
                } else {
                    System.out.println("SD卡照片缓存目录:创建失败!");
                }
            }

            File fff = new File(Constants.CACHE_DIR_UPLOADING_IMG);
            if (fff.exists()) {
                System.out.println("SD卡照片缓存目录:已存在!");
            } else {
                if (fff.mkdirs()) {
                    System.out.println("SD卡照片缓存目录:" + fff.getAbsolutePath()
                            + "已创建!");
                } else {
                    System.out.println("SD卡照片缓存目录:创建失败!");
                }
            }
        }
    }

}
