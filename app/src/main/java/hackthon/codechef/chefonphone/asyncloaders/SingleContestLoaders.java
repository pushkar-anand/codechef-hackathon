package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;

import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.data.Contest;
import hackthon.codechef.chefonphone.utils.Helpers;
import hackthon.codechef.chefonphone.utils.Internet;

public class SingleContestLoaders extends AsyncTaskLoader<Contest> {
    public SingleContestLoaders(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public Contest loadInBackground() {
        try {
            String build_url = Helpers.buildUrl(getContext(), Urls.SINGLE_CONTEST_URL);


            String result = Internet.getHTTPSRequestResponse(build_url, "GET", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
