package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.data.Contest;
import hackthon.codechef.chefonphone.data.Problem;
import hackthon.codechef.chefonphone.utils.Helpers;
import hackthon.codechef.chefonphone.utils.Internet;

public class ContestDetailsLoader extends AsyncTaskLoader<Contest> {

    private String contest_code;

    public ContestDetailsLoader(@NonNull Context context, String contest_code) {
        super(context);
        this.contest_code = contest_code;
    }

    @Nullable
    @Override
    public Contest loadInBackground() {

        ArrayList<Problem> problemArrayList = new ArrayList<>();

        String url = Helpers.buildContestDetailsUrl(getContext(), Urls.CONTEST_DETAILS_URL, contest_code);
        try {
            String result = Internet.getHTTPSGetRequestResponse(url);
            Log.d(getClass().getSimpleName(), result);

            //TODO parse json
            //{"code":"XXX","name":"XXX","startDate":"dateTime","endDate":"dateTime","bannerFile":"link","freezingTime":0,"announcements":"some annoucments","problemsList":[{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"PHOTOCOM","contestCode":"SEPT18A","successfulSubmissions":3,"accuracy":6.1224489795918},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"MAGICHF","contestCode":"SEPT18A","successfulSubmissions":21,"accuracy":84},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"CHEFLST","contestCode":"SEPT18A","successfulSubmissions":0,"accuracy":0},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"XORIER","contestCode":"SEPT18A","successfulSubmissions":9,"accuracy":27.027027027027},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"CHEFZERO","contestCode":"SEPT18A","successfulSubmissions":7,"accuracy":69.230769230769},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"FCTR","contestCode":"SEPT18A","successfulSubmissions":1,"accuracy":1.9607843137255},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"TABGAME","contestCode":"SEPT18A","successfulSubmissions":8,"accuracy":17.021276595745},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"CHEFADV","contestCode":"SEPT18A","successfulSubmissions":10,"accuracy":66.666666666667},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"BSHUFFLE","contestCode":"SEPT18A","successfulSubmissions":33,"accuracy":11.458333333333},{"viewStart":"2018-09-07 15:00:00","submitStart":"2018-09-07 15:00:00","visibleStart":"2018-09-17 15:00:00","end":"2018-09-17 15:00:00","problemCode":"ANDSQR","contestCode":"SEPT18A","successfulSubmissions":7,"accuracy":14}]}

            Contest contest = new Contest();
            JSONObject contestObj = new JSONObject(result);

            contest.setContestCode(contestObj.getString("code"));
            contest.setContestName(contestObj.getString("name"));
            contest.setContestStartDate(contestObj.getString("startDate"));
            contest.setContestEndDate(contestObj.getString("endDate"));
            contest.setContestBanner(contestObj.getString("bannerFile"));
            contest.setContestFreezingTime(contestObj.getString("freezingTime"));
            contest.setContestAnnouncements(contestObj.getString("announcements"));

            JSONArray problemArray = contestObj.getJSONArray("problemsList");
            for(int i=0; i < problemArray.length() ; i++) {
                Problem problem = new Problem();
                JSONObject problemObject = problemArray.getJSONObject(i);

                problem.setProblemCode(problemObject.getString("problemCode"));
                problem.setSuccessfulSubmissions(problemObject.getString("successfulSubmissions"));
                problem.setProblemAccuracy(problemObject.getDouble("accuracy"));
                problem.setProblemContestCode(problemObject.getString("contestCode"));

                problemArrayList.add(problem);
            }

            contest.setContestProblemsList(problemArrayList);

            return contest;
            
        } catch (IOException |JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
