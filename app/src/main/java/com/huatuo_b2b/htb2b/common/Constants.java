package com.huatuo_b2b.htb2b.common;

/**
 * Created by admin on 15/12/30.
 */

import android.os.Environment;

/**
 * 常量类
 *
 * @author KingKong-HE
 * @Time 2014-12-31
 * @Email KingKong@QQ.COM
 */
public final class Constants {
    /**
     * 系统初始化配置文件名
     */
    public static final String SYSTEM_INIT_FILE_NAME = "sysini";

    /**
     * 本地缓存目录
     */
    public static final String CACHE_DIR;

    /**
     * 分页显示个数
     */
    public static final int PAGESIZE = 10;

    /**
     * 表情缓存目录
     */
    public static final String CACHE_DIR_SMILEY;

    /**
     * 图片缓存目录
     */
    public static final String CACHE_DIR_IMAGE;

    /**
     * 待上传图片缓存目录
     */
    public static final String CACHE_DIR_UPLOADING_IMG;

    /**
     * 微信APPID
     */
    public static final String APP_ID = "wx76c019464a5ebdaa";

    /**
     * 登录成功广播返回标识
     */
    public static final String LOGIN_SUCCESS_URL = "2";

    /**
     * 商品跳转购物车广播返回标识
     */
    public static final String SHOW_CART_URL = "3";

    /**
     * 点击去逛逛跳转首页广播返回标识
     */
    public static final String SHOW_HOME_URL = "9";

    /**
     * 选中发票后返回标识
     */
    public static final int SELECT_INVOICE = 4;

    /**
     * 新增收货地址返回标识
     */
    public static final int ADD_ADDRESS_SUCC = 5;

    /**
     * 选中收货地址返回标识
     */
    public static final int SELECT_ADDRESS = 6;

    /**
     * 支付成功返回标识
     */
    public static final String PAYMENT_SUCCESS = "7";

    /**
     * 虚拟订单支付成功返回标识
     */
    public static final String VPAYMENT_SUCCESS = "8";

    /**
     * IM新消息刷新页面返回标识
     */
    public static final String IM_UPDATA_UI = "10";

    /**
     * IM好友列表状态刷新页面返回标识
     */
    public static final String IM_FRIENDS_LIST_UPDATA_UI = "11";

    static {
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            CACHE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ShopNC/";
        } else {
            CACHE_DIR = Environment.getRootDirectory().getAbsolutePath() + "/ShopNC/";
        }

        CACHE_DIR_SMILEY = CACHE_DIR + "/smiley";
        CACHE_DIR_IMAGE = CACHE_DIR + "/pic";
        CACHE_DIR_UPLOADING_IMG = CACHE_DIR + "/uploading_img";
    }

    private Constants() {
    }

    /**
     * 与服务器端连接的协议名
     */
    public static final String PROTOCOL = "http://";

    /**
     * 服务器域名
     */
//    public static final String HOST = "b2b.huatuoyf.com";//101.201.197.50www.htyyao.com  b2b.huatuoyf.com 测试地址    原来的地址

    public static final String HOST = "b2b.htyyao.com";//101.201.197.50  b2b.huatuoyf.com 测试地址    test1.htyyao.com原来的地址

    /**
     * IM服务器地址、端口号
     */
    public static final String IM_HOST = PROTOCOL + "121.43.11test0.146:8095";//www.shopnctest.com:8095 原来的地址

    /**
     * 服务器端口号
     */
    public static final String PORT = "80";

    /**
     * 应用上下文名
     */
    public static final String APP = "/mobile";///mobile

    /**
     * 应用上下文完整路径
     */
    //http://b2b.htyyao.com/mobile/index.php?act=index&op=index?
    public static final String URL_CONTEXTPATH = PROTOCOL + HOST + APP + "/index.php?";

    /**
     * 首页请求地址
     * http://b2b.htyyao.com/mobile/index.php?act=index&op=index?
     */
    public static final String URL_HOME = URL_CONTEXTPATH + "act=index&op=index";

    /**
     * 专题接口(get)
     * http://b2b.htyyao.com/mobile/index.php?act=index&op=index?act=index&op=special
     */
    public static final String URL_SPECIAL = URL_CONTEXTPATH + "act=index&op=special";

    /**
     * 一级分类请求地址
     */
    public static final String URL_GOODSCLASS = URL_CONTEXTPATH + "act=goods_class";

    /**
     * 商品列表请求地址
     * http://b2b.htyyao.com/mobile/index.php?act=index&op=index?
     */
    public static final String URL_GOODSLIST = URL_CONTEXTPATH + "act=goods&op=goods_list";

    /**
     * 商品详情请求地址
     * http://b2b.htyyao.com/mobile/index.php?act=index&op=index?    act=goods&op=goods_detail
     */
    public static final String URL_GOODSDETAILS = URL_CONTEXTPATH + "act=goods&op=goods_detail";

    /**
     * 登录请求地址
     */
    public static final String URL_MB_LONGIN_MODE = URL_CONTEXTPATH + "act=index&op=mb_login_mode";

    /**
     * 登录请求地址
     */
    public static final String URL_ADDRESS_GET = URL_CONTEXTPATH + "act=area&op=area_list&area_id=";//act=login


    /**
     * 登录请求地址
     */
    public static final String URL_LOGIN = URL_CONTEXTPATH + "act=login&op=loginByUsername";//act=login


    /**
     * 获取所有品牌
     */
    public static final String URL_GET_ALL_BRAND = URL_CONTEXTPATH + "act=brand&op=index";//act=login

//    act=login&op=loginByUsername

    /**
     * 我的商城请求地址
     */
    public static final String URL_MYSTOIRE = URL_CONTEXTPATH + "act=member_index";

    /**
     * 商品详情WEB页面
     */
    public static final String URL_GOODS_DETAILS_WEB = URL_CONTEXTPATH + "act=goods&op=goods_body";

    /**
     * 添加收藏请求地址
     */
    public static final String URL_ADD_FAVORITES = URL_CONTEXTPATH + "act=member_favorites&op=favorites_add";

    /**
     * 收藏列表请求地址
     */
    public static final String URL_FAVORITES_LIST = URL_CONTEXTPATH + "act=member_favorites&op=favorites_list";

    /**
     * 删除收藏请求地址
     */
    public static final String URL_FAVORITES_DELETE = URL_CONTEXTPATH + "act=member_favorites&op=favorites_del";

    /**
     * 地址列表请求地址
     */
    public static final String URL_ADDRESS_LIST = URL_CONTEXTPATH + "act=member_address&op=address_list";

    /**
     * 订单列表请求地址
     */
    public static final String URL_ORDER_LIST = URL_CONTEXTPATH + "act=member_order&op=order_list";

    /**
     * 添加购物车请求地址
     */
    public static final String URL_ADD_CART = URL_CONTEXTPATH + "act=member_cart&op=cart_add";

    /**
     * 购物车列表请求地址
     */
    public static final String URL_CART_LIST = URL_CONTEXTPATH + "act=member_cart&op=cart_list";

    /**
     * 购物车删除请求地址
     */
    public static final String URL_CART_DETELE = URL_CONTEXTPATH + "act=member_cart&op=cart_del";

    /**
     * 购物车修改数量
     */
    public static final String URL_CART_EDIT_QUANTITY = URL_CONTEXTPATH + "act=member_cart&op=cart_edit_quantity";

    /**
     * 注销登出请求地址
     */
    public static final String URL_LOGIN_OUT = URL_CONTEXTPATH + "act=logout";

    /**
     * 地址详细信息请求地址
     */
    public static final String URL_ADDRESS_DETAILS = URL_CONTEXTPATH + "act=member_address&op=address_info";

    /**
     * 地区列表请求地址
     */
    public static final String URL_GET_CITY = URL_CONTEXTPATH + "act=member_address&op=area_list";

    /**
     * 地址编辑请求地址
     */
    public static final String URL_ADDRESS_EDIT = URL_CONTEXTPATH + "act=member_address&op=address_edit";

    /**
     * 地址删除请求地址
     */
    public static final String URL_ADDRESS_DETELE = URL_CONTEXTPATH + "act=member_address&op=address_del";

    /**
     * 地址添加请求地址
     */
    public static final String URL_ADDRESS_ADD = URL_CONTEXTPATH + "act=member_address&op=address_add";

    /**
     * 在线帮助请求地址
     */
    public static final String URL_HELP = PROTOCOL + HOST + APP + "/help.html";

    /**
     * 购买步骤1请求地址
     */
    public static final String URL_BUY_STEP1 = URL_CONTEXTPATH + "act=member_buy&op=buy_step1";

    /**
     * 购买步骤2请求地址
     */
    public static final String URL_BUY_STEP2 = URL_CONTEXTPATH + "act=member_buy&op=buy_step2";

    /**
     * 发票列表请求地址
     */
    public static final String URL_INVOICE_LIST = URL_CONTEXTPATH + "act=member_invoice&op=invoice_list";

    /**
     * 发票内容列表请求地址
     */
    public static final String URL_INVOICE_CONTEXT_LIST = URL_CONTEXTPATH + "act=member_invoice&op=invoice_content_list";

    /**
     * 添加发票请求地址
     */
    public static final String URL_INVOICE_ADD = URL_CONTEXTPATH + "act=member_invoice&op=invoice_add";

    /**
     * 删除发票请求地址
     */
    public static final String URL_INVOICE_DELETE = URL_CONTEXTPATH + "act=member_invoice&op=invoice_del";

    /**
     * 更换收货地址请求地址
     */
    public static final String URL_UPDATE_ADDRESS = URL_CONTEXTPATH + "act=member_buy&op=change_address";

    /**
     * 验证密码请求地址
     */
    public static final String URL_CHECK_PASSWORD = URL_CONTEXTPATH + "act=member_buy&op=check_password";

    /**
     * 订单取消(未付款)请求地址
     */
    public static final String URL_ORDER_CANCEL = URL_CONTEXTPATH + "act=member_order&op=order_cancel";

    /**
     * 订单删除请求地址
     */
    public static final String URL_ORDER_DELETE = URL_CONTEXTPATH + "act=member_order&op=order_delete";

    /**
     * 订单确认收货请求地址
     */
    public static final String URL_ORDER_RECEIVE = URL_CONTEXTPATH + "act=member_order&op=order_receive";

    /**
     * 订单付款请求地址
     */
    public static final String URL_ORDER_PAYMENT = URL_CONTEXTPATH + "act=member_payment&op=pay";

    /**
     * 收藏添加
     */
    public static final String URL_ADD_FAV = URL_CONTEXTPATH + "act=member_favorites&op=favorites_add";

    /**
     * 收藏删除
     */
    public static final String URL_DELETE_FAV = URL_CONTEXTPATH + "act=member_favorites&op=favorites_del";

    /**
     * 支付成功调用
     */
    public static final String URL_PAYFOR_SUCCES = URL_CONTEXTPATH + "act=payment&op=mbreturn";

    /**
     * 虚拟订单付款请求地址
     */
    public static final String URL_VIRTUAL_ORDER_PAYMENT = URL_CONTEXTPATH + "act=member_payment&op=vr_pay";

    /**
     * 可用支付方式列表
     */
    public static final String URL_ORDER_PAYMENT_LIST = URL_CONTEXTPATH + "act=member_payment&op=payment_list";

    /**
     * 虚拟订单可用码列表
     */
    public static final String URL_MEMBER_VR_ODER = URL_CONTEXTPATH + "act=member_vr_order&op=indate_code_list";

    /**
     * 注册
     */
    public static final String URL_REGISTER = URL_CONTEXTPATH + "act=login&op=register";

    /**
     * 手机注册
     */
    public static final String URL_REGISTER_BY_CAPTCHA = URL_CONTEXTPATH + "act=login&op=registeBycaptcha";


    /**
     * 获取验证码
     */

    public static final String URL_GET_CAPTCHA = URL_CONTEXTPATH + "act=login&op=get_captcha";

    /**
     * 手机找回密码
     */

    public static final String URL_FIND_PAS = URL_CONTEXTPATH + "act=login&op=find_password";

    /**
     * 验证码登录
     */
    public static final String URL_LONGIN_BY_CAPTCHA = URL_CONTEXTPATH + "act=login&op=loginByCaptcha";

    /**
     * 验证验证码是否正确
     */
    public static final String URL_CHECK_CAPTCHA = URL_CONTEXTPATH + "act=login&op=check_captcha";

    /**
     * 我的抵用券
     */
    public static final String URL_VOUCHER = URL_CONTEXTPATH + "act=member_voucher&op=voucher_list";

    /**
     * 意见反馈
     */
    public static final String URL_FEEDBACK_ADD = URL_CONTEXTPATH + "act=member_feedback&op=feedback_add";

    /**
     * 版本更新
     */
    public static final String URL_VERSION_UPDATE = URL_CONTEXTPATH + "act=index&op=apk_version";

    /**
     * 物流查询
     */
    public static final String URL_QUERY_DELIVER = URL_CONTEXTPATH + "act=member_order&op=search_deliver";

    /**
     * 会员聊天--webivew
     * 请求参数
     * key 当前登录令牌
     */
    public static final String URL_MEMBER_CHAT = URL_CONTEXTPATH + "act=member_chat&op=get_node_info&key=";

    /**
     * 会员聊天--最近联系人列表
     * 调用接口(post) index.php?act=member_chat&op=get_user_list 请求参数
     * key 当前登录令牌 n 查询会员数量
     */
    public static final String URL_MEMBER_CHAT_GET_USER_LIST = URL_CONTEXTPATH + "act=member_chat&op=get_user_list";

    /**
     * 会员聊天--发消息
     * 调用接口(post) index.php?act=member_chat&op=send_msg
     * 请求参数
     * key 当前登录令牌
     * t_id  接收消息会员编号
     * t_name 接收消息会员帐号
     * t_msg 消息
     */
    public static final String URL_MEMBER_CHAT_SEND_MSG = URL_CONTEXTPATH + "act=member_chat&op=send_msg";

    /**
     * 会员聊天--会员信息 调用接口(get)
     * index.php?act=member_chat&op=get_info
     * 请求参数
     * key 当前登录令牌
     * u_id  会员编号
     * t 查询类型('member_id','member_name','store_id','member')
     */
    public static final String URL_MEMBER_CHAT_GET_USER_INFO = URL_CONTEXTPATH + "act=member_chat&op=get_info";

    /**
     * 会员聊天--聊天记录查询
     * 调用接口(post)
     * index.php?act=member_chat&op=get_chat_log
     * 请求参数
     * key 当前登录令牌
     * t_id 接收消息会员编号
     * t 查询天数('7','15','30')
     * page 每页显示数量，可为空，默认为5个
     */
    public static final String URL_MEMBER_CHAT_GET_LOG_INFO = URL_CONTEXTPATH + "act=member_chat&op=get_chat_log";

    /**
     * 我的好友--删除好友
     */
    public static final String URL_IM_FRIENDS_DELETE = URL_CONTEXTPATH + "act=member_snsfriend&op=friend_del";

    /**
     * 我的好友--会员资料
     */
    public static final String URL_IM_MEMBER_INFO = URL_CONTEXTPATH + "act=member_chat&op=get_info";

    /**
     * 虚拟购买第二步
     */
    public static final String URL_MEMBER_VR_BUY = URL_CONTEXTPATH + "act=member_vr_buy&op=buy_step2";

    /**
     * 虚拟购买第三步
     */
    public static final String URL_MEMBER_VR_BUY3 = URL_CONTEXTPATH + "act=member_vr_buy&op=buy_step3";

    /**
     * 虚拟订单列表
     */
    public static final String URL_MEMBER_VR_ORDER = URL_CONTEXTPATH + "act=member_vr_order&op=order_list";

    /**
     * 虚拟订单取消
     */
    public static final String URL_MEMBER_VR_ORDER_CANCEL = URL_CONTEXTPATH + "act=member_vr_order&op=order_cancel";

    /**
     * 店铺信息
     */
    public static final String URL_STORE_INFO = URL_CONTEXTPATH + "act=store&op=store_info";

    /**
     * 店铺商品分类
     */
    public static final String URL_STORE_GOODS_CLASS = URL_CONTEXTPATH + "act=store&op=store_goods_class";

    /**
     * 店铺商品列表
     */
    public static final String URL_STORE_GOODS_LIST = URL_CONTEXTPATH + "act=store&op=store_goods";

    /**
     * 店铺收藏列表
     */
    public static final String URL_STORE_FAV_LIST = URL_CONTEXTPATH + "act=member_favorites_store&op=favorites_list";

    /**
     * 店铺收藏删除
     */
    public static final String URL_STORE_DELETE = URL_CONTEXTPATH + "act=member_favorites_store&op=favorites_del";

    /**
     * 店铺新增收藏
     */
    public static final String URL_STORE_ADD_FAVORITES = URL_CONTEXTPATH + "act=member_favorites_store&op=favorites_add";

    /**
     * 获取微信参数
     */
    public static final String URL_MEMBER_WX_PAYMENT = URL_CONTEXTPATH + "act=member_payment&op=wx_app_pay3";

    /**
     * 获取微信参数
     */
    public static final String URL_MEMBER_WX_VPAYMENT = URL_CONTEXTPATH + "act=member_payment&op=wx_app_vr_pay3";

    /**
     * 主页广告轮播图参数
     */
    public static final int AD_HEIGTH = 240;    //高度
    public static final int AD_WHIDE = 640;     //宽度

    //调试控制台日志
    public static final boolean DEBUG = true;
    //是否打印log
    public static final boolean LOG_ENABLE = DEBUG ? true : false;
    //是否弹出测试toast
    public static final boolean TOAST_ENABLE = DEBUG ? true : false;
    //是否将LOG写到sd上
    public static final boolean LOG2SD_ENABLE = false;


}

