package com.langhua.yicor.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private List<Article> articleList;
    private String APPID = "44aaca9931fa2c23b01071a474a081be";
    private Article_bmob ab = new Article_bmob();
    private SwipeRefreshLayout swipeRefreshLayout;
    private BmobUser bmobUser;
    private SignUpAndLogin signUpAndLogin;

    public RecyclerViewAdapter adapter;

    ArrayList<String> my_list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bmob.initialize(this, APPID);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //adapter.notifyDataSetChanged();
                //swipeRefreshLayout.setRefreshing(false);
            }
        });

        initData();
        adapter = new RecyclerViewAdapter(articleList, MainActivity.this);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));

        /*
        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //Snackbar.make(v, "Test Item Click", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();

                Intent intent = new Intent();
            }
        });
        */

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                //        .setAction("Action", null).show();
                //saveArticle("Test_title", "Test_desc");

                //Intent intent = new Intent(MainActivity.this, WriteArticle.class);
                //startActivity(intent);
                signUpAndLogin = new SignUpAndLogin();

                bmobUser = bmobUser.getCurrentUser();
                if (bmobUser != null) {
                    //Snackbar.make(view, "已登录，可以使用", Snackbar.LENGTH_LONG)
                    //        .setAction("Action", null).show();
                    showSignUpDialog();
                } else {
                    //signUpAndLogin.UserSignUp("NickName", "123456");
                    showSignUpDialog();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void initData() {

        articleList = new ArrayList<>();

        //空指针，因为先执行该函数再生成 adapter 的对象
        //adapter.notifyDataSetChanged();

        queryArticle();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.item_login) {
            //View v = getWindow().getDecorView();
            // Handle the camera action
            if (isLogin()) {
                Snackbar.make(recyclerView, "已登录", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } else if (id == R.id.item_signup) {
            if (isLogin()) {
                Snackbar.make(recyclerView, "已登录", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void saveArticle(String title, String desc) {
        ab.setTitle(title);
        ab.setDesc(desc);

        ab.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {

                if(e == null) {
                    //Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                } else {
                    //Toast.makeText(MainActivity.this, "fail: " + e, Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });

    }

    public void queryArticle() {
        articleList = new ArrayList<>();

        my_list = new ArrayList<String>();

        BmobQuery<Article_bmob> bmobQuery = new BmobQuery<Article_bmob>();

        bmobQuery.findObjects(new FindListener<Article_bmob>() {
            @Override
            public void done(List<Article_bmob> list, BmobException e) {

                if (e == null) for (Article_bmob ab : list) {
                    //my_list.add(ab.getTitle());
                    articleList.add(new Article(ab.getTitle(), ab.getDesc()));
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(MainActivity.this, "失败: " + e, Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void showSignUpDialog() {
        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View view = inflater.inflate(R.layout.dialog_signup, null);

        new AlertDialog.Builder(MainActivity.this).setTitle("注册").setView(view)
                .setPositiveButton("注册", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText edt_nick = (EditText) view.findViewById(R.id.user_nick);
                        EditText edt_pwd = (EditText) view.findViewById(R.id.user_pwd);

                        String user_nick, user_pwd;

                        user_nick = edt_nick.getText().toString();
                        user_pwd = edt_pwd.getText().toString();

                        signUpAndLogin.UserSignUp(user_nick, user_pwd);

                        Toast.makeText(MainActivity.this, "注册成功，用户名：" + user_nick + " 密码：" + user_pwd,
                                Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();
    }

    private boolean isLogin() {
        boolean flag;
        bmobUser = bmobUser.getCurrentUser();
        if (bmobUser != null) {
            flag = true;
        } else {
            flag = false;
        }

        return flag;
    }

}
