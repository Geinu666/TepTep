package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Rating;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * 评论
 */
public class CommentActivity extends AppCompatActivity implements View.OnClickListener{

    private String gameName;
    private int gameId;
    private float rating;
    private RatingBar bar;
    private ImageView back;
    private TextView rateText;
    private TextView rateTitle;
    private ImageView gameIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        LightStatusBarUtils.setAndroidNativeLightStatusBar(this, true);

        gameId = getIntent().getExtras().getInt("gameId");
        rating = getIntent().getExtras().getFloat("rating");
        gameName = getIntent().getExtras().getString("gameName");

        back = findViewById(R.id.comment_back);
        bar = findViewById(R.id.comment_bar);
        rateText = findViewById(R.id.rating_text);
        rateTitle = findViewById(R.id.rating_title);
        gameIcon = findViewById(R.id.game_icon);

        bar.setRating(rating);
        setRatingText();

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
            }
        });

        Glide.with(getApplicationContext())
                .load("https://dss0.bdstatic.com/-0U0bnSm1A5BphGlnYG/tam-ogel/c7540fa48a64bbaadb88ce07f0d6a1bb_264_264.jpg")
                .into(gameIcon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment_back:
                finish();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.rightout_exit);
    }

    public void setRatingText(){
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
    }
}