package com.example.teptep;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.teptep.utils.LightStatusBarUtils;

import WebKit.Bean.ChangeContent;
import WebKit.Bean.CommentBean;
import WebKit.RetrofitFactory;
import WebKit.Service.CommentService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 发评论
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
    private TextView gameNameView;
    private TextView release;
    private CommentService service;
    private EditText commentContent;
    private String userId;
    private String content;
    private Boolean isChange = false;
    private String commentId;
    private TextView commentTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        LightStatusBarUtils.setAndroidNativeLightStatusBar(this, true);

        gameId = getIntent().getExtras().getString("gameId");
        outrating = getIntent().getExtras().getFloat("rating", 0);
        gameName = getIntent().getExtras().getString("gameName");
        Log.i("test", gameName);
        iconUrl = getIntent().getExtras().getString("iconUrl", null);
        userId = getIntent().getExtras().getString("userId", null);
        content = getIntent().getExtras().getString("content", null);
        isChange = getIntent().getExtras().getBoolean("isChange", false);
        commentId = getIntent().getExtras().getString("commentId", null);

        back = findViewById(R.id.comment_back);
        bar = findViewById(R.id.comment_bar);
        rateText = findViewById(R.id.rating_text);
        rateTitle = findViewById(R.id.rating_title);
        gameIcon = findViewById(R.id.game_icon);
        release = findViewById(R.id.release_comment);
        commentContent = findViewById(R.id.comment_content);
        gameNameView = findViewById(R.id.comment_game_name);
        commentTitle = findViewById(R.id.comment_title);

        gameNameView.setText(gameName);

        if (content != null) {
            commentContent.setText(content);
        }

        if (isChange) {
            commentTitle.setText("编辑评价");
        }

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
                break;
            case R.id.release_comment:
                Integer score = (Integer)(int) (outrating * 2);
                String content = commentContent.getText().toString().trim();
                if (!isChange){
                    commitComment(gameId, content, score);
                    break;
                } else {
                    changeComment(commentId, content, score);
                    break;
                }
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

    /**
     * 发送评论
     * @param gameId
     * @param content
     * @param score
     */
    public void commitComment(String gameId, String content, int score){
        CommentBean.Comment comment = new CommentBean.Comment();
        comment.setGameId(gameId);
        comment.setContent(content);
        comment.setScore(score);
        Call<CommentBean> call = service.postComment(comment);
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
                if (userId != null) {
                    Toast.makeText(getApplicationContext(), "发布失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "尚未登陆！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 修改评论
     * @param commentId
     * @param content
     * @param score
     */
    public void changeComment(String commentId, String content, Integer score) {
        Call<ChangeContent> call = service.postChangeContent(commentId, content, score);
        call.enqueue(new Callback<ChangeContent>() {
            @Override
            public void onResponse(Call<ChangeContent> call, Response<ChangeContent> response) {
                Toast.makeText(getApplicationContext(), "修改成功!", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ChangeContent> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "修改失败!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}