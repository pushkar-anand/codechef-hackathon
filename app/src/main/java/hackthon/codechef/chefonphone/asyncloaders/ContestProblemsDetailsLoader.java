package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.data.Problem;
import hackthon.codechef.chefonphone.utils.Internet;
import hackthon.codechef.chefonphone.utils.UrlBuilder;

public class ContestProblemsDetailsLoader extends AsyncTaskLoader<Problem> {

    private String contest_code, problem_code;

    public ContestProblemsDetailsLoader(@NonNull Context context, String contest_code, String problem_code) {
        super(context);
        this.contest_code = contest_code;
        this.problem_code = problem_code;
    }

    @Nullable
    @Override
    public Problem loadInBackground() {

        String url = UrlBuilder.buildProblemDetailsUrl(getContext(), Urls.CONTEST_PROBLEM_DETAILS_LOADER, contest_code, problem_code);
        try {
            String result = Internet.getHTTPSGetRequestResponse(url);
            Log.d(getClass().getSimpleName(), result);

            //json FORMAT AT: https://jsoneditoronline.org/?id=d5bbbfc412ab432bad98dd8e4bda3c3f

            JSONObject problemObj = new JSONObject(result);
            Problem contestProblem = new Problem();

            contestProblem.setProblemName(problemObj.getString("problemName"));
            contestProblem.setProblemCode(problemObj.getString("problemCode"));
            if (problemObj.has("dateAdded")) {
                contestProblem.setDateAdded(problemObj.getString("dateAdded"));
            }
            contestProblem.setSourceSizeLimit(problemObj.getString("sourceSizeLimit"));
            contestProblem.setMaxTimeLimit(problemObj.getString("maxTimeLimit"));
            contestProblem.setChallengeType(problemObj.getString("challengeType"));
            contestProblem.setAuthor(problemObj.getString("author"));
            contestProblem.setSuccessfulSubmissions(problemObj.getString("successfulSubmissions"));
            contestProblem.setBody(problemObj.getString("body"));

            return contestProblem;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }


        return null;
    }


}
