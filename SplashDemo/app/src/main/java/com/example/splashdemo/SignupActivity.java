package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    private EditText signupAct;
    private EditText signupPsd;
    private Button signupButton;
    private TextView normalLogin;
    private EditText signupPhoneNumber;
    private EditText signupMail;
    private String act;
    private String psw;
    private String phone;
    private String mail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupAct = (EditText) findViewById(R.id.accountSignupText);
        signupPsd = (EditText) findViewById(R.id.passwordSignupText);
        signupPhoneNumber = (EditText) findViewById(R.id.phoneNumberSignupText);
        signupMail = (EditText) findViewById(R.id.mailSignupText);
        signupButton = (Button) findViewById(R.id.signupButton);
        normalLogin = (TextView) findViewById(R.id.normalLogin);

        fetchExtra();

        LightStatusBarUtils.setAndroidNativeLightStatusBar(this, true);
        //结束Activity
        normalLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    //注册按钮点击事件
    public void onClick(View view){
        if(emptyJudge()) {
            signupJudge();
        }
    }

    /**
     * EditText判空
     * @return true代表不空
     */
    public boolean emptyJudge(){
        act = signupAct.getText().toString().trim();
        psw = signupPsd.getText().toString().trim();
        phone = signupPhoneNumber.getText().toString().trim();
        mail = signupMail.getText().toString().trim();
        if(act.equals("")){
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            return false;
        } else if (psw.equals("")){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        } else if (phone.equals("")){
            return false;
        } else return !mail.equals("");
    }

    /**
     * 发后端请求进行注册
     */
    public void signupJudge(){
        act = signupAct.getText().toString().trim();
        psw = signupPsd.getText().toString().trim();
        phone = signupPhoneNumber.getText().toString().trim();
        mail = signupMail.getText().toString().trim();
        // TODO: 2021/6/22 未完注册判定
        LoginActivity.instance.finish();
        finish();
    }

    /**
     * 接收设置各种Extra
     */
    public void fetchExtra(){
        signupAct.setText(getIntent().getExtras().getString("actTrans", ""));
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.rightout_exit);
    }
}