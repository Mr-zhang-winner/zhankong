package com.ruoyi.xkd.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
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
}