package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashdemo.entity.Login;
import com.example.splashdemo.entity.Register;
import com.example.splashdemo.utils.HuCryptoUtil;
import com.example.splashdemo.utils.LightStatusBarUtils;

import WebKit.Bean.Key;
import WebKit.Bean.LoginBean;
import WebKit.CookieUtil;
import WebKit.RetrofitFactory;
import WebKit.Service.LoginService;
import okhttp3.Headers;
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
    private String cryptedPsw;
    private EditText signupNickname;
    private String nic;
    private LoginService service;
    private Boolean isReg = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupAct = findViewById(R.id.accountSignupText);
        signupPsd = findViewById(R.id.passwordSignupText);
        signupPhoneNumber = findViewById(R.id.phoneNumberSignupText);
        signupMail = findViewById(R.id.mailSignupText);
        signupButton = findViewById(R.id.signupButton);
        normalLogin = findViewById(R.id.normalLogin);
        signupNickname =  findViewById(R.id.nicknameSignupText);

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
            encryptedGetKey(true);
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
        Call<LoginBean> call = service.postRegister(nic, psw, act);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                    loginAfterRegister(act, psw);
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
        getRegisterState();
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.rightout_exit);
    }

    public void syncCookie(String url, String cookie) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this);
        }
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();
        cookieManager.setCookie(url, cookie);
//        CookieSyncManager.getInstance().sync();
        Log.i("test", "cookie sync" + cookie);
    }

    /**
     * 未加密的自动登录
     * @param Act
     * @param Psw
     */
    private void loginAfterRegister(String Act, String Psw){
        LoginService loginService = RetrofitFactory.getSpecialService(this);
        Call<LoginBean> call = loginService.postLogin(Act, Psw);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response.isSuccessful()) {
                    LoginBean result = response.body();
                    String cookies = response.headers().get("Set-Cookie");
                    SharedPreferences.Editor config = getApplicationContext().getSharedPreferences("config", getApplicationContext().MODE_PRIVATE).edit();
                    config.putString("cookie", cookies);
                    config.commit();
                    Log.i("test", cookies);
                    //同步cookies到全局WebView
                    syncCookie("http://119.91.130.198/api/", cookies);
                    setIsReg(true);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "登陆失败！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setIsReg(Boolean is) {
        isReg = is;
    }

    public void getRegisterState(){
        Intent intent = new Intent();
        intent.putExtra("result", isReg);
        setResult(101, intent);
    }

    public void encryptedGetKey(Boolean Reg){
        LoginService service = RetrofitFactory.getSpecialService(getApplicationContext());
        Call<Key> call = service.getKey();
        call.enqueue(new Callback<Key>() {
            @Override
            public void onResponse(Call<Key> call, Response<Key> response) {
                if (response.isSuccessful()) {
                    Key result = response.body();
                    if (result != null) {
                        Headers headers = response.headers();
                        Log.i("test", "public key " + result.getData());
                        String encrypted = HuCryptoUtil.encryptData(psw, result.getData());
                        Log.i("test", encrypted);
                        cryptedPsw = encrypted;

                        String cookies = response.headers().get("Set-Cookie");
                        SharedPreferences.Editor config = getApplicationContext().getSharedPreferences("config", getApplicationContext().MODE_PRIVATE).edit();
                        config.putString("cookie", cookies);
                        config.commit();
                        //同步cookies到全局WebView
                        CookieUtil.syncCookie("http://119.91.130.198/api/", cookies, getApplicationContext());
                        if (Reg) {
                            encryptedRegister();
                        } else {
                            encryptedLogin();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Key> call, Throwable t) {
                Log.i("test", "公钥获取失败!");
            }
        });
    }

    public void encryptedRegister(){
        Register register = new Register(act, cryptedPsw, nic);
        LoginService service = RetrofitFactory.getLoginService(getApplicationContext());
        Call<LoginBean>  call = service.encryptedRegister(register);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                Toast.makeText(getApplicationContext(), "注册成功!", Toast.LENGTH_SHORT).show();
                encryptedGetKey(false);
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "注册失败!",  Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 加密登录
     */
    public void encryptedLogin() {
        LoginService service = RetrofitFactory.getLoginService(this);
        Call<LoginBean> call = service.encryptedLogin(new Login(act, cryptedPsw));
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response.isSuccessful()) {
                    setIsReg(true);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "登陆失败!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}