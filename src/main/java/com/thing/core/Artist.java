package com.thing.core;

public class Artist implements Comparable<Artist> {
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String bio;

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Artist(String nickname, String firstName, String lastName, String bio) {
        this.nickname = nickname;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
    }

    public Artist(String nickname, String firstName, String lastName) {
        this(nickname, firstName, lastName, "");
    }

    @Override
    public int compareTo(Artist o) {
        return getNickname().compareTo(o.getNickname());
    }
}
