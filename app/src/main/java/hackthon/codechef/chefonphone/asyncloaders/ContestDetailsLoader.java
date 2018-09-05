package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.io.IOException;

import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.utils.Helpers;
import hackthon.codechef.chefonphone.utils.Internet;

public class ContestDetailsLoader extends AsyncTaskLoader {

    private String contest_code;

    public ContestDetailsLoader(@NonNull Context context, String contest_code) {
        super(context);
        this.contest_code = contest_code;
    }

    @Nullable
    @Override
    public Object loadInBackground() {

        String url = Helpers.buildContestDetailsUrl(getContext(), Urls.CONTEST_DETAILS_URL, contest_code);
        try {
            String result = Internet.getHTTPSGetRequestResponse(url);

            //TODO parse json

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
