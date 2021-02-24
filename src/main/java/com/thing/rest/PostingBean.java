package com.thing.rest;

public class PostingBean {
    private Ad posting;
    private PostingBean() {
    }
    public static PostingBean getInstance() {
        return PostingBeanHolder.INSTANCE;
    }
    private static class PostingBeanHolder {
        private static final PostingBean INSTANCE = new PostingBean();
    }
    public Ad getPosting(){
        return posting;
    }
    public void setPosting(Ad p){
        this.posting = p;
    }
}
