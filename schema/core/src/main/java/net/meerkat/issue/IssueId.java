package net.meerkat.issue;

/**
 *
 * @author ollie
 */
public interface IssueId extends HasIssueId {

    @Override
    @Deprecated
    default IssueId issueId() {
        return this;
    }

}
