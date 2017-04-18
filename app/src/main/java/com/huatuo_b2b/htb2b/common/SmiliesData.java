package com.huatuo_b2b.htb2b.common;

import com.huatuo_b2b.htb2b.R;
import com.huatuo_b2b.htb2b.bean.SmiliesList;

import java.util.ArrayList;

/**
 * 记录表情图片数据
 *
 * @author KingKong-HE
 * @Time 2015-2-5
 * @Email KingKong@QQ.COM
 */
public final class SmiliesData {

    public static final ArrayList<SmiliesList> smiliesLists = new ArrayList<SmiliesList>();

    static {
        smiliesLists.add(new SmiliesList("微笑", ":smile:", R.mipmap.smile));
        smiliesLists.add(new SmiliesList("难过", ":sad:", R.mipmap.sad));
        smiliesLists.add(new SmiliesList("呲牙", ":biggrin:", R.mipmap.biggrin));
        smiliesLists.add(new SmiliesList("大哭", ":cry:", R.mipmap.cry));
        smiliesLists.add(new SmiliesList("发怒", ":huffy:", R.mipmap.huffy));
        smiliesLists.add(new SmiliesList("惊讶", ":shocked:", R.mipmap.shocked));
        smiliesLists.add(new SmiliesList("调皮", ":tongue:", R.mipmap.tongue));
        smiliesLists.add(new SmiliesList("害羞", ":shy:", R.mipmap.shy));
        smiliesLists.add(new SmiliesList("偷笑", ":titter:", R.mipmap.titter));
        smiliesLists.add(new SmiliesList("流汗", ":sweat:", R.mipmap.sweat));
        smiliesLists.add(new SmiliesList("抓狂", ":mad:", R.mipmap.mad));
        smiliesLists.add(new SmiliesList("阴险", ":lol:", R.mipmap.lol));
        smiliesLists.add(new SmiliesList("可爱", ":loveliness:", R.mipmap.loveliness));
        smiliesLists.add(new SmiliesList("惊恐", ":funk:", R.mipmap.funk));
        smiliesLists.add(new SmiliesList("咒骂", ":curse:", R.mipmap.curse));
        smiliesLists.add(new SmiliesList("晕", ":dizzy:", R.mipmap.dizzy));
        smiliesLists.add(new SmiliesList("闭嘴", ":shutup:", R.mipmap.shutup));
        smiliesLists.add(new SmiliesList("睡", ":sleepy:", R.mipmap.sleepy));
        smiliesLists.add(new SmiliesList("拥抱", ":hug:", R.mipmap.hug));
        smiliesLists.add(new SmiliesList("胜利", ":victory:", R.mipmap.victory));
        smiliesLists.add(new SmiliesList("太阳", ":sun:", R.mipmap.sun));
        smiliesLists.add(new SmiliesList("月亮", ":moon:", R.mipmap.moon));
        smiliesLists.add(new SmiliesList("示爱", ":kiss:", R.mipmap.kiss));
        smiliesLists.add(new SmiliesList("握手", ":handshake:", R.mipmap.handshake));
    }

}
