package com.stefanini.gamification.service;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JenkinsService {

    //https://github.com/jenkinsci/java-client-api
    public Map<String, String> builds() throws URISyntaxException, IOException {
        JenkinsServer jenkins = new JenkinsServer(new URI("http://10.2.106.79:8081/"), "stefanini", "stefanini");
        Map<String, String> builds = new HashMap<>();
        Map<String, Job> jobs = jenkins.getJobs();
        for (Map.Entry<String, Job> jobEntry : jobs.entrySet()) {
            jobEntry.getValue().details().getAllBuilds();

            for (Build build : jobEntry.getValue().details().getAllBuilds()) {
                BuildResult result = build.details().getResult();
                List<BuildCause> list = build.details().getCauses();
                for (BuildCause buildCause : list) {
                    if(buildCause.getShortDescription().contains("Iniciado")){
                        builds.put(build.details().getFullDisplayName() + " - " + result, buildCause.getUserId());
                    }
                }
            }
        }
        return builds;
    }
}
