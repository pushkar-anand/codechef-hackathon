package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

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

        if (type.equals("short")) {
            url = Helpers.buildUrl(getContext(), Urls.CONTEST_URL_SHORT_LIST);
        } else {
            url = Helpers.buildUrl(getContext(), Urls.CONTEST_URL_LONG);
        }

        try {
            String result = Internet.getHTTPSGetRequestResponse(url);

            //TODO parse the json result

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
