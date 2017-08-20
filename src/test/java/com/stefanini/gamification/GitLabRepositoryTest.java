package com.stefanini.gamification;

import com.stefanini.gamification.repository.GitLabRepository;
import com.stefanini.gamification.service.GitLabService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitLabRepositoryTest {

    @Autowired
    private GitLabRepository gitLabRepository;

    @Autowired
    private GitLabService gitLabService;

    @Test
    public void searchTest() throws Exception {
        gitLabRepository.searchAll();
    }

    @Test
    public void loadUserCommitsTest() throws Exception {
        gitLabRepository.loadUserCommits(gitLabService.totalCommits(null, null), LocalDate.now());
    }
}
