package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;

import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.utils.Helpers;
import hackthon.codechef.chefonphone.utils.Internet;

public class IDECompileRunStatusLoader extends AsyncTaskLoader {

    private String status_code;

    public IDECompileRunStatusLoader(@NonNull Context context, String status_code) {
        super(context);
        this.status_code = status_code;
    }

    @Nullable
    @Override
    public Object loadInBackground() {

        String url = Helpers.buildStatusUrl(getContext(), Urls.IDE_STATUS_URL, status_code);
        try {
            String result = Internet.getHTTPSGetRequestResponse(url);
            Log.d(getClass().getSimpleName(), result);

            //TODO parse the response.

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
