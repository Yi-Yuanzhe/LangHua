package com.langhua.yicor.myapplication;

import cn.bmob.v3.BmobUser;

/**
 * Created by yicor on 2016/8/25.
 */

public class User extends BmobUser {
    private String userName;

     public void setUserName(String userName) {
         this.userName = userName;
     }

    public String getUserName() {
        return this.userName;
    }
}
