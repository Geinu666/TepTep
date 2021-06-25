package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;

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

import com.example.splashdemo.LightStatusBarUtils;

import WebKit.Bean.LoginBean;
import WebKit.Bean.LoginData;
import WebKit.Service.LoginService;
import WebKit.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {
    //供SignupActivity操作的实例
    public static LoginActivity instance = null;
    private EditText accountLoginText;
    private EditText passwordLoginText;
    private TextView signupText;
    private Button loginButton;
    private String act;
    private String psw;
    private Retrofit retrofit;
    private LoginService loginService;
    private Boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        instance = this;
        accountLoginText = (EditText) findViewById(R.id.accountLoginText);
        passwordLoginText = (EditText) findViewById(R.id.passwordLoginText);
        signupText = (TextView) findViewById(R.id.signup_text);
        ;
        loginButton = (Button) findViewById(R.id.loginButton);
        retrofit = Retrofit.getRetrofit();
        loginService = retrofit.getService();
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
        accountLoginText.setKeyListener(new DigitsKeyListener(Locale.CHINA) {
            @Override
            public int getInputType() {
                return InputType.TYPE_TEXT_VARIATION_PASSWORD;
            }

            @Override
            protected char[] getAcceptedChars() {
                char[] data = getStringData(R.string.login_only_can_input).toCharArray();
                return data;
            }
        });


    }

    /**
     * 登录按钮的点击事件
     *
     * @param view Button
     */
    public void onClick(View view) {
        act = accountLoginText.getText().toString().trim();
        psw = passwordLoginText.getText().toString().trim();
        if (loginJudgement(act, psw)) {
            Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
            Log.i("login", "登陆成功");
            finish();
        } else {
            Toast.makeText(this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 发送登录请求
     * @param account 账号栏输入
     * @param password 密码栏输入
     * @return 返回登录状态
     */
    public boolean loginJudgement(String account, String password) {
        Call<LoginBean> call = loginService.postLogin(account, password);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response.isSuccessful()) {
                    LoginBean result = response.body();
                    if (result != null) {
                        LoginData data = result.getData();
                        String i = data.toString();
                        isLogin = true;
                        Log.i("loginPOST", i);
                    } else {
                        Log.i("loginPOST", "empty result");
                    }
                } else {
                    Log.i("loginPOST", "400 Bad request");
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {

            }
        });
        Log.i("loginPOST", "before return");
        return isLogin;
    }

    public String getStringData(int id) {
        return getResources().getString(id);
    }
}