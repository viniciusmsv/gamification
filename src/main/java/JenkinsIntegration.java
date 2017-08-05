import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.helper.Range;
import com.offbytwo.jenkins.model.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;


public class JenkinsIntegration {

    public static void main(String[] args) throws URISyntaxException, IOException {
        JenkinsServer jenkins = new JenkinsServer(new URI("http://ci.jenkins-ci.org/"), "admin", "password");
        Map<String, Job> jobs = jenkins.getJobs();
        for (Map.Entry<String, Job> jobEntry : jobs.entrySet()) {
            jobEntry.getValue().details().getAllBuilds();
            for (Build build : jobEntry.getValue().details().getAllBuilds()) {
                BuildResult result = build.details().getResult();
                System.out.println(result);

                BuildChangeSet buildChangeSet = build.details().getChangeSet();

                List<BuildChangeSetAuthor> list = build.details().getCulprits();
                for (BuildChangeSetAuthor buildChangeSetAuthor : list) {
                    System.out.println(buildChangeSetAuthor.getFullName());
                }
            }
        }
    }

}
