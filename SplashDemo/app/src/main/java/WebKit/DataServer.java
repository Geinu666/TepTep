package WebKit;

import com.example.splashdemo.Game;

import java.util.ArrayList;

/**
 * 伪 从网络获取数据
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
                        .setUrl("https://cdn.jsdelivr.net/gh/Lyw2017/image-hosting@master/images/mrfz.2w3y70akdfw0.jpg")
                        .setScore("9.5")
                        .setRank("NO.1")
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
}
