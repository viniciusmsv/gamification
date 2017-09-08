package com.stefanini.gamification.controller;

import com.stefanini.gamification.service.GitLabService;
import org.gitlab4j.api.GitLabApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/gitlab")
public class GitLabController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    GitLabService gitLabService;

    @RequestMapping(value = "/total-commits/period", method = RequestMethod.GET)
    public ResponseEntity totalCommitsWithPeriods(@RequestParam(value = "start", required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                       @RequestParam(value = "end", required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        try {
            return ResponseEntity.ok(gitLabService.totalCommits(start, end));
        } catch (GitLabApiException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/total-commits", method = RequestMethod.GET)
    public ResponseEntity totalCommits() {
        try {
            return ResponseEntity.ok(gitLabService.totalCommits(null, null));
        } catch (GitLabApiException e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/total-commits/{start}", method = RequestMethod.GET)
    public ResponseEntity totalCommits(@PathVariable(value = "start")
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start) {
        try {
            return ResponseEntity.ok(gitLabService.totalCommits(start, null));
        } catch (GitLabApiException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/total-commits/{start}/{end}", method = RequestMethod.GET)
    public ResponseEntity totalCommits(@PathVariable(value = "start")
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
                                       @PathVariable(value = "end", required = false)
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {
        try {
            return ResponseEntity.ok(gitLabService.totalCommits(start, end));
        } catch (GitLabApiException e) {
            return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
        }
    }
}
