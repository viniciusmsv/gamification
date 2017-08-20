package com.stefanini.gamification.controller.model.redis.gitlab;

import com.stefanini.gamification.controller.model.redis.Key;

import java.time.LocalDate;

public class UserCommit implements Key {

    public static final String PREFIX = "gitlab:";

    private String user;

    private Long commits;

    private LocalDate period;

    public UserCommit() {
    }

    public UserCommit(String user, Long commits, LocalDate period) {
        this.user = user;
        this.commits = commits;
        this.period = period;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Long getCommits() {
        return commits;
    }

    public void setCommits(Long commits) {
        this.commits = commits;
    }

    public LocalDate getPeriod() {
        return period;
    }

    public void setPeriod(LocalDate period) {
        this.period = period;
    }

    @Override
    public String getKey() {
        return PREFIX+period+user;
    }
}
