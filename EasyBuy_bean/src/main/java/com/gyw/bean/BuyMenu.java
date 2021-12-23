package com.gyw.bean;

import java.io.Serializable;

public class BuyMenu implements Serializable {
    private Integer id;

    private String pername;

    private String perdesc;

    private String url;

    private Integer parentId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPername() {
        return pername;
    }

    public void setPername(String pername) {
        this.pername = pername == null ? null : pername.trim();
    }

    public String getPerdesc() {
        return perdesc;
    }

    public void setPerdesc(String perdesc) {
        this.perdesc = perdesc == null ? null : perdesc.trim();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}