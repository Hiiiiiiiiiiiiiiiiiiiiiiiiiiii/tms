package com.helloword.entity;

import java.util.Date;

public class LogCard {
    private Integer id;

    private Date logTime;

    private Integer cardNum;

    private Integer scenicid;

    private String spare;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public Integer getCardNum() {
        return cardNum;
    }

    public void setCardNum(Integer cardNum) {
        this.cardNum = cardNum;
    }

    public Integer getScenicid() {
        return scenicid;
    }

    public void setScenicid(Integer scenicid) {
        this.scenicid = scenicid;
    }

    public String getSpare() {
        return spare;
    }

    public void setSpare(String spare) {
        this.spare = spare;
    }
}