package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;

import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.data.Problem;
import hackthon.codechef.chefonphone.utils.Internet;
import hackthon.codechef.chefonphone.utils.UrlBuilder;

public class ContestProblemsDetailsLoader extends AsyncTaskLoader<Problem> {

    private String contest, problem;

    public ContestProblemsDetailsLoader(@NonNull Context context, String contest, String problem) {
        super(context);
        this.contest = contest;
        this.problem = problem;
    }

    @Nullable
    @Override
    public Problem loadInBackground() {

        String url = UrlBuilder.buildProblemDetailsUrl(getContext(), Urls.CONTEST_PROBLEM_DETAILS_LOADER, contest, problem);
        try {
            String result = Internet.getHTTPSGetRequestResponse(url);
            Log.d(getClass().getSimpleName(), result);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }


}
