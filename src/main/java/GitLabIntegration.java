import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.Pager;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitLabIntegration {


    public static void main(String[] args) throws GitLabApiException {
        GitLabApi gitLabApi = new GitLabApi(GitLabApi.ApiVersion.V3, "https://gitlabcorp.stefanini.com.br/", "zQRcytA7mBoz81Zi7gqi");
        Group group = gitLabApi.getGroupApi().getGroup(31);
        List<Project> projects = group.getProjects();

        Map<String, Long> userCommits = new HashMap<String, Long>();

        for (Project project : projects) {
            List<Branch> branches = gitLabApi.getRepositoryApi().getBranches(project.getId());
            for (Branch branch : branches) {
                List<Commit> allCommits = gitLabApi.getCommitsApi().getCommits(project.getId(), branch.getName(), null, null);
                for (Commit commit : allCommits) {
                    Long count = userCommits.get(commit.getAuthorName());
                    if(count == null){
                        count = 0L;
                    } else {
                        count = ++count;
                    }
                    userCommits.put(commit.getAuthorName(), count);
                }
            }
        }

        for (Map.Entry<String, Long> userCommit : userCommits.entrySet()) {
            System.out.println(userCommit.getKey() + " - " + userCommit.getValue());
        }

    }

}
