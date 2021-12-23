package com.gyw.bean;

import java.io.Serializable;
import java.sql.Date;

public class BuyProductComment implements Serializable {
    private Integer id;

    private Integer pid;

    private Integer uid;

    private String message;

    private Integer messagescore;

    private Integer isdelete;

    private Date createdate;

    private BuyUser user;//评论的用户

    @Override
    public String toString() {
        return "BuyProductComment{" +
                "id=" + id +
                ", pid=" + pid +
                ", uid=" + uid +
                ", message='" + message + '\'' +
                ", messagescore=" + messagescore +
                ", isdelete=" + isdelete +
                ", createdate=" + createdate +
                ", user=" + user +
                '}';
    }

    public BuyUser getUser() {
        return user;
    }

    public void setUser(BuyUser user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message == null ? null : message.trim();
    }

    public Integer getMessagescore() {
        return messagescore;
    }

    public void setMessagescore(Integer messagescore) {
        this.messagescore = messagescore;
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}