package com.example.keshe;
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

public class RegistActivity extends BaseActivity implements View.OnClickListener{

    private EditText user_et;
    private  EditText pas_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        init();

    }


    private void init(){
        user_et=findViewById(R.id.username);
        pas_et=findViewById(R.id.password);
        Button regist=findViewById(R.id.regist);
        regist.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.regist:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String n = user_et.getText().toString().trim();
                        String psw = pas_et.getText().toString().trim();

                        if(n.equals("")||psw.equals("")){
                            Looper.prepare();
                            Toast toast = Toast.makeText(RegistActivity.this,"输入的账户名或密码不能为空！",Toast.LENGTH_SHORT);
                            toast.show();
                            Looper.loop();
                        }
                        DBUtils dbUtils = new DBUtils();
                        boolean result =dbUtils.regist(n,psw);
                        if (!result){
                            Looper.prepare();
                            Toast toast = Toast.makeText(RegistActivity.this,"注册成功！",Toast.LENGTH_SHORT);
                            toast.show();
                            //Looper.loop();
                            open(LoginActivity.class);
                            finish();

                        }

                        //以上为jdbc注册
                    }
                }).start();
                break;
            default:
                break;


        }
    }
}
