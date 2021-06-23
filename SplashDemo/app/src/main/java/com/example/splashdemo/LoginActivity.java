package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    private EditText accountLoginText;
    private EditText passwordLoginText;
    private TextView signupText;
    private Button loginButton;
    private String act;
    private String psw;
    //供SignupActivity操作的实例
    public static LoginActivity instance = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instance = this;
        accountLoginText = (EditText) findViewById(R.id.accountLoginText);
        passwordLoginText = (EditText) findViewById(R.id.passwordLoginText);
        signupText = (TextView) findViewById(R.id.signup_text);;
        loginButton = (Button) findViewById(R.id.loginButton);
        LightStatusBarUtils.setAndroidNativeLightStatusBar(this, true);
        //跳转注册的逻辑
        signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String transAct = accountLoginText.getText().toString().trim();
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                intent.putExtra("actTrans", transAct);
                startActivity(intent);
            }
        });

        //设置账号栏输入限制
        accountLoginText.setKeyListener(new DigitsKeyListener(Locale.CHINA){
            @Override
            public int getInputType(){
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }
            @Override
            protected char[] getAcceptedChars(){
                char[] data = getStringData(R.string.login_only_can_input).toCharArray();
                return data;
            }
        });


    }

    /**
     * 登录按钮的点击事件
     * @param view Button
     */
    public void onClick(View view){
        act = accountLoginText.getText().toString().trim();
        psw = passwordLoginText.getText().toString().trim();
        if(loginJudgement(act, psw)){
            finish();
        } else {
            Toast.makeText(this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
        }
    }

    // TODO: 2021/6/21 等待完成登录检验
    public boolean loginJudgement(String account, String password){
        return false;
    }

    public String getStringData(int id){
        return getResources().getString(id);
    }
}