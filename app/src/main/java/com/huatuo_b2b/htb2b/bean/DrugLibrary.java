package com.huatuo_b2b.htb2b.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by jinguo on 2016/4/18.
 */
public class DrugLibrary implements Serializable {

    private String drug_name;
    private String drug_common_name;
    private String drug_spec;
    private String drug_dosage;
    private String approval_number;
    private String product_company;
    private String adaptation_disease;
    private String drug_usage;
    private String untoward_effect;
    private String attention;


    public static class Attr {
        public static final String DRUG_NAME = "drug_name";
        public static final String DRUG_COMMON_NAME = "drug_common_name";
        public static final String DRUG_SPEC = "drug_spec";//规格
        public static final String DRUG_DOSAGE = "drug_dosage";//剂型
        public static final String APPROVAL_NUMBER = "approval_number";// 国药准字
        public static final String PRODUCT_COMPANY = "product_company";//生产厂家
        public static final String ADAPTATION_DISEASE = "adaptation_disease";
        public static final String DRUG_USAGE = "drug_usage";
        public static final String UNTOWARD_EFFECT = "untoward_effect";
        public static final String ATTENTION = "attention";
    }


    public DrugLibrary() {
    }

    public DrugLibrary(String drug_name, String drug_common_name, String drug_spec, String drug_dosage,
                       String approval_number, String product_company, String adaptation_disease,
                       String drug_usage, String untoward_effect, String attention) {

        super();

        this.drug_name = drug_name;
        this.drug_common_name = drug_common_name;
        this.drug_spec = drug_spec;
        this.drug_dosage = drug_dosage;
        this.approval_number = approval_number;
        this.product_company = product_company;
        this.adaptation_disease = adaptation_disease;
        this.drug_usage = drug_usage;
        this.untoward_effect = untoward_effect;
        this.attention = attention;
    }


    public static DrugLibrary newInstanceList(String json) {
        DrugLibrary bean = null;
        try {
            JSONObject obj = new JSONObject(json);
            if (obj.length() > 0) {

                String drug_name = obj.optString(Attr.DRUG_NAME);
                String drug_common_name = obj.optString(Attr.DRUG_COMMON_NAME);
                String drug_spec = obj.optString(Attr.DRUG_SPEC);
                String drug_dosage = obj.optString(Attr.DRUG_DOSAGE);
                String approval_number = obj.optString(Attr.APPROVAL_NUMBER);
                String product_company = obj.optString(Attr.PRODUCT_COMPANY);
                String adaptation_disease = obj.optString(Attr.ADAPTATION_DISEASE);
                String drug_usage = obj.optString(Attr.DRUG_USAGE);
                String untoward_effect = obj.optString(Attr.UNTOWARD_EFFECT);
                String attention = obj.optString(Attr.ATTENTION);

                bean = new DrugLibrary(drug_name, drug_common_name, drug_spec, drug_dosage,
                        approval_number, product_company, adaptation_disease, drug_usage, untoward_effect, attention);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }

    public String getDrug_common_name() {
        return drug_common_name;
    }

    public void setDrug_common_name(String drug_common_name) {
        this.drug_common_name = drug_common_name;
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

    public String getAdaptation_disease() {
        return adaptation_disease;
    }

    public void setAdaptation_disease(String adaptation_disease) {
        this.adaptation_disease = adaptation_disease;
    }

    public String getDrug_usage() {
        return drug_usage;
    }

    public void setDrug_usage(String drug_usage) {
        this.drug_usage = drug_usage;
    }

    public String getUntoward_effect() {
        return untoward_effect;
    }

    public void setUntoward_effect(String untoward_effect) {
        this.untoward_effect = untoward_effect;
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention;
    }

    @Override
    public String toString() {
        return "DrugLibrary{" +
                "drug_name='" + drug_name + '\'' +
                ", drug_common_name='" + drug_common_name + '\'' +
                ", drug_spec='" + drug_spec + '\'' +
                ", drug_dosage='" + drug_dosage + '\'' +
                ", approval_number='" + approval_number + '\'' +
                ", product_company='" + product_company + '\'' +
                ", adaptation_disease='" + adaptation_disease + '\'' +
                ", drug_usage='" + drug_usage + '\'' +
                ", untoward_effect='" + untoward_effect + '\'' +
                ", attention='" + attention + '\'' +
                '}';
    }
}
