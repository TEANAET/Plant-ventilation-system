package com.example.keshe;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import base.BaseActivity;
import mysqllink.DBUtils;

import static android.content.ContentValues.TAG;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    private EditText user_et;
    private  EditText pas_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();

    }


    //初始化
    private void init(){
        user_et=findViewById(R.id.username);
        pas_et=findViewById(R.id.password);
        Button login=findViewById(R.id.login);
        Button regist=findViewById(R.id.regist);
        login.setOnClickListener(this);
        regist.setOnClickListener(this);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //登录按钮
            case R.id.login:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String n=user_et.getText().toString().trim();
                        String psw= pas_et.getText().toString().trim();

                        if(n.equals("")||psw.equals("")){
                            Looper.prepare();
                            Toast toast = Toast.makeText(LoginActivity.this,"账户名或密码不能为空！",Toast.LENGTH_SHORT);
                            toast.show();
                            Looper.loop();
                        }
                        DBUtils dbUtils= new DBUtils();
                        Boolean result = dbUtils.login(n,psw);
                        if(!result){
                            Looper.prepare();
                            Toast toast=Toast.makeText(LoginActivity.this,"用户名不存在或密码错误！请重新输入！",Toast.LENGTH_SHORT);
                            toast.show();
                           // user_et.setText("");
                            pas_et.setText("");
                            Looper.loop();

                        }
                        else{
                            Looper.prepare();
                            Toast toast=Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT);
                            toast.show();
                            open(HomeActivity.class);
                            finish();
                        }
                    }
                }).start();
                break;

            case R.id.regist:
                //注释按钮
                finish();
                open(RegistActivity.class);
            default:
                break;



        }
    }
}
