package com.langhua.yicor.myapplication;

import cn.bmob.v3.BmobObject;

/**
 * Created by yicor on 2016/8/22.
 */

public class Article_bmob extends BmobObject {
    private String title;
    private String desc;

    public Article_bmob() {

    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public String getTitle() { return title; }
}
