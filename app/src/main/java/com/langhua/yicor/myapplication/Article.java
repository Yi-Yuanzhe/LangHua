package com.langhua.yicor.myapplication;

import java.io.Serializable;

/**
 * Created by yicor on 2016/8/21.
 */

public class Article implements Serializable {
    private String title;
    private String desc;

    public Article(String title, String desc) {
        this.title = title;
        this.desc = desc;
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
