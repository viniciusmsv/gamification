package com.stefanini.gamification.service;

import com.stefanini.gamification.controller.model.redis.gitlab.UserCommit;
import com.stefanini.gamification.validator.CommitValidator;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.Project;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class GitLabService {

    //https://github.com/gmessner/gitlab4j-api\
    public Map<String, Long> totalCommits(LocalDate start, LocalDate end) throws GitLabApiException {
        GitLabApi gitLabApi = new GitLabApi(GitLabApi.ApiVersion.V3, "https://gitlabcorp.stefanini.com.br/", "7YsYYAHrzmhY1cvQCbsy");
        gitLabApi.setIgnoreCerificateErrors(true);
        Group group = gitLabApi.getGroupApi().getGroup(31);
        List<Project> projects = group.getProjects();
        Map<String, Long> userCommits = totalCommits(start, end, gitLabApi, projects);
        return userCommits;
    }

    public Map<String, UserCommit> totalCommitsUser(LocalDate start, LocalDate end) throws GitLabApiException {
        GitLabApi gitLabApi = new GitLabApi(GitLabApi.ApiVersion.V3, "https://gitlabcorp.stefanini.com.br/", "7YsYYAHrzmhY1cvQCbsy");
        gitLabApi.setIgnoreCerificateErrors(true);
        Group group = gitLabApi.getGroupApi().getGroup(31);
        List<Project> projects = group.getProjects();
        Map<String, UserCommit> userCommits = totalCommitsUser(start, end, gitLabApi, projects);
        return userCommits;
    }

    private Map<String, Long> totalCommits(LocalDate start, LocalDate end, GitLabApi gitLabApi, List<Project> projects) throws GitLabApiException {
        Map<String, Long> userCommits = new HashMap<>();

        Date dateStart = start == null ? null : Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateEnd = end == null ? null : Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());

        for (Project project : projects) {
            List<Branch> branches = gitLabApi.getRepositoryApi().getBranches(project.getId());
            for (Branch branch : branches) {
                List<Commit> allCommits = gitLabApi.getCommitsApi().getCommits(project.getId(), branch.getName(), dateStart
                        , dateEnd);
                for (Commit commit : allCommits) {
                    if(CommitValidator.valid(commit)){
                        Long count = userCommits.get(commit.getAuthorName());
                        if (count == null) {
                            count = 0L;
                        } else {
                            count = ++count;
                        }
                        userCommits.put(commit.getAuthorName(), count);
                    }
                }
            }
        }
        return userCommits;
    }

    private Map<String, UserCommit> totalCommitsUser(LocalDate start, LocalDate end, GitLabApi gitLabApi, List<Project> projects) throws GitLabApiException {
        Map<String, UserCommit> userCommits = new HashMap<>();

        Date dateStart = start == null ? null : Date.from(start.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date dateEnd = end == null ? null : Date.from(end.atStartOfDay(ZoneId.systemDefault()).toInstant());

        for (Project project : projects) {
            List<Branch> branches = gitLabApi.getRepositoryApi().getBranches(project.getId());
            for (Branch branch : branches) {
                List<Commit> allCommits = gitLabApi.getCommitsApi().getCommits(project.getId(), branch.getName(), dateStart
                        , dateEnd);
                for (Commit commit : allCommits) {
                    if(CommitValidator.valid(commit)){
                        UserCommit userCommit = userCommits.get(commit.getAuthorName());
                        if(userCommit == null){
                            userCommit = new UserCommit(commit.getAuthorEmail(), null, commit.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                        }
                        Long count = userCommit.getCommits();
                        if (count == null) {
                            count = 0L;
                        } else {
                            count = ++count;
                        }
                        userCommit.setCommits(count);
                        userCommits.put(commit.getAuthorName(), userCommit);
                    }
                }
            }
        }
        return userCommits;
    }

}
