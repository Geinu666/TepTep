package WebKit.Bean;

import java.util.List;

public class LoginBean {
    private int status;
    private String msg;
    private LoginData data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public LoginData getData() {
        return data;
    }
}
