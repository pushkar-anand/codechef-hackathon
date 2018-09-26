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
import hackthon.codechef.chefonphone.data.Problem;
import hackthon.codechef.chefonphone.utils.Internet;
import hackthon.codechef.chefonphone.utils.UrlBuilder;

public class PracticeProblemLoader extends AsyncTaskLoader<ArrayList<Problem>> {

    private final String level;
    private final Integer start;

    public PracticeProblemLoader(@NonNull Context context, @NonNull String level, @Nullable Integer start) {
        super(context);
        this.level = level;

        this.start = start;
    }

    @Nullable
    @Override
    public ArrayList<Problem> loadInBackground() {

        ArrayList<Problem> problemArrayList = new ArrayList<>();

        String url;

        if (start != null) {

            url = UrlBuilder.buildUrl(getContext(), Urls.PRACTICE_BASE_URL + "/" + level, start);

        } else {

            url = UrlBuilder.buildUrl(getContext(), Urls.PRACTICE_BASE_URL + "/" + level);

        }

        try {
            String result = Internet.getHTTPSGetRequestResponse(url);
            Log.d(getClass().getSimpleName(), level + ": " + result);

            //[{"problemCode":"PCJ18C","problemName":"Chef and Polygon Cakes","successfulSubmissions":642,"accuracy":17.881989178047}]

            JSONArray problemArray = new JSONArray(result);
            for (int i = 0; i < problemArray.length(); i++) {
                Problem problem = new Problem();
                JSONObject problemObj = problemArray.getJSONObject(i);

                problem.setProblemCode(problemObj.getString("problemCode"));
                problem.setProblemName(problemObj.getString("problemName"));
                problem.setSuccessfulSubmissions(problemObj.getString("successfulSubmissions"));
                problem.setProblemAccuracy(problemObj.getDouble("accuracy"));

                problemArrayList.add(problem);
            }
            return problemArrayList;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
