package com.stefanini.gamification.validator.rule;

import org.gitlab4j.api.models.Commit;

public class CommitRule {
    protected static boolean maxCommentLength(Commit commit) {
        //return commit.getMessage().length() >= 50;
        return true;
    }
}
