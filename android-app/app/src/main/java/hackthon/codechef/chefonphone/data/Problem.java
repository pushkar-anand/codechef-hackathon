package hackthon.codechef.chefonphone.data;

@SuppressWarnings("unused")
public class Problem {
    private String problemCode;
    private String problemName;
    private String supportedLanguage;
    private String sourceSizeLimit;
    private String challengeType;
    private String maxTimeLimit;
    private Double problemAccuracy;
    private String successfulSubmissions;
    private String body;
    private String tags;
    private String problemContestCode;
    private String viewStart;
    private String submitStart;
    private String visibleStart;
    private String end;
    private String dateAdded;
    private String author;

    public Problem() {
        dateAdded = author = null;
    }

    public String getProblemCode() {
        return problemCode;
    }

    public void setProblemCode(String problemCode) {
        this.problemCode = problemCode;
    }

    public String getProblemName() {
        return problemName;
    }

    public void setProblemName(String problemName) {
        this.problemName = problemName;
    }

    public String getSupportedLanguage() {
        return supportedLanguage;
    }

    public void setSupportedLanguage(String supportedLanguage) {
        this.supportedLanguage = supportedLanguage;
    }

    public String getSourceSizeLimit() {
        return sourceSizeLimit;
    }

    public void setSourceSizeLimit(String sourceSizeLimit) {
        this.sourceSizeLimit = sourceSizeLimit;
    }

    public String getChallengeType() {
        return challengeType;
    }

    public void setChallengeType(String challengeType) {
        this.challengeType = challengeType;
    }

    public String getMaxTimeLimit() {
        return maxTimeLimit;
    }

    public void setMaxTimeLimit(String maxTimeLimit) {
        this.maxTimeLimit = maxTimeLimit;
    }

    public String getSuccessfulSubmissions() {
        return successfulSubmissions;
    }

    public void setSuccessfulSubmissions(String successfulSubmissions) {
        this.successfulSubmissions = successfulSubmissions;
    }

    public Double getProblemAccuracy() {
        return problemAccuracy;
    }

    public void setProblemAccuracy(Double problemAccuracy) {
        this.problemAccuracy = problemAccuracy;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getProblemContestCode() {
        return problemContestCode;
    }

    public void setProblemContestCode(String problemContestCode) {
        this.problemContestCode = problemContestCode;
    }

    public String getViewStart() {
        return viewStart;
    }

    public void setViewStart(String viewStart) {
        this.viewStart = viewStart;
    }

    public String getSubmitStart() {
        return submitStart;
    }

    public void setSubmitStart(String submitStart) {
        this.submitStart = submitStart;
    }

    public String getVisibleStart() {
        return visibleStart;
    }

    public void setVisibleStart(String visibleStart) {
        this.visibleStart = visibleStart;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
