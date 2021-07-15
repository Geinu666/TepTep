package WebKit.Bean;

import com.example.teptep.entity.Comment;

import java.util.List;

/**
 * 获取评论列表的实体类
 */
public class AllCommentBean {
    private Integer status;
    private String msg;
    private List<Comment> data;

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setData(List<Comment> data) {
        this.data = data;
    }

    public List<Comment> getData() {
        return data;
    }
}
