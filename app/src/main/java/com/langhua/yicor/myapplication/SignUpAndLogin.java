package com.langhua.yicor.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;

import com.langhua.yicor.myapplication.User;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by yicor on 2016/8/25.
 */

public class SignUpAndLogin {
    BmobUser bmobUser;

    public void UserSignUp(String username, String pwd) {
        bmobUser = new BmobUser();
        bmobUser.setUsername(username);
        bmobUser.setPassword(pwd);

        bmobUser.signUp(new SaveListener<User>() {

            @Override
            public void done(User user, BmobException e) {
                if(e == null) {
                    //Snackbar.make(v, "注册成功", Snackbar.LENGTH_LONG)
                    //       .setAction("Action", null).show();
                } else {
                    //Snackbar.make(v, "注册失败", Snackbar.LENGTH_LONG)
                    //       .setAction("Action", null).show();
                    e.printStackTrace();
                }

            }
        });
    }

    public void UserLogIn(String username, String pwd) {
        bmobUser = new BmobUser();
        bmobUser.setUsername(username);
        bmobUser.setPassword(pwd);

        bmobUser.login(new SaveListener<BmobUser>() {

            @Override
            public void done(BmobUser bmobUser, BmobException e) {
                if (e == null) {
                    //Snackbar.make(v, "登录成功", Snackbar.LENGTH_LONG)
                    //        .setAction("Action", null).show();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

}
