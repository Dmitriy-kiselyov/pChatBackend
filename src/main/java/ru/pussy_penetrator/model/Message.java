package ru.pussy_penetrator.model;

import java.util.Date;

public class Message {
    private long time;
    private String text;
    private String target;

    public Message() {
        time = new Date().getTime();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target.toLowerCase().trim();
    }

    public long getTime() {
        return time;
    }
}
