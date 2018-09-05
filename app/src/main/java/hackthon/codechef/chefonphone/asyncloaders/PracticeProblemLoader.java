package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.data.Problem;
import hackthon.codechef.chefonphone.utils.Helpers;
import hackthon.codechef.chefonphone.utils.Internet;

public class PracticeProblemLoader extends AsyncTaskLoader<ArrayList<Problem>> {

    private String level;
    private Integer start;

    public PracticeProblemLoader(@NonNull Context context, @NonNull String level, @Nullable Integer start) {
        super(context);
        this.level = level;

        this.start = start;
    }

    @Nullable
    @Override
    public ArrayList<Problem> loadInBackground() {

        String url = Helpers.buildUrl(getContext(), Urls.PRACTICE_BASE_URL + "/" + level);

        if (start != null) {
            url += "&offset=" + String.valueOf(start);
        }

        try {
            String result = Internet.getHTTPSGetRequestResponse(url);
            Log.d(getClass().getSimpleName(), result);

            //TODO PARSE RESULT

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
