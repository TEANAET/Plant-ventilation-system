package com.example.keshe;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import base.BaseActivity;
import mysqllink.*;

public class HomeActivity extends BaseActivity { // 请注意此处继承的是 BaseActivity 而不是 Activity
    /**
     * onCreate(): 活动创建时触发。
     */

    private TextView temp,hum,timer,thre_val;
    private Button btn_give;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_home);
        init();
        setListener();
    } // onCreate()

    private void init(){
        temp=findViewById(R.id.temp_text);
        hum=findViewById(R.id.hum_text);
        timer=findViewById(R.id.time_text);
        btn_give=findViewById(R.id.give_button);
        thre_val=findViewById(R.id.xian_value);

    }

    /**
     * onNavButtonsTapped(): 点击导航栏上的标签时触发。
     *
     * @param v 点击的按钮对象，用 v.getId() 获取其资源 ID。
     */
    public void onNavButtonsTapped(View v) {
        switch (v.getId()) {
            case R.id.btnNavMessage:
                open(ControlActivity.class);
                break; // case R.id.btnNavMessage

            case R.id.btnNavSettings:
                open(ShowActivity.class);
                break; // case R.id.btnNavSettings
        } // switch (v.getId())
    } // onNavButtonsTapped()*/

    /**
     * onKeyDown(): 按下回退键时触发。
     * 弹出对话框询问是否退出程序。
     */

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showExitDialog();
            return true;
        } // if (keyCode == KeyEvent.KEYCODE_BACK)
        else {
            return super.onKeyDown(keyCode, event);
        } // else
    } // onKeyDown()
    private void setListener() {
        // 按钮点击事件
        btn_give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBUtils.getDeviceList();
                        DBUtils.getther_value();
                    }
                }).start();
                temp.setText(dht.getTem());
                hum.setText(dht.getHum());
                timer.setText(dht.getTime());
                thre_val.setText(thrval.getTher_value());



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btn_give.performClick();
                    }
                },3000);

            }

        });
    }
} // HomeActivity Class