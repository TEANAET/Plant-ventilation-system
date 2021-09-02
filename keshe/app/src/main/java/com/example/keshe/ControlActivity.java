package com.example.keshe;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import base.BaseActivity;
import mysqllink.DBUtils;

public class ControlActivity extends BaseActivity {

    public TextView kai_show;
    private EditText thre_val;
    private Button on_bt,off_bt,confr_bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_control);
        init();
        setListener();
        setListner1();
        setListner2();
    } // onCreate()

    private void init(){
        kai_show = findViewById(R.id.status_text);
        thre_val = findViewById(R.id.thr_value_et);
        on_bt =findViewById(R.id.ON_bt);
        off_bt = findViewById(R.id.OFF_bt);
        confr_bt = findViewById(R.id.confirm_btn);
    }

    private void setListener(){
        on_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBUtils.Insert_on();
                    }
                }).start();
                kai_show.setTextColor(Color.RED);
                kai_show.setText("开");


            }
        });
    }

    private void setListner1(){
        off_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBUtils.Insert_off();
                    }
                }).start();
                kai_show.setTextColor(Color.BLACK);
                kai_show.setText("关");
            }
        });
    }
    private void setListner2(){
        confr_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String  value = thre_val.getText().toString().trim();
                if(value.equals("") ){
                    Toast.makeText(ControlActivity.this,"阀值不能为空！",Toast.LENGTH_SHORT).show();
                }
                else {
                    if( kai_show.getText()=="开"){
                        Toast.makeText(ControlActivity.this,"当前除湿机为手动开状态，无法修改阀值",Toast.LENGTH_SHORT).show();

                    }
                    else{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                DBUtils.Insert_value(value);
                            }
                        }).start();
                        Toast.makeText(ControlActivity.this,"设置成功，当前湿度的阀值为"+value+"%",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    // 请注意本方法内容的变化
    public void onNavButtonsTapped(View v) {
        switch (v.getId()) {
            case R.id.btnNavHome:
                open(HomeActivity.class);
                break; // case R.id.btnNavHome

            case R.id.btnNavSettings:
                open(ShowActivity.class);
                break; // case R.id.btnNavSettings
        } // switch (v.getId())
    } // onNavButtonsTapped()

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
} // ControlActivity Class

// E.O.F