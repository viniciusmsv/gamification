package com.stefanini.gamification;

import com.stefanini.gamification.repository.GitLabRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GitLabRepositoryTest {

    @Autowired
    private GitLabRepository gitLabRepository;

    @Test
    public void contextLoads() throws Exception {
        gitLabRepository.search();
    }
}
