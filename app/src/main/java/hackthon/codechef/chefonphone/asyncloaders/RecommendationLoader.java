package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;

import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.data.Problem;
import hackthon.codechef.chefonphone.utils.Internet;
import hackthon.codechef.chefonphone.utils.UrlBuilder;

public class RecommendationLoader extends AsyncTaskLoader<Problem> {

    public RecommendationLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public Problem loadInBackground() {

        String url = UrlBuilder.buildUrl(getContext(), Urls.RECOMMEND_URL);

        try {
            String result = Internet.getHTTPSGetRequestResponse(url);

            //TODO parse the json

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
