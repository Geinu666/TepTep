package WebKit.Bean;

public class Key {
    private Integer status;
    private String msg;
    private String data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}
