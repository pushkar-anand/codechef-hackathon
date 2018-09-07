package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.data.Contest;
import hackthon.codechef.chefonphone.utils.Helpers;
import hackthon.codechef.chefonphone.utils.Internet;

public class ContestLongListLoader extends AsyncTaskLoader<Pair<ArrayList<Contest>, ArrayList<Contest>>> {


    public ContestLongListLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public Pair<ArrayList<Contest>, ArrayList<Contest>> loadInBackground() {

        String url;
        ArrayList<Contest> presentList = new ArrayList<>();
        ArrayList<Contest> futureList = new ArrayList<>();

        url = Helpers.buildUrl(getContext(), Urls.CONTEST_URL_LONG);


        try {
            String result = Internet.getHTTPSGetRequestResponse(url);
            Log.d(getClass().getSimpleName(), result);

            JSONObject rootObject = new JSONObject(result);


            return Pair.create(presentList, futureList);

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
