package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.data.Contest;
import hackthon.codechef.chefonphone.utils.Helpers;
import hackthon.codechef.chefonphone.utils.Internet;

public class ContestListLoaderShort extends AsyncTaskLoader<ArrayList<Contest>> {
    public ContestListLoaderShort(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public ArrayList<Contest> loadInBackground() {
        try {
            String build_url = Helpers.buildUrl(getContext(), Urls.SINGLE_CONTEST_URL);


            String result = Internet.getHTTPSGetRequestResponse(build_url);

            //TODO parse the json result

            Log.d(getClass().getSimpleName(), result);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
