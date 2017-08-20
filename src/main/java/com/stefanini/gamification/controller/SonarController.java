package com.stefanini.gamification.controller;

import com.stefanini.gamification.service.SonarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/sonar")
public class SonarController {

    @Autowired
    private SonarService sonarService;

    @RequestMapping("get")
    public ResponseEntity get(){
        return ResponseEntity.ok(sonarService.get());
    }
}
