package com.stefanini.gamification.repository;

import com.stefanini.gamification.controller.model.redis.gitlab.UserCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Map;

@Repository
public class GitLabRepository {

    @Autowired
    private StringRedisTemplate template;

    public void loadUserCommits(Map<String, Long> userCommits, LocalDate date){
        for (String user : userCommits.keySet()) {
            UserCommit userCommit = new UserCommit(user,userCommits.get(user),date);
            BoundHashOperations<String, String, String> ops = this.template.boundHashOps(userCommit.getKey());
            ops.put(user, userCommits.get(user).toString());
        }
    }

    public void searchAll() throws Exception {
        String key = "gitlab:*";
        BoundHashOperations<String, String,String> ops = this.template.boundHashOps(key);
        for (String s : ops.values()) {
            System.out.println("Found key " + key + ", value=" + s);
        }
    }
}
