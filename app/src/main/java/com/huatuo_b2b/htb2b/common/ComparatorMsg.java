package com.huatuo_b2b.htb2b.common;

import com.huatuo_b2b.htb2b.bean.IMMsgList;

import java.util.Comparator;

/**
 * 聊天数据排序
 *
 * @author KingKong·HE
 * @Time 2014年5月28日 下午5:54:33
 */
public class ComparatorMsg implements Comparator {

    @Override
    public int compare(Object lhs, Object rhs) {
        IMMsgList bean1 = (IMMsgList) lhs;
        IMMsgList bean2 = (IMMsgList) rhs;

        int flag = bean1.getM_id().compareTo(bean2.getM_id());
        return flag;
    }

}
