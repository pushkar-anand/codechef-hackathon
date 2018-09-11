package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.data.Contest;
import hackthon.codechef.chefonphone.utils.Internet;
import hackthon.codechef.chefonphone.utils.UrlBuilder;

public class ContestLongListLoader extends AsyncTaskLoader<Pair<ArrayList<Contest>, ArrayList<Contest>>> {


    public ContestLongListLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public Pair<ArrayList<Contest>, ArrayList<Contest>> loadInBackground() {

        String url;
        ArrayList<Contest> presentList = new ArrayList<>();
        ArrayList<Contest> futureList = new ArrayList<>();

        url = UrlBuilder.buildUrl(getContext(), Urls.CONTEST_URL_LONG);


        try {
            String result = Internet.getHTTPSGetRequestResponse(url);
            Log.d(getClass().getSimpleName(), result);

            JSONObject rootObject = new JSONObject(result);

            JSONArray presentJSONArray = rootObject.getJSONArray("present");

            for (int i = 0; i < presentJSONArray.length(); i++) {
                JSONObject contestObj = presentJSONArray.getJSONObject(i);

                Contest contest = new Contest();
                contest.setContestName(contestObj.getString("name"));
                contest.setContestCode(contestObj.getString("code"));
                contest.setContestStartDate(contestObj.getString("startDate"));
                contest.setContestEndDate(contestObj.getString("endDate"));

                presentList.add(contest);
            }

            JSONArray futureJSONArray = rootObject.getJSONArray("future");

            for (int i = 0; i < futureJSONArray.length(); i++) {
                JSONObject contestObj = futureJSONArray.getJSONObject(i);

                Contest contest = new Contest();
                contest.setContestName(contestObj.getString("name"));
                contest.setContestCode(contestObj.getString("code"));
                contest.setContestStartDate(contestObj.getString("startDate"));
                contest.setContestEndDate(contestObj.getString("endDate"));

                futureList.add(contest);
            }

            return Pair.create(presentList, futureList);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
