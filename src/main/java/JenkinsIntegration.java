import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.helper.Range;
import com.offbytwo.jenkins.model.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;


public class JenkinsIntegration {

    //https://github.com/jenkinsci/java-client-api
    public static void main(String[] args) throws URISyntaxException, IOException {
        JenkinsServer jenkins = new JenkinsServer(new URI("http://10.2.106.79:8081/"), "stefanini", "stefanini");
        Map<String, Job> jobs = jenkins.getJobs();
        for (Map.Entry<String, Job> jobEntry : jobs.entrySet()) {
            jobEntry.getValue().details().getAllBuilds();

            for (Build build : jobEntry.getValue().details().getAllBuilds()) {
                BuildResult result = build.details().getResult();
                System.out.println(build.details().getFullDisplayName() + " - " + result);

                List<BuildCause> list = build.details().getCauses();
                for (BuildCause buildCause : list) {
                    if(buildCause.getShortDescription().contains("Iniciado")){
                        System.out.println(buildCause.getUserId());
                    }
                }
            }
        }
    }

}
