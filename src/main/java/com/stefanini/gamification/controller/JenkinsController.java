package com.stefanini.gamification.controller;

import com.stefanini.gamification.service.JenkinsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

@Controller
@RequestMapping("/jenkins")
public class JenkinsController {

    @Autowired
    private JenkinsService jenkinsService;

    @RequestMapping("builds")
    public ResponseEntity builds(){
        try {
            Map<String, String> builds = jenkinsService.builds();
            return ResponseEntity.ok(builds);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
