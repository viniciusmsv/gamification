package com.stefanini.gamification.validator;

import com.stefanini.gamification.validator.rule.CommitRule;
import org.gitlab4j.api.models.Commit;

public class CommitValidator extends CommitRule {

    public static boolean valid(Commit commit){
        return CommitRule.maxCommentLength(commit);
    }
}
