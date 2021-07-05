package com.example.splashdemo.entity;

public class Comment {
    private String commentId;
    private String gameId;
    private String userId;
    private String content;
    private int score;
    private int likesCount;
    private long commentAt;
    private String avatar;
    private String nickname;
    private Boolean like;

    public Comment setGameId(String gameId) {
        this.gameId = gameId;
        return this;
    }

    public String getGameId() {
        return gameId;
    }

    public Comment setLikesCount(int likesCount) {
        this.likesCount = likesCount;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Comment setContent(String content) {
        this.content = content;
        return this;
    }

    public String getCommentId() {
        return commentId;
    }

    public Comment setCommentId(String commentId) {
        this.commentId = commentId;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Comment setCommentAt(long commentAt) {
        this.commentAt = commentAt;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public Comment setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getAvatar() {
        return avatar;
    }

    public Comment setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public Comment setAvatar(String avatar) {
        this.avatar = avatar;
        return this;
    }

    public long getCommentAt() {
        return commentAt;
    }

    public Comment setScore(int score) {
        this.score = score;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Comment setLike(Boolean like) {
        this.like = like;
        return this;
    }

    public Boolean getLike() {
        return like;
    }
}
