package com.langhua.yicor.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

/**
 * Created by yicor on 2016/8/25.
 */

public class ArticleActivity extends AppCompatActivity {
    TextView tv_title, tv_desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        tv_title = (TextView) findViewById(R.id.art_title);
        tv_desc = (TextView) findViewById(R.id.art_desc);

        Intent intent = getIntent();

        Article item = (Article)  intent.getSerializableExtra("Article");
        tv_title.setText(item.getTitle());
        tv_desc.setText("\t\t" + item.getDesc());
    }
}
