package com.gitHubClient.client;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryId;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GitHubIssuesClient {

    private GitHubIssuesClient() {}

    private static List<Issue> getIssuesUsingThirdParty(String repoId) throws IOException {
        RepositoryService svc = new RepositoryService();
        RepositoryId repoObjId = RepositoryId.createFromId(repoId);
        Repository repo = svc.getRepository(repoObjId.getOwner(), repoObjId.getName());

        IssueService issueSvc = new IssueService();
        return issueSvc.getIssues(repo, null);
    }

    /**
     *
     * @param repoIds an array of repoIds as "owner/repo"
     * @return an accumulated list of all issues of all the repos
     */
    public static List<com.gitHubClient.issue.Issue> getIssues(String... repoIds){
        List<com.gitHubClient.issue.Issue> issues = new ArrayList<>();
        if (repoIds == null) return issues;

        for (String repo:repoIds){
            try {
                List<Issue> list = getIssuesUsingThirdParty(repo);
                for (Issue x: list){
                    issues.add(
                            new com.gitHubClient.issue.Issue(
                                    x.getId(),
                                    x.getState(),
                                    x.getTitle(),
                                    repo,
                                    x.getCreatedAt())
                    );
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return issues;
    }

}
