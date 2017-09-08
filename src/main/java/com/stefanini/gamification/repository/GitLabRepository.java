package com.stefanini.gamification.repository;

import com.stefanini.gamification.controller.model.redis.gitlab.UserCommit;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.hash.DecoratingStringHashMapper;
import org.springframework.data.redis.hash.Jackson2HashMapper;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

@Repository
public class GitLabRepository {

    @Autowired
    private StringRedisTemplate template;

    private RedisTemplate redisTemplate;

    public void loadUserCommitsMap(Map<String, Long> userCommits, LocalDate date){
        for (String user : userCommits.keySet()) {
            UserCommit userCommit = new UserCommit(user,userCommits.get(user),date);
            BoundHashOperations<String, String, String> ops = this.template.boundHashOps("gitlab");
            ops.put(userCommit.getKey(), userCommits.get(user).toString());
        }
    }

    public void searchAll() throws Exception {
        String key = "gitlab";
        BoundHashOperations<String, String,String> ops = this.template.boundHashOps(key);
        for (String s : ops.keys()) {
            System.out.println("Found key " + s + ", value=" + ops.get(s));
        }
    }

    public void loadUserCommits(Map<String, UserCommit> userCommits, LocalDate date){
        for (String user : userCommits.keySet()) {
            UserCommit userCommit = userCommits.get(user);
            BoundHashOperations<String, String, String> ops = this.template.boundHashOps(userCommit.getKey());
            ops.put(userCommit.getField(), userCommits.get(user).getCommits().toString());
        }
    }

//    public void loadUserCommits(Map<String, UserCommit> userCommits, LocalDate date){
//        Jackson2HashMapper mapper = new Jackson2HashMapper(true);
//        template.setValueSerializer(new Jackson2JsonRedisSerializer(UserCommit.class));
//        template.setHashValueSerializer(new Jackson2JsonRedisSerializer(UserCommit.class));
//        for (String user : userCommits.keySet()) {
//            UserCommit userCommit = userCommits.get(user);
//            BoundHashOperations<String, String, UserCommit> ops = template.boundHashOps(userCommit.getKey());
//            ops.put(userCommit.getField(), userCommit);
//        }
//    }

    public void searchByFilter(String filter) {
        BoundHashOperations<String, String, String> ops = this.template.boundHashOps(filter);
        Jackson2HashMapper mapper = new Jackson2HashMapper(true);
        Cursor<Map.Entry<String, String>> cursor = ops.scan(ScanOptions.scanOptions().match("*ta*").build());
        while (cursor.hasNext()) {
            Map.Entry entry = cursor.next();
            System.out.println("Found key " + entry.getKey() + ", value=" + entry.getValue());
        }
//        for (String s : ops.keys()) {
//            System.out.println("Found key " + s + ", value=" + ops.get(s));
//        }
    }

    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate();
        RedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }
}
