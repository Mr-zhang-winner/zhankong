package com.ruoyi.xkd.domain.dto;

import java.util.Date;

public class WebSocketMessage {

    private String type;

    private Date timestamp;

    private Object data;

    public WebSocketMessage() {
    }

    public WebSocketMessage(String type, Object data) {
        this.type = type;
        this.timestamp = new Date();
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
