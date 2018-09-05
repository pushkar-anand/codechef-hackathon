package hackthon.codechef.chefonphone.data;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Contest {

    private String contestCode;
    private String contestName;
    private String contestStartDate;
    private String contestEndDate;
    private String contestType;
    private String contestUrl;
    private String contestBanner;
    private ArrayList<Problem> contestProblemsList;
    private String contestFreezingTime;
    private String contestAnnouncements;

    public String getContestCode() {
        return contestCode;
    }

    public void setContestCode(String contestCode) {
        this.contestCode = contestCode;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getContestStartDate() {
        return contestStartDate;
    }

    public void setContestStartDate(String contestStartDate) {
        this.contestStartDate = contestStartDate;
    }

    public String getContestEndDate() {
        return contestEndDate;
    }

    public void setContestEndDate(String contestEndDate) {
        this.contestEndDate = contestEndDate;
    }

    public String getContestType() {
        return contestType;
    }

    public void setContestType(String contestType) {
        this.contestType = contestType;
    }

    public String getContestUrl() {
        return contestUrl;
    }

    public void setContestUrl(String contestUrl) {
        this.contestUrl = contestUrl;
    }

    public String getContestBanner() {
        return contestBanner;
    }

    public void setContestBanner(String contestBanner) {
        this.contestBanner = contestBanner;
    }

    public ArrayList<Problem> getContestProblemsList() {
        return contestProblemsList;
    }

    public void setContestProblemsList(ArrayList<Problem> contestProblemsList) {
        this.contestProblemsList = contestProblemsList;
    }

    public String getContestFreezingTime() {
        return contestFreezingTime;
    }

    public void setContestFreezingTime(String contestFreezingTime) {
        this.contestFreezingTime = contestFreezingTime;
    }

    public String getContestAnnouncements() {
        return contestAnnouncements;
    }

    public void setContestAnnouncements(String contestAnnouncements) {
        this.contestAnnouncements = contestAnnouncements;
    }
}
