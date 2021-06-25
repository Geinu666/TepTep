package Deprecate;
//用于给RecyclerView传东西

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 一个写完就扔的类(┭┮﹏┭┮)
 */
public class GameCard implements Parcelable {
    private String gameName;
    private String gameIntro;
    private int image;
    private String score;
    private String category;

    public GameCard(Parcel in) {
        gameName = in.readString();
        gameIntro = in.readString();
        image = in.readInt();
        score = in.readString();
        category = in.readString();
    }

    public GameCard(){

    }

    public static final Creator<GameCard> CREATOR = new Creator<GameCard>() {
        @Override
        public GameCard createFromParcel(Parcel in) {
            return new GameCard(in);
        }

        @Override
        public GameCard[] newArray(int size) {
            return new GameCard[size];
        }
    };

    public void setCategory(String category) {
        this.category = category;
    }

    public void setGameIntro(String gameIntro) {
        this.gameIntro = gameIntro;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getImage() {
        return image;
    }

    public String getGameIntro() {
        return gameIntro;
    }

    public String getCategory() {
        return category;
    }

    public String getGameName() {
        return gameName;
    }

    public String getScore() {
        return score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gameName);
        dest.writeString(score);
        dest.writeInt(image);
        dest.writeString(category);
        dest.writeString(gameIntro);

    }
}
