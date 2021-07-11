package Deprecate;

import com.example.splashdemo.entity.Comment;
import com.example.splashdemo.entity.Game;

import java.util.ArrayList;

/**
 * 造假中心(不太实际，即将弃用）
 */
public class DataServer {
    /**
     * 推荐列表数据
     *
     * @return
     */
    public static ArrayList<Game> getRecommendationData() {
        ArrayList<Game> games = new ArrayList<>();
        games.add(
                new Game().setName("明日方舟")
                        .setType("塔防")
                        //
                        .setUrl("https://img.tapimg.com/market/images/207696c2f229569914514eb800b7e998.jpg?imageView2/0/w/1080/h/9999/q/80/format/jpg/interlace/1/ignore-error/1")
                        .setScore("9.5")
                        .setId("2")
        );
        games.add(
                new Game().setName("王者荣耀")
                        .setType("MOBA")
                        .setUrl("https://cdn.jsdelivr.net/gh/Lyw2017/image-hosting@master/images/1171624561108_.pic.4mbpkvof0lu0.jpg")
                        .setScore("9.0")
        );
        games.add(
                new Game().setName("和平精英")
                        .setType("射击")
                        .setUrl("https://cdn.jsdelivr.net/gh/Lyw2017/image-hosting@master/images/1191624578089_.pic_hd.5a8s0n2sv2g0.jpg")
                        .setScore("9.3")

        );
        games.add(
                new Game().setName("明日方舟")
                        .setType("塔防")
                        .setUrl("https://cdn.jsdelivr.net/gh/Lyw2017/image-hosting@master/images/mrfz.2w3y70akdfw0.jpg")
                        .setScore("9.5")
        );
        games.add(
                new Game().setName("王者荣耀")
                        .setType("MOBA")
                        .setUrl("https://cdn.jsdelivr.net/gh/Lyw2017/image-hosting@master/images/1171624561108_.pic.4mbpkvof0lu0.jpg")
                        .setScore("9.0")
        );
        games.add(
                new Game().setName("和平精英")
                        .setType("射击")
                        .setUrl("https://cdn.jsdelivr.net/gh/Lyw2017/image-hosting@master/images/1191624578089_.pic_hd.5a8s0n2sv2g0.jpg")
                        .setScore("9.3 ")

        );
        games.add(
                new Game().setName("明日方舟")
                        .setType("塔防")
                        .setUrl("https://cdn.jsdelivr.net/gh/Lyw2017/image-hosting@master/images/mrfz.2w3y70akdfw0.jpg")
                        .setScore("9.5")
        );
        games.add(
                new Game().setName("王者荣耀")
                        .setType("MOBA")
                        .setUrl("https://cdn.jsdelivr.net/gh/Lyw2017/image-hosting@master/images/1171624561108_.pic.4mbpkvof0lu0.jpg")
                        .setScore("9.0")
        );
        games.add(
                new Game().setName("和平精英")
                        .setType("射击")
                        .setUrl("https://cdn.jsdelivr.net/gh/Lyw2017/image-hosting@master/images/1191624578089_.pic_hd.5a8s0n2sv2g0.jpg")
                        .setScore("9.3")

        );


        return games;
    }

    /**
     * 排行数据
     * @return
     */
    public static ArrayList<Game> getRankData() {

        ArrayList<Game> games = new ArrayList<>();
        games.add(
                new Game().setName("明日方舟")
                        .setType("塔防")
//                        .setUrl("https://cdn.jsdelivr.net/gh/Lyw2017/image-hosting@master/images/mrfz.2w3y70akdfw0.jpg")
                        .setUrl("https://img.tapimg.com/market/images/207696c2f229569914514eb800b7e998.jpg?imageView2/0/w/1080/h/9999/q/80/format/jpg/interlace/1/ignore-error/1")
                        .setScore("9.5")
                        .setRank("NO.1")
                        .setId("2")
        );
        games.add(
                new Game().setName("王者荣耀")
                        .setType("MOBA")
                        .setUrl("https://cdn.jsdelivr.net/gh/Lyw2017/image-hosting@master/images/1171624561108_.pic.4mbpkvof0lu0.jpg")
                        .setScore("9.0")
                        .setRank("NO.2")
        );
        games.add(
                new Game().setName("和平精英")
                        .setType("射击")
                        .setUrl("https://cdn.jsdelivr.net/gh/Lyw2017/image-hosting@master/images/1191624578089_.pic_hd.5a8s0n2sv2g0.jpg")
                        .setScore("9.3")
                        .setRank("NO.3")

        );
        return games;
    }

    public static ArrayList<Comment> getComment(){


//        call.enqueue(new Callback<AllCommentBean>() {
//            @Override
//            public void onResponse(Call<AllCommentBean> call, Response<AllCommentBean> response) {
//                if (response.isSuccessful()) {
//                    AllCommentBean result = response.body();
//                    if (result != null) {
//                        List<Comment> comments = new ArrayList<>();
//                        comments = result.getData();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<AllCommentBean> call, Throwable t) {
//                Toast.makeText(context, "请求评论列表失败", Toast.LENGTH_SHORT).show();
//            }
//        });
        ArrayList<Comment> comments = new ArrayList<>();
        comments.add(
                new Comment().setCommentId("1")
                .setGameId("2")
                .setUserId("123123")
                .setContent("这是亿点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点点长的评论")
                .setLikesCount(30)
                .setCommentAt(123)
                .setAvatar("https://img.taplb.com/md5/22/f1/22f1196f825298281376608459bfa7fe")
                .setNickname("小明")
                .setScore(6)
                .setLike(true)
        );
        comments.add(
                new Comment().setCommentId("1")
                        .setGameId("2")
                        .setUserId("123123")
                        .setContent("这是内容")
                        .setLikesCount(30)
                        .setCommentAt(123)
                        .setLike(true)
                        .setAvatar("https://img.taplb.com/md5/22/f1/22f1196f825298281376608459bfa7fe")
                        .setNickname("小红明")
                        .setScore(8)
        );
        comments.add(
                new Comment().setCommentId("1")
                        .setGameId("2")
                        .setUserId("123123")
                        .setContent("这是内容")
                        .setLikesCount(30)
                        .setCommentAt(123)
                        .setLike(true)
                        .setAvatar("https://img.taplb.com/md5/22/f1/22f1196f825298281376608459bfa7fe")
                        .setNickname("小明")
                        .setScore(5)
        );
        comments.add(
                new Comment().setCommentId("1")
                        .setGameId("2")
                        .setUserId("123123")
                        .setContent("这是内容")
                        .setLikesCount(30)
                        .setLike(true)
                        .setCommentAt(123)
                        .setAvatar("https://img.taplb.com/md5/22/f1/22f1196f825298281376608459bfa7fe")
                        .setNickname("小明")
                        .setScore(8)
        );
        comments.add(
                new Comment().setCommentId("1")
                        .setGameId("2")
                        .setUserId("123123")
                        .setContent("这是内容")
                        .setLikesCount(30)
                        .setCommentAt(123)
                        .setLike(false)
                        .setAvatar("https://img.taplb.com/md5/22/f1/22f1196f825298281376608459bfa7fe")
                        .setNickname("小明")
                        .setScore(2)
        );
        comments.add(
                new Comment().setCommentId("1")
                        .setGameId("2")
                        .setUserId("123123")
                        .setContent("这是内容")
                        .setLikesCount(30)
                        .setLike(true)
                        .setCommentAt(123)
                        .setAvatar("https://img.taplb.com/md5/22/f1/22f1196f825298281376608459bfa7fe")
                        .setNickname("小明")
                        .setScore(1)
        );
        return comments;
    }
}
