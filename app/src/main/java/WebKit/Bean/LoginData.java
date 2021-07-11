package WebKit.Bean;

public class LoginData {
    private String userId;
    private String nickname;
    private String username;
    private String avatar;
    private String gender;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "data{" +
                "userId='" + userId + '\'' +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender='" + gender + '\'' +
                "}";
    }
}
