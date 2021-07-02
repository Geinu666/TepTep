package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.InputQueue;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.List;

import WebKit.Bean.AllBean;
import WebKit.Bean.LikeBean;
import WebKit.Bean.OneGame;
import WebKit.DataServer;
import WebKit.RetrofitFactory;
import WebKit.Service.GameService;
import WebKit.Service.GetGameService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 显示游戏信息的界面
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    private String gameId;
    private ImageView gameIcon;
    private RelativeLayout gameForum;
    private RelativeLayout gameLike;
    private RelativeLayout gameComment;
    private ImageView gameBack;
    private RatingBar ratingBar;
    private TextView gameName;
    private float score;
    private String iconUrl;
    private GameService service;
    private TextView likeText;
    private ImageView likeIcon;
    private TextView avgScore;
    private TextView gameIntro;
    private TextView rateScore;
    private TextView commentCount;
    private ImageView bigImg;
    private TextView followersCount;
    private TextView issuer;
    private Button showComment;
    private List<Comment> commentList = new ArrayList<Comment>();

    private ImageView commentUserAvatar;
    private TextView commentUserNickname;
    private TextView commentLastTime;
    private ImageView starOne;
    private ImageView starTwo;
    private ImageView starThree;
    private ImageView starFour;
    private ImageView starFive;
    private TextView commentContent;
    private ImageView commentLike;
    private TextView commentLikeCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        LightStatusBarUtils.setAndroidNativeLightStatusBar(this, true);

        commentList = DataServer.getComment();
//        CommentRecyclerView();

        gameIcon = findViewById(R.id.game_icon);
        gameForum = findViewById(R.id.game_forum);
        gameLike = findViewById(R.id.game_like);
        gameComment = findViewById(R.id.game_comment);
        gameBack = findViewById(R.id.game_back);
        ratingBar = findViewById(R.id.rating_bar);
        gameName = findViewById(R.id.game_name);
        likeIcon = findViewById(R.id.like_icon);
        likeText = findViewById(R.id.like_text);
        avgScore = findViewById(R.id.game_score);
        gameIntro = findViewById(R.id.game_intro);
        rateScore = findViewById(R.id.rate_score);
        commentCount = findViewById(R.id.comment_count);
        bigImg = findViewById(R.id.big_img);
        followersCount = findViewById(R.id.game_followers);
        issuer = findViewById(R.id.game_company);
        showComment = findViewById(R.id.button_show_comment);



        initComment();

        service = RetrofitFactory.getGameService(getApplicationContext());

        gameLike.setOnClickListener(this);
        gameForum.setOnClickListener(this);
        gameComment.setOnClickListener(this);
        gameBack.setOnClickListener(this);
        showComment.setOnClickListener(this);

        gameId = "2";
        gameId = getIntent().getStringExtra("gameId");
        //用gameId拿数据
        Log.i("test", "gameId:" + gameId);
        Log.i("test", "before setget");
        getDataAndSet(gameId);
        Log.i("test", "after setget");

        iconUrl = getIntent().getStringExtra("iconUrl");
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    Log.i("test", String.valueOf(rating * 2));
                    Intent intent1 = new Intent(GameActivity.this, CommentActivity.class);
                    intent1.putExtra("rating", rating)
                            .putExtra("gameId", gameId)
                            .putExtra("gameName", gameName.getText().toString())
                            .putExtra("iconUrl", iconUrl);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.game_like:
                likeGame(gameId);
                break;
            case R.id.game_comment:
                Intent intent1 = new Intent(GameActivity.this, CommentActivity.class);
                intent1.putExtra("gameId", gameId)
                        .putExtra("gameName", gameName.getText().toString())
                        .putExtra("iconUrl", iconUrl);
                startActivity(intent1);
                overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
                break;
            case R.id.game_forum:
                Log.i("test", "onclick");
                Intent intent = new Intent(GameActivity.this, WebViewActivity.class);
                intent.putExtra("url", "GameForum/" + gameId);
                intent.putExtra("gameId", gameId);
                startActivity(intent);
                overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
                break;
            case R.id.game_back:
                finish();
                break;
            case R.id.button_show_comment:
                Intent intent2 = new Intent(GameActivity.this, CommentListActivity.class);
                intent2.putExtra("gameId", gameId)
                        .putExtra("gameName", gameName.getText().toString())
                        .putExtra("iconUrl", iconUrl);
                startActivity(intent2);
                overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
                break;
        }
    }

    /**
     * 关注游戏，再按取关
     * @param gameId
     */
    public void likeGame(String gameId){
        Call<LikeBean> call = service.postLikeGame(gameId);
        call.enqueue(new Callback<LikeBean>() {
            @Override
            public void onResponse(Call<LikeBean> call, Response<LikeBean> response) {
                    LikeBean result = response.body();
                    if (result != null) {
                        Boolean isLike = result.getData().isLikes();
                        if (isLike) {
                            Toast.makeText(getApplicationContext(), "关注成功！", Toast.LENGTH_SHORT).show();
                            likeIcon.setImageResource(R.drawable.baseline_favorite_24);
                            likeText.setText(R.string.game_is_like);
                        } else {
                            Toast.makeText(getApplicationContext(), "取关成功！", Toast.LENGTH_SHORT).show();
                            likeIcon.setImageResource(R.drawable.baseline_favorite_border_24);
                            likeText.setText(R.string.game_like);
                        }

                    } else {
                        Log.i("test", "result is empty");
                    }
            }

            @Override
            public void onFailure(Call<LikeBean> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "网络异常！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.no_anim, R.anim.rightout_exit);
    }

    /**
     * 用id请求数据并刷新布局
     * @param Id 从Intent传进来的gameId
     */
    public void getDataAndSet(String Id) {
        Log.i("test", "begin getset");
        GetGameService service = RetrofitFactory.getGetGameService(getApplicationContext());
        Call<OneGame> call = service.getOneGame(Id);
        call.enqueue(new Callback<OneGame>() {
            @Override
            public void onResponse(Call<OneGame> call, Response<OneGame> response) {
                if (response.isSuccessful()) {
                    OneGame result = response.body();
                    if (result != null) {
                        double getScore = result.getData().getAvgScore();
                        String newScore =  String.format("%.1f", getScore);
                        avgScore.setText(newScore);
                        ratingBar.setRating((float) getScore / 2);
                        rateScore.setText(newScore);

                        gameIntro.setText(result.getData().getBriefIntro());
                        commentCount.setText(result.getData().getCommentCount().toString() + " 人评分");
                        Glide.with(getApplicationContext())
                                .load(result.getData().getDisplayDrawings())
                                .into(bigImg);
//                        result.getData().getDownloads();
                        Glide.with(getApplicationContext())
                                .load(result.getData().getIcon())
                                .into(gameIcon);
                        iconUrl = result.getData().getIcon();
                        followersCount.setText("关注 " + result.getData().getInterestCount());
                        issuer.setText("厂商 " + result.getData().getIssuer());
                        gameName.setText(result.getData().getName());
//                        result.getData().getSize();
                        if (result.getData().getCurrentUserLikes()) {
                            likeIcon.setImageResource(R.drawable.baseline_favorite_24);
                            likeText.setText(R.string.game_is_like);
                        } else {
                            likeIcon.setImageResource(R.drawable.baseline_favorite_border_24);
                            likeText.setText(R.string.game_like);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "result is null !", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<OneGame> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "初始化失败！", Toast.LENGTH_SHORT);
            }
        });
    }

    public void initComment(){
        commentUserAvatar = findViewById(R.id.comment_user_avatar);
        commentUserNickname = findViewById(R.id.comment_user_nickname);
        commentLastTime = findViewById(R.id.comment_last_time);
        starOne = findViewById(R.id.star_one);
        starTwo = findViewById(R.id.star_two);
        starThree = findViewById(R.id.star_three);
        starFour = findViewById(R.id.star_four);
        starFive = findViewById(R.id.star_five);
        commentContent = findViewById(R.id.comment_content);
        commentLike = findViewById(R.id.comment_like);
        commentLikeCount = findViewById(R.id.comment_like_count);

        Comment comment = commentList.get(0);
        Glide.with(getApplicationContext())
                .load(comment.getAvatar())
                .into(commentUserAvatar);
        commentUserNickname.setText(comment.getNickname());
        //commentLastTime.setText(comment.get);
        int score = comment.getScore() / 2;
        if (score <= 1.0) {
            starTwo.setVisibility(View.INVISIBLE);
            starThree.setVisibility(View.INVISIBLE);
            starFour.setVisibility(View.INVISIBLE);
            starFive.setVisibility(View.INVISIBLE);
        } else if (score <= 2.0) {
            starThree.setVisibility(View.INVISIBLE);
            starFour.setVisibility(View.INVISIBLE);
            starFive.setVisibility(View.INVISIBLE);
        } else if (score <= 3.0) {
            starFour.setVisibility(View.INVISIBLE);
            starFive.setVisibility(View.INVISIBLE);
        } else if (score <= 4.0) {
            starFive.setVisibility(View.INVISIBLE);
        }
        commentContent.setText(comment.getContent());
        commentLikeCount.setText(String.valueOf(comment.getLikesCount()));
        //差个点赞识别
        if (comment.getLike()) {
            commentLike.setImageResource(R.drawable.baseline_favorite_24);
        }
    }

//    private void CommentRecyclerView(){
//        RecyclerView recyclerView = findViewById(R.id.comment_recyclerview);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
//        CommentAdapter adapter = new CommentAdapter(commentList);
//        recyclerView.setAdapter(adapter);
//    }
}