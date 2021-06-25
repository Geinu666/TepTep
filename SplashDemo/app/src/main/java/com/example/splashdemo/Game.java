package com.example.splashdemo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 游戏实体类
 */
public class Game implements Parcelable {
    //游戏名
    private String name;
    //图片URl
    private String url;
    //分类
    private String type;
    //排行
    private String rank;
    //描述
    private String description;
    //评分
    private String score;

    public Game(Parcel in) {
        name = in.readString();
        url = in.readString();
        type = in.readString();
        rank = in.readString();
        description = in.readString();
        score = in.readString();
    }

    public static final Creator<Game> CREATOR = new Creator<Game>() {
        @Override
        public Game createFromParcel(Parcel in) {
            return new Game(in);
        }

        @Override
        public Game[] newArray(int size) {
            return new Game[size];
        }
    };

    public Game() {

    }

    public String getName() {
        return name;
    }

    public Game setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Game setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getType() {
        return type;
    }

    public Game setType(String type) {
        this.type = type;
        return this;
    }

    public String getRank() {
        return rank;
    }

    public Game setRank(String rank) {
        this.rank = rank;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Game setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getScore() {
        return score;
    }

    public Game setScore(String score) {
        this.score = score;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(type);
        dest.writeString(rank);
        dest.writeString(description);
        dest.writeString(score);
    }
}
