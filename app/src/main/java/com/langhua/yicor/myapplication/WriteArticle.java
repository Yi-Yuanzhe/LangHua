package com.langhua.yicor.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

/**
 * Created by yicor on 2016/8/24.
 */

public class WriteArticle extends AppCompatActivity {
    MainActivity mainActivity = new MainActivity();

    private String art_title, art_desc;
    private EditText edt_title, edt_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_article);

        edt_title = (EditText) findViewById(R.id.w_art_title);
        edt_desc = (EditText) findViewById(R.id.w_art_desc);

        Button btn_send = (Button) findViewById(R.id.send_article);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                art_title = edt_title.getText().toString();
                art_desc = edt_desc.getText().toString();

                if (art_title.isEmpty()) {
                    Snackbar.make(view, "请输入标题", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else if (art_desc.isEmpty()) {
                    Snackbar.make(view, "请输入内容", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    mainActivity.saveArticle(art_title, art_desc);

                    closeInputMethod();
                    mainActivity.adapter.notifyDataSetChanged();

                    finish();
                }

            }
        });

    }

    private void closeInputMethod() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();

        if(isOpen) {
            imm.hideSoftInputFromWindow(edt_desc.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
