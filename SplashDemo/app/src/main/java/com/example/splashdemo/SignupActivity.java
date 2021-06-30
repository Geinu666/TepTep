package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import WebKit.Bean.LoginBean;
import WebKit.RetrofitFactory;
import WebKit.Service.LoginService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private EditText signupAct;
    private EditText signupPsd;
    private Button signupButton;
    private TextView normalLogin;
    private EditText signupPhoneNumber;
    private EditText signupMail;
    private String act;
    private String psw;
    private EditText signupNickname;
    private String nic;
    private LoginService service;
//    private String phone;
//    private String mail;
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
        signupNickname = (EditText) findViewById(R.id.nicknameSignupText);

        service = RetrofitFactory.getLoginService(getApplicationContext());

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
    public boolean emptyJudge() {
        act = signupAct.getText().toString().trim();
        psw = signupPsd.getText().toString().trim();
        nic = signupNickname.getText().toString().trim();
//        phone = signupPhoneNumber.getText().toString().trim();
//        mail = signupMail.getText().toString().trim();
        if (act.equals("")) {
            Toast.makeText(this, "请输入账号", Toast.LENGTH_SHORT).show();
            return false;
        } else if (nic.equals("")) {
            Toast.makeText(this, "请输入昵称", Toast.LENGTH_SHORT).show();
            return false;
        } else if (psw.equals("")){
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * 发后端请求进行注册
     */
    public void signupJudge(){
//        phone = signupPhoneNumber.getText().toString().trim();
//        mail = signupMail.getText().toString().trim();
        // TODO: 2021/6/22 未完注册判定
        Call<LoginBean> call = service.postRegister(nic, psw, act);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                    LoginActivity.instance.finish();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "注册失败！", Toast.LENGTH_SHORT).show();
            }
        });

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