package com.micromap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.micromap.db.DBManager;
import com.micromap.view.BaseActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gotoMapActivity();

        //初始化数据库
        DBManager dbManager = DBManager.getInstance();
        dbManager.installLocalDatabase(this);

        //初始化道路图
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * 跳转到地图界面
     */
    protected void gotoMapActivity(){
        //转到地图界面
        new Handler().postDelayed(new Runnable() {
            public void run() {
                Intent mainIntent = new Intent(MainActivity.this,
                        MapActivity.class);
                MainActivity.this.startActivity(mainIntent);
                MainActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in,
                        android.R.anim.fade_out);
            }
        }, 1500);
    }
}
