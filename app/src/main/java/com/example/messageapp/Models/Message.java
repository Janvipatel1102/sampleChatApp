package com.example.messageapp.Models;

public class Message {

    private String message,messageId,senderid;
    private long timeStamp;
    private int feelling=-1;

    public Message() {

    }

    public Message(String message, String senderid, long timeStamp) {
        this.message = message;
        this.senderid = senderid;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getFeelling() {
        return feelling;
    }

    public void setFeelling(int feelling) {
        this.feelling = feelling;
    }
}
