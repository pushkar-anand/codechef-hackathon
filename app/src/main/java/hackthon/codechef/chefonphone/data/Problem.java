package hackthon.codechef.chefonphone.data;

@SuppressWarnings("unused")
public class Problem {
    private String problemCode;
    private String problemName;
    private String supportedLanguage;
    private String sourceSizeLimit;
    private String challengeType;
    private String maxTimeLimit;
    private String successfulSubmissions;
    private String body;
    private String tags;

    public Problem() {
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
}
