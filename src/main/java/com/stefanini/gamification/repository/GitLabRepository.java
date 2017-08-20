package com.stefanini.gamification.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
public class GitLabRepository {

    @Autowired
    private StringRedisTemplate template;

    public void search() throws Exception {
        ValueOperations<String, String> ops = this.template.opsForValue();
        String key = "spring.boot.redis.test";
        if (!this.template.hasKey(key)) {
            ops.set(key, "foo");
        }
        System.out.println("Found key " + key + ", value=" + ops.get(key));
    }
}
