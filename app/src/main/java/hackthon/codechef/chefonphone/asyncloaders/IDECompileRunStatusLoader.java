package hackthon.codechef.chefonphone.asyncloaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import hackthon.codechef.chefonphone.constants.Urls;
import hackthon.codechef.chefonphone.data.CompileRunOutput;
import hackthon.codechef.chefonphone.utils.Internet;
import hackthon.codechef.chefonphone.utils.UrlBuilder;

public class IDECompileRunStatusLoader extends AsyncTaskLoader<CompileRunOutput> {

    private final String status_code;

    public IDECompileRunStatusLoader(@NonNull Context context, String status_code) {
        super(context);
        this.status_code = status_code;
    }

    @Nullable
    @Override
    public CompileRunOutput loadInBackground() {

        String url = UrlBuilder.buildStatusUrl(getContext(), Urls.IDE_STATUS_URL, status_code);
        try {
            String result = Internet.getHTTPSGetRequestResponse(url);
            Log.d(getClass().getSimpleName(), result);

            // parse the response at https://jsoneditoronline.org/?id=df284ecc1caa495ca4ffd768604f5b57

            JSONObject compileRunStatusObj = new JSONObject(result);
            CompileRunOutput compileRunOutput = new CompileRunOutput();

            compileRunOutput.setLangName(compileRunStatusObj.getString("langName"));
            compileRunOutput.setLangVersion(compileRunStatusObj.getString("langVersion"));
            compileRunOutput.setTime(compileRunStatusObj.getString("time"));
            compileRunOutput.setDate(compileRunStatusObj.getString("date"));
            compileRunOutput.setStatus(compileRunStatusObj.getString("status"));
            compileRunOutput.setMemory(compileRunStatusObj.getString("memory"));
            compileRunOutput.setSignal(compileRunStatusObj.getString("signal"));
            compileRunOutput.setInput(compileRunStatusObj.getString("input"));
            compileRunOutput.setOutput(compileRunStatusObj.getString("output"));
            compileRunOutput.setStderr(compileRunStatusObj.getString("stderr"));
            compileRunOutput.setCmpinfo(compileRunStatusObj.getString("cmpinfo"));

            return compileRunOutput;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
