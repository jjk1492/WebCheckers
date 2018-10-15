package com.webcheckers.model;

public class Message {

    public enum Type{
        INFO,
        ERROR
    }

    private String text;
    private Type type;

    public Message(String text, Type type) {
        this.text = text;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public Type getType() {
        return type;
    }
}
