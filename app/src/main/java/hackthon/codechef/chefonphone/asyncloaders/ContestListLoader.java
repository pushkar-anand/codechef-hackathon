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
import hackthon.codechef.chefonphone.utils.Helpers;
import hackthon.codechef.chefonphone.utils.Internet;

public class ContestListLoader extends AsyncTaskLoader<ArrayList<Contest>> {

    private String type;

    public ContestListLoader(@NonNull Context context, String type) {
        super(context);
        this.type = type;
    }

    @Nullable
    @Override
    public ArrayList<Contest> loadInBackground() {

        String url;
        ArrayList<Contest> contestArrayList = new ArrayList<>();

        if (type.equals("short")) {
            url = Helpers.buildUrl(getContext(), Urls.CONTEST_URL_SHORT_LIST);
        } else {
            url = Helpers.buildUrl(getContext(), Urls.CONTEST_URL_LONG);
        }

        try {
            String result = Internet.getHTTPSGetRequestResponse(url);
            Log.d(getClass().getSimpleName(), result);

            JSONArray rootArray = new JSONArray(result);

            for (int i = 0; i < rootArray.length(); i++) {
                JSONObject contestObj = rootArray.getJSONObject(i);

                Contest contest = new Contest();
                contest.setContestName(contestObj.getString("name"));
                contest.setContestCode(contestObj.getString("code"));
                contest.setContestStartDate(contestObj.getString("startDate"));
                contest.setContestEndDate(contestObj.getString("endDate"));

                contestArrayList.add(contest);
            }
            return contestArrayList;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
