package com.stefanini.gamification.dto;

import java.io.Serializable;

public class GitLabCommitDTO implements Serializable{

    String user;

    Long commits;

    public GitLabCommitDTO() {
    }

    public GitLabCommitDTO(String user) {
        this.user = user;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GitLabCommitDTO that = (GitLabCommitDTO) o;

        return user != null ? user.equals(that.user) : that.user == null;
    }

    @Override
    public int hashCode() {
        return user != null ? user.hashCode() : 0;
    }
}
