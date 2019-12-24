package com.tiptoptips.xl.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Share {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("accessLevel")
    @Expose
    private Integer accessLevel;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
    }
}
