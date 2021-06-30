package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import WebKit.Bean.CommentBean;
import WebKit.Bean.Failure;
import WebKit.RetrofitFactory;
import WebKit.Service.CommentService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 评论
 */
public class CommentActivity extends AppCompatActivity implements View.OnClickListener{

    private String gameName;
    private String gameId;
    private float outrating;
    private RatingBar bar;
    private ImageView back;
    private String iconUrl;
    private TextView rateText;
    private TextView rateTitle;
    private ImageView gameIcon;
    private TextView release;
    private CommentService service;
    private EditText commentContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        LightStatusBarUtils.setAndroidNativeLightStatusBar(this, true);

        gameId = getIntent().getExtras().getString("gameId", "2");
        outrating = getIntent().getExtras().getFloat("rating");
        gameName = getIntent().getExtras().getString("gameName", "明日方舟");
        iconUrl = getIntent().getExtras().getString("iconUrl", null);

        back = findViewById(R.id.comment_back);
        bar = findViewById(R.id.comment_bar);
        rateText = findViewById(R.id.rating_text);
        rateTitle = findViewById(R.id.rating_title);
        gameIcon = findViewById(R.id.game_icon);
        release = findViewById(R.id.release_comment);
        commentContent = findViewById(R.id.comment_content);

        service = RetrofitFactory.getCommentService(getApplicationContext());

        bar.setRating(outrating);
        setRatingText();

        release.setOnClickListener(this);
        back.setOnClickListener(this);
        bar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating <= 1.0) {
                    rateTitle.setText("不好玩");
                    rateText.setText("游戏有致命的缺陷，很难找到亮点");
                } else if (rating <= 2.0) {
                    rateTitle.setText("非常一般");
                    rateText.setText("缺点大过优点，游戏内容仍需大幅调整");
                } else if (rating <= 3.0) {
                    rateTitle.setText("中规中矩");
                    rateText.setText("游戏有亮点，同时缺点也很明显");
                } else if (rating <= 4.0) {
                    rateTitle.setText("优秀之作");
                    rateText.setText("虽然有小瑕疵，但游戏整体非常棒");
                } else {
                    rateTitle.setText("旷世神作");
                    rateText.setText("游戏无可挑剔，基本没有缺点");
                }
                outrating = rating;
                Log.i("test", String.valueOf(outrating));
            }
        });

        Glide.with(getApplicationContext())
                .load(iconUrl)
                .into(gameIcon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_back:
                finish();
            case R.id.release_comment:
                Log.i("test", "发布");
                int score = (int) (outrating * 2);
                Log.i("test", "score: " + score);
                String content = commentContent.getText().toString().trim();
                commitComment(gameId, content, score);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.rightout_exit);
    }
    //初始化RatingBar
    public void setRatingText(){
        if (outrating <= 1.0) {
            rateTitle.setText("不好玩");
            rateText.setText("游戏有致命的缺陷，很难找到亮点");
        } else if (outrating <= 2.0) {
            rateTitle.setText("非常一般");
            rateText.setText("缺点大过优点，游戏内容仍需大幅调整");
        } else if (outrating <= 3.0) {
            rateTitle.setText("中规中矩");
            rateText.setText("游戏有亮点，同时缺点也很明显");
        } else if (outrating <= 4.0) {
            rateTitle.setText("优秀之作");
            rateText.setText("虽然有小瑕疵，但游戏整体非常棒");
        } else {
            rateTitle.setText("旷世神作");
            rateText.setText("游戏无可挑剔，基本没有缺点");
        }
    }

    public void commitComment(String gameId, String content, int score){
        Call<CommentBean> call = service.postComment(content, gameId, score);
        call.enqueue(new Callback<CommentBean>() {
            @Override
            public void onResponse(Call<CommentBean> call, Response<CommentBean> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "发布成功！", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<CommentBean> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "发布失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}