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

public class ContestDetailsLoader extends AsyncTaskLoader<Contest> {

    private String contest_code;

    public ContestDetailsLoader(@NonNull Context context, String contest_code) {
        super(context);
        this.contest_code = contest_code;
    }

    @Nullable
    @Override
    public Contest loadInBackground() {

        String url = Helpers.buildContestDetailsUrl(getContext(), Urls.CONTEST_DETAILS_URL, contest_code);
        try {
            String result = Internet.getHTTPSGetRequestResponse(url);
            Log.d(getClass().getSimpleName(), result);

            //TODO parse json

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
