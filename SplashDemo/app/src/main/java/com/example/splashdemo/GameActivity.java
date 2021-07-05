package com.example.splashdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.splashdemo.adapter.BannerAdapter;
import com.example.splashdemo.entity.Comment;
import com.example.splashdemo.utils.LightStatusBarUtils;
import com.example.splashdemo.utils.Time;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import WebKit.Bean.AllCommentBean;
import WebKit.Bean.LikeBean;
import WebKit.Bean.LoginBean;
import WebKit.Bean.OneGame;
import WebKit.RetrofitFactory;
import WebKit.Service.CommentService;
import WebKit.Service.GameService;
import WebKit.Service.GetGameService;
import WebKit.Service.LoginService;
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
    private String userId;
    private LinearLayout commentCard;
    private ViewPager2 viewPager2;
    private LinearLayout indicatorContainer;
    private int lastPosition;
    private Handler mHandler = new Handler();
    private LinearLayout tagContainer;
    private RelativeLayout scrollPic;
    private MaterialButton categoryOne;
    private MaterialButton categoryTwo;
    private MaterialButton categoryThree;

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
    private TextView commentId;
    private TextView commentWriterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //同步登陆状态
        judgeState();
        //设置浅色状态栏
        LightStatusBarUtils.setAndroidNativeLightStatusBar(this, true);

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
        commentCard = findViewById(R.id.comment_card);
        viewPager2 = findViewById(R.id.banner_viewpager);
        indicatorContainer = findViewById(R.id.banner_indicator);
        tagContainer = findViewById(R.id.tag_container);
        scrollPic = findViewById(R.id.scroll_pic);
        categoryOne = findViewById(R.id.game_category_1);
        categoryTwo = findViewById(R.id.game_category_2);
        categoryThree = findViewById(R.id.game_category_3);

        service = RetrofitFactory.getGameService(getApplicationContext());

        gameLike.setOnClickListener(this);
        gameForum.setOnClickListener(this);
        gameComment.setOnClickListener(this);
        gameBack.setOnClickListener(this);
        showComment.setOnClickListener(this);
        commentCard.setOnClickListener(this);

        iconUrl = getIntent().getStringExtra("iconUrl");
        gameId = getIntent().getStringExtra("gameId");

        //用gameId拿数据
        getDataAndSet(gameId);
        initComment();

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    Log.i("test", String.valueOf(rating * 2));
                    Intent intent1 = new Intent(GameActivity.this, CommentActivity.class);
                    intent1.putExtra("rating", rating)
                            .putExtra("gameId", gameId)
                            .putExtra("gameName", gameName.getText().toString())
                            .putExtra("iconUrl", iconUrl)
                            .putExtra("userId", userId);
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
                        .putExtra("iconUrl", iconUrl)
                        .putExtra("userId", userId);
                Log.i("test", "传到commentActivity的gamename: " + gameName.getText().toString());
                startActivity(intent1);
                overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
                break;
            case R.id.game_forum:
                if (userId != null) {
                    Log.i("test", "onclick");
                    Intent intent = new Intent(GameActivity.this, WebViewActivity.class);
                    intent.putExtra("url", "GameForum/" + gameId);
                    intent.putExtra("gameId", gameId);
                    intent.putExtra("func", "GameForum");
                    startActivity(intent);
                    overridePendingTransition(R.anim.rightin_enter, R.anim.no_anim);
                } else {
                    Toast.makeText(getApplicationContext(), "尚未登陆！", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.game_back:
                finish();
                break;
            case R.id.button_show_comment:
            case R.id.comment_card:
                Intent intent2 = new Intent(GameActivity.this, CommentListActivity.class);
                intent2.putExtra("gameId", gameId)
                        .putExtra("gameName", gameName.getText().toString())
                        .putExtra("userId", userId)
                        .putExtra("iconUrl", iconUrl);
                Log.i("test", "传到commentActivity的gamename: " + gameName.getText().toString());
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
                if (userId != null) {
                    Toast.makeText(getApplicationContext(), "网络异常！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "尚未登陆！", Toast.LENGTH_SHORT).show();
                }
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

                        String[] list = result.getData().getDisplayDrawings().split("\\|");
                        List<String> drawings = new ArrayList<>(Arrays.asList(list));
                        Glide.with(getApplicationContext())
                                .load(drawings.get(0))
                                .into(bigImg);
                        if (drawings.size() > 1) {
                            initIndicator(drawings);
                            BannerAdapter adapter = new BannerAdapter(drawings, getApplicationContext());
                            viewPager2.setAdapter(adapter);
                            viewPager2.setCurrentItem(500);
                            lastPosition = 500;

                            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                                @Override
                                public void onPageSelected(int position) {
                                    super.onPageSelected(position);
                                    int current = position % (drawings.size() - 1);
                                    int last = lastPosition % (drawings.size() - 1);
                                    indicatorContainer.getChildAt(current).setBackgroundResource(R.drawable.blue_indicator);
                                    indicatorContainer.getChildAt(last).setBackgroundResource(R.drawable.white_indicator);
                                    lastPosition = position;
                                }
                            });
                        } else {
                            scrollPic.setVisibility(View.GONE);
                        }
                        Glide.with(getApplicationContext())
                                .load(result.getData().getIcon())
                                .into(gameIcon);

                        iconUrl = result.getData().getIcon();

                        followersCount.setText("关注 " + result.getData().getInterestCount());
                        String textfollow = "关注 "
                                + "<b><tt>" + result.getData().getInterestCount() + "</tt></b>";
                        followersCount.setText(Html.fromHtml(textfollow));

                        gameName.setText(result.getData().getName());
                        String textissue = "厂商 "
                                + "<b><tt>" + result.getData().getIssuer() + "</tt></b>";
                        issuer.setText(Html.fromHtml(textissue));

                        if (result.getData().getCurrentUserLikes()) {
                            likeIcon.setImageResource(R.drawable.baseline_favorite_24);
                            likeText.setText(R.string.game_is_like);
                        } else {
                            likeIcon.setImageResource(R.drawable.baseline_favorite_border_24);
                            likeText.setText(R.string.game_like);
                        }


                        String listOrigin = result.getData().getCategory();
                        if (listOrigin != null) {
                            String[] cList = listOrigin.split(" ");
                            List<String> category = new ArrayList<>(Arrays.asList(cList));
                            switch (category.size()) {
                                case 1:
                                    categoryTwo.setVisibility(View.GONE);
                                    categoryThree.setVisibility(View.GONE);
                                    categoryOne.setText(category.get(0));
                                    break;
                                case 2:
                                    categoryThree.setVisibility(View.GONE);
                                    categoryOne.setText(category.get(0));
                                    categoryTwo.setText(category.get(1));
                                    break;
                                case 3:
                                    categoryOne.setText(category.get(0));
                                    categoryTwo.setText(category.get(1));
                                    categoryThree.setText(category.get(2));
                            }
                        } else {
                            categoryOne.setVisibility(View.GONE);
                            categoryTwo.setVisibility(View.GONE);
                            categoryThree.setVisibility(View.GONE);
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
    //做评论的绑定和加载
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
        commentId = findViewById(R.id.comment_id);
        commentWriterId = findViewById(R.id.comment_writer_id);

        getAndSetComment();
    }

    /**
     * 网络请求拿评论，有就把第一条展示，没有就隐藏评论卡片
     */
    private void getAndSetComment(){
        CommentService commentService = RetrofitFactory.getCommentService(this);
        Log.i("test", "获取gameId=" + gameId);
        Call<AllCommentBean> call = commentService.getAllComment(gameId);
        call.enqueue(new Callback<AllCommentBean>() {
            @Override
            public void onResponse(Call<AllCommentBean> call, Response<AllCommentBean> response) {
                if (response.isSuccessful()) {
                    AllCommentBean result = response.body();
                    if (result != null) {
                        List<Comment> commentList = result.getData();
                        if (!commentList.isEmpty()){
                            Comment comment = result.getData().get(0);
                            setComment(comment);
                            Log.i("test", "获取一条评论成功");
                        } else {
                            Log.i("test", "此游戏还没有评论");
                            commentCard.setVisibility(View.GONE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AllCommentBean> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "获取评论失败", Toast.LENGTH_SHORT).show();
                Log.i("test", "网络爆炸");
            }
        });
    }

    /**
     * 根据数据加载评论到视图
     * @param comment 读入的评论
     */
    private void setComment(Comment comment){
        Glide.with(getApplicationContext())
                .load(comment.getAvatar() == null?"https://img.taplb.com/md5/22/f1/22f1196f825298281376608459bfa7fe": comment.getAvatar())
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
        commentId.setText(comment.getCommentId());
        commentWriterId.setText(comment.getUserId());
        commentLastTime.setText(Time.CalculateTime(comment.getCommentAt()));
    }

    /**
     * 进入Activity的时候检测一次登陆状态并获取userId
     */
    private void judgeState(){
        LoginService service = RetrofitFactory.getLoginService(this);
        Call<LoginBean> call = service.getUserMessage();
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                if (response.isSuccessful()) {
                    LoginBean result = response.body();
                    userId = result.getData().getUserId();
                    Log.i("test", "gameactivity: " + userId);
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Log.i("test", "没登陆就进来？");
                userId = null;
            }
        });
    }

    /**
     * 初始化轮播的进度条
     * @param drawings 轮播图片链表
     */
    private void initIndicator(List<String> drawings){
        for (int i = 0; i < drawings.size() - 1; i++) {
            ImageView imageView = new ImageView(this);
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.blue_indicator);
            } else {
                imageView.setBackgroundResource(R.drawable.white_indicator);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMarginEnd(4);
            imageView.setLayoutParams(layoutParams);
            indicatorContainer.addView(imageView);
        }
    }
    //设置轮播
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //获得轮播图当前的位置
            int currentPosition = viewPager2.getCurrentItem();
            currentPosition++;
            viewPager2.setCurrentItem(currentPosition,true);
            mHandler.postDelayed(runnable,5000);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        mHandler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(runnable, 5000);
    }
}