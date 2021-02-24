package com.thing.rest;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Ad {
    private String title;
    private String text;
    public Ad() {
    }
    public Ad(String title, String text) {
        this.title = title;
        this.text = text;
    }
    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }
    public String getText() { return this.text; }
    public void setText(String text) { this.text = text; }
    @Override public String toString() { return String.format("%s\n%s", this.title, this.text); }
}
