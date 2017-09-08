package com.stefanini.gamification;

import com.stefanini.gamification.controller.model.redis.gitlab.UserCommit;
import com.stefanini.gamification.repository.GitLabRepository;
import com.stefanini.gamification.service.GitLabService;
import org.apache.catalina.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitLabRepositoryTest {

    @Autowired
    private GitLabRepository gitLabRepository;

    @Autowired
    private GitLabService gitLabService;

    @Test
    public void loadUserCommitsTest() throws Exception {
        Map<String, UserCommit> userCommits = gitLabService.totalCommitsUser(null, null);
        gitLabRepository.loadUserCommits(userCommits, LocalDate.now());
    }

    @Test
    public void loadUserCommitsMapTest() throws Exception {
        Map<String, Long> userCommits = gitLabService.totalCommits(null, null);
        gitLabRepository.loadUserCommitsMap(userCommits, LocalDate.now());
    }


    @Test
    public void searchTest() throws Exception {
        gitLabRepository.searchAll();
    }

    @Test
    public void searchPerFilterTest() throws Exception {
        gitLabRepository.searchByFilter("gitlab:commits:2017-07");
    }
}
