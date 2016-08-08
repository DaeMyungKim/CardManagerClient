package com.cardmanager.kdml.cardmanager.DTO;

/**
 * Created by 김대명사무실 on 2016-07-29.
 */
public class ChatData {
    private String message;
    private String receiver;
    private String sender;
    private String datetime;

    public ChatData() { }

    public ChatData(String message, String receiver, String sender, String datetime) {
        this.message = message;
        this.receiver = receiver;
        this.sender = sender;
        this.datetime = datetime;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

