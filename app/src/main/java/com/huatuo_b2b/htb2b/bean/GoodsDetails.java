package com.huatuo_b2b.htb2b.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 商品详情Bean
 *
 * @author KingKong·HE
 * @Time 2014年1月17日 下午4:44:35
 * @E-mail hjgang@bizpoer.com
 */
public class GoodsDetails {
    public static class Attr {
        public static final String GOODS_NAME = "goods_name";// 商品名称
        public static final String GOODS_JINGLE = "goods_jingle";//商品说明
        public static final String SPEC_NAME = "spec_name";//规格名称
        public static final String SPEC_VALUE = "spec_value";
        public static final String GOODS_ATTR = "goods_attr";
        public static final String GOODS_SPECNAME = "goods_specname";
        public static final String GOODS_PRICE = "goods_price";
        public static final String GOODS_MARKETPRICE = "goods_marketprice";
        public static final String GOODS_COSTPRICE = "goods_costprice";
        public static final String GOODS_DISCOUNT = "goods_discount";
        public static final String GOODS_SERIAL = "goods_serial";
        public static final String TRANSPORT_ID = "transport_id";
        public static final String TRANSPORT_TITLE = "transport_title";
        public static final String GOODS_FREIGHT = "goods_freight";
        public static final String GOODS_VAT = "goods_vat";
        public static final String AREAID_1 = "areaid_1";
        public static final String AREAID_2 = "areaid_2";
        public static final String GOODS_STCIDS = "goods_stcids";
        public static final String PLATEID_TOP = "plateid_top";
        public static final String PLATEID_BOTTOM = "plateid_bottom";
        public static final String GOODS_ID = "goods_id";
        public static final String GOODS_CLICK = "goods_click";
        public static final String GOODS_COLLECT = "goods_collect";
        public static final String GOODS_SALENUM = "goods_salenum";
        public static final String GOODS_SPEC = "goods_spec";
        public static final String GOODS_STORAGE = "goods_storage";//库存
        public static final String COLOR_ID = "color_id";
        public static final String EVALUATION_GOOD_STAR = "evaluation_good_star";
        public static final String EVALUATION_COUNT = "evaluation_count";
        public static final String PROMOTION_TYPE = "promotion_type"; //促销类型 groupbuy-团购 xianshi-限时折扣
        public static final String PROMOTION_PRICE = "promotion_price"; //促销价格
        public static final String UPPER_LIMIT = "lower_limit"; // 最多购买数
        public static final String GOODS_URL = "goods_url";
        public static final String MOBILE_BOBY = "mobile_body";
        public static final String IS_VIRTUAL = "is_virtual";//是否为虚拟商品 1-是 0-否
        public static final String VIRTUAL_INDATE = "virtual_indate";// 虚拟商品有效期
        public static final String VIRTUAL_LIMIT = "virtual_limit";// 虚拟商品购买上限
        public static final String IS_FCODE = "is_fcode";//是否为F码商品 1-是 0-否
        public static final String IS_APPOINT = "is_appoint";//是否是预约商品 1-是 0-否
        public static final String IS_PRESELL = "is_presell";//是否是预售商品 1-是 0-否
        public static final String HAVE_GIFT = "have_gift";//是否拥有赠品
        public static final String VIRTUAL_INVALID_REFUNDTINY = "virtual_invalid_refund";//是否允许过期退款
        public static final String APPOINT_SATEDATE = "appoint_satedate";//预约商品出售时间
        public static final String PRESELL_DELIVERDATE = "presell_deliverdate";//预售发货时间

        public static final String DRUG_SPEC = "drug_spec";//规格
        public static final String DRUG_DOSAGE = "drug_dosage";//剂型
        public static final String APPROVAL_NUMBER = "approval_number";// 国药准字
        public static final String PRODUCT_COMPANY = "product_company";//生产厂家
        public static final String SALE_UNIT = "sale_unit";//销售单位
        public static final String XSKZ_PACKAGE = "xskz_package";//销售控制
        public static final String MID_PACKAGE = "mid_package";//中包
        public static final String BIG_PACKAGE = "big_package";//大包

//			public static final String ;//生产厂家
//			public static final String ;//生产厂家
//			public static final String ;//生产厂家
//			public static final String ;//生产厂家
    }

    private String virtual_invalid_refundtiny;
    private String appoint_satedate;
    private String presell_deliverdate;
    private String goods_name;
    private String goods_jingle;
    private String spec_name;
    private String spec_value;
    private String goods_attr;
    private String goods_specname;
    private String goods_price;
    private String goods_marketprice;
    private String goods_costprice;
    private String goods_discount;
    private String goods_serial;
    private String transport_id;
    private String transport_title;
    private String goods_freight;
    private String goods_vat;
    private String areaid_1;
    private String areaid_2;
    private String goods_stcids;
    private String plateid_top;
    private String plateid_bottom;
    private String goods_id;
    private String goods_click;
    private String goods_collect;
    private String goods_salenum;
    private String goods_spec;
    private String goods_storage;
    private String color_id;
    private String evaluation_good_star;
    private String evaluation_count;
    private String promotion_type;
    private String promotion_price; //促销价格
    private String upper_limit; // 最多购买数
    private String goods_url;
    private String mobile_body;
    private String is_virtual;
    private String virtual_indate;
    private String virtual_limit;
    private String is_fcode;
    private String is_appoint;
    private String is_presell;
    private String have_gift;
    private String drug_spec;//规格
    private String drug_dosage;//剂型
    private String approval_number;
    private String product_company;
    private String sale_unit;
    private String xskz_package;
    private String mid_package;
    private String big_package;

    public GoodsDetails() {
    }

    public GoodsDetails(String virtual_invalid_refundtiny,
                        String appoint_satedate, String presell_deliverdate,
                        String goods_name, String goods_jingle, String spec_name,
                        String spec_value, String goods_attr, String goods_specname,
                        String goods_price, String goods_marketprice,
                        String goods_costprice, String goods_discount,
                        String goods_serial, String transport_id,
                        String transport_title, String goods_freight, String goods_vat,
                        String areaid_1, String areaid_2, String goods_stcids,
                        String plateid_top, String plateid_bottom, String goods_id,
                        String goods_click, String goods_collect, String goods_salenum,
                        String goods_spec, String goods_storage, String color_id,
                        String evaluation_good_star, String evaluation_count,
                        String promotion_type, String promotion_price,
                        String upper_limit, String goods_url, String mobile_body,
                        String is_virtual, String virtual_indate, String virtual_limit,
                        String is_fcode, String is_appoint, String is_presell,
                        String have_gift, String drug_spec, String drug_dosage,
                        String approval_number, String product_company, String sale_unit
            , String xskz_package, String mid_package, String big_package) {
        super();
        this.virtual_invalid_refundtiny = virtual_invalid_refundtiny;
        this.appoint_satedate = appoint_satedate;
        this.presell_deliverdate = presell_deliverdate;
        this.goods_name = goods_name;
        this.goods_jingle = goods_jingle;
        this.spec_name = spec_name;
        this.spec_value = spec_value;
        this.goods_attr = goods_attr;
        this.goods_specname = goods_specname;
        this.goods_price = goods_price;
        this.goods_marketprice = goods_marketprice;
        this.goods_costprice = goods_costprice;
        this.goods_discount = goods_discount;
        this.goods_serial = goods_serial;
        this.transport_id = transport_id;
        this.transport_title = transport_title;
        this.goods_freight = goods_freight;
        this.goods_vat = goods_vat;
        this.areaid_1 = areaid_1;
        this.areaid_2 = areaid_2;
        this.goods_stcids = goods_stcids;
        this.plateid_top = plateid_top;
        this.plateid_bottom = plateid_bottom;
        this.goods_id = goods_id;
        this.goods_click = goods_click;
        this.goods_collect = goods_collect;
        this.goods_salenum = goods_salenum;
        this.goods_spec = goods_spec;
        this.goods_storage = goods_storage;
        this.color_id = color_id;
        this.evaluation_good_star = evaluation_good_star;
        this.evaluation_count = evaluation_count;
        this.promotion_type = promotion_type;
        this.promotion_price = promotion_price;
        this.upper_limit = upper_limit;
        this.goods_url = goods_url;
        this.mobile_body = mobile_body;
        this.is_virtual = is_virtual;
        this.virtual_indate = virtual_indate;
        this.virtual_limit = virtual_limit;
        this.is_fcode = is_fcode;
        this.is_appoint = is_appoint;
        this.is_presell = is_presell;
        this.have_gift = have_gift;
        this.drug_spec = drug_spec;
        this.drug_dosage = drug_dosage;
        this.approval_number = approval_number;
        this.product_company = product_company;
        this.sale_unit = sale_unit;
        this.xskz_package = xskz_package;
        this.mid_package = mid_package;
        this.big_package = big_package;
    }


    public static GoodsDetails newInstanceList(String json) {
        GoodsDetails bean = null;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.length() > 0) {
                String goods_name = obj.optString(Attr.GOODS_NAME);
                String goods_jingle = obj.optString(Attr.GOODS_JINGLE);
                String spec_name = obj.optString(Attr.SPEC_NAME);
                String spec_value = obj.optString(Attr.SPEC_VALUE);
                String goods_attr = obj.optString(Attr.GOODS_ATTR);
                String goods_specname = obj.optString(Attr.GOODS_SPECNAME);
                String goods_price = obj.optString(Attr.GOODS_PRICE);
                String goods_marketprice = obj.optString(Attr.GOODS_MARKETPRICE);
                String goods_costprice = obj.optString(Attr.GOODS_COSTPRICE);
                String goods_discount = obj.optString(Attr.GOODS_DISCOUNT);
                String goods_serial = obj.optString(Attr.GOODS_SERIAL);
                String transport_id = obj.optString(Attr.TRANSPORT_ID);
                String transport_title = obj.optString(Attr.TRANSPORT_TITLE);
                String goods_freight = obj.optString(Attr.GOODS_FREIGHT);
                String goods_vat = obj.optString(Attr.GOODS_VAT);
                String areaid_1 = obj.optString(Attr.AREAID_1);
                String areaid_2 = obj.optString(Attr.AREAID_2);
                String goods_stcids = obj.optString(Attr.GOODS_STCIDS);
                String plateid_top = obj.optString(Attr.PLATEID_TOP);
                String plateid_bottom = obj.optString(Attr.PLATEID_BOTTOM);
                String goods_id = obj.optString(Attr.GOODS_ID);
                String goods_click = obj.optString(Attr.GOODS_CLICK);
                String goods_collect = obj.optString(Attr.GOODS_COLLECT);
                String goods_salenum = obj.optString(Attr.GOODS_SALENUM);
                String goods_spec = obj.optString(Attr.GOODS_SPEC);
                String goods_storage = obj.optString(Attr.GOODS_STORAGE);
                String color_id = obj.optString(Attr.COLOR_ID);
                String evaluation_good_star = obj.optString(Attr.EVALUATION_GOOD_STAR);
                String evaluation_count = obj.optString(Attr.EVALUATION_COUNT);
                String promotion_type = obj.optString(Attr.PROMOTION_TYPE);
                ;
                String promotion_price = obj.optString(Attr.PROMOTION_PRICE);
                ; //促销价格
                String upper_limit = obj.optString(Attr.UPPER_LIMIT);
                ; // 最多购买数
                String goods_url = obj.optString(Attr.GOODS_URL);
                ;
                String mobile_body = obj.optString(Attr.MOBILE_BOBY);
                ;
                String is_virtual = obj.optString(Attr.IS_VIRTUAL);
                ;
                String virtual_indate = obj.optString(Attr.VIRTUAL_INDATE);
                ;
                String virtual_limit = obj.optString(Attr.VIRTUAL_LIMIT);
                ;
                String is_fcode = obj.optString(Attr.IS_FCODE);
                ;
                String is_appoint = obj.optString(Attr.IS_APPOINT);
                ;
                String is_presell = obj.optString(Attr.IS_PRESELL);
                ;
                String have_gift = obj.optString(Attr.HAVE_GIFT);
                ;
                String virtual_invalid_refundtiny = obj.optString(Attr.VIRTUAL_INVALID_REFUNDTINY);
                ;
                String appoint_satedate = obj.optString(Attr.APPOINT_SATEDATE);
                ;
                String presell_deliverdate = obj.optString(Attr.PRESELL_DELIVERDATE);
                String drug_spec = obj.optString(Attr.DRUG_SPEC);
                String drug_dosage = obj.optString(Attr.DRUG_DOSAGE);
                String approval_number = obj.optString(Attr.APPROVAL_NUMBER);
                String product_company = obj.optString(Attr.PRODUCT_COMPANY);
                String sale_unit = obj.optString(Attr.SALE_UNIT);
                String xskz_package = obj.optString(Attr.XSKZ_PACKAGE);
                String mid_package = obj.optString(Attr.MID_PACKAGE);
                String big_package = obj.optString(Attr.BIG_PACKAGE);
                bean = new GoodsDetails(virtual_invalid_refundtiny, appoint_satedate,
                        presell_deliverdate, goods_name, goods_jingle, spec_name,
                        spec_value, goods_attr, goods_specname, goods_price,
                        goods_marketprice, goods_costprice, goods_discount,
                        goods_serial, transport_id, transport_title, goods_freight,
                        goods_vat, areaid_1, areaid_2, goods_stcids, plateid_top,
                        plateid_bottom, goods_id, goods_click, goods_collect,
                        goods_salenum, goods_spec, goods_storage, color_id,
                        evaluation_good_star, evaluation_count, promotion_type,
                        promotion_price, upper_limit, goods_url, mobile_body,
                        is_virtual, virtual_indate, virtual_limit, is_fcode,
                        is_appoint, is_presell, have_gift, drug_spec, drug_dosage,
                        approval_number, product_company, sale_unit, xskz_package, mid_package, big_package);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public String getVirtual_invalid_refundtiny() {
        return virtual_invalid_refundtiny;
    }

    public void setVirtual_invalid_refundtiny(String virtual_invalid_refundtiny) {
        this.virtual_invalid_refundtiny = virtual_invalid_refundtiny;
    }

    public String getAppoint_satedate() {
        return appoint_satedate;
    }

    public void setAppoint_satedate(String appoint_satedate) {
        this.appoint_satedate = appoint_satedate;
    }

    public String getPresell_deliverdate() {
        return presell_deliverdate;
    }

    public void setPresell_deliverdate(String presell_deliverdate) {
        this.presell_deliverdate = presell_deliverdate;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getGoods_jingle() {
        //liubw
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@  goods_jingle:" + goods_jingle);

//goods_jingle:华佗医药测试商品{商品名}{通用名}{品牌}{主治}{生产厂家}

        return goods_jingle;
    }

    public void setGoods_jingle(String goods_jingle) {
        this.goods_jingle = goods_jingle;
    }

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getSpec_value() {
        return spec_value;
    }

    public void setSpec_value(String spec_value) {
        this.spec_value = spec_value;
    }

    public String getGoods_attr() {
        return goods_attr;
    }

    public void setGoods_attr(String goods_attr) {
        this.goods_attr = goods_attr;
    }

    public String getGoods_specname() {
        return goods_specname;
    }

    public void setGoods_specname(String goods_specname) {
        this.goods_specname = goods_specname;
    }

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_marketprice() {
        return goods_marketprice;
    }

    public void setGoods_marketprice(String goods_marketprice) {
        this.goods_marketprice = goods_marketprice;
    }

    public String getGoods_costprice() {
        return goods_costprice;
    }

    public void setGoods_costprice(String goods_costprice) {
        this.goods_costprice = goods_costprice;
    }

    public String getGoods_discount() {
        return goods_discount;
    }

    public void setGoods_discount(String goods_discount) {
        this.goods_discount = goods_discount;
    }

    public String getGoods_serial() {
        return goods_serial;
    }

    public void setGoods_serial(String goods_serial) {
        this.goods_serial = goods_serial;
    }

    public String getTransport_id() {
        return transport_id;
    }

    public void setTransport_id(String transport_id) {
        this.transport_id = transport_id;
    }

    public String getTransport_title() {
        return transport_title;
    }

    public void setTransport_title(String transport_title) {
        this.transport_title = transport_title;
    }

    public String getGoods_freight() {
        return goods_freight;
    }

    public void setGoods_freight(String goods_freight) {
        this.goods_freight = goods_freight;
    }

    public String getGoods_vat() {
        return goods_vat;
    }

    public void setGoods_vat(String goods_vat) {
        this.goods_vat = goods_vat;
    }

    public String getAreaid_1() {
        return areaid_1;
    }

    public void setAreaid_1(String areaid_1) {
        this.areaid_1 = areaid_1;
    }

    public String getAreaid_2() {
        return areaid_2;
    }

    public void setAreaid_2(String areaid_2) {
        this.areaid_2 = areaid_2;
    }

    public String getGoods_stcids() {
        return goods_stcids;
    }

    public void setGoods_stcids(String goods_stcids) {
        this.goods_stcids = goods_stcids;
    }

    public String getPlateid_top() {
        return plateid_top;
    }

    public void setPlateid_top(String plateid_top) {
        this.plateid_top = plateid_top;
    }

    public String getPlateid_bottom() {
        return plateid_bottom;
    }

    public void setPlateid_bottom(String plateid_bottom) {
        this.plateid_bottom = plateid_bottom;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_click() {
        return goods_click;
    }

    public void setGoods_click(String goods_click) {
        this.goods_click = goods_click;
    }

    public String getGoods_collect() {
        return goods_collect;
    }

    public void setGoods_collect(String goods_collect) {
        this.goods_collect = goods_collect;
    }

    public String getGoods_salenum() {
        return goods_salenum;
    }

    public void setGoods_salenum(String goods_salenum) {
        this.goods_salenum = goods_salenum;
    }

    public String getGoods_spec() {
        return goods_spec;
    }

    public void setGoods_spec(String goods_spec) {
        this.goods_spec = goods_spec;
    }

    public String getGoods_storage() {
        return goods_storage;
    }

    public void setGoods_storage(String goods_storage) {
        this.goods_storage = goods_storage;
    }

    public String getColor_id() {
        return color_id;
    }

    public void setColor_id(String color_id) {
        this.color_id = color_id;
    }

    public String getEvaluation_good_star() {
        return evaluation_good_star;
    }

    public void setEvaluation_good_star(String evaluation_good_star) {
        this.evaluation_good_star = evaluation_good_star;
    }

    public String getEvaluation_count() {
        return evaluation_count;
    }

    public void setEvaluation_count(String evaluation_count) {
        this.evaluation_count = evaluation_count;
    }

    public String getPromotion_type() {
        return promotion_type;
    }

    public void setPromotion_type(String promotion_type) {
        this.promotion_type = promotion_type;
    }

    public String getPromotion_price() {
        return promotion_price;
    }

    public void setPromotion_price(String promotion_price) {
        this.promotion_price = promotion_price;
    }

    public String getUpper_limit() {
        return upper_limit;
    }

    public void setUpper_limit(String upper_limit) {
        this.upper_limit = upper_limit;
    }

    public String getGoods_url() {
        return goods_url;
    }

    public void setGoods_url(String goods_url) {
        this.goods_url = goods_url;
    }

    public String getMobile_body() {
        return mobile_body;
    }

    public void setMobile_body(String mobile_body) {
        this.mobile_body = mobile_body;
    }

    public String getIs_virtual() {
        return is_virtual;
    }

    public void setIs_virtual(String is_virtual) {
        this.is_virtual = is_virtual;
    }

    public String getVirtual_indate() {
        return virtual_indate;
    }

    public void setVirtual_indate(String virtual_indate) {
        this.virtual_indate = virtual_indate;
    }

    public String getVirtual_limit() {
        return virtual_limit;
    }

    public void setVirtual_limit(String virtual_limit) {
        this.virtual_limit = virtual_limit;
    }

    public String getIs_fcode() {
        return is_fcode;
    }

    public void setIs_fcode(String is_fcode) {
        this.is_fcode = is_fcode;
    }

    public String getIs_appoint() {
        return is_appoint;
    }

    public void setIs_appoint(String is_appoint) {
        this.is_appoint = is_appoint;
    }

    public String getIs_presell() {
        return is_presell;
    }

    public void setIs_presell(String is_presell) {
        this.is_presell = is_presell;
    }

    public String getHave_gift() {
        return have_gift;
    }

    public void setHave_gift(String have_gift) {
        this.have_gift = have_gift;
    }


    public String getDrug_spec() {
        return drug_spec;
    }

    public void setDrug_spec(String drug_spec) {
        this.drug_spec = drug_spec;
    }


    public String getDrug_dosage() {
        return drug_dosage;
    }

    public void setDrug_dosage(String drug_dosage) {
        this.drug_dosage = drug_dosage;
    }

    public String getApproval_number() {
        return approval_number;
    }

    public void setApproval_number(String approval_number) {
        this.approval_number = approval_number;
    }

    public String getProduct_company() {
        return product_company;
    }

    public void setProduct_company(String product_company) {
        this.product_company = product_company;
    }

    public String getSale_unit() {
        return sale_unit;
    }

    public void setSale_unit(String sale_unit) {
        this.sale_unit = sale_unit;
    }

    public String getXskz_package() {
        return xskz_package;
    }

    public void setXskz_package(String xskz_package) {
        this.xskz_package = xskz_package;
    }

    public String getMid_package() {
        return mid_package;
    }

    public void setMid_package(String mid_package) {
        this.mid_package = mid_package;
    }

    public String getBig_package() {
        return big_package;
    }

    public void setBig_package(String big_package) {
        this.big_package = big_package;
    }

    @Override
    public String toString() {
        return "GoodsDetails{" +
                "virtual_invalid_refundtiny='" + virtual_invalid_refundtiny + '\'' +
                ", appoint_satedate='" + appoint_satedate + '\'' +
                ", presell_deliverdate='" + presell_deliverdate + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", goods_jingle='" + goods_jingle + '\'' +
                ", spec_name='" + spec_name + '\'' +
                ", spec_value='" + spec_value + '\'' +
                ", goods_attr='" + goods_attr + '\'' +
                ", goods_specname='" + goods_specname + '\'' +
                ", goods_price='" + goods_price + '\'' +
                ", goods_marketprice='" + goods_marketprice + '\'' +
                ", goods_costprice='" + goods_costprice + '\'' +
                ", goods_discount='" + goods_discount + '\'' +
                ", goods_serial='" + goods_serial + '\'' +
                ", transport_id='" + transport_id + '\'' +
                ", transport_title='" + transport_title + '\'' +
                ", goods_freight='" + goods_freight + '\'' +
                ", goods_vat='" + goods_vat + '\'' +
                ", areaid_1='" + areaid_1 + '\'' +
                ", areaid_2='" + areaid_2 + '\'' +
                ", goods_stcids='" + goods_stcids + '\'' +
                ", plateid_top='" + plateid_top + '\'' +
                ", plateid_bottom='" + plateid_bottom + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", goods_click='" + goods_click + '\'' +
                ", goods_collect='" + goods_collect + '\'' +
                ", goods_salenum='" + goods_salenum + '\'' +
                ", goods_spec='" + goods_spec + '\'' +
                ", goods_storage='" + goods_storage + '\'' +
                ", color_id='" + color_id + '\'' +
                ", evaluation_good_star='" + evaluation_good_star + '\'' +
                ", evaluation_count='" + evaluation_count + '\'' +
                ", promotion_type='" + promotion_type + '\'' +
                ", promotion_price='" + promotion_price + '\'' +
                ", upper_limit='" + upper_limit + '\'' +
                ", goods_url='" + goods_url + '\'' +
                ", mobile_body='" + mobile_body + '\'' +
                ", is_virtual='" + is_virtual + '\'' +
                ", virtual_indate='" + virtual_indate + '\'' +
                ", virtual_limit='" + virtual_limit + '\'' +
                ", is_fcode='" + is_fcode + '\'' +
                ", is_appoint='" + is_appoint + '\'' +
                ", is_presell='" + is_presell + '\'' +
                ", have_gift='" + have_gift + '\'' +
                ", drug_spec='" + drug_spec + '\'' +
                ", drug_dosage='" + drug_dosage + '\'' +
                ", approval_number='" + approval_number + '\'' +
                ", product_company='" + product_company + '\'' +
                ", sale_unit='" + sale_unit + '\'' +
                ", xskz_package='" + xskz_package + '\'' +
                ", mid_package='" + mid_package + '\'' +
                ", big_package='" + big_package + '\'' +
                '}';
    }
}
